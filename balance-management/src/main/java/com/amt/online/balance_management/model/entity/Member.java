package com.amt.online.balance_management.model.entity;

import java.time.LocalDate;

import com.amt.online.balance_management.model.entity.consts.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Member extends AbstractEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(optional = false)
	private Account account;
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String email;
	
	private String phone;

	
	private LocalDate dob;
	
	private Gender gender;
	private String profileImage;
	
	@ManyToOne
	private Township township;
	
	private String address;
	
	@OneToOne(mappedBy = "member")
	private MemberActivity activity;
}
