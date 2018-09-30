package com.ziemo.login.helpers;

import com.sun.net.httpserver.HttpExchange;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CookieHandler {

	public static String getSessionId(HttpExchange exchange) {

		String cookie = exchange.getRequestHeaders().getFirst("Cookie");

		System.out.println(cookie);

		if (cookie != null) {
			cookie = parseCookie(cookie).get("sessionId");
		}

		System.out.println(cookie);

		return cookie;
	}

	private static Map<String, String> parseCookie(String cookie) {

		Map<String, String> values = new HashMap<>();

		String[] pairs = cookie.split("; ");

		for (String pair : pairs) {
			String[] keyValue = pair.split("=");

			if (keyValue.length == 2) {
				values.put(keyValue[0], keyValue[1]);
			} else {
				values.put(keyValue[0], null);
			}
		}

		return values;
	}

	public static String getSessionStatus(HttpExchange exchange) {

		String cookie = exchange.getRequestHeaders().getFirst("Cookie");

		if (cookie != null) {
			cookie = parseCookie(cookie).get("sessionStatus");
		}

		return cookie;
	}

	public static String setNewSessionId(HttpExchange exchange) {

		String newSessionId = HashPassword.getPepper();
		String cookie = "sessionId=" + newSessionId + createExpireString();

		exchange.getResponseHeaders().add("Set-Cookie", cookie);

		return newSessionId;
	}

	private static String createExpireString() {
		StringBuilder expireString = new StringBuilder("; expire=");

//		String date = LocalDateTime.MAX.format(DateTimeFormatter.ISO_DATE_TIME); generalnie nie jest potrzebne
		String date = LocalDateTime.now().plusMinutes(1).format(DateTimeFormatter.ISO_DATE_TIME);
		expireString.append(date);

		return expireString.toString();
	}

	public static void setStatusToLoggedIn(HttpExchange exchange) {
		String cookie = "sessionStatus=loggedIn" + createExpireString();

		exchange.getResponseHeaders().add("Set-Cookie", cookie);
	}

	public static void setStatusToLoggedOut(HttpExchange exchange) {
		String cookie = "sessionStatus=loggedOut" + createExpireString();

		exchange.getResponseHeaders().add("Set-Cookie", cookie);
	}

	public static void clearCookie(HttpExchange exchange) {
		exchange.getResponseHeaders().add("Set-Cookie", "sessionId=");
		exchange.getResponseHeaders().add("Set-Cookie", "sessionStatus=");
	}

}
