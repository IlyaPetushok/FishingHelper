create table users(
	id_user integer generated always as identity,
	name character varying(30) not null,
	login character varying(30) unique not null,
	password character varying(100) not null,
	mail character varying(50) unique null,
	mail_status boolean default false,
	data_registration date not null,
	primary key(id_user)
);