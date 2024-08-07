create table place(
	id_place integer generated always as identity,
	name character varying(60) default 'place for catch fish',
	coordinates text not null,
	place_id_type integer not null,
	paid boolean default false,
	description text,
	rating integer default 0,
	status character varying(30) null,
	place_id_owner integer null,
	constraint check_status check(status in ('одобренно','неодобренно', 'удалено', 'в обработке')),
	constraint fk_owner foreign key (place_id_owner) references owner(id_owner),
	constraint fk_type foreign key (place_id_type) references type_place(id_type) on delete cascade,
	primary key(id_place)
);