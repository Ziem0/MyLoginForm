package com.ziemo.login.dao;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.ziemo.login.helpers.HashPassword;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import javax.xml.soap.SAAJMetaFactory;
import java.sql.Connection;

public class UserDao extends SqlDao {

	public void addUser(String login, String password) {
		String command = "insert into users values(?,?,?);";

		String salt = HashPassword.getPepper();
		String hashedPassword = HashPassword.hashInput(password, salt);

		handleQuery(command, new String[]{login, hashedPassword, salt});
	}

	public boolean isLoginCorrect(String login, String password) {

		String query = "select password, salt from users where login=?;";
		boolean isCorrectLogin = false;

		handleQuery(query, new String[]{login});

		if (isRecordExists()) {
			String dbPassword = getResults().split(",")[0];
			String salt = getResults().split(",")[1];

			String userHashedPass = HashPassword.hashInput(password, salt);

			isCorrectLogin = userHashedPass.equals(dbPassword);
		}
		return isCorrectLogin;
	}

	public boolean isLoginUnique(String login) {
		String query = "select login from users where login=?;";

		handleQuery(query, new String[]{login});

		return !isRecordExists();
	}

}
