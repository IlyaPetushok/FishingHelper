create table survey(
	id_survey integer generated always as identity,
	survey_id_user integer not null,
	survey_id_place integer not null,
	survey_id_time integer not null,
	primary key (id_survey),
	constraint fk_user foreign key (survey_id_user) references users(id_user),
	constraint fk_place foreign key (survey_id_place) references place(id_place),
	constraint fk_time foreign key (survey_id_time) references time_catch_fish(id_time)
);