create table if not exists users(
	login TXT NOT NULL UNIQUE,
	password TXT NOT NULL,
	salt TXT NOT NULL
);

create table if not exists sessions(
	sessionId TXT,
	userId TXT NOT NULL,
	creation TXT NOT NULL,
	last TXT NOT NULL,
	expire TXT NOT NULL,
	FOREIGN KEY(userId) references users(login) on delete cascade
);