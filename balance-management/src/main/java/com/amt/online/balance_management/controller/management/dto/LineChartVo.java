package com.amt.online.balance_management.controller.management.dto;

import java.time.LocalDate;

public record LineChartVo(
		LocalDate date,
		Long value
		) {

}
