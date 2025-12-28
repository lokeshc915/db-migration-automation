package com.matilda.cloud.db.migration.automation.utils;

public final class DbMigrationUtils {

	/**
	 * DbMigrationAutomationConstants should not normally be instantiated
	 */
	private DbMigrationUtils() {

	}

	// re use for better performance
	public static StringBuilder reuseStringBuilder(final StringBuilder sb) {
		sb.delete(0, sb.length());
		return sb;
	}
}
