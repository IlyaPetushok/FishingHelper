create table tags(
	id_tag integer generated always as identity,
	name character varying(50) unique,
	primary key (id_tag) 
);