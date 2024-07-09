create table type_fish_fish(
	type_fish_id_type integer not null,
	fish_id_fish integer not null,
	constraint fk_type foreign key (type_fish_id_type) references type_fish(id_type),
	constraint fk_fish foreign key (fish_id_fish) references fish(id_fish)
);