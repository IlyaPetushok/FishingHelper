create table words_for_find(
	id_words integer generated always as identity,
	name character varying(50) unique,
	primary key (id_words) 
);