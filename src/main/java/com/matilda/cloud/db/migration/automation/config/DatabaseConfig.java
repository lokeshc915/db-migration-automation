package com.matilda.cloud.db.migration.automation.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DatabaseConfig {

	@Bean(name = "awsDatasource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "mysqlNamedParameterJdbcTemplate")
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(
			@Qualifier("awsDatasource") DataSource mysqlDataSource) {
		return new NamedParameterJdbcTemplate(mysqlDataSource);
	}

	@Bean(name = "awsJdbcTemplate")
	public JdbcTemplate jdbcTemplate(@Qualifier("awsDatasource") DataSource mysqlDataSource) {
		return new JdbcTemplate(mysqlDataSource);
	}

}
