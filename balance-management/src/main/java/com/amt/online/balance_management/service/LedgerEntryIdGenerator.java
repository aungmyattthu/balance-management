package com.amt.online.balance_management.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.amt.online.balance_management.model.entity.LedgerEntrySeq;
import com.amt.online.balance_management.model.entity.embeddables.LedgerEntryPk;
import com.amt.online.balance_management.model.entity.embeddables.LedgerEntrySeqPk;
import com.amt.online.balance_management.model.repo.LedgerEntrySeqRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LedgerEntryIdGenerator {
	
	private final LedgerEntrySeqRepo repo;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
	public LedgerEntryPk next(long memberId, LocalDate issueDate) {
		
		var seqId = new LedgerEntrySeqPk(memberId, issueDate);
		
		var entrySeq = repo.findById(seqId).orElseGet(()->{
			var seq = new LedgerEntrySeq();
			seq.setId(seqId);
			
			return repo.save(seq);
		});
		
		return entrySeq.next();
	}
	
}
