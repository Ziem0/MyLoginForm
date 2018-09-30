package com.ziemo.login.dao;

import org.flywaydb.core.Flyway;
import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {

	private static Connection connection = null;
	private static final String DB_URL = "jdbc:sqlite:src/main/resources/db/data.db";

	private ConnectDB() {
	}

	public static Connection getConnection() {
		if (connection == null) {
			synchronized (ConnectDB.class) {
				if (connection == null) {
					createConnection();
					migrate();
				}
			}
		}
		return connection;
	}

	private static void createConnection() {
		try {
			DriverManager.registerDriver(new JDBC());
			connection = DriverManager.getConnection(DB_URL);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void migrate() {
		Flyway fw = new Flyway();
		fw.setDataSource(DB_URL, "none", "none");
		fw.migrate();
	}

	public static void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
