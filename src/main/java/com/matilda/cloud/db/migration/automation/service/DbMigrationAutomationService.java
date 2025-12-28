package com.matilda.cloud.db.migration.automation.service;

import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_SCHEMA_REFERENCES;

import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_SCHEMA_DROP_TABLE;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_SCHEMA_COMMENT_START;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_SCHEMA_COMMENT_LINE_SYMBOL_STAR;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_SCHEMA_COMMENT_HEADER;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_SCHEMA_COMMENT_END;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_SCHEMA_SET_NAMES_UTF8;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_SCHEMA_SET_SQL_MODE_EMPTY;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_SCHEMA_SET_UNIQUE_CHECKS;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_SCHEMA_SET_FOREIGN_KEY_CHECKS;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_SCHEMA_SET_SQL_MODE;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_SCHEMA_SET_SQL_NOTES;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_SCHEMA_SET_OLD_SQL_MODE;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_SCHEMA_SET_OLD_FOREIGN_KEY_CHECKS;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_SCHEMA_SET_OLD_UNIQUE_CHECKS;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_SCHEMA_SET_OLD_SQL_NOTES;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_SCHEMA_TABLE_STRUCTURE_COMMENT;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.NEW_LINE;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.SEMI_COLON;
import static com.matilda.cloud.db.migration.automation.utils.DbMigrationUtils.*;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_DATA_INSERT_COMMAND;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_SINGLE_QOTE;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_COLUMN_SEPRATE_BY_COMMA;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_OPEN_PARENTHESIS;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_CLOSE_PARENTHESIS;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_DATA_INSERT_VALUES;

import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_MYSQL_DATA_TYPE_INT;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_MYSQL_DATA_TYPE_DECIMAL;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_MYSQL_DATA_TYPE_SMALLINT;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_MYSQL_DATA_TYPE_VARCHAR;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_MYSQL_DATA_TYPE_DATE;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_MYSQL_DATA_TYPE_TEXT;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_MYSQL_DATA_TYPE_MEDIUMTEXT;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_MYSQL_DATA_TYPE_MEDIUMBLOB;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_MYSQL_DATA_TYPE_CLOB;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_MYSQL_DATA_TYPE_BLOB;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_MYSQL_DATA_TYPE_TINYTEXT;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_MYSQL_DATA_TYPE_LONGTEXT;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_MYSQL_DATA_TYPE_TINYINT;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_MYSQL_DATA_TYPE_MEDIUMINT;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_MYSQL_DATA_TYPE_BIGINT;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_MYSQL_DATA_TYPE_CHAR;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.SINGLE_QUOTE;

import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_SCHEMA_CREATE_COMMAND;
import static com.matilda.cloud.db.migration.automation.constants.DbMigrationAutomationConstants.DB_SCHEMA_USE_WITH_SINGLE_QOUTE;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.matilda.cloud.db.migration.automation.interfaces.DbMigrationAutomationInterface;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Service
@Slf4j
public class DbMigrationAutomationService implements DbMigrationAutomationInterface {

	@Autowired
	JdbcTemplate jdbcTemplate;
	DatabaseMetaData metadata;
	static String bucketName = "dbmigrationmatilda"; // bucket name
	List<HashMap<String, String>> refNonRefSchemaMapList = null;

