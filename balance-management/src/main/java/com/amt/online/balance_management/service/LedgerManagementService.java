package com.amt.online.balance_management.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amt.online.balance_management.controller.member.dto.LedgerForm;
import com.amt.online.balance_management.controller.member.dto.LedgerListItem;
import com.amt.online.balance_management.controller.member.dto.LedgerSearch;
import com.amt.online.balance_management.controller.member.dto.LedgerSelectItem;
import com.amt.online.balance_management.model.PageResult;
import com.amt.online.balance_management.model.entity.Account_;
import com.amt.online.balance_management.model.entity.Ledger;
import com.amt.online.balance_management.model.entity.Ledger_;
import com.amt.online.balance_management.model.entity.Member_;
import com.amt.online.balance_management.model.entity.consts.BalanceType;
import com.amt.online.balance_management.model.repo.LedgerRepo;
import com.amt.online.balance_management.model.repo.MemberRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LedgerManagementService {
	
	private final LedgerRepo ledgerRepo;
	
	private final MemberRepo memberRepo;
	
	
	@PreAuthorize("authentication.name eq #username")
	public PageResult<LedgerListItem> search(String username, LedgerSearch form, int page, int size) {
		
		return ledgerRepo.search(queryFunc(username, form), countFunc(username, form), page, size);
	}
	
	
	@Transactional
	public void save(LedgerForm ledgerForm) {
		
		var entity = Optional.ofNullable(ledgerForm.getId())
							.flatMap(id -> ledgerRepo.findById(id))
							.orElse(new Ledger());
		
		entity.setName(ledgerForm.getName());
		entity.setDeleted(ledgerForm.getStatus());
		entity.setType(ledgerForm.getType());
		
		if(entity.getMember() == null) {
			var username = SecurityContextHolder.getContext()
						.getAuthentication().getName();
			entity.setMember(memberRepo.findOneByAccountUsername(username).orElseThrow());
		}
		
		ledgerRepo.save(entity);
	}

	public LedgerForm findForEdit(Integer id) {
		
		return ledgerRepo.findById(id).map(LedgerForm::from)
					.orElse(new LedgerForm());
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<LedgerListItem>> queryFunc(String username, LedgerSearch form) {
		
		
		return cb -> {
			var cq = cb.createQuery(LedgerListItem.class);
			var root = cq.from(Ledger.class);
			LedgerListItem.select(cb, cq, root);
			cq.where(form.where(username, cb, root));
			cq.orderBy(
					cb.asc(root.get(Ledger_.id))
			);
			return cq;
		};
	}
	
	@PreAuthorize("authentication.name eq #username")
	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(String username, LedgerSearch form) {
		return cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(Ledger.class);
			
			cq.select(cb.count(root.get(Ledger_.id)));
			cq.where(form.where(username, cb, root));
			
			return cq;
		};
	}


	public List<LedgerSelectItem> findForEntry(String username, BalanceType type) {
		
		return ledgerRepo.search(queryFunc(username, type));
	}


	private Function<CriteriaBuilder, CriteriaQuery<LedgerSelectItem>> queryFunc(String username, BalanceType type) {
		
		return cb -> {
			var cq = cb.createQuery(LedgerSelectItem.class);
			var root = cq.from(Ledger.class);
			
			cq.multiselect(
					root.get(Ledger_.id),
					root.get(Ledger_.name)					
					);
			
			cq.where(
					cb.equal(root.get(Ledger_.member).get(Member_.account).get(Account_.username), username),
					cb.equal(root.get(Ledger_.type), type)
					);
			
			cq.orderBy(cb.asc(root.get(Ledger_.name)));
			
			return cq;
		};
	}

	

}
