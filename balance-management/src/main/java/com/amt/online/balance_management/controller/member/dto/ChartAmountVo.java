package com.amt.online.balance_management.controller.member.dto;

import java.math.BigDecimal;

public record ChartAmountVo(
			String ledger,
			BigDecimal value
		) {

}
