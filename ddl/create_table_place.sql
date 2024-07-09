create table place(
	id_place integer generated always as identity,
	name character varying(40) default 'place for catch fish',
	coordinates text not null,
	place_id_type integer not null,
	paid boolean default false,
	description text,
	probabylity integer default 0,
	processing_status boolean default false,
	place_id_owner integer null,
	constraint fk_owner foreign key (place_id_owner) references owner(id_owner),
	constraint fk_type foreign key (place_id_type) references type_place(id_type),
	primary key(id_place)
);