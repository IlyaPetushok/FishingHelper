create table article(
	id_article integer generated always as identity,
	name character varying(30) unique not null,
	description text not null,
	importance boolean default false not null,
	article_id_user integer not null,
	processing_status boolean default false,
    primary key (id_article),
	constraint fk_user foreign key (article_id_user) references users(id_user)
);