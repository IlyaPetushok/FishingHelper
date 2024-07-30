CREATE TABLE mistake
(
    id_mistake integer generated always as identity,
    text_mistake text  NOT NULL,
    mistake_id_article integer,
    mistake_id_place integer,
    CONSTRAINT fk_article FOREIGN KEY (mistake_id_article)
    REFERENCES article (id_article),
    CONSTRAINT fk_place FOREIGN KEY (mistake_id_place)
    REFERENCES place (id_place)
);