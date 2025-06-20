package com.amt.online.balance_management.model.entity;

import com.amt.online.balance_management.model.entity.consts.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Account {
	@Id
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private Role role;
	private boolean active;
}
