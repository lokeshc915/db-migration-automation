package com.matilda.cloud.db.migration.automation.constants;

public final class DbMigrationAutomationConstants {

	/**
	 * DbMigrationAutomationConstants should not normally be instantiated
	 */
	private DbMigrationAutomationConstants() {

	}

	public static final String DB_SCHEMA_REFERENCES = "REFERENCES";

	public static final String DB_SCHEMA_COMMENT_START = "/*";
	public static final String DB_SCHEMA_COMMENT_LINE_SYMBOL_STAR = "*********************************************************************";
	public static final String DB_SCHEMA_COMMENT_HEADER = "Matilda database schema backup";
	public static final String DB_SCHEMA_COMMENT_END = "*/";
	
	public static final String DB_SCHEMA_USE_WITH_SINGLE_QOUTE ="USE `%s`;";
	public static final String DB_SCHEMA_CREATE_COMMAND =  "CREATE DATABASE IF NOT EXISTS `%s`;";

	public static final String DB_SCHEMA_SET_NAMES_UTF8 = "/*! SET NAMES utf8 */;";
	public static final String DB_SCHEMA_SET_SQL_MODE_EMPTY = "/*! SET SQL_MODE=''*/;";
	public static final String DB_SCHEMA_SET_UNIQUE_CHECKS = "/*! SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;";
	public static final String DB_SCHEMA_SET_FOREIGN_KEY_CHECKS = "/*! SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;";
	public static final String DB_SCHEMA_SET_SQL_MODE = "/*! SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;";
	public static final String DB_SCHEMA_SET_SQL_NOTES = "/*! SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;";

	public static final String DB_SCHEMA_SET_OLD_SQL_MODE = "/*! SET SQL_MODE=@OLD_SQL_MODE */;";
	public static final String DB_SCHEMA_SET_OLD_FOREIGN_KEY_CHECKS = "/*! SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;";
	public static final String DB_SCHEMA_SET_OLD_UNIQUE_CHECKS = "/*! SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;";
	public static final String DB_SCHEMA_SET_OLD_SQL_NOTES = "/*! SET SQL_NOTES=@OLD_SQL_NOTES */;";

	public static final String DB_SCHEMA_TABLE_STRUCTURE_COMMENT = "/*Table structure for table `%s` */";

	public static final String DB_SCHEMA_DROP_TABLE = "DROP TABLE IF EXISTS `%s`;";
	public static final String NEW_LINE = "\n";
	public static final String SEMI_COLON = ";";

	public static final String DB_DATA_INSERT_COMMAND = "insert  into `%s` ";

	public static final String DB_OPEN_PARENTHESIS = "(";
	public static final String DB_CLOSE_PARENTHESIS = ")";

	public static final String DB_DATA_INSERT_VALUES = " values";
	public static final String DB_SINGLE_QOTE = "`";
	public static final String DB_COLUMN_SEPRATE_BY_COMMA = ",";
	public static final String SINGLE_QUOTE = "'";

	public static final String DB_MYSQL_DATA_TYPE_INT = "INT";
	public static final String DB_MYSQL_DATA_TYPE_TINYINT ="TINYINT";
	public static final String DB_MYSQL_DATA_TYPE_SMALLINT = "SMALLINT";
	public static final String DB_MYSQL_DATA_TYPE_MEDIUMINT = "MEDIUMINT";
	public static final String DB_MYSQL_DATA_TYPE_BIGINT = "BIGINT";
	
	public static final String DB_MYSQL_DATA_TYPE_CHAR = "CHAR";
	public static final String DB_MYSQL_DATA_TYPE_DECIMAL = "DECIMAL";

	public static final String DB_MYSQL_DATA_TYPE_VARCHAR = "VARCHAR";
	public static final String DB_MYSQL_DATA_TYPE_DATE = "DATE";
	public static final String DB_MYSQL_DATA_TYPE_TEXT = "TEXT";
	
	public static final String DB_MYSQL_DATA_TYPE_BINARY = "BINARY";
	public static final String DB_MYSQL_DATA_TYPE_VARBINARY = "VARBINARY";
	public static final String DB_MYSQL_DATA_TYPE_ENUM = "ENUM";
	public static final String DB_MYSQL_DATA_TYPE_SET = "SET";
	public static final String DB_MYSQL_DATA_TYPE_MEDIUMTEXT = "MEDIUMTEXT";
	public static final String DB_MYSQL_DATA_TYPE_MEDIUMBLOB = "MEDIUMBLOB";
	
	public static final String DB_MYSQL_DATA_TYPE_CLOB = "CLOB";
	public static final String DB_MYSQL_DATA_TYPE_BLOB = "BLOB";
	
	public static final String DB_MYSQL_DATA_TYPE_TINYTEXT = "TINYTEXT";
	public static final String DB_MYSQL_DATA_TYPE_LONGTEXT = "LONGTEXT";
	
	
	

}
