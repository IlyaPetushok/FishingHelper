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

-- fish
insert into fish(name,description) values ('Щука','рыба семейства щуковых. Распространена в пресных водах Евразии, центральной России, севера России и Северной Америки. Живёт обычно в прибрежной зоне, в водных зарослях, в непроточных или слабопроточных водах.');
insert into fish(name,description) values ('Окунь','вид лучепёрых рыб из рода пресноводных окуней семейства окунёвых. Речной окунь широко распространён в пресных водоёмах Европы и Северной Азии, завезён в Африку, Австралию и Новую Зеландию');
insert into fish(name,description) values ('Карп','большая всеядная рыба семейства карповых. У рыбы крупное удлиненное тело с золотисто-бурой чешуей. Еще одна отличительная особенность — небольшие усики с обеих сторон рта.');
insert into fish(name,description) values ('Лещ','Окраска леща меняется в зависимости от возраста рыбы, цвета грунта и воды в водоеме. Мелкий лещ серо-серебристый, в старшем возрасте темнеет и приобретает золотистый отлив. В торфяных озерах лещ имеет бурый цвет. Все плавники у леща серые, в анальном плавнике 23-30 ветвистых лучей. Лещ достигает длины 45 см, веса 2,5-3 кг; живет до 20 лет, но обычно меньше.');
insert into fish(name,description) values ('Судак','Хищная рыба семейства судаковых. Тело удлиненное и несколько сжатое по бокам. Окраска серо-зеленоватая, спина темнее, бока светлее. Судак распространен в пресных водоемах Европы и Азии, предпочитает холодные и чистые воды.');
insert into fish(name,description) values ('Треска','Морская рыба семейства тресковых. Имеет удлиненное тело и три спинных плавника. Треска обитает в холодных водах Северной Атлантики, часто встречается у побережий Канады и Скандинавии. Рыба ценится за свое мясо, используемое в кулинарии.');
insert into fish(name,description) values ('Сом','Крупная пресноводная рыба семейства сомовых. Обладает длинным телом, большими усами по бокам рта и мясистыми плавниками. Сомы живут в реках и озерах Европы, Азии и Северной Америки. Могут достигать значительных размеров и весов.');
insert into fish(name,description) values ('Карась','Мелкая пресноводная рыба семейства карповых. Тело округлое, покрыто крупной чешуей, окраска варьируется от золотистой до серо-зеленой. Карась широко распространен в реках и прудах Европы и Азии, вынослив и приспособлен к различным условиям.');
insert into fish(name,description) values ('Семга','Морская рыба семейства лососевых. Обладает серебристым телом и характерными черными пятнами. Семга обитает в холодных водах Атлантического океана и реках Северной Европы и Америки, является объектом промыслового рыболовства.');
insert into fish(name,description) values ('Лосось','Ценная промысловая рыба семейства лососевых. Имеет удлиненное тело с серебристой окраской и характерной темной спиной. Лосось обитает в холодных водах Северного полушария, мигрирует из моря в реки для нереста.');

-- type fish
insert into type_fish (type_name) values ('Хищная');
insert into type_fish (type_name) values ('Речная');
insert into type_fish (type_name) values ('Пресноводная');
insert into type_fish (type_name) values ('Детритофагам');

insert into type_fish_fish (type_fish_id_type,fish_id_fish) values (1,1);
insert into type_fish_fish (type_fish_id_type,fish_id_fish) values (2,1);
insert into type_fish_fish (type_fish_id_type,fish_id_fish) values (3,3);
insert into type_fish_fish (type_fish_id_type,fish_id_fish) values (1,2);
insert into type_fish_fish (type_fish_id_type,fish_id_fish) values (2,4);
insert into type_fish_fish (type_fish_id_type,fish_id_fish) values (2,5);
insert into type_fish_fish (type_fish_id_type,fish_id_fish) values (1,5);
insert into type_fish_fish (type_fish_id_type,fish_id_fish) values (2,6);
insert into type_fish_fish (type_fish_id_type,fish_id_fish) values (2,7);
insert into type_fish_fish (type_fish_id_type,fish_id_fish) values (1,7);
insert into type_fish_fish (type_fish_id_type,fish_id_fish) values (3,8);
insert into type_fish_fish (type_fish_id_type,fish_id_fish) values (2,9);
insert into type_fish_fish (type_fish_id_type,fish_id_fish) values (4,10);


 insert into users(name,login,password,mail,data_registration) values ('artem','1111','$2a$10$nnut4h0ECvLJApfp.xrnrOgMhFM3snV88LSjSqvwKEVjoCXdvLdxi','artem@mail.ru','2024-09-09');
 insert into users(name,login,password,mail,data_registration) values ('marya','0000','$2a$10$uVUZrNplm.AF8Zhbnp.8A.DuQfqq9E0jslpMksA68Vyp04YyUqaSS','a399@gmail.com','2019-12-01');
 insert into users(name,login,password,mail,data_registration) values ('egor','2222','$2a$10$ir3hGFwMAeBypLEYObkIFe/2HFqf70jUbLaxgPnbzbJur3LaEaSTK','a323@vk.com','2024-09-01');
 insert into users(name,login,password,mail,data_registration) values ('misha','12344321','$2a$10$OtH.HmSbKz1UxDitvyf/aOMmNf2AUKY84wx4rlfE.2Q7ntRpgcbkW','a321345@mail.ru','2023-04-22');
