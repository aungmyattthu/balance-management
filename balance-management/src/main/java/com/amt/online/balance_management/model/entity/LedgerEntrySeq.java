package com.amt.online.balance_management.model.entity;

import com.amt.online.balance_management.model.entity.embeddables.LedgerEntryPk;
import com.amt.online.balance_management.model.entity.embeddables.LedgerEntrySeqPk;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class LedgerEntrySeq {
	
	@EmbeddedId
	private LedgerEntrySeqPk id;
	
	@Column(nullable = false)
	private int seqNumber;
	
	public LedgerEntryPk next() {
		seqNumber++;
		return LedgerEntryPk.from(this);
	}
	
	
}
