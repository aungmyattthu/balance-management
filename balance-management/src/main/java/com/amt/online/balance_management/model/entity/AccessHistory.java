package com.amt.online.balance_management.model.entity;

import com.amt.online.balance_management.model.entity.consts.AccessStatus;
import com.amt.online.balance_management.model.entity.consts.AccessType;
import com.amt.online.balance_management.model.entity.embeddables.AccessHistoryPk;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class AccessHistory {

	@EmbeddedId
	private AccessHistoryPk id;
	
	@Column(nullable = false)
	private AccessType type;
	private AccessStatus status;
	private String remark;
}
