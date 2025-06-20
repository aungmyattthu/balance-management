package com.amt.online.balance_management.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amt.online.balance_management.controller.anonymous.dto.SignUpForm;
import com.amt.online.balance_management.model.entity.Account;
import com.amt.online.balance_management.model.entity.Member;
import com.amt.online.balance_management.model.entity.MemberActivity;
import com.amt.online.balance_management.model.entity.consts.MemberStatus;
import com.amt.online.balance_management.model.entity.consts.Role;
import com.amt.online.balance_management.model.repo.AccountRepo;
import com.amt.online.balance_management.model.repo.MemberActivityRepo;
import com.amt.online.balance_management.model.repo.MemberRepo;
import com.amt.online.balance_management.utils.Exception.AppBusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignUpService {

	private final AccountRepo accountRepo;
	private final MemberRepo memberRepo;
	private final PasswordEncoder passwordEncoder;
	private final AccessHistoryService accessHistoryService;
	private final MemberActivityRepo memberActivityRepo;
	
	@Transactional
	public String signUp(SignUpForm signUpForm) {
		
		if(accountRepo.existsById(signUpForm.getUsername())) {
			throw new AppBusinessException("Your email is already registered. Please try another email.");
		}
		
		var account = new Account();
		account.setUsername(signUpForm.getUsername());
		account.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
		account.setActive(true);
		account.setRole(Role.Member);
		
		account = accountRepo.save(account);
		
		var member = new Member();
		member.setAccount(account);
		member.setEmail(signUpForm.getUsername());
		member.setName(signUpForm.getName());
		memberRepo.save(member);
		
		var activity = new MemberActivity();
		activity.setMember(member);
		activity.setBalance(BigDecimal.ZERO);
		activity.setRegisteredAt(LocalDateTime.now());
		activity.setStatus(MemberStatus.Active);
		memberActivityRepo.save(activity);
		
		accessHistoryService.signUp(signUpForm.getUsername());
		return "Your account has been created successfully. Please sign in again.";
	}
	
}
