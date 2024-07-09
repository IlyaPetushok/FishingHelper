create table users(
	id_user integer generated always as identity,
	name character varying(30) unique not null,
	login character varying(30) unique not null,
	password character varying(50) not null,
	main character varying(50) unique,
	mail_status boolean default false,
	data_registration date ,
	primary key(id_user)
);