create table priviligies(
	id_priviligies integer generated always as identity,
	name character varying(30) unique,
	primary key(id_priviligies)
);