insert into users(name,login,password,mail,data_registration) values ('misha','7777','$2a$10$OtH.HmSbKz1UxDitvyf/aOMmNf2AUKY84wx4rlfE.2Q7ntRpgcbkW','a37777@mail.ru','2023-04-22');


insert into user_role(user_id_user,role_id_role) values (1,4);
insert into user_role(user_id_user,role_id_role) values (2,5);
insert into user_role(user_id_user,role_id_role) values (3,6);
insert into user_role(user_id_user,role_id_role) values (4,3);
insert into user_role(user_id_user,role_id_role) values (5,5);



insert into article(name,description,importance,article_id_user,status) values ('лучшее место 2023', 'В 2023 году Беларусь представила свое самое прекрасное и популярное рыбатское место - озеро Нарочь. Этот великолепный водоем, расположенный в Минской области, стал идеальным местом для рыболовных приключений и отдыха',false,2,'одобренно');
insert into article(name,description,importance,article_id_user,status) values ('соревнование пужич 10.10.2024', 'В тихом и живописном городке Пужичи, расположенном в самом сердце Беларуси, проходит уникальное соревнование по рыбалке. Это событие привлекает рыболовов со всей страны, жаждущих показать свои навыки и соревноваться за звание лучшего рыболова в регионе',true,2,'в обработке');
insert into article(name,description,importance,article_id_user,status) values ('Новые правила рыбалки 2024', 'С 2024 года вступили в силу новые правила рыбалки в Беларуси. Изменения касаются разрешенных методов ловли, размеров улова и охраняемых видов рыбы. Все рыболовы должны ознакомиться с новыми правилами, чтобы избежать штрафов',true,2,'одобренно');
insert into article(name,description,importance,article_id_user,status) values ('Лучшие приманки для щуки', 'Щука – одна из самых популярных рыболовных целей. В этой статье мы рассмотрим лучшие приманки для ловли щуки, которые помогут вам увеличить количество улова. Мы также расскажем о лучших местах для ловли этой рыбы',false,2,'в обработке');
insert into article(name,description,importance,article_id_user,status) values ('Рыбалка на озере Свитязь', 'Озеро Свитязь, расположенное на границе Украины и Беларуси, славится своими рыболовными возможностями. В статье мы расскажем о лучших способах ловли рыбы в этом живописном водоеме и предоставим советы по выбору снастей',false,2,'одобренно');
insert into article(name,description,importance,article_id_user,status) values ('Зимняя рыбалка в Беларуси', 'Зимняя рыбалка в Беларуси – это не только испытание для профессионалов, но и отличная возможность для любителей насладиться природой и спокойствием зимних водоемов. Узнайте, где лучше всего ловить рыбу зимой и какие снасти использовать',true,2,'в обработке');
insert into article(name,description,importance,article_id_user,status) values ('Лучшие рыболовные магазины', 'Выбор качественного снаряжения – ключ к успешной рыбалке. В статье представлены лучшие рыболовные магазины в Беларуси, где вы сможете найти все необходимое для вашего увлечения',false,5,'в обработке');
insert into article(name,description,importance,article_id_user,status) values ('Путеводитель по речным видам рыбы', 'Изучите наиболее распространенные речные виды рыбы Беларуси. Мы предоставим подробную информацию о каждом виде, включая их предпочтения по среде обитания и методы ловли',true,5,'одобренно');
insert into article(name,description,importance,article_id_user,status) values ('Экотуризм и рыбалка', 'Как совместить рыбалку с экотуризмом? Узнайте, какие водоемы Беларуси идеально подходят для экологичного отдыха и рыбалки, сохраняя природу нетронутой',false,5,'в обработке');
insert into article(name,description,importance,article_id_user,status) values ('Инновационные технологии в рыболовстве', 'Современные технологии значительно изменили рыболовство. В статье мы рассмотрим последние новинки в области рыболовных снастей, электронных устройств и их влияние на улов',true,5,'одобренно');
insert into article(name,description,importance,article_id_user,status) values ('Советы по ловле карпа', 'Карп – одна из самых интересных рыб для ловли. В статье представлены советы по выбору снастей, приманок и технике ловли карпа, которые помогут вам увеличить шанс на успешный улов',false,5,'в обработке');


