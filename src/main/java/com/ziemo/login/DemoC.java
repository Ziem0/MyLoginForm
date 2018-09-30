package com.ziemo.login;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.ziemo.login.view.WebDisplay;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;

public class DemoC implements HttpHandler {


	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
//		String response = new WebDisplay().createLoginPage(null);

		JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/login.twig");
		JtwigModel model = JtwigModel.newModel();
		model.with("name", "ziemo");
		String response = template.render(model);

		httpExchange.sendResponseHeaders(200, response.length());
		OutputStream os = httpExchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}
