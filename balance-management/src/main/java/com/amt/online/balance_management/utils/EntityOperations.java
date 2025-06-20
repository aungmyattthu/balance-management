package com.amt.online.balance_management.utils;

import java.util.Optional;

import com.amt.online.balance_management.utils.Exception.AppBusinessException;

public class EntityOperations {
	public static <T,ID> T safeCall(Optional<T> optional, String resourceName, String keyName, ID keyvalue) {
		return optional.orElseThrow(() -> 
			new AppBusinessException("There is no %s with %s : %s.".formatted(resourceName, keyName, keyvalue)));
	}
}
