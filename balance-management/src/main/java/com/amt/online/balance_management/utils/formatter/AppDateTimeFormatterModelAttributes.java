package com.amt.online.balance_management.utils.formatter;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AppDateTimeFormatterModelAttributes {
	
	@ModelAttribute(name = "dtf")
	public AppDateTimeFormatter dateTimeFormatter() {
		return new AppDateTimeFormatter();
	}
}