insert into tags(name) values ('лучшиеместа');
insert into tags(name) values ('соревнования');
insert into tags(name) values ('2023');
insert into tags(name) values ('крупныйулов');
insert into tags(name) values ('новости');
insert into tags(name) values ('приманки');
insert into tags(name) values ('озероСвятозеро');
insert into tags(name) values ('зимняярыбалка');
insert into tags(name) values ('турнир');
insert into tags(name) values ('магазины');
insert into tags(name) values ('речныевиды');
insert into tags(name) values ('экотуризм');
insert into tags(name) values ('технологии');
insert into tags(name) values ('карп');

insert into article_tags(article_id_integer,tags_id_article) values (2,1);
insert into article_tags(article_id_integer,tags_id_article) values (2,2);
insert into article_tags(article_id_integer,tags_id_article) values (3,5);
insert into article_tags(article_id_integer,tags_id_article) values (4,6);
insert into article_tags(article_id_integer,tags_id_article) values (5,7);
insert into article_tags(article_id_integer,tags_id_article) values (6,8);
insert into article_tags(article_id_integer,tags_id_article) values (7,9);
insert into article_tags(article_id_integer,tags_id_article) values (8,10);
insert into article_tags(article_id_integer,tags_id_article) values (9,11);
insert into article_tags(article_id_integer,tags_id_article) values (10,12);
insert into article_tags(article_id_integer,tags_id_article) values (11,13);

insert into type_place(name) values ('Речка');
insert into type_place(name) values ('Озеро');
insert into type_place(name) values ('Водаём');

insert into owner(phone_number,name) values ('3445354535','Сергей');
insert into owner(phone_number,name) values ('3445355535','Саша');
insert into owner(phone_number,name) values ('341239935','Дима');
insert into owner(phone_number, name) values ('3423456789', 'Анна');
insert into owner(phone_number, name) values ('3456789012', 'Игорь');
insert into owner(phone_number, name) values ('3467890123', 'Марина');
insert into owner(phone_number, name) values ('3478901234', 'Виктор');
insert into owner(phone_number, name) values ('3489012345', 'Елена');


--
insert into place(name,coordinates,place_id_type,paid,description,place_id_owner,status) values ('Нарочь','54.953°,27.618°',1,true,'асположено в Минской области. Озеро Нарочь является одним из крупнейших озер в Беларуси и популярным местом для рыбной ловли и отдыха.',1,'одобренно');
insert into place(name,coordinates,place_id_type,description,status) values ('Свитязь','54.618°,25.173°',2,'Находится в Гродненской области. Озеро Свитязь является самым глубоким озером в Беларуси и привлекает туристов своей красотой и чистотой воды.','одобренно');
insert into place(name,coordinates,place_id_type,paid,description,place_id_owner,status) values ('Припять','52.167°,28.643°',1,true,' Протекает через территорию Беларуси. Река Припять является одной из крупнейших рек в Европе и предлагает отличные возможности для рыбной ловли и водных видов спорта.',2,'одобренно');
insert into place(name,coordinates,place_id_type,paid,description,status) values ('Смольнянское озеро','54.889°,27.530°',1,false,'Находится в Витебской области. Озеро Смольнянское привлекает рыболовов своей разнообразной рыбой и живописными пейзажами.','одобренно');
insert into place(name,coordinates,place_id_type,paid,description,place_id_owner,status) values ('Озеро Белое','53.758°,27.501°',2,true,'Расположено в Брестской области. Озеро Белое известно своей прозрачной водой и большими уловами рыбы, особенно карпа и щуки.',4,'одобренно');
insert into place(name,coordinates,place_id_type,paid,description,status) values ('Река Днепр','53.7,35°,30.897°',3,false,'Протекает через центр Беларуси. Река Днепр известна своими живописными берегами и отличными условиями для рыбалки.','в обработке');
insert into place(name,coordinates,place_id_type,paid,description,status) values ('Река Неман','53.568°,23.765°',1,false,'Река Неман протекает через Гродненскую область. Она известна своими широкими водами и обилием рыбы, особенно леща и судака.','в обработке');
insert into place(name,coordinates,place_id_type,paid,description,place_id_owner,status) values ('Озеро Черное','52.728°,28.355°',2,true,'Находится в Гомельской области. Озеро Черное привлекает рыболовов своими уникальными условиями для ловли, особенно в период нереста.',7,'в обработке');

