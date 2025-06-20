package com.amt.online.balance_management.service;

import java.time.Instant;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amt.online.balance_management.controller.member.dto.AccessHistoryListItem;
import com.amt.online.balance_management.controller.member.dto.AccessHistorySearch;
import com.amt.online.balance_management.model.PageResult;
import com.amt.online.balance_management.model.entity.AccessHistory;
import com.amt.online.balance_management.model.entity.AccessHistory_;
import com.amt.online.balance_management.model.entity.consts.AccessStatus;
import com.amt.online.balance_management.model.entity.consts.AccessType;
import com.amt.online.balance_management.model.entity.embeddables.AccessHistoryPk;
import com.amt.online.balance_management.model.entity.embeddables.AccessHistoryPk_;
import com.amt.online.balance_management.model.repo.AccessHistoryRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccessHistoryService {

	private final AccessHistoryRepo accessHistoryRepo;

	@Transactional
	public void signUp(String username) {

		createHistory(username, AccessType.SignUp, AccessStatus.Success);
	}

	@Transactional
	public void loginSuccess(String username) {

		createHistory(username, AccessType.Login, AccessStatus.Success);
	}

	@Transactional
	public void logoutSuccess(String username) {
		createHistory(username, AccessType.Logout, AccessStatus.Success);

	}

	@Transactional
	public void loginError(String username, String message) {
		createHistory(username, AccessType.Login, AccessStatus.Fails, message);

	}

	private void createHistory(String username, AccessType type, AccessStatus status) {
		createHistory(username, type, status, null);
	}

	private void createHistory(String username, AccessType type, AccessStatus status, String message) {
		var id = new AccessHistoryPk();
		id.setAccessAt(Instant.now());
		id.setUsername(username);
		var history = new AccessHistory();
		history.setId(id);
		history.setType(type);
		history.setStatus(status);
		history.setRemark(message);

		accessHistoryRepo.save(history);

	}

	@Transactional(readOnly = true)
	public PageResult<AccessHistoryListItem> search(AccessHistorySearch form, 
			int page,
			int size) {
		
		return accessHistoryRepo.search(queryFunc(form), countFunc(form), page, size);
	}

	private Function<CriteriaBuilder, CriteriaQuery<AccessHistoryListItem>> queryFunc(AccessHistorySearch form) {

		return cb -> {
			var cq = cb.createQuery(AccessHistoryListItem.class);
			var root = cq.from(AccessHistory.class);
			
			AccessHistoryListItem.select(cq, root);
			cq.where(form.where(cb,root));
			cq.orderBy(cb.desc(root.get(AccessHistory_.id).get(AccessHistoryPk_.accessAt)));
			return cq;
		};
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(AccessHistorySearch form) {
		
		return cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(AccessHistory.class);
			cq.select(cb.count(root.get(AccessHistory_.id)));
			cq.where(form.where(cb, root));
			return cq;
		};
	}

}
