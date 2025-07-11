package com.amt.online.balance_management.utils.listener;

import java.time.LocalDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationFailureDisabledEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.amt.online.balance_management.model.entity.consts.Role;
import com.amt.online.balance_management.model.repo.MemberActivityRepo;
import com.amt.online.balance_management.service.AccessHistoryService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationEventListener {

	private final AccessHistoryService service;
	
	private final MemberActivityRepo activityRepo;

	@EventListener
	@Transactional
	public void handle(AuthenticationSuccessEvent event) {
		// Update Access History
		service.loginSuccess(event.getAuthentication().getName());
		
		// Update Last Access Info
		var authentication = event.getAuthentication();
		if(authentication.getAuthorities()
						.stream()
						.map(a -> a.getAuthority())
						.filter(a -> Role.Member.name().equals(a))
						.count() > 0 ) {
			var username = authentication.getName();
			activityRepo.updateLastAccess(LocalDateTime.now(), username);
		}
		
	}

	@EventListener
	public void handle(AbstractAuthenticationFailureEvent event) {
		var message = switch (event) {
		case AuthenticationFailureBadCredentialsEvent e -> "Missing login Id or password.";
		case AuthenticationFailureDisabledEvent e -> "Account is disabled.";
		default -> "Other Authentication Failure.";

		};
		
		service.loginError(event.getAuthentication().getName(), message);
	}

	@EventListener
	public void handle(LogoutSuccessEvent event) {
		service.logoutSuccess(event.getAuthentication().getName());
	}
}
