package com.ziemo.login.view;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class WebDisplay {

	public String createLoginPage(String status) {
		JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/login.twig");
		JtwigModel model = JtwigModel.newModel();

		if (status != null) {
			String message = prepareStatus(status);
			model.with("message", message);
		}

		return template.render(model);
	}

	private String prepareStatus(String status) {
		String message = "";

		switch (status) {
			case "logout":
				message = "you have been log out!";
				break;
			case "expired":
				message = "Your session have expired!";
				break;
			case "invalid":
				message = "Wrong login or password!";
				break;
		}
		return message;
	}

	public String createWelcomePage(String name) {
		JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/logged.twig");
		JtwigModel model = JtwigModel.newModel();

		model.with("name",name);

		return template.render(model);
	}

	public String createRegistryPage() {
		JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/registry.twig");
		JtwigModel model = JtwigModel.newModel();

		model.with("message", null);

		return template.render(model);
	}

	public String createRegistryPage(Boolean originalLogin, Boolean matchingPassword) {
		JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/registry.twig");
		JtwigModel model = JtwigModel.newModel();

		String message = "";

		if (!matchingPassword) {
			message = "Invalid passwords matching! Both passwords should be the same!";
		} else if (!originalLogin) {
			message = "Given login already exists!";
		}
		model.with("message", message);

		return template.render(model);
	}

	public String createSuccessPage() {
		JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/success.twig");
		JtwigModel model = JtwigModel.newModel();

		return template.render(model);
	}

}
