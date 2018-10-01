package com.ziemo.login.model;

import com.sun.net.httpserver.HttpServer;
import com.ziemo.login.SetLog;
import com.ziemo.login.controller.LoginController;
import com.ziemo.login.controller.LogoutController;
import com.ziemo.login.controller.RegisterController;
import com.ziemo.login.controller.Static;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;

@Slf4j
public class Server {

	HttpServer server;

	public void setServer(int port) throws IOException {
		SetLog.getLog();
		server = HttpServer.create(new InetSocketAddress(port), 0);
		server.createContext("/login", new LoginController());
		server.createContext("/logout", new LogoutController());
		server.createContext("/registry", new RegisterController());
		server.createContext("/static", new Static());

		server.setExecutor(null);
	}

	public void runServer() {
		server.start();
		log.info("server is running..");
	}
}
