package com.ziemo.login;

import org.apache.log4j.*;
import org.apache.log4j.jdbc.JDBCAppender;
import org.flywaydb.core.Flyway;
import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SetLog {
	private static Connection connection = null;
	private static SetLog log = null;
	private static final String LOG_URL = "jdbc:sqlite:src/main/resources/db/data4log.db";

	private SetLog() {
		Layout lay1 = new PatternLayout("[%p] %c - %m - Data wpisu: %d %n");
		Appender app1 = new ConsoleAppender(lay1);
		BasicConfigurator.configure(app1);

		Appender appender3 = new JDBCAppender();
		((JDBCAppender) appender3).setBufferSize(1);
		((JDBCAppender) appender3).setUser("ziemo");
		((JDBCAppender) appender3).setPassword("123");
		((JDBCAppender) appender3).setDriver("org.sqlite.JDBC");
		((JDBCAppender) appender3).setURL(LOG_URL);
		((JDBCAppender) appender3).setSql("insert into LOGS(p,c,m,date) values('%p', '%c', '%m', '%d');");
		BasicConfigurator.configure(appender3);
	}

	public static SetLog getLog() {
		if (log == null) {
			synchronized (SetLog.class) {
				if (log == null) {
					clearDatabase();
					log = new SetLog();
				}
			}
		}
		return log;
	}

	private static void clearDatabase() {
		createConnection();
		String command = "delete from LOGS where date < ?;";

		handleQuery(command);
	}

	private static void handleQuery(String command) {
		try {
			PreparedStatement statement = connection.prepareStatement(command);
			String now = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
			statement.setString(1, now);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void createConnection() {
		try {
			DriverManager.registerDriver(new JDBC());
			connection = DriverManager.getConnection(LOG_URL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
