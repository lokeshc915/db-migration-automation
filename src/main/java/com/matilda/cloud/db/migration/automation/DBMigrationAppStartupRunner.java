package com.matilda.cloud.db.migration.automation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.matilda.cloud.db.migration.automation.service.DbMigrationAutomationService;

@Component
public class DBMigrationAppStartupRunner implements CommandLineRunner {
	@Autowired
	private DbMigrationAutomationService dbMigrationAutomationService;

	@Override
	public void run(String... args) throws Exception {
		dbMigrationAutomationService.retriveMetadataDetails();
	}
}