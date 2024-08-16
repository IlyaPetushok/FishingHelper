create table article(
	id_article integer generated always as identity,
	name character varying(50) unique not null,
	description text not null,
	importance boolean default false not null,
	article_id_user integer not null,
	status character varying(30) null,
    primary key (id_article),
    constraint check_status check(status in ('одобренно','неодобренно', 'удалено', 'в обработке')),
	constraint fk_user foreign key (article_id_user) references users(id_user)
);