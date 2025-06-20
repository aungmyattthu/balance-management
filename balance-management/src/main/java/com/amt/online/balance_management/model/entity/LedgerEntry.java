package com.amt.online.balance_management.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.amt.online.balance_management.model.entity.embeddables.LedgerEntryPk;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class LedgerEntry extends AbstractEntity{
	
	@EmbeddedId
	private LedgerEntryPk id;
	
	@ManyToOne(optional = false)
	private Ledger ledger;
	
	@Column(nullable = false)
	private BigDecimal lastAmount;
	
	@Column(nullable = false)	
	private BigDecimal amount;
	
	@Column(nullable = false)
	private String particular;
	
	@Column(nullable = false)
	private LocalDateTime issueAt;
	
	@OneToMany(mappedBy = "entry")
	private List<LedgerEntryItem> items;
}
