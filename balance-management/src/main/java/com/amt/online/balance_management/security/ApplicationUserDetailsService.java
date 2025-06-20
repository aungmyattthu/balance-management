package com.amt.online.balance_management.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.amt.online.balance_management.model.repo.AccountRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService {

	private final AccountRepo accountRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return accountRepo.findById(username)
				.map(account -> User.withUsername(username)
						.password(account.getPassword())
						.authorities(account.getRole().name())
						.disabled(!account.isActive())
						.build())
				.orElseThrow(()-> new UsernameNotFoundException("Invalid Login Id"));			
				
	}

}
