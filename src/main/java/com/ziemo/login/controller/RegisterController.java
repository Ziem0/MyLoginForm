package com.ziemo.login.controller;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class RegisterController extends HttpController {

	private String method;

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		setExchange(httpExchange);
		method = httpExchange.getRequestMethod();

		if (method.equalsIgnoreCase("GET")) {
			response = display.createRegistryPage();
		} else if (method.equalsIgnoreCase("POST")) {
			handleInputData();
		}

		sendResponse();
	}

	private void handleInputData() throws IOException {

		readInputs();

		boolean isLoginUnique = userDao.isLoginUnique(login);
		boolean isPasswordsMactching = password.equals(passwordRepeated);

		if (isLoginUnique && isPasswordsMactching) {
			userDao.addUser(login, password);
			response = display.createSuccessPage();
		} else {
			response = display.createRegistryPage(isLoginUnique, isPasswordsMactching);
		}
	}

}
