package com.amt.online.balance_management.model.entity;

import java.math.BigDecimal;

import com.amt.online.balance_management.model.entity.embeddables.LedgerEntryItemPk;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class LedgerEntryItem {
	@EmbeddedId
	private LedgerEntryItemPk id;
	
	@ManyToOne(optional = false)
	private LedgerEntry entry;
	
	@Column(nullable = false)
	private String item;
	
	@Column(nullable = false)
	private BigDecimal unitPrice;
	
	@Column(nullable = false)
	private int quantity;
}