insert into comment(text,grade,comment_id_place,comment_id_user) values ('классное место',4,1,1);
insert into comment(text,grade,comment_id_place,comment_id_user) values ('клёва совсем нету хоть место красивое',2,2,3);
insert into comment(text,grade,comment_id_place,comment_id_user) values ('ужастное место',1,3,4);
insert into comment(text,grade,comment_id_place,comment_id_user) values ('Отличное место для рыбалки, всегда хороший улов',5,1,2);
insert into comment(text,grade,comment_id_place,comment_id_user) values ('Очень красивое место, но рыбы почти нет',3,2,1);
insert into comment(text,grade,comment_id_place,comment_id_user) values ('Неплохое место, но много народу',3,1,3);
insert into comment(text,grade,comment_id_place,comment_id_user) values ('Не рекомендую, много мусора и грязи',2,3,2);
insert into comment(text,grade,comment_id_place,comment_id_user) values ('Прекрасное озеро, отличные условия для отдыха',4,2,4);
insert into comment(text,grade,comment_id_place,comment_id_user) values ('Туристическое место, но рыбалка не удалась',2,3,1);


insert into food_fish(name) values ('Червяк');
insert into food_fish(name) values ('кукуруза');
insert into food_fish(name) values ('опарыш');
insert into food_fish(name) values ('матыль');


insert into fish_place values (1,1);
insert into fish_place values (1,3);
insert into fish_place values (3,2);
insert into fish_place values (4,1);
insert into fish_place values (5,3);
insert into fish_place values (6,2);
insert into fish_place values (6,4);
insert into fish_place values (6,7);
insert into fish_place values (7,6);
insert into fish_place values (8,4);
insert into fish_place values (9,5);
insert into fish_place values (10,3);
insert into fish_place values (10,6);
insert into fish_place values (10,8);


insert into fish_food_fish values (1,1);
insert into fish_food_fish values (2,2);
insert into fish_food_fish values (4,1);
insert into fish_food_fish values (3,1);
insert into fish_food_fish values (4,2);
insert into fish_food_fish values (5,3);
insert into fish_food_fish values (6,1);
insert into fish_food_fish values (7,3);
insert into fish_food_fish values (8,1);
insert into fish_food_fish values (9,1);
insert into fish_food_fish values (10,2);
insert into fish_food_fish values (10,4);

