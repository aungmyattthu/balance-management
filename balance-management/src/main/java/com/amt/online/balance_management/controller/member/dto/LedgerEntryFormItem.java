package com.amt.online.balance_management.controller.member.dto;

import java.math.BigDecimal;

import com.amt.online.balance_management.model.entity.LedgerEntryItem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LedgerEntryFormItem {
	@NotBlank(message = "Please enter item name")
	private String itemName;
	
	@Min(value = 1, message = "Please enter unit price")
	@NotNull(message = "Please enter unit price")
	private BigDecimal unitPrice = BigDecimal.ZERO;
	
	@Min(value = 1, message = "Please enter quantity")
	@NotNull(message = "Please enter quantity")
	private Integer quantity = 0;
	
	private boolean deleted;
	
	public static LedgerEntryFormItem from(LedgerEntryItem entity) {
		
		var item = new LedgerEntryFormItem();
		item.setItemName(entity.getItem());
		item.setUnitPrice(entity.getUnitPrice());
		item.setQuantity(entity.getQuantity());
		return item;
	}
}
 