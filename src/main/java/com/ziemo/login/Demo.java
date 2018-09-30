package com.ziemo.login;

import com.sun.net.httpserver.HttpServer;
import com.ziemo.login.controller.Static;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Demo {

	public static void main(String[] args) {
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
			server.createContext("/", new DemoC());
			server.createContext("/static", new Static());
			server.setExecutor(null);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
