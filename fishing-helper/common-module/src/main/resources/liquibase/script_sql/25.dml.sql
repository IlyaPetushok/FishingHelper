insert into role (name) values ('MODERATOR');
insert into role (name) values ('GUEST');
insert into role (name) values ('USER');
insert into role (name) values ('ADMIN');
insert into role (name) values ('JOURNALIST');
insert into role (name) values ('AUTHOR');

insert into privileges(name) values ('CREATE');
insert into privileges(name) values ('DELETE');
insert into privileges(name) values ('READ');
insert into privileges(name) values ('UPDATE');
insert into privileges(name) values ('BLOCKING');
insert into privileges(name) values ('ADD_PRIVILEGES');

-- moderator
insert into role_privileges values(1,1);
insert into role_privileges values(1,2);
insert into role_privileges values(1,3);
insert into role_privileges values(1,4);
insert into role_privileges values(1,5);
insert into role_privileges values(1,6);

-- admin
insert into role_privileges values(4,1);
insert into role_privileges values(4,2);
insert into role_privileges values(4,3);
insert into role_privileges values(4,4);
insert into role_privileges values(4,5);
insert into role_privileges values(4,6);

-- author

insert into role_privileges values(6,1);
insert into role_privileges values(6,3);

-- journalist

insert into role_privileges values(5,1);
insert into role_privileges values(5,3);

-- user
insert into role_privileges values(3,1);
insert into role_privileges values(3,3);

insert into fish(name,description) values ('Щука','рыба семейства щуковых. Распространена в пресных водах Евразии, центральной России, севера России и Северной Америки. Живёт обычно в прибрежной зоне, в водных зарослях, в непроточных или слабопроточных водах.');
insert into fish(name,description) values ('Окунь','вид лучепёрых рыб из рода пресноводных окуней семейства окунёвых. Речной окунь широко распространён в пресных водоёмах Европы и Северной Азии, завезён в Африку, Австралию и Новую Зеландию');
insert into fish(name,description) values ('Карп','большая всеядная рыба семейства карповых. У рыбы крупное удлиненное тело с золотисто-бурой чешуей. Еще одна отличительная особенность — небольшие усики с обеих сторон рта.');
insert into fish(name,description) values ('Лещ','Окраска леща меняется в зависимости от возраста рыбы, цвета грунта и воды в водоеме. Мелкий лещ серо-серебристый, в старшем возрасте темнеет и приобретает золотистый отлив. В торфяных озерах лещ имеет бурый цвет. Все плавники у леща серые, в анальном плавнике 23-30 ветвистых лучей. Лещ достигает длины 45 см, веса 2,5-3 кг; живет до 20 лет, но обычно меньше.');

insert into type_fish (type_name) values ('Хищная');
insert into type_fish (type_name) values ('Речная');
insert into type_fish (type_name) values ('Пресноводная');
insert into type_fish (type_name) values ('Детритофагам');

insert into type_fish_fish (type_fish_id_type,fish_id_fish) values (1,1);
insert into type_fish_fish (type_fish_id_type,fish_id_fish) values (2,1);
insert into type_fish_fish (type_fish_id_type,fish_id_fish) values (3,3);
insert into type_fish_fish (type_fish_id_type,fish_id_fish) values (1,2);


 insert into users(name,login,password,mail,data_registration) values ('artem','1111','$2a$10$nnut4h0ECvLJApfp.xrnrOgMhFM3snV88LSjSqvwKEVjoCXdvLdxi','artem@mail.ru','2024-09-09');
 insert into users(name,login,password,mail,data_registration) values ('marya','0000','$2a$10$uVUZrNplm.AF8Zhbnp.8A.DuQfqq9E0jslpMksA68Vyp04YyUqaSS','a399@gmail.com','2019-12-01');
 insert into users(name,login,password,mail,data_registration) values ('egor','2222','$2a$10$ir3hGFwMAeBypLEYObkIFe/2HFqf70jUbLaxgPnbzbJur3LaEaSTK','a323@vk.com','2024-09-01');
 insert into users(name,login,password,mail,data_registration) values ('misha','12344321','$2a$10$OtH.HmSbKz1UxDitvyf/aOMmNf2AUKY84wx4rlfE.2Q7ntRpgcbkW','a321345@mail.ru','2023-04-22');


