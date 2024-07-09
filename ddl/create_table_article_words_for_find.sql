create table article_words_for_find(
	article_id_integer integer not null,
	words_for_find_article integer not null,
	constraint fk_article foreign key (article_id_integer) references article(id_article),
	constraint fk_words  foreign key (words_for_find_article) references words_for_find(id_words)
);