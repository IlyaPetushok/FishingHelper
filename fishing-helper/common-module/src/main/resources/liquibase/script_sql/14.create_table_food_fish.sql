create table food_fish(
	id_food integer generated always as identity,
	name character varying(30) unique,
	primary key(id_food)
);