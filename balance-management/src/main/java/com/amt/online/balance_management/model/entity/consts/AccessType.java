package com.amt.online.balance_management.model.entity.consts;

public enum AccessType {
	Login, Logout, SignUp {
		@Override
		public String getDisplayName() {
			return "Sign Up";
		}
	}, Timeout;
	
	public String getDisplayName() {
		return this.name();
	}
}
