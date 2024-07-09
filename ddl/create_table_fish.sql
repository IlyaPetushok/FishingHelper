create table fish(
	id_fish integer generated always as identity,
	name character varying(30),
	description text,
	life_style text,
	primary key (id_fish)
);