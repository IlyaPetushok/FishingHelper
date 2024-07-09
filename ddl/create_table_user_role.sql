create table user_role(
	user_id_user integer,
	role_id_role integer,
	constraint user_fk foreign key (user_id_user) references users(id_user),
	constraint role_fk foreign key (role_id_role) references role(id_role)
	
);