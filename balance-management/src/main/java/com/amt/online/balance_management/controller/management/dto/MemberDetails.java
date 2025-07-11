package com.amt.online.balance_management.controller.management.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.StringUtils;

import com.amt.online.balance_management.model.entity.Member;
import com.amt.online.balance_management.model.entity.consts.Gender;
import com.amt.online.balance_management.model.entity.consts.MemberStatus;

public record MemberDetails(
		long id, 
		String name, 
		String profileImage,
		LocalDate dateOfBirth, 
		Gender gender, 
		String phone, 
		String email,
		String address, 
		String township, 
		String region, 
		MemberStatus status, 
		LocalDateTime registeredAt,
		LocalDateTime lastAccessAt
		) {
	
	public String getDisplayAddress() {
		
		if(StringUtils.hasLength(address)
				|| StringUtils.hasLength(township)
				|| StringUtils.hasLength(region)) {
			return Stream.of(address, township, region)
				.filter(StringUtils::hasLength)
				.collect(Collectors.joining(", "));
		}
		
		return null;
	}

	public static MemberDetails from(Member member) {
		// Convert Member entity to MemberDetails DTO
		var builder = new Builder();
		
		builder.id(member.getId())
			.name(member.getName())
			.profileImage(Optional.ofNullable(member.getProfileImage()).orElse("default-profile.png"))
			.dateOfBirth(member.getDob())
			.gender(member.getGender())
			.phone(member.getPhone())
			.email(member.getEmail())
			.address(member.getAddress())
			.status(member.getActivity().getStatus())
			.registeredAt(member.getActivity().getRegisteredAt())
			.lastAccessAt(member.getActivity().getLastAccessAt());
			
		var township = member.getTownship();
		
		if(null != township) {
			builder.township(township.getName())
					.region(township.getDistrict().getRegion().getName());
		}
		
		return builder.build();
	}

	public static class Builder {
		private long id;
		private String name;
		private String profileImage;
		private LocalDate dateOfBirth;
		private Gender gender;
		private String phone;
		private String email;
		private String address;
		private String township;
		private String region;
		private MemberStatus status;
		private LocalDateTime registeredAt;
		private LocalDateTime lastAccessAt;

		public MemberDetails build() {
			return new MemberDetails(
					id, 
					name, 
					profileImage,
					dateOfBirth, 
					gender, 
					phone, 
					email, 
					address, 
					township, 
					region, 
					status,
					registeredAt, 
					lastAccessAt);
		}

		public Builder id(long id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}
		
		public Builder profileImage(String profileImage) {
			this.profileImage = profileImage;
			return this;
		}

		public Builder dateOfBirth(LocalDate dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
			return this;
		}

		public Builder gender(Gender gender) {
			this.gender = gender;
			return this;
		}

		public Builder phone(String phone) {
			this.phone = phone;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder address(String address) {
			this.address = address;
			return this;
		}

		public Builder township(String township) {
			this.township = township;
			return this;
		}

		public Builder region(String region) {
			this.region = region;
			return this;
		}

		public Builder status(MemberStatus status) {
			this.status = status;
			return this;
		}

		public Builder registeredAt(LocalDateTime registeredAt) {
			this.registeredAt = registeredAt;
			return this;
		}

		public Builder lastAccessAt(LocalDateTime lastAccessAt) {
			this.lastAccessAt = lastAccessAt;
			return this;
		}
	}

}