insert into photo(photo,photo_id_article) values('https://downloader.disk.yandex.ru/disk/3de45de58b51693dfa8245a17c0c0553f95ca5bec4416bebcaca3286f5a3dd63/66bf41e8/0otFc_nzep39fQhSdhCWuB1ZlIUGwITJ2xXjWghJyqjxMvTocD--AJfgsaonCA6J0oBO4mCovnIJ2rVfqYSNUw%3D%3D?uid=1645277963&filename=images.jpeg&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=1613&hid=a0d3307dec6c4eb7c5c419b5b32c723e&media_type=image&tknv=v2&etag=6dcb230dc201c1cbe49f60b0a32e1c0b',1);
insert into photo(photo,photo_id_article) values('https://downloader.disk.yandex.ru/disk/580afabf4b283d342dcf7087fab23579a08c27cef9e9dee92e33363bb35432dd/66bf41ea/0otFc_nzep39fQhSdhCWuO9dsSL8DkC6YG8CfkvPjFw7KBKIC2j5TiJUrTHhaiXcAs3Ly6pZhhh4w-3J1NB2ug%3D%3D?uid=1645277963&filename=%D0%B7%D0%B0%D0%B3%D1%80%D1%83%D0%B6%D0%B5%D0%BD%D0%BE.jpeg&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=1521&hid=ea550be1c8d16089ff2457f956c8f7d9&media_type=image&tknv=v2&etag=c929f22051eee68bd390555ef0d82174',1);
insert into photo(photo, photo_id_article) values('https://downloader.disk.yandex.ru/disk/24d174317f4fbcfee23c22dc4b717590b69fa5ec715a1841022b9817eb0ffcf2/66bf4299/0otFc_nzep39fQhSdhCWuEYKTFLMSgPG9ETSymJtPeazkMOzC7E7b0X435YPBexTZdabVTwiGWwp-0VeWtBkVQ%3D%3D?uid=1645277963&filename=IMG-f37d4f9fde5c80b4843a52951ee960ed-V.jpg&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=9193&hid=ec850e45919f36d0ecfcef86def55187&media_type=image&tknv=v2&etag=c2cb9958b36f41269c1a716bd06b4bfb', 2);
insert into photo(photo, photo_id_article) values('https://downloader.disk.yandex.ru/disk/2712d7bb71de5c1322cfd3824a94fd8927d1c2738cb4a9d294598cda9d6bc44b/66bf449d/0otFc_nzep39fQhSdhCWuHIEmecyUi5FcfkTFCYNDtHonxRO_3QLs6FdH8r4rCTQRDNnxYWtMu9meigqvn3shw%3D%3D?uid=1645277963&filename=dvice_carp.jpg&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=6696&hid=4e58107ed56ea6d26cd648d2bd9ee1df&media_type=image&tknv=v2&etag=d0e5322f23d99786602f85711f9f8ede', 11);
insert into photo(photo, photo_id_article) values('https://downloader.disk.yandex.ru/disk/03c1474754d9650ab1cf842f2bacba63ca1c0c8043e014f113a90570c0212b76/66bf44a0/0otFc_nzep39fQhSdhCWuHfhBEbArrwvLSDubs05755ipKce1CLJ5eLAFNh_Cfz5Ss5M2AaHXvfqR-cWwNYkLA%3D%3D?uid=1645277963&filename=ekoturizm.jpeg&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=1884&hid=766be931dfcec3c1ee94b94fc5e0a350&media_type=image&tknv=v2&etag=a1bbc97e6a7e1aaf429031cd19bc30bc', 9);
insert into photo(photo, photo_id_article) values('https://downloader.disk.yandex.ru/disk/e92ad6bc261eb5208fdd47050cc264505b13f2e5e4cff31e47ebc68696d0763e/66bf44a3/0otFc_nzep39fQhSdhCWuLCIfot8GgFqR48SDzTc2oCQWG2YsLIlxqcmezXE0mayvRUcyDW9YKNvKUeUV8sEmQ%3D%3D?uid=1645277963&filename=tehno.jpg&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=11267&hid=000eea5b56f2b4fe44620edfbc5823f4&media_type=image&tknv=v2&etag=82fa575b9940817f61edf2c9fbf01b56', 10);
insert into photo(photo, photo_id_article) values('https://downloader.disk.yandex.ru/disk/fe79a0328133cb481d7f0b4edf7830b9da6bc3fd3b445c7ebc6f3d6a52741cca/66bf44a5/0otFc_nzep39fQhSdhCWuAKfqZTAIhwOrh7dXv0Lv-WimwwQvaP_pm_387oglUpBxnq7VuBMZcwVXs7IHBkADA%3D%3D?uid=1645277963&filename=na_chuka_primanka.jpeg&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=1790&hid=7754b4c18554c206ca127869f832a3f0&media_type=image&tknv=v2&etag=67e84e91321a94335b757146677767c8', 4);
insert into photo(photo, photo_id_article) values('https://downloader.disk.yandex.ru/disk/07f8a2ae0bd95691238167ac13e41a514a0a4d604d5cc7ab81e0fa49bac971d9/66bf4526/0otFc_nzep39fQhSdhCWuG9x9VCiq3ec174HlF2loG7WqWfsApvnxffwPNZJHNSb_IyndWmQudVpypcj2pHmLQ%3D%3D?uid=1645277963&filename=roles_2024.jpeg&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=1794&hid=5d68c6b2984060e0fa1d3f9fe55e9314&media_type=image&tknv=v2&etag=5086754fb3a7f1a81f3e491420ca6aa8', 3);
insert into photo(photo, photo_id_article) values('https://downloader.disk.yandex.ru/disk/5926a2880bd727b3f498da7f9e6c01fe877d108c940a5152c9c473d6f036daf1/66bf45a9/0otFc_nzep39fQhSdhCWuPZZy0KS3IIQRN952yeBY-MxBxZFppejAgnJexOr6rK01Bf7SfkotBXYTYC3p39QVg%3D%3D?uid=1645277963&filename=svityaz.jpg&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=17410&hid=626fc3737ff12cb9f9bee0ebdbd0f8e6&media_type=image&tknv=v2&etag=ba0f249132510259965bea82b76cda1d', 5);
insert into photo(photo, photo_id_article) values('https://downloader.disk.yandex.ru/disk/7bfb770e9bb2323777d3405ddd75c42f78e5ea04125b529fbf838426c4dd95c4/66bf45ab/0otFc_nzep39fQhSdhCWuILA-a5aaYCJdjABe0KOoY1tNArBLEaTMCL94ntOfP0t0vuTFiDH11VfJpGL8p1wbA%3D%3D?uid=1645277963&filename=imn_fish_blr.jpg&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=4631&hid=c261a99ea5f60779d9cda0d55c6f4dc4&media_type=image&tknv=v2&etag=7821b133794347fa364efd6de2e2ad9d', 6);
insert into photo(photo, photo_id_article) values('https://downloader.disk.yandex.ru/disk/033c39e0cd7f26da2c07bd9ac8a42ecc3944a8f40b6656e08cf333284472b755/66bf481c/0otFc_nzep39fQhSdhCWuMDISRGCJlDq9eO9Mtv6P3eqS5_yXLkBB1Ooj6fiHpEYy8HGWHYB0ULQKxQ--gV6Jw%3D%3D?uid=1645277963&filename=shops.jpeg&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=1798&hid=b826b8339acf4d406703d9b8ed442a27&media_type=image&tknv=v2&etag=81fbca3df6fc589249f7530b4ad20987', 7);
insert into photo(photo, photo_id_article) values('https://downloader.disk.yandex.ru/disk/19405553c387e3ab0d5767d786e0acac558a828cfa5f519f4966b31e8cf032e9/66bf481a/0otFc_nzep39fQhSdhCWuPC9bTcnUUffwrL2RS03_tYIv8c8pt_4tvxIevmG7icWo0R61GzJWyXM4y92oj_Krg%3D%3D?uid=1645277963&filename=putevoditel.png&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=1406&hid=54700118a105b8506c50419462403f12&media_type=image&tknv=v2&etag=0160a4bd80325e16e6c8bf94331ae562', 8);


