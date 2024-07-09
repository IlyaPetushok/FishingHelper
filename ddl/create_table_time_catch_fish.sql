create table time_catch_fish(
	id_time integer generated always as identity,
	morning boolean default false,
	afternoon boolean default false,
	evening boolean default false,
	primary key (id_time)
);
