package com.amt.online.balance_management.controller.member.dto;

import java.math.BigDecimal;
import java.util.Optional;

public record ChartSummaryVo(
			BigDecimal incomes,
			BigDecimal expenses
		) {
	public BigDecimal getBalances() {
		return Optional.ofNullable(incomes).orElse(BigDecimal.ZERO)
					.subtract(Optional.ofNullable(expenses).orElse(BigDecimal.ZERO));
	}
}
