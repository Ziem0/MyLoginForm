package com.ziemo.login.dao;

import com.ziemo.login.model.Session;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SessionDao extends SqlDao{

	public void addSession(Session session, String login) {
		String command = "insert into sessions values(?,?,?,?,?);";

		handleQuery(command, new String[]{session.getSessionId()
				, login
				, session.getCreation().toString()
				, session.getLast().toString()
				, session.getExpire().toString()});
	}

	public void updateSession(String sessionId) {
		String command = "update sessions set last=?, expire=? where sessionId=?;";
		String now = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
		String expire = LocalDateTime.now().plusMinutes(1).format(DateTimeFormatter.ISO_DATE_TIME);

		handleQuery(command, new String[]{now
				, expire
				, sessionId});
	}

	public void clearExpiredSessions() {
		String dateNow = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
		String command = "delete from sessions where expire<?;";

		handleQuery(command, new String[]{dateNow});
	}

	public String getUserNameBySessionId(String sessionId) {
		String query = "select userId from sessions where sessionId=?;";

		handleQuery(query, new String[]{sessionId});

		return getResults();
	}

	public boolean isSessionActive(String sessionId) {
		String query = "select expire from sessions where sessionId=?;";

		handleQuery(query, new String[]{sessionId});

		if (!isRecordExists()) {
			return false;
		}
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expire = LocalDateTime.parse(getResults());

		return expire.isAfter(now);
	}

}
