package com.amt.online.balance_management.model.entity.embeddables;

import java.time.Instant;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class AccessHistoryPk {
	private String username;
	private Instant accessAt;
}
