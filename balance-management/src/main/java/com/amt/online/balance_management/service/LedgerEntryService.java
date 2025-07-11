package com.amt.online.balance_management.service;

import static com.amt.online.balance_management.utils.EntityOperations.safeCall;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.amt.online.balance_management.controller.member.dto.LedgerEntryForm;
import com.amt.online.balance_management.controller.member.dto.LedgerEntryListItem;
import com.amt.online.balance_management.controller.member.dto.LedgerEntrySearch;
import com.amt.online.balance_management.model.PageResult;
import com.amt.online.balance_management.model.entity.LedgerEntry;
import com.amt.online.balance_management.model.entity.LedgerEntryItem;
import com.amt.online.balance_management.model.entity.LedgerEntry_;
import com.amt.online.balance_management.model.entity.Member;
import com.amt.online.balance_management.model.entity.consts.BalanceType;
import com.amt.online.balance_management.model.entity.embeddables.LedgerEntryItemPk;
import com.amt.online.balance_management.model.entity.embeddables.LedgerEntryPk;
import com.amt.online.balance_management.model.repo.LedgerEntryItemRepo;
import com.amt.online.balance_management.model.repo.LedgerEntryRepo;
import com.amt.online.balance_management.model.repo.LedgerRepo;
import com.amt.online.balance_management.model.repo.MemberRepo;
import com.amt.online.balance_management.utils.Exception.AppBusinessException;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LedgerEntryService {
	
	private final MemberRepo memberRepo;
	private final LedgerRepo ledgerRepo;
	private final LedgerEntryItemRepo itemRepo;
	private final LedgerEntryRepo entryRepo;
	private final LedgerEntryIdGenerator idGenerator;
	
	public LedgerEntryForm findForEdit(String id) {
		var username = SecurityContextHolder.getContext()
					.getAuthentication().getName();
		
		var member = memberRepo.findOneByAccountUsername(username)
					.get();
		
		var entryPk = LedgerEntryPk.parse(member.getId(), id);
		
		return safeCall(entryRepo.findById(entryPk).map(LedgerEntryForm :: from)
				, "ledger entry", "entry id", id);
	}
	
	@Transactional
	public String save(LedgerEntryForm entryForm) {
		var username = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		var member = memberRepo.findOneByAccountUsername(username)
				.get();
		if(StringUtils.hasLength(entryForm.getId())) {
			return update(member, entryForm);
		}
		 
		return insert(member, entryForm);
	}

	
	private String insert(Member member, LedgerEntryForm form) {
		var entry = new LedgerEntry();
		entry.setId(idGenerator.next(member.getId(), LocalDate.now()));
		entry.setParticular(form.getParticular());
		entry.setLedger(ledgerRepo.findById(form.getLedgerId()).get());
		entry.setIssueAt(LocalDateTime.now());
		
		var lastAmount = Optional.ofNullable(member.getActivity().getBalance())
					.orElse(BigDecimal.ZERO);
		var amount = form.getItems().stream()
					.filter(a -> !a.isDeleted())
					.map(a -> a.getUnitPrice().multiply(BigDecimal.valueOf(a.getQuantity())))
					.reduce((a,b) -> a.add(b))
					.orElse(BigDecimal.ZERO);
		
		entry.setAmount(amount);
		entry.setLastAmount(lastAmount);
		
		// Insert Ledger Entry
		entry = entryRepo.save(entry);
		// Insert Ledger Entry Items
		for (var i = 0; i < form.getItems().size(); i++) {

			var item = form.getItems().get(i);

			if (!item.isDeleted()) {
				var entryItem = new LedgerEntryItem();
				var pk = new LedgerEntryItemPk();
				pk.setIssueDate(entry.getId().getIssueDate());
				pk.setMemberId(entry.getId().getMemberId());
				pk.setSeqNumber(entry.getId().getSeqNumber());
				pk.setItemNumber(i + 1);

				entryItem.setId(pk);

				entryItem.setEntry(entry);
				entryItem.setItem(item.getItemName());
				entryItem.setQuantity(item.getQuantity());
				entryItem.setUnitPrice(item.getUnitPrice());

				itemRepo.save(entryItem);
			}
		}
		// Update Member last Balance
		var balance = switch (entry.getLedger().getType()) {
		case Expenses -> entry.getLastAmount().subtract(amount);
		case Incomes -> entry.getLastAmount().add(amount);
		};
		
		member.getActivity().setBalance(balance);
		return entry.getId().getCode();
	}

	private String update(Member member, LedgerEntryForm form) {
		
		// Check Issue Date
		var entryId = LedgerEntryPk.parse(member.getId(), form.getId());
		if(!entryId.getIssueDate().equals(LocalDate.now())) {
			throw new AppBusinessException("You can only update entry on today.");
		}
		
		// Get Ledger Entry
		var entry = entryRepo.findById(entryId).get();
		// Get Ledger Entry Items
		
		for(var item:entry.getItems()) {
			// Delete All Items
			itemRepo.deleteById(item.getId());
		}		
		
		// INsert all Items
		var newItems = form.getItems().stream()
						.filter(a -> !a.isDeleted()).toList();
		
		for(var i = 0; i < newItems.size(); i++ ) {
			var item = newItems.get(i);
			var entryItem = new LedgerEntryItem();
			var pk = new LedgerEntryItemPk();
			pk.setIssueDate(entry.getId().getIssueDate());
			pk.setMemberId(entry.getId().getMemberId());
			pk.setSeqNumber(entry.getId().getSeqNumber());
			pk.setItemNumber(i + 1);

			entryItem.setId(pk);

			entryItem.setEntry(entry);
			entryItem.setItem(item.getItemName());
			entryItem.setQuantity(item.getQuantity());
			entryItem.setUnitPrice(item.getUnitPrice());

			itemRepo.save(entryItem);
		}
		
		// Update Ledger Entry Info
		entry.setParticular(form.getParticular());
		entry.setLedger(ledgerRepo.findById(form.getLedgerId()).get());
	
		var lastAmount = entry.getLastAmount();
		var amount = form.getItems().stream()
					.filter(a -> !a.isDeleted())
					.map(a -> a.getUnitPrice().multiply(BigDecimal.valueOf(a.getQuantity())))
					.reduce((a,b) -> a.add(b))
					.orElse(BigDecimal.ZERO);
		
		entry.setAmount(amount);
		entry.setLastAmount(lastAmount);
		
		
		// update Member Balance
		var balance = switch (entry.getLedger().getType()) {
		case Expenses -> entry.getLastAmount().subtract(amount);
		case Incomes -> entry.getLastAmount().add(amount);
		};

		member.getActivity().setBalance(balance);
		// Update Remain Entry Balances
		var entries = entryRepo.findRemainingEntries(member.getId(), entry.getId().getIssueDate(), entry.getId().getSeqNumber());
		
		for(var remain:entries) {
			remain.setLastAmount(member.getActivity().getBalance());
			var remainAmount = remain.getItems().stream()
					.map(a->a.getUnitPrice().multiply( BigDecimal.valueOf(a.getQuantity())))
					.reduce((a,b) -> a.add(b))
					.orElse(BigDecimal.ZERO);
			
			var remainBalance = switch(remain.getLedger().getType()) {
			case Expenses -> remain.getLastAmount().subtract(remainAmount);
			case Incomes -> remain.getLastAmount().add(remainAmount);
			};
			
			member.getActivity().setBalance(remainBalance);
		}
		
		return entry.getId().getCode();
	}

	@PreAuthorize("authentication.name eq #username")
	public PageResult<LedgerEntryListItem> search(String username,
			BalanceType type, LedgerEntrySearch search, int page, int size) {
		
		
		return entryRepo.search(queryFunc(username, type, search), 
				countFunc(username, type, search), page, size);
	}

	private Function<CriteriaBuilder, CriteriaQuery<LedgerEntryListItem>> queryFunc
			(String username, BalanceType type, LedgerEntrySearch search) {
		
		return cb ->{
			var cq = cb.createQuery(LedgerEntryListItem.class);
			var root = cq.from(LedgerEntry.class);
			
			LedgerEntryListItem.select(cq, root);
			cq.where(search.where(cb, root, username, type));
			cq.orderBy(cb.desc(root.get(LedgerEntry_.issueAt)));
			return cq;
		};
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc
			(String username, BalanceType type, LedgerEntrySearch search) {
		return cb ->{
			var cq = cb.createQuery(Long.class);
			var root = cq.from(LedgerEntry.class);
			
			cq.select(cb.count(root.get(LedgerEntry_.id)));
			cq.where(search.where(cb, root, username, type));
			
			return cq;
		};
	}


	

}
