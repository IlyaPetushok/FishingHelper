create table comment(
	id_comment integer generated always as identity,
	text text null,
	grade integer not null default 5,
	comment_id_place integer not null,
	comment_id_user integer not null,
	constraint fk_place foreign key (comment_id_place) references place(id_place) on delete cascade,
	constraint fk_user foreign key (comment_id_user) references users(id_user),
	constraint check_grade check (grade>=1 and grade<=5),
	primary key (id_comment)
);