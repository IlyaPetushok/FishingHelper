create table article_tags(
	article_id_integer integer not null,
	tags_id_article integer not null,
	constraint fk_article foreign key (article_id_integer) references article(id_article),
	constraint fk_words  foreign key (tags_id_article) references tags(id_tag)
);