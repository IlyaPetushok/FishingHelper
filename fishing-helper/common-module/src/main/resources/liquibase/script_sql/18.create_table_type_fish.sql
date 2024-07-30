create table type_fish(
	id_type integer generated always as identity,
	type_name character varying(30) unique,
	primary key(id_type)
);