package com.ziemo.login.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.ziemo.login.dao.SessionDao;
import com.ziemo.login.dao.UserDao;
import com.ziemo.login.view.WebDisplay;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public abstract class HttpController implements HttpHandler {

	final UserDao userDao = new UserDao();
	final SessionDao sessionDao = new SessionDao();

	final WebDisplay display = new WebDisplay();

	HttpExchange exchange;
	String response;
	String login;
	String password;
	String passwordRepeated;

	void sendResponse() throws IOException {

		exchange.sendResponseHeaders(200, response.getBytes().length);

		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

	void readInputs() throws IOException {

		InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
		BufferedReader br = new BufferedReader(isr);

		String inputs = br.readLine();

		Map<String, String> formedInputs = formInputs(inputs);

		login = formedInputs.get("login");
		password = formedInputs.get("password");
		passwordRepeated = formedInputs.get("password2");
	}

	private Map<String, String> formInputs(String inputs) throws UnsupportedEncodingException {

		Map<String, String> formed = new HashMap<>();
		String[] pairs = inputs.split("&");

		for (String pair : pairs) {
			String[] keyValue = pair.split("=");
			String value = URLDecoder.decode(keyValue[1], "UTF-8");
			formed.put(keyValue[0], value);
		}

		return formed;
	}

	void setExchange(HttpExchange exchange) {

		this.exchange = exchange;
	}

	String getMethod() {
		return exchange.getRequestMethod();
	}

}
