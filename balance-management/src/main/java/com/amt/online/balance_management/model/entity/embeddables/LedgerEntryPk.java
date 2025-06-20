package com.amt.online.balance_management.model.entity.embeddables;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.amt.online.balance_management.model.entity.LedgerEntrySeq;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class LedgerEntryPk {
	private long memberId;
	private LocalDate issueDate;
	private int seqNumber;
	
	private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyyMMdd");
	
	public String getCode() {
		
		return "%s%03d".formatted(issueDate.format(DF), seqNumber);
	}

	
	public static LedgerEntryPk parse(long memberId, String id) {
		var pk = new LedgerEntryPk();
		
		var issueDate = LocalDate.parse(id.substring(0, 8), DF);
		var seqNumber = Integer.parseInt(id.substring(9));
		
		pk.setMemberId(memberId);
		pk.setIssueDate(issueDate);
		pk.setSeqNumber(seqNumber);
		return pk;
	}


	public static LedgerEntryPk from(LedgerEntrySeq seq) {
		var pk = new LedgerEntryPk();
		
		pk.setMemberId(seq.getId().getMemberId());
		pk.setIssueDate(seq.getId().getIssueDate());
		pk.setSeqNumber(seq.getSeqNumber());
		return pk;
	}
}
