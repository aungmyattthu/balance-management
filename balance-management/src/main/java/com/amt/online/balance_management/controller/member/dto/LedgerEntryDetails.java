package com.amt.online.balance_management.controller.member.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.amt.online.balance_management.model.entity.LedgerEntry;
import com.amt.online.balance_management.model.entity.consts.BalanceType;

public record LedgerEntryDetails(
			String code,
			BalanceType type,
			String ledgerName,
			BigDecimal amount,
			LocalDateTime issueAt,
			String particular,
			java.util.List<LedgerEntryDetailsItem> items
		) {

	public BigDecimal getTotal() {
		return items.stream().map(a-> a.getTotal())
				.reduce((a,b) -> a.add(b)).orElse(BigDecimal.ZERO);
	}
	
	public static LedgerEntryDetails from(LedgerEntry entity) {
		return new LedgerEntryDetails(
				entity.getId().getCode(), 
				entity.getLedger().getType(), 
				entity.getLedger().getName(), 
				entity.getAmount(), 
				entity.getIssueAt(), 
				entity.getParticular(), 
				entity.getItems().stream().map(LedgerEntryDetailsItem::from).toList()
				);
	}
}
