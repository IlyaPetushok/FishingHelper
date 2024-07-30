create table privileges(
	id_privileges integer generated always as identity,
	name character varying(30) unique,
	primary key(id_privileges)
);