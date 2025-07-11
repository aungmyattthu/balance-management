package com.amt.online.balance_management.service;

import static com.amt.online.balance_management.utils.EntityOperations.safeCall;

import java.time.LocalDate;
import java.util.function.Function;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amt.online.balance_management.controller.management.dto.MemberDetails;
import com.amt.online.balance_management.controller.management.dto.MemberListItem;
import com.amt.online.balance_management.controller.management.dto.MemberSearch;
import com.amt.online.balance_management.controller.management.dto.MemberStatusForm;
import com.amt.online.balance_management.model.PageResult;
import com.amt.online.balance_management.model.entity.Member;
import com.amt.online.balance_management.model.entity.MemberActivity_;
import com.amt.online.balance_management.model.entity.Member_;
import com.amt.online.balance_management.model.entity.consts.MemberStatus;
import com.amt.online.balance_management.model.repo.MemberActivityRepo;
import com.amt.online.balance_management.model.repo.MemberRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberManagementService {

	private static final String RESOURCE_NAME = "member";
	private static final String KEY_NAME = "id";
	private final MemberRepo memberRepo;
	private final MemberActivityRepo activityRepo;
	
	@Transactional
	public void updateStatus(long id, MemberStatusForm form) {
		
		var member = safeCall(memberRepo.findById(id), RESOURCE_NAME, KEY_NAME, id);
		
		member.getAccount().setActive(form.getStatus());
		
		var activity = member.getActivity();
		
		activity.setStatus(form.getStatus()?MemberStatus.Active : MemberStatus.Denied);
		activity.setStatusChangeReason(form.getReason());
	}

	public Long findMemberCount(LocalDate dateFrom) {

		if(null == dateFrom) {
			return memberRepo.count();
		}
		
		
		return activityRepo.countByRegisteredAtIsGreaterThanEqual(dateFrom.atStartOfDay());
	}
	
	public MemberDetails findById(long id) {
		var member = safeCall(memberRepo.findById(id).map(MemberDetails::from), RESOURCE_NAME, KEY_NAME, id);
		return member;
	}

	@PreAuthorize("authentication.name eq #username")
	public PageResult<MemberListItem> search(MemberSearch search, int page, int size) {
		
		return memberRepo.search(queryFunc(search), countFunc(search), page, size);
	}

	private Function<CriteriaBuilder, CriteriaQuery<MemberListItem>> queryFunc(MemberSearch search) {
		
		return cb ->{
			var cq = cb.createQuery(MemberListItem.class);
			var root = cq.from(Member.class);
			
			MemberListItem.select(cq, root);
			cq.where(search.where(cb, root));
			cq.orderBy(cb.desc(root.get(Member_.activity).get(MemberActivity_.updatedAt)));
			return cq;
		};
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(MemberSearch search) {
		return cb ->{
			var cq = cb.createQuery(Long.class);
			var root = cq.from(Member.class);
			
			cq.select(cb.count(root.get(Member_.id)));
			cq.where(search.where(cb, root));
			return cq;
		};
	}

	

}
