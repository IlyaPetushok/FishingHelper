create table role_priviliges(
	role_id_role integer,
	priviliges_id_priviliges integer,
	Constraint fk_role foreign key (role_id_role) references role(id_role),
	Constraint fk_privileges foreign key (priviliges_id_priviliges) references priviliges(id_priviliges) 
);