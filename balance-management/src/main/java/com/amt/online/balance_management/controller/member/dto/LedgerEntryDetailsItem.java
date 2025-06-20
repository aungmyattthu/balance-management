package com.amt.online.balance_management.controller.member.dto;

import java.math.BigDecimal;

import com.amt.online.balance_management.model.entity.LedgerEntryItem;

public record LedgerEntryDetailsItem(
			String itemName,
			BigDecimal unitPrice,
			int quantity
		) {
	
	public BigDecimal getTotal() {
		return unitPrice.multiply(BigDecimal.valueOf(quantity));
	}
	
	public static LedgerEntryDetailsItem from(LedgerEntryItem entity) {
		return new LedgerEntryDetailsItem(
				entity.getItem(),
				entity.getUnitPrice(),
				entity.getQuantity()
				);
	}
}
