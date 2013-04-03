CREATE TABLE user (
	id			INTEGER PRIMARY KEY AUTO_INCREMENT,
	username	VARCHAR(255) NOT NULL UNIQUE,
	password	CHAR(128) NOT NULL
);

CREATE TABLE category (
	id		INTEGER PRIMARY KEY AUTO_INCREMENT,
	name	VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE city (
	id		INTEGER PRIMARY KEY AUTO_INCREMENT,
	name	VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE project (
	id			INTEGER PRIMARY KEY AUTO_INCREMENT,
	title		VARCHAR(255) NOT NULL UNIQUE,
	description TEXT,
	goal		DECIMAL(10,2) NOT NULL CHECK (goal >= 0.0),
	start_date	DATETIME NOT NULL,
	end_date	DATETIME NOT NULL,
	city_id		INTEGER NOT NULL,
	category_id	INTEGER NOT NULL,
	owner_id	INTEGER NOT NULL,
	FOREIGN KEY (city_id) REFERENCES city(id),
	FOREIGN KEY (category_id) REFERENCES category(id),
	FOREIGN KEY (owner_id) REFERENCES user(id)
);

CREATE TABLE funding_amount (
	id			INTEGER PRIMARY KEY AUTO_INCREMENT,
	amount		DECIMAL(10, 2) NOT NULL CHECK (amount > 0.0),
	reward		TEXT,
	project_id	INTEGER NOT NULL,
	FOREIGN KEY (project_id) REFERENCES project(id)
);

CREATE TABLE funds (
	id			INTEGER PRIMARY KEY AUTO_INCREMENT,
	user_id		INTEGER NOT NULL,
	fa_id		INTEGER	NOT NULL,
	FOREIGN KEY (user_id) REFERENCES user(id),
	FOREIGN KEY (fa_id) REFERENCES funding_amount(id)
);

CREATE TABLE streched_goal (
	id			INTEGER PRIMARY KEY AUTO_INCREMENT,
	goal		DECIMAL(10,2) NOT NULL CHECK (goal >= 0.0),
	bonus		TEXT NOT NULL,
	project_id	INTEGER NOT NULL,
	FOREIGN KEY (project_id) REFERENCES project(id)
);

CREATE TABLE comment (
	id			INTEGER PRIMARY KEY AUTO_INCREMENT,
	text		TEXT NOT NULL,
	create_date	TIMESTAMP,
	user_id		INTEGER NOT NULL,
	project_id	INTEGER NOT NULL,
	FOREIGN KEY (user_id) REFERENCES user(id),
	FOREIGN KEY (project_id) REFERENCES project(id)
);