insert into photo(photo, photo_id_place) values('https://downloader.disk.yandex.ru/disk/15724a68f3f37a1866de1469d7199c7ba565f1a898146d32dc7e7ef46386997f/66bf4914/0otFc_nzep39fQhSdhCWuIZpDrS3h2VB_OZLdB3YIWpytkWztHtn-ARJ9eGFqPb3cT8dNhw5uXaleGG72kAJaA%3D%3D?uid=1645277963&filename=001290_310086ec6ff877d2b9e0ef09798cbae8.jpg&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=11251&hid=32154729a7676e853167fa7d10355f83&media_type=image&tknv=v2&etag=972de9ccca7c53e00acadcd8b958d919', 1);
insert into photo(photo, photo_id_place) values('https://downloader.disk.yandex.ru/disk/15724a68f3f371866de1469d7199c7ba565f1a898146d32dc7e7ef46386997f/66bf4914/0otFc_nzep39fQhSdhCWuIZpDrS3h2VB_OZLdB3YIWpytkWztHtn-ARJ9eGFqPb3cT8dNhw5uXaleGG72kAJaA%3D%3D?uid=1645277963&filename=001290_310086ec6ff877d2b9e0ef09798cbae8.jpg&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=11251&hid=32154729a7676e853167fa7d10355f83&media_type=image&tknv=v2&etag=972de9ccca7c53e00acadcd8b958d919', 2);
insert into photo(photo,photo_id_place) values('https://downloader.disk.yandex.ru/disk/21c74606c8cb23b4ccb147d9c797bcbe75f7bdbfa01a8558df012ebb2f2749fc/66bf4917/0otFc_nzep39fQhSdhCWuI8HVekay7FNtt4pr-beG42cIzz_b8FNwZ3E5nzcPeXFpXl3UbUdQsdCYxGvVGcs7A%3D%3D?uid=1645277963&filename=2016_20_skidel_otdyh_skidel_otdyh_kotra.jpg&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=5085&hid=53462e22b49fa2e3703a30b8f0dca134&media_type=image&tknv=v2&etag=67f4adefe2c98a21adaba0baab329dd5',3);
insert into photo(photo,photo_id_place) values('https://downloader.disk.yandex.ru/disk/21c74606c8cb2b4ccb147d9c797bcbe75f7bdbfa01a8558df012ebb2f2749fc/66bf4917/0otFc_nzep39fQhSdhCWuI8HVekay7FNtt4pr-beG42cIzz_b8FNwZ3E5nzcPeXFpXl3UbUdQsdCYxGvVGcs7A%3D%3D?uid=1645277963&filename=2016_20_skidel_otdyh_skidel_otdyh_kotra.jpg&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=5085&hid=53462e22b49fa2e3703a30b8f0dca134&media_type=image&tknv=v2&etag=67f4adefe2c98a21adaba0baab329dd5',4);
insert into photo(photo,photo_id_place) values('https://downloader.disk.yandex.ru/disk/638e6edecf112e3d661d2ad5ad11ac618252a2f5c97ea86a558d518752e3a3cf/66bf4919/0otFc_nzep39fQhSdhCWuCP73hVpSD9TAfioXquOWyF008c70fsGr69xEn4i7i88OoocR6_lZ2H-hh1vkE4iHg%3D%3D?uid=1645277963&filename=neman3.jpg&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=12408&hid=609cdb74cd4d486d6156ed2215bc78f5&media_type=image&tknv=v2&etag=11be8676c13a88bcbc23a8c3faa93633',5);
insert into photo(photo,photo_id_place) values('https://downloader.disk.yandex.ru/disk/638e6edecf11e3d661d2ad5ad11ac618252a2f5c97ea86a558d518752e3a3cf/66bf4919/0otFc_nzep39fQhSdhCWuCP73hVpSD9TAfioXquOWyF008c70fsGr69xEn4i7i88OoocR6_lZ2H-hh1vkE4iHg%3D%3D?uid=1645277963&filename=neman3.jpg&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=12408&hid=609cdb74cd4d486d6156ed2215bc78f5&media_type=image&tknv=v2&etag=11be8676c13a88bcbc23a8c3faa93633',6);
insert into photo(photo,photo_id_place) values('https://downloader.disk.yandex.ru/disk/5273814defee47161957fd930da2c73ed2041d2fee9eac0f795c37b99285ad41/66bf491c/0otFc_nzep39fQhSdhCWuI9XU05Bwz4QLksgSb5rUHBLglt3NFBX1gNxDCBrkAxnV8MWHLInhnNGD5YEv0mF9g%3D%3D?uid=1645277963&filename=unnamed.jpg&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=4136&hid=eb60b4f7ee506e6e01b1063a98972ec9&media_type=image&tknv=v2&etag=de206ee479447d340ee1cf0508b6041f',7);
insert into photo(photo,photo_id_place) values('https://downloader.disk.yandex.ru/disk/5273814defe47161957fd930da2c73ed2041d2fee9eac0f795c37b99285ad41/66bf491c/0otFc_nzep39fQhSdhCWuI9XU05Bwz4QLksgSb5rUHBLglt3NFBX1gNxDCBrkAxnV8MWHLInhnNGD5YEv0mF9g%3D%3D?uid=1645277963&filename=unnamed.jpg&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=4136&hid=eb60b4f7ee506e6e01b1063a98972ec9&media_type=image&tknv=v2&etag=de206ee479447d340ee1cf0508b6041f',8);


