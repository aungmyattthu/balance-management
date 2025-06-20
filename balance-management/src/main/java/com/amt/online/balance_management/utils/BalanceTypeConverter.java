package com.amt.online.balance_management.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.amt.online.balance_management.model.entity.consts.BalanceType;

public class BalanceTypeConverter implements Converter<String, BalanceType>{

	@Override
	public BalanceType convert(String source) {
		if(StringUtils.hasLength(source)) {
			return BalanceType.from(source);
		}
		return null;
	}
	
}