	public void retriveMetadataDetails() throws SQLException {
		Connection jdbcConnection = null;
		DatabaseMetaData metaData = null;
		String schemaName = null;
		try {
			jdbcConnection = jdbcTemplate.getDataSource().getConnection();
			metaData = jdbcConnection.getMetaData();
			schemaName = jdbcConnection.getCatalog();

			printDatabaseMetadata(metaData);

			ArrayList<String> tablesList = getTablesMetadata(metaData, schemaName);
			if (CollectionUtils.isEmpty(tablesList)) {
				//log.info("Schema does not have the tables.");
				System.out.println("Schema does not have the tables.");
				return;
			}

			//String tableName = "orders";
			//ArrayList<String> tablesList = new ArrayList<>();
			//tablesList.add(tableName);
			String schemaScript = createTablesSchema(jdbcConnection, tablesList, schemaName);
			//System.out.println(schemaScript);

			String dataScript = collectTablesData(jdbcConnection, tablesList, schemaName);
			//System.out.println(dataScript);

			// write it to file
			try {
				FileWriter fw = new FileWriter(schemaName+"_schema.sql", true);
				PrintWriter out = new PrintWriter(fw);
				out.println(schemaScript);
				out.close();
				
				fw = new FileWriter(schemaName+"_data.sql", true);
				out = new PrintWriter(fw);
				out.println(dataScript);
				out.close();
			} catch (IOException e) {
				log.error("File writing failed: "+e.getMessage());
			}
			
			/*
			 * if(MapUtils.isNotEmpty(tableSchemaMap)) { refNonRefSchemaMapList =
			 * splitNonReferenceTablesSchema(tableSchemaMap); }
			 * 
			 * if(CollectionUtils.isNotEmpty(refNonRefSchemaMapList)) {
			 * System.out.println("::::::: References table schema list :::::::::");
			 * refNonRefSchemaMapList.get(0).forEach((k, v) -> {System.out.println(v); });
			 * System.out.println("::::::: Non References table schema list :::::::::");
			 * refNonRefSchemaMapList.get(1).forEach((k, v) -> {System.out.println(v); }); }
			 */

			// pushtos3bucket
			String fileName = schemaName+"_schema.sql";
			accessS3Bucket(fileName);

			fileName = schemaName+"_data.sql";
			accessS3Bucket(fileName);
		} catch (SQLException e) {
			e.printStackTrace();
		 log.error("Error getting the metadata: " + e.getMessage());
		} finally {
			if (metaData != null) {
				metaData = null;
			}
			if (jdbcConnection != null) {
				jdbcConnection.close();
			}
		}
	}

	public List<HashMap<String, String>> splitNonReferenceTablesSchema(HashMap<String, String> tableSchemaMap) {
		List<HashMap<String, String>> refNonRefSchemaMapList = new ArrayList<>();
		HashMap<String, String> nonRefSchemaMap = new HashMap<String, String>();
		HashMap<String, String> refSchemaMap = new HashMap<String, String>();

		tableSchemaMap.forEach((k, v) -> {
			if (v.toLowerCase().contains(DB_SCHEMA_REFERENCES.toLowerCase())) {
				refSchemaMap.put(k, v);
			} else {
				nonRefSchemaMap.put(k, v);
			}
		});

		refNonRefSchemaMapList.add(refSchemaMap);
		refNonRefSchemaMapList.add(nonRefSchemaMap);

		return refNonRefSchemaMapList;
	}

	public void accessS3Bucket(String fileName) {
		// getting all file names in a directory
		String key = fileName;
		// pass your keys which you get from AWS
	
				
		
		AwsBasicCredentials awsCreds = AwsBasicCredentials.create("",
				"");

		S3AsyncClient client = S3AsyncClient.builder().credentialsProvider(StaticCredentialsProvider.create(awsCreds))
				.region(software.amazon.awssdk.regions.Region.US_WEST_2).build();

		// Created a stream of files, and calling write on each file
		CompletableFuture<PutObjectResponse> future = client.putObject(
				PutObjectRequest.builder().bucket(bucketName).key(key).build(),
				AsyncRequestBody.fromFile(Paths.get(fileName)));

		future.whenComplete((resp, err) -> {
			try {
				if (resp != null) {
					System.out.println("my response: " + resp);
				} else {
					// Handle error
					err.printStackTrace();
				}
			} finally {
				// Lets the application shut down. Only close the client when you are completely
				// done with it.
				client.close();
			}
		});
	}

