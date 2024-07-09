create table survey_fish(
	survey_id_survey integer,
	fish_id_fish integer ,
	constraint fk_survey foreign key (survey_id_survey) references survey(id_survey),
	constraint fk_fish foreign key (fish_id_fish) references fish(id_fish)
);