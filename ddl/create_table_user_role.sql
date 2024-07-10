create table user_role(
	user_id_user integer not null,
	role_id_role integer not null,
	constraint user_fk foreign key (user_id_user) references users(id_user) on delete cascade,
	constraint role_fk foreign key (role_id_role) references role(id_role) on delete cascade
);