insert into photo(photo, photo_id_fish) values('https://downloader.disk.yandex.ru/disk/e8d1ccf872acbc1aee2c0040abc583b2a3e9997258c7421da27a894aff3bcd46/66bf49ec/0otFc_nzep39fQhSdhCWuIYfLpaNTErwWKNa8gfRBBChbbcBsiZMvd3jy50OeK8xF6Or6QlCPUk_x8O9mUidVQ%3D%3D?uid=1645277963&filename=caras.jpeg&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=1133&hid=534aff37ef35cbc5b1cf8f0e8355669c&media_type=image&tknv=v2&etag=485905fa99e1b3f28330178a7dae3c4b', 2);
insert into photo(photo, photo_id_fish) values('https://downloader.disk.yandex.ru/disk/40908d6996276b38387996cbe08035622a127b4769acf7f28e1e2fa634c06cfe/66bf4ffe/0otFc_nzep39fQhSdhCWuEry9QXeY13iD91Gw0z3Valmw8TrQflQuQ_Yon08fIp9d6kWnkpm9KsxspGh4OzSuw%3D%3D?uid=1645277963&filename=AbramisBramaCarpBream.jpeg&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=32494&hid=7dbf088a5d7b8c9ddb6ae1dcd602f814&media_type=image&tknv=v2&etag=ccbccbea7ded406becb945a05e18876d', 4);
insert into photo(photo, photo_id_fish) values('https://downloader.disk.yandex.ru/disk/f6d671a3b37e7ea2051b900e2836d9c1d448a6be5afd7fed0a6e5c921fb89a16/66bf5005/0otFc_nzep39fQhSdhCWuACF146mIJ0dJs4_AUxOI1-OBWxMjJ87akqI1fRvyTGAaXrr1M9BTVJOIZom38BJNA%3D%3D?uid=1645277963&filename=Esox_lucius1.jpg&disposition=attachment&hash=&limit=0&content_type=image%2Fjpeg&owner_uid=1645277963&fsize=15451&hid=01768a40222c217a56c52c243b10da2f&media_type=image&tknv=v2&etag=4934c866c8dec7b7edc65d015c1c0bf3', 5);
insert into photo(photo, photo_id_fish) values('https://downloader.disk.yandex.ru/disk/f9e2abcae267214c317f18eaf9f50383309492371f8545dac937a64e89c5e22c/66bf5008/Gc4tJqwF_8gTQTp0ubc_ep9XWkuZmUU6RAYtGaYFlADsV4o_yowDnvDR9nan35DfcVfdPKEY6me_iJVjhqvmbw%3D%3D?uid=1645277963&filename=shhuka-1.png&disposition=attachment&hash=&limit=0&content_type=text%2Fplain&owner_uid=1645277963&hid=cbe7b309b4bbe3a94682616cb7e88e01&media_type=image&tknv=v2&etag=d41d8cd98f00b204e9800998ecf8427e',1);



