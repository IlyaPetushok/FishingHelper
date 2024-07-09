create table photo(
	id_photo integer generated always as identity,
	photo text unique not null,
	photo_id_article integer null,
	photo_id_place integer null,
	photo_id_fish integer null,
	constraint fk_articel foreign key (photo_id_article) references article(id_article),
	constraint fk_place foreign key (photo_id_place) references place(id_place),
	constraint fk_fish foreign key (photo_id_fish) references fish(id_fish),
	primary key(id_photo)
);