package com.amt.online.balance_management.model.repo;

import java.util.Optional;

import com.amt.online.balance_management.model.BaseRepository;
import com.amt.online.balance_management.model.entity.Member;

public interface MemberRepo extends BaseRepository<Member, Long> {

	Optional<Member> findOneByAccountUsername(String username);
	
}
