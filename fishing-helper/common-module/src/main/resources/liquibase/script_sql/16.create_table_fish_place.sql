create table fish_place(
	fish_id_fish integer not null,
	place_id_place integer not null,
	constraint fk_fish foreign key (fish_id_fish) references fish(id_fish) on delete cascade,
	constraint fk_place foreign key (place_id_place) references place(id_place) on delete cascade
);