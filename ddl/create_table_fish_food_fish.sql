create table fish_food_fish(
	fish_id_fish integer,
	food_fish_id_food integer ,
	constraint fk_food foreign key (food_fish_id_food) references food_fish(id_food),
	constraint fk_fish foreign key (fish_id_fish) references fish(id_fish)
);