	/**
	 * Prints the Database metadata.
	 * 
	 * @throws SQLException
	 */
	public void printDatabaseMetadata(DatabaseMetaData metaData) throws SQLException {
		try {
			metaData.getDatabaseProductName();
			/*
			 * System.out.println("******************************************************");
			 * System.out.println("****************DATABASE DETAILS**********************");
			 * System.out.println("******************************************************");
			 * System.out.println("Database Product Name: " +
			 * metaData.getDatabaseProductName());
			 * System.out.println("Database Product Version: " +
			 * metaData.getDatabaseProductVersion()); System.out.println("Schema Term : " +
			 * metaData.getSchemaTerm()); System.out.println("Logged User: " +
			 * metaData.getUserName()); System.out.println("JDBC Driver: " +
			 * metaData.getDriverName()); System.out.println("Driver Version: " +
			 * metaData.getDriverVersion()); System.out.println("\n");
			 * System.out.println("******************************************************");
			 * System.out.println("\n"); System.out.println("\n");
			 */
		} catch (SQLException e) {
			// log.error("Error getting the metadata: " + e.getMessage());
		}
	}

	/**
	 * 
	 * @return ArrayList with the table's name
	 * @throws SQLException
	 */
	public static ArrayList<String> getTablesMetadata(DatabaseMetaData metaData, String schemaName)
			throws SQLException {
		String table[] = { "TABLE" };
		ResultSet resultSet = null;
		ArrayList<String> tables = null;

		try {
			resultSet = metaData.getTables(schemaName, null, null, table);
			tables = new ArrayList<>();
			while (resultSet.next()) {
				tables.add(resultSet.getString("TABLE_NAME"));
			}

			System.out.println("******************************************************");
			System.out.println("****************TABLES("+tables.size()+")******************************");
			System.out.println("******************************************************");
			for (String tablename : tables) {
				System.out.println("               " + tablename.toUpperCase() + "                  ");
			}

			System.out.println("******************************************************");
			System.out.println("\n");
			System.out.println("\n");

		} finally {

			if (resultSet != null) {
				resultSet.close();
			}
		}
		return tables;
	}

	/**
	 * Prints i columns metadata.
	 * 
	 * @param tables
	 * @throws SQLException
	 */
	public static void getColumnsMetadata(ArrayList<String> tables, DatabaseMetaData metaData) throws SQLException {
		ResultSet resultSet = null;
		ResultSet primarykeyResultSet = null;
		try {
			for (String table : tables) {
				resultSet = metaData.getColumns(null, null, table, null);
				System.out.println("******************************************************");
				System.out.println(table.toUpperCase());
				System.out.println("******************************************************");
				System.out.println("\tCOLUMN_NAME \tTYPE_NAME \tCOLUMN_SIZE ");
				System.out.println("******************************************************");
				while (resultSet.next()) {
					System.out.println("\t" + resultSet.getString("COLUMN_NAME") + " \t"
							+ resultSet.getString("TYPE_NAME") + " \t" + resultSet.getString("COLUMN_SIZE"));
				}

				/*
				 * primarykeyResultSet = metaData.getPrimaryKeys(null, null, table); while
				 * (primarykeyResultSet.next()) {
				 * System.out.println(primarykeyResultSet.getString("COLUMN_NAME")); }
				 */
				ResultSetMetaData resultMeta = resultSet.getMetaData();
				for (int i = 1; i <= resultMeta.getColumnCount(); i++) {
					String fieldName = resultMeta.getColumnName(i);
					int type = resultMeta.getColumnType(i); // java.sql.Types
					// Class _fieldType = convertType(type);
					boolean nullable = resultMeta.isNullable(i) != java.sql.ResultSetMetaData.columnNoNulls;
					boolean isAutoincrement = resultMeta.isAutoIncrement(i);
					// System.out.println("Field Name: " + fieldName + " : type : " + type + " ,
					// nullable: " + nullable
					// + ", autoincrement: " + isAutoincrement );
				}

				System.out.println("\n");
			}
			System.out.println("******************************************************");
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
		}

	}

