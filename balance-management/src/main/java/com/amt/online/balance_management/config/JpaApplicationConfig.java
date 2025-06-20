package com.amt.online.balance_management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.amt.online.balance_management.model.BaseRepositoryImpl;

@Configuration
@EnableJpaRepositories(basePackages = "com.amt.online.balance_management.model",
						repositoryBaseClass = BaseRepositoryImpl.class)
@EnableJpaAuditing
public class JpaApplicationConfig {

}
