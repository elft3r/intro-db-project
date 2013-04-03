CREATE DATABASE dmdb2013;

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