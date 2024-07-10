create table fish(
	id_fish integer generated always as identity,
	name character varying(30) unique,
	description text,
	primary key (id_fish)
);