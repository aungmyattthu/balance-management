package com.amt.online.balance_management.controller.member.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ChartBalanceVo(
		LocalDate date,
		BigDecimal expenses,
		BigDecimal incomes
		) {

}
