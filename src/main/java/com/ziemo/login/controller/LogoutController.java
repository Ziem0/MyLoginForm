package com.ziemo.login.controller;

import com.sun.net.httpserver.HttpExchange;
import com.ziemo.login.helpers.CookieHandler;

import java.io.IOException;

public class LogoutController extends HttpController {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		CookieHandler.setStatusToLoggedOut(httpExchange);

		httpExchange.getResponseHeaders().set("Location", "/login");
		httpExchange.sendResponseHeaders(302, -1);
	}
}
