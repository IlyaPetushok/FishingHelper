create table role_privileges(
	role_id_role integer,
	privileges_id_privileges integer,
	Constraint fk_role foreign key (role_id_role) references role(id_role),
	Constraint fk_privileges foreign key (privileges_id_privileges) references privileges(id_privileges) on delete cascade
);