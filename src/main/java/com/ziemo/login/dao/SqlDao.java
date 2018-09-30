package com.ziemo.login.dao;

import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class SqlDao {

	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet result = null;
	private String results;

	public void handleQuery(String query, String[] data) {

		connection = ConnectDB.getConnection();

		try {
			buildQuery(query, data);

			if (query.startsWith("select")) {
				result = statement.executeQuery();
				saveResults();
			} else {
				statement.executeUpdate();
			}

			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void buildQuery(String query, String[] data) throws SQLException {
		statement = connection.prepareStatement(query);
		int counter = 1;
		for (String i : data) {
			statement.setString(counter, i);
			counter++;
		}
	}

	private void saveResults() throws SQLException {
		int columnCounter = result.getMetaData().getColumnCount();
		StringBuilder row = new StringBuilder();

		if (result.next()) {
			for (int i = 1; i <= columnCounter; i++) {
				row.append(result.getString(i)).append(",");
			}
			results = row.toString().substring(0,row.length()-1);
		}
	}

	private void close() throws SQLException {
		statement.close();
		if (result != null) {
			result.close();
		}
	}

	public String getResults() {
		return results;
	}

	public boolean isRecordExists() {
		return results != null;
	}

}
