CREATE TABLE users (
	id serial not null,
	username varchar(255) not null,
	password varchar(10) not null,
	
	CONSTRAINT pk_user PRIMARY KEY (id)
)

CREATE TABLE task(
	id serial not null,
	id_user integer not null,
	description text not null,
	creation_date timestamp without time zone,
	time_to_complete time without time zone,
	completed boolean default false,
	
	CONSTRAINT pk_task PRIMARY KEY (id),
	CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES users(id)
)
