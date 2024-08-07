create table survey(
	id_survey integer generated always as identity,
	survey_id_user integer not null,
	survey_id_place integer not null,
	morning boolean default false,
    afternoon boolean default false,
    evening boolean default false,
	primary key (id_survey),
	constraint fk_user foreign key (survey_id_user) references users(id_user),
	constraint fk_place foreign key (survey_id_place) references place(id_place) on delete cascade
);