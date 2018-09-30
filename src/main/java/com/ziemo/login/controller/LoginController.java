package com.ziemo.login.controller;

import com.sun.net.httpserver.HttpExchange;
import com.ziemo.login.helpers.CookieHandler;
import com.ziemo.login.model.Session;

import java.io.IOException;

public class LoginController extends HttpController {
	private String method;

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		setExchange(httpExchange);
		method = getMethod();

		switch (getStatus()) {
			case "fresh":
				handleLoginProcess();
				break;
			case "expired":
				handleExpiredSession();
				break;
			case "active":
				handleActiveSession();
				break;
			case "loggedOut":
				handleLogoutSession();
				break;
		}
		sendResponse();
	}

	private void handleLogoutSession() {

		response = display.createLoginPage("logout");

		CookieHandler.clearCookie(exchange);
	}

	private void handleActiveSession() {

		String sessionId = CookieHandler.getSessionId(exchange);
		String userName = sessionDao.getUserNameBySessionId(sessionId);

		sessionDao.updateSession(sessionId);
		response = display.createWelcomePage(userName);
	}

	private void handleExpiredSession() {

		response = display.createLoginPage("expired");

		CookieHandler.clearCookie(exchange);
	}

	private void handleLoginProcess() throws IOException {

		if (method.equalsIgnoreCase("GET")) {
			response = display.createLoginPage(null);
		} else if (method.equalsIgnoreCase("POST")) {
			readInputs();
			checkLogin();
		}
	}

	private void checkLogin() {

		boolean isLoginCorrect = userDao.isLoginCorrect(login, password);

		if (isLoginCorrect) {
			createNewSession();
			response = display.createWelcomePage(login);
			CookieHandler.setStatusToLoggedIn(exchange);
		} else {
			response = display.createLoginPage("invalid");
		}
	}

	private void createNewSession() {

		String sessionId = CookieHandler.setNewSessionId(exchange);
		Session session = new Session(sessionId);

		sessionDao.addSession(session, login);
	}

	private String getStatus() {

		String statusHttp = CookieHandler.getSessionStatus(exchange);
		String sessionId = CookieHandler.getSessionId(exchange);
		String result;

		boolean active = sessionDao.isSessionActive(sessionId);

		if (statusHttp == null) {
			result = "fresh";
		} else if (statusHttp.equalsIgnoreCase("loggedOut")) {
			result = "loggedOut";
		} else {
			if (active) {               //loggedIn should be
				result = "active";
			} else {
				result = "expired";
			}
		}
		return result;
	}

}
