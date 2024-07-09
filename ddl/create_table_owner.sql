create table owner(
	id_owner integer generated always as identity,
	name character varying(30) not null,
	phone_number character varying(13) unique not null,
	primary key(id_owner)
);