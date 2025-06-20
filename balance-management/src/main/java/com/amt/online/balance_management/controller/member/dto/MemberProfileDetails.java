package com.amt.online.balance_management.controller.member.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.amt.online.balance_management.model.entity.Member;

public record MemberProfileDetails(
			long id,
			String name,
			String phone,
			String profileImage,
			String email,
			String address,
			LocalDateTime registeredAt,
			LocalDateTime lastAccessAt
		) {
	
	public static MemberProfileDetails from(Member member) {
		
		var builder = new Builder();
		
		
		builder.id(member.getId())
				.name(member.getName())
				.phone(member.getPhone())
				.profileImage(Optional.ofNullable(member.getProfileImage()).orElse("default-profile.png"))
				.email(member.getEmail())				
				.registeredAt(member.getActivity().getRegisteredAt())
				.lastAccessAt(member.getActivity().getLastAccessAt());
				
		var list = List.of(Optional.ofNullable(member.getAddress()).orElse(""),
				Optional.ofNullable(member.getTownship()).map(a -> a.getName()).orElse(""),
				Optional.ofNullable(member.getTownship())
											.map(a -> a.getDistrict())
											.map(a -> a.getRegion())
											.map(a -> a.getName()).orElse("")
				);
		var address = list.stream().filter(StringUtils::hasLength)
						.collect(Collectors.joining(","));
		
		builder.address(address);
		return builder.build();
	}
	
	public static class Builder {
		private long id;
		private String name;
		private String phone;
		private String profileImage;
		private String email;
		private String address;
		private LocalDateTime registeredAt;
		private LocalDateTime lastAccessAt;

		public Builder id(long id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder phone(String phone) {
			this.phone = phone;
			return this;
		}

		public Builder profileImage(String profileImage) {
			this.profileImage = profileImage;
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

		public Builder registeredAt(LocalDateTime registeredAt) {
			this.registeredAt = registeredAt;
			return this;
		}

		public Builder lastAccessAt(LocalDateTime lastAccessAt) {
			this.lastAccessAt = lastAccessAt;
			return this;
		}

		public MemberProfileDetails build() {
			return new MemberProfileDetails(id, name, phone, profileImage, email, address, registeredAt, lastAccessAt);
		}
		
	}
}
