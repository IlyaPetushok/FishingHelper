create table type_place(
	id_type integer generated always as identity,
	name character varying(30) unique,
    constraint check_name check (name in ('Речка','Озеро','Водаём','Болота')),
	primary key(id_type)
);