	public String createTablesSchema(Connection connection, List<String> tableNameList, String schemaName)
			throws SQLException {
		HashMap<String, String> tableSchemaMap = new HashMap<String, String>();
		Statement stmt = connection.createStatement();
		ResultSet rs = null;
		String dropTableScript = null;
		StringBuilder schemaBuilder = new StringBuilder();
		schemaBuilder.append(DB_SCHEMA_COMMENT_START + NEW_LINE);
		schemaBuilder.append(DB_SCHEMA_COMMENT_LINE_SYMBOL_STAR + NEW_LINE);
		schemaBuilder.append(DB_SCHEMA_COMMENT_HEADER + NEW_LINE);
		schemaBuilder.append(DB_SCHEMA_COMMENT_LINE_SYMBOL_STAR + NEW_LINE);
		schemaBuilder.append(DB_SCHEMA_COMMENT_END + NEW_LINE);
		schemaBuilder.append(DB_SCHEMA_SET_NAMES_UTF8 + NEW_LINE);
		schemaBuilder.append(DB_SCHEMA_SET_SQL_MODE_EMPTY + NEW_LINE);
		schemaBuilder.append(DB_SCHEMA_SET_UNIQUE_CHECKS + NEW_LINE);
		schemaBuilder.append(DB_SCHEMA_SET_FOREIGN_KEY_CHECKS + NEW_LINE);
		schemaBuilder.append(DB_SCHEMA_SET_SQL_MODE + NEW_LINE);
		schemaBuilder.append(DB_SCHEMA_SET_SQL_NOTES + NEW_LINE+NEW_LINE);
		schemaBuilder.append(String.format(DB_SCHEMA_CREATE_COMMAND, schemaName) + NEW_LINE + NEW_LINE);
		schemaBuilder.append(String.format(DB_SCHEMA_USE_WITH_SINGLE_QOUTE, schemaName) + NEW_LINE + NEW_LINE);

		for (String tableName : tableNameList) {
			dropTableScript = String.format(DB_SCHEMA_DROP_TABLE, tableName) + NEW_LINE;
			rs = stmt.executeQuery("show create table " + schemaName + "." + tableName);
			if (rs.next()) {
				schemaBuilder.append(
						NEW_LINE + String.format(DB_SCHEMA_TABLE_STRUCTURE_COMMENT, tableName) + NEW_LINE + NEW_LINE);
				schemaBuilder.append(dropTableScript);
				tableSchemaMap.put(rs.getString(1), dropTableScript + rs.getString(2));
				schemaBuilder.append(rs.getString(2) + SEMI_COLON + NEW_LINE);
			}
			if (rs != null) {
				rs.close();
			}
		}
		stmt.close();
		schemaBuilder.append(NEW_LINE + NEW_LINE + DB_SCHEMA_SET_OLD_SQL_MODE + NEW_LINE);
		schemaBuilder.append(DB_SCHEMA_SET_OLD_FOREIGN_KEY_CHECKS + NEW_LINE);
		schemaBuilder.append(DB_SCHEMA_SET_OLD_UNIQUE_CHECKS + NEW_LINE);
		schemaBuilder.append(DB_SCHEMA_SET_OLD_SQL_NOTES + NEW_LINE);

		// System.out.println(schemaBuilder.toString());
		tableSchemaMap.forEach((k, v) -> {
			// System.out.println(v);

			// write it to file
			/*
			 * try { FileWriter fw = new FileWriter("schema.txt",true); PrintWriter out =
			 * new PrintWriter(fw);
			 * 
			 * // Write the name of four oceans to the file // out.println(v);
			 * 
			 * // Close the file. out.close(); // Step 4le } catch (IOException e) {
			 * 
			 * }
			 */
		});
		return schemaBuilder.toString();
	}

	public String collectTablesData(Connection connection, List<String> tableNameList, String schemaName)
			throws SQLException {
		StringBuilder dataBuilder = new StringBuilder();
		int tableCount = tableNameList.size();
		for (String tableName : tableNameList) {
			dataBuilder.append(getTableData(connection, tableName, schemaName));
		}
		return dataBuilder.toString();
	}
	
	public String getTableData(Connection connection, String tableName, String schemaName) {
		ResultSet rs = null;
		StringBuilder dataBuilder = new StringBuilder();
		ResultSetMetaData rsmd = null;
		PreparedStatement preparedStatement = null;
		LinkedHashMap<String, String> columnTypeMap = new LinkedHashMap<>();
		final StringBuilder insertBuilder = new StringBuilder();
		String insertFormatData = null;
		String columnsFormat = null;

		try {
			preparedStatement = connection.prepareStatement("select * from  " + schemaName + "." + tableName,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			rs = preparedStatement.executeQuery();
			rsmd = rs.getMetaData();

			for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
				//System.out.print(rsmd.getColumnName(i) + ":" + rsmd.getColumnTypeName(i) + " ");
				columnTypeMap.put(rsmd.getColumnName(i), rsmd.getColumnTypeName(i));
			}
			//System.out.println(" ");
			insertFormatData = String.format(DB_DATA_INSERT_COMMAND, tableName);
			columnsFormat = convertTableColumns(columnTypeMap);
			int count = 0;
			while (rs.next()) {
				count++;
				StringBuilder insertRowData = reuseStringBuilder(insertBuilder);
				if (count == 1) {
					insertRowData.append(insertFormatData);
					insertRowData.append(columnsFormat);
					insertRowData.append(DB_DATA_INSERT_VALUES);
					insertRowData.append(NEW_LINE);
				}

				insertRowData.append(DB_OPEN_PARENTHESIS);
				int columnCount = columnTypeMap.entrySet().size();
				int commaCount = 1;
				for (Map.Entry<String, String> columnEntry : columnTypeMap.entrySet()) {
					insertRowData.append(getColumndata(columnEntry.getKey(), columnEntry.getValue(), rs));
					if (commaCount < columnCount) {
						insertRowData.append(DB_COLUMN_SEPRATE_BY_COMMA);
						commaCount++;
					} else {
						commaCount = 0;
					}
				}
				insertRowData.append(DB_CLOSE_PARENTHESIS);
				if (rs.isLast()) {
					insertRowData.append(SEMI_COLON);
				} else {
					insertRowData.append(DB_COLUMN_SEPRATE_BY_COMMA);
				}
				insertRowData.append(NEW_LINE);
				//System.out.println(insertRowData.toString());
				dataBuilder.append(insertRowData.toString());
			}
			//System.out.println("------ fsdffs  fdgfgfdg ---------");
			if (rs != null) {
				rs.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
				preparedStatement = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataBuilder.toString();
	}
		
	public Object getColumndata(String columnName, String columnType, ResultSet rs) throws SQLException {
		Object value = null;
		switch (columnType) {
		case DB_MYSQL_DATA_TYPE_TINYINT:
		case DB_MYSQL_DATA_TYPE_INT:
		case DB_MYSQL_DATA_TYPE_SMALLINT:
		case DB_MYSQL_DATA_TYPE_MEDIUMINT:
		case DB_MYSQL_DATA_TYPE_BIGINT:
			value = convertToNumber(rs.getObject(columnName));
			break;
			
		case DB_MYSQL_DATA_TYPE_CHAR:
		case DB_MYSQL_DATA_TYPE_VARCHAR:
		case DB_MYSQL_DATA_TYPE_TEXT:
		case DB_MYSQL_DATA_TYPE_MEDIUMTEXT:
		case DB_MYSQL_DATA_TYPE_TINYTEXT:
		case DB_MYSQL_DATA_TYPE_LONGTEXT:
		case DB_MYSQL_DATA_TYPE_DECIMAL:
			value = convertToString(rs.getObject(columnName));
			break;


		case DB_MYSQL_DATA_TYPE_DATE:
			value = convertDateToString(rs.getDate(columnName));
			break;
			
		case DB_MYSQL_DATA_TYPE_CLOB:
			value = rs.getClob(columnName);
			break;
		case DB_MYSQL_DATA_TYPE_BLOB:
		case DB_MYSQL_DATA_TYPE_MEDIUMBLOB:
			value = rs.getBlob(columnName);
			break;

		default:
			value = rs.getObject(columnName);
			break;
		}
		
		return value;
	}
	
	
	private Long convertToNumber(Object object) {
		Long numberValue = null;
		try {
			if (object != null) {
				if (object instanceof Long)
					numberValue = ((Long) object).longValue();
				else if (object instanceof Integer) {
					numberValue = ((Integer) object).longValue();
				}
				if (object instanceof Byte) {
					numberValue = ((Integer) object).longValue();
				} else if (object instanceof String) {
					numberValue = Long.valueOf((String) object);
				}
			}

		} catch (Exception e) {
			// do something
		}
		return numberValue;
	}
	
	private String convertToString(Object object) {
		String stringValue = null;
		if (object != null) {
			stringValue = SINGLE_QUOTE+object.toString().replaceAll("\"", "\\\"").replaceAll("'", "\\'").replaceAll("\r\n", "\\r\\n")+SINGLE_QUOTE;
		}
		
		return stringValue;
	}
	
	private String convertDateToString(Date date) {
		String dateStringValue = null;
		if (date != null) {
			dateStringValue = SINGLE_QUOTE + date.toString() + SINGLE_QUOTE;
		}
		return dateStringValue;
	}

	public String convertTableColumns(LinkedHashMap<String, String> columnTypeMap) {
		return columnTypeMap.keySet().stream().map(key -> DB_SINGLE_QOTE + key + DB_SINGLE_QOTE)
				.collect(Collectors.joining(DB_COLUMN_SEPRATE_BY_COMMA, DB_OPEN_PARENTHESIS, DB_CLOSE_PARENTHESIS));
	}

	public static void spitOutAllTableRows(String tableName, Connection conn) {
		try {
			System.out.println("current " + tableName + " is:");
			try (PreparedStatement selectStmt = conn.prepareStatement("SELECT * from " + tableName,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ResultSet rs = selectStmt.executeQuery()) {
				if (!rs.isBeforeFirst()) {
					System.out.println("no rows found");
				} else {
					System.out.println("types:");
					for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
						System.out.print(rs.getMetaData().getColumnName(i + 1) + ":"
								+ rs.getMetaData().getColumnTypeName(i + 1) + " ");
					}
					System.out.println();
					while (rs.next()) {
						for (int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
							System.out.print(" " + rs.getMetaData().getColumnName(i) + "=" + rs.getNString(i));
						}
						System.out.println("");
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void processLargeTable(Connection connection, List<String> tableNameList, String schemaName)
			throws SQLException {
		PreparedStatement prepStmt = connection.prepareStatement("SELECT * FROM table", ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_READ_ONLY);
		prepStmt.setFetchSize(Integer.MIN_VALUE);
		ResultSet results = prepStmt.executeQuery();
		while (results.next()) {

		}
	}

	public void testForeignKeyIsNotDuplicated(Connection connection, String schemaName, String tableName)
			throws SQLException {
		DatabaseMetaData meta = connection.getMetaData();
		System.out.println("Scheama:=" + schemaName);
		ResultSet rs = meta.getImportedKeys(connection.getCatalog(), null, tableName);
		int numberOfFk = 0;
		while (rs.next()) {
			String pkTableCatalog = rs.getString("PKTABLE_CAT");
			String pkTableSchema = rs.getString("PKTABLE_SCHEM");
			String pkTableName = rs.getString("PKTABLE_NAME");
			String pkColumnName = rs.getString("PKCOLUMN_NAME");

			String fkTableCatalog = rs.getString("FKTABLE_CAT");
			String fkTableSchema = rs.getString("FKTABLE_SCHEM");
			String fkTableName = rs.getString("FKTABLE_NAME");
			String fkColumnName = rs.getString("FKCOLUMN_NAME");
			int fkSequence = rs.getInt("KEY_SEQ");

			numberOfFk++;

			// Additional logging
			System.out.println("getExportedKeys(): number of foreignKey=" + numberOfFk);

			System.out.println("getExportedKeys(): pkTableCatalog=" + pkTableCatalog);
			System.out.println("getExportedKeys(): pkTableSchema=" + pkTableSchema);
			System.out.println("getExportedKeys(): pkTableName=" + pkTableName);
			System.out.println("getExportedKeys(): pkColumnName=" + pkColumnName);

			System.out.println("getExportedKeys(): fkTableCatalog=" + fkTableCatalog);
			System.out.println("getExportedKeys(): fkTableSchema=" + fkTableSchema);
			System.out.println("getExportedKeys(): fkTableName=" + fkTableName);
			System.out.println("getExportedKeys(): fkColumnName=" + fkColumnName);

			System.out.println("getExportedKeys(): fkSequence=" + fkSequence);

			System.out.println();
		}
	}

}
