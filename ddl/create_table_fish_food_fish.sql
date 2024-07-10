create table fish_food_fish(
	fish_id_fish integer not null,
	food_fish_id_food integer not null,
	constraint fk_food foreign key (food_fish_id_food) references food_fish(id_food) on delete cascade,
	constraint fk_fish foreign key (fish_id_fish) references fish(id_fish) on delete cascade
);