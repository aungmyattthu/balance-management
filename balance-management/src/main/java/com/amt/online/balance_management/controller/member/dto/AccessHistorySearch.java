package com.amt.online.balance_management.controller.member.dto;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.amt.online.balance_management.model.entity.AccessHistory;
import com.amt.online.balance_management.model.entity.AccessHistory_;
import com.amt.online.balance_management.model.entity.consts.AccessStatus;
import com.amt.online.balance_management.model.entity.embeddables.AccessHistoryPk_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

@Data
public class AccessHistorySearch {

	private String username;
	private AccessStatus status;
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private String keyword;

	public Predicate[] where(CriteriaBuilder cb, Root<AccessHistory> root) {

		var params = new ArrayList<Predicate>();

		if (StringUtils.hasLength(username)) {
			params.add(cb.equal(root.get(AccessHistory_.id).get(AccessHistoryPk_.username), username));
		}

		if (null != status) {
			params.add(cb.equal(root.get(AccessHistory_.status), status));
		}

		if (null != dateFrom) {

			var value = dateFrom.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
			params.add(cb.greaterThanOrEqualTo(root.get(AccessHistory_.id).get(AccessHistoryPk_.accessAt), value));
		}
		if (null != dateTo) {
			var value = dateTo.plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
			params.add(cb.lessThanOrEqualTo(root.get(AccessHistory_.id).get(AccessHistoryPk_.accessAt), value));
		}
		if (StringUtils.hasLength(keyword)) {
			params.add(cb.like(cb.lower(root.get(AccessHistory_.remark)), keyword.toLowerCase().concat("%")

			));
		}
		return params.toArray(size -> new Predicate[size]);
	}
}
