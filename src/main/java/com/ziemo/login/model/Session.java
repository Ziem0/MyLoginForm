package com.ziemo.login.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Getter
@Slf4j
public class Session {

	private final String sessionId;
	private LocalDateTime creation;
	private LocalDateTime last;
	private LocalDateTime expire;
	private final int duration = 1;

	public Session(String sessionId) {
		this.sessionId = sessionId;
		this.creation = LocalDateTime.now();
		this.last = creation;
		this.expire = creation.plusMinutes(duration);
		log.info("session created..");
	}
}

// session object is not created from database;
// object is created only for new session and save temporarly in database