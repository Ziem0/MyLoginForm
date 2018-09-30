package com.ziemo.login.helpers;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

public class HashPassword {

	public static String getPepper() {
		int startChar = 97;
		int endChar = 122;
		int finalLength = 30;
		StringBuilder pepper = new StringBuilder();

		for (int i = 0; i < finalLength; i++) {
			int randomChar = startChar
					+ (int) ((endChar - startChar + 1) * new Random().nextFloat());
			pepper.append((char) randomChar);
		}

		return pepper.toString();
	}

	public static String hashInput(String password, String pepper) {
		return DigestUtils.sha256Hex(password + pepper);
	}

//	public static void main(String[] args) {
//		String pepper = getPepper();
//		System.out.println(pepper);
//		System.out.println(hashInput("123", pepper));
//	}
}
