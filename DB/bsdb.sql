
CREATE TABLE ARENA
(
	ID INTEGER NOT NULL,
	NAME TEXT NOT NULL,
	LOCATION TEXT,
	PRIMARY KEY (ID)
);

/
CREATE TABLE GAME
(
	ID INTEGER NOT NULL UNIQUE,
	DATE TEXT NOT NULL,
	TIME TEXT NOT NULL,
	PLACE INTEGER DEFAULT 0 NOT NULL,
	TEAM_A INTEGER,
	TEAM_B INTEGER,
	PRIMARY KEY (ID),
	CONSTRAINT GAME_ID UNIQUE (DATE, TIME, PLACE)
);

/
CREATE TABLE PERSON
(
	ID INTEGER NOT NULL UNIQUE,
	NAME TEXT NOT NULL,
	ORGANIZATION INTEGER,
	PRIMARY KEY (ID)
);

/
CREATE TABLE POINT
(
	ID INTEGER NOT NULL UNIQUE,
	GAME INTEGER NOT NULL,
	PERSON INTEGER,
	SECTION TEXT,
	TIME INTEGER DEFAULT 0,
	AMOUNT INTEGER DEFAULT 1,
	PRIMARY KEY (ID)
);

/
CREATE TABLE TEAM
(
	ID INTEGER NOT NULL,
	NAME TEXT NOT NULL,
	LOCATION TEXT,
	LEADER TEXT,
	CONTACT TEXT,
	PRIMARY KEY (ID)
);


/
ALTER TABLE GAME
	ADD FOREIGN KEY (PLACE)
	REFERENCES ARENA (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

/
ALTER TABLE POINT
	ADD FOREIGN KEY (GAME)
	REFERENCES GAME (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

/
ALTER TABLE POINT
	ADD FOREIGN KEY (PERSON)
	REFERENCES PERSON (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

/
ALTER TABLE GAME
	ADD FOREIGN KEY (TEAM_B)
	REFERENCES TEAM (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

/
ALTER TABLE GAME
	ADD FOREIGN KEY (TEAM_A)
	REFERENCES TEAM (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

/
ALTER TABLE PERSON
	ADD FOREIGN KEY (ORGANIZATION)
	REFERENCES TEAM (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



