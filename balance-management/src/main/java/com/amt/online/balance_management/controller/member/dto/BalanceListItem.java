package com.amt.online.balance_management.controller.member.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.amt.online.balance_management.model.entity.Ledger;
import com.amt.online.balance_management.model.entity.LedgerEntry;
import com.amt.online.balance_management.model.entity.LedgerEntry_;
import com.amt.online.balance_management.model.entity.consts.BalanceType;
import com.amt.online.balance_management.model.entity.embeddables.LedgerEntryPk;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record BalanceListItem(
			String code,
			BalanceType type,
			String ledgerName,
			LocalDateTime issueAt,
			String particular,
			BigDecimal income,
			BigDecimal expense,
			BigDecimal balance
		) {

	public BalanceListItem(
				LedgerEntryPk id,
				Ledger ledger,
				LocalDateTime issueAt,
				String particular,
				BigDecimal amount,
				BigDecimal lastAmount
			) {
		this(
			id.getCode(),
			ledger.getType(),
			ledger.getName(),
			issueAt,
			particular,
			ledger.getType() == BalanceType.Incomes ? amount : BigDecimal.ZERO,	
			ledger.getType() == BalanceType.Expenses ? amount : BigDecimal.ZERO,
			ledger.getType() == BalanceType.Incomes	? lastAmount.add(amount):lastAmount.subtract(amount)	
			);
	}
	
	public static void select(CriteriaQuery<BalanceListItem> cq, 
								Root<LedgerEntry> root) {
		cq.multiselect(
				root.get(LedgerEntry_.id),
				root.get(LedgerEntry_.ledger),
				root.get(LedgerEntry_.issueAt),
				root.get(LedgerEntry_.particular),
				root.get(LedgerEntry_.amount),
				root.get(LedgerEntry_.lastAmount)
				
				);
	}
	
}