insert into survey(survey_id_user,survey_id_place,morning,afternoon,evening) values (3,1,true,false,true);
insert into survey(survey_id_user,survey_id_place,morning,afternoon,evening) values (3,2,true,false,false);
insert into survey(survey_id_user,survey_id_place,morning,afternoon,evening) values (3,2,false,true,false);
insert into survey(survey_id_user, survey_id_place, morning, afternoon, evening) values (1, 1, false, true, true);
insert into survey(survey_id_user, survey_id_place, morning, afternoon, evening) values (1, 3, true, true, false);
insert into survey(survey_id_user, survey_id_place, morning, afternoon, evening) values (2, 1, true, false, true);
insert into survey(survey_id_user, survey_id_place, morning, afternoon, evening) values (2, 3, false, true, true);
insert into survey(survey_id_user, survey_id_place, morning, afternoon, evening) values (3, 2, true, true, false);
insert into survey(survey_id_user, survey_id_place, morning, afternoon, evening) values (4, 3, false, false, true);
insert into survey(survey_id_user, survey_id_place, morning, afternoon, evening) values (5, 1, true, true, false);
insert into survey(survey_id_user, survey_id_place, morning, afternoon, evening) values (5, 2, false, true, true);

insert into survey_fish values(1,1);
insert into survey_fish values(1,2);
insert into survey_fish values(3,4);
insert into survey_fish values(4,1);
insert into survey_fish values(5,2);
insert into survey_fish values(6,4);
insert into survey_fish values(7,1);
insert into survey_fish values(8,2);
insert into survey_fish values(9,4);
insert into survey_fish values(10,1);
insert into survey_fish values(11,2);
