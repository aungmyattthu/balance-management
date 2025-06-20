package com.amt.online.balance_management.controller.member.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.amt.online.balance_management.model.entity.LedgerEntry;
import com.amt.online.balance_management.model.entity.LedgerEntry_;
import com.amt.online.balance_management.model.entity.Ledger_;
import com.amt.online.balance_management.model.entity.embeddables.LedgerEntryPk;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record LedgerEntryListItem(
			String code,
			LocalDateTime issueAt,
			String ledgerName,
			String particular,
			BigDecimal amount
		) {

	public LedgerEntryListItem(
			LedgerEntryPk id,
			LocalDateTime issueAt,
			String ledgerName,
			String particular,
			BigDecimal amount
			) {
		this(id.getCode(), issueAt, ledgerName, particular, amount);
	}
	
	public static void select(CriteriaQuery<LedgerEntryListItem> cq, Root<LedgerEntry> root) {
		cq.multiselect(
				root.get(LedgerEntry_.id),
				root.get(LedgerEntry_.issueAt),
				root.get(LedgerEntry_.ledger).get(Ledger_.name),
				root.get(LedgerEntry_.particular),
				root.get(LedgerEntry_.amount)
				);	
		
	}

}
