package com.ziemo.login;

import com.sun.net.httpserver.HttpServer;
import com.ziemo.login.dao.SessionDao;
import com.ziemo.login.model.Server;
import com.ziemo.login.view.WebDisplay;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App {

	public static void main(String[] args) {
		Server server = new Server();
		try {
			cleanUpSessions();
			server.setServer(8000);
			server.runServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void cleanUpSessions() {
		SessionDao dao = new SessionDao();
		dao.clearExpiredSessions();
	}
}