insert into user_role(user_id_user,role_id_role) values (1,4);
insert into user_role(user_id_user,role_id_role) values (2,5);
insert into user_role(user_id_user,role_id_role) values (3,6);
insert into user_role(user_id_user,role_id_role) values (4,3);


insert into article(name,description,importance,article_id_user,status) values ('лучшее место 2023', 'В 2023 году Беларусь представила свое самое прекрасное и популярное рыбатское место - озеро Нарочь. Этот великолепный водоем, расположенный в Минской области, стал идеальным местом для рыболовных приключений и отдыха',false,2,'одобренно');
insert into article(name,description,importance,article_id_user,status) values ('соревнование пужич 10.10.2024', 'В тихом и живописном городке Пужичи, расположенном в самом сердце Беларуси, проходит уникальное соревнование по рыбалке. Это событие привлекает рыболовов со всей страны, жаждущих показать свои навыки и соревноваться за звание лучшего рыболова в регионе',true,2,'в обработке');

insert into tags(name) values ('лучшиеместа');
insert into tags(name) values ('соревнования');
insert into tags(name) values ('2023');
insert into tags(name) values ('крупныйулов');

insert into article_tags(article_id_integer,tags_id_article) values (2,1);
insert into article_tags(article_id_integer,tags_id_article) values (2,2);

insert into type_place(name) values ('Речка');
insert into type_place(name) values ('Озеро');
insert into type_place(name) values ('Водаём');

insert into owner(phone_number,name) values ('3445354535','Сергей');
insert into owner(phone_number,name) values ('3445355535','Саша');
insert into owner(phone_number,name) values ('341239935','Дима');
--
insert into place(name,coordinates,place_id_type,paid,description,place_id_owner) values ('Нарочь','54.953°,27.618°',1,true,'асположено в Минской области. Озеро Нарочь является одним из крупнейших озер в Беларуси и популярным местом для рыбной ловли и отдыха.',1);
insert into place(name,coordinates,place_id_type,description) values ('Свитязь','54.618°,25.173°',2,'Находится в Гродненской области. Озеро Свитязь является самым глубоким озером в Беларуси и привлекает туристов своей красотой и чистотой воды.');
insert into place(name,coordinates,place_id_type,paid,description,place_id_owner) values ('Припять','52.167°,28.643°',1,true,' Протекает через территорию Беларуси. Река Припять является одной из крупнейших рек в Европе и предлагает отличные возможности для рыбной ловли и водных видов спорта.',2);

insert into comment(text,grade,comment_id_place,comment_id_user) values ('классное место',4,1,1);
insert into comment(text,grade,comment_id_place,comment_id_user) values ('клёва совсем нету хоть место красивое',2,2,3);
insert into comment(text,grade,comment_id_place,comment_id_user) values ('ужастное место',1,3,4);

insert into food_fish(name) values ('Червяк');
insert into food_fish(name) values ('кукуруза');
insert into food_fish(name) values ('опарыш');
insert into food_fish(name) values ('матыль');

insert into fish_place values (1,1);
insert into fish_place values (1,3);
insert into fish_place values (3,2);

insert into fish_food_fish values (3,1);
insert into fish_food_fish values (3,2);
insert into fish_food_fish values (4,1);

insert into photo(photo,photo_id_article) values('photo1',2);
insert into photo(photo,photo_id_place) values('photo2',2);
insert into photo(photo,photo_id_fish) values('photo3',1);


insert into survey(survey_id_user,survey_id_place,morning,afternoon,evening) values (3,1,true,false,true);
insert into survey(survey_id_user,survey_id_place,morning,afternoon,evening) values (3,2,true,false,false);
insert into survey(survey_id_user,survey_id_place,morning,afternoon,evening) values (3,2,false,true,false);


insert into survey_fish values(1,1);
insert into survey_fish values(1,2);
insert into survey_fish values(3,4);