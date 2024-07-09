create table role( 
	id_role integer generated always as identity,
	name character varying(30) unique,
	primary key(id_role),
	constraint cheak_list_role check (name in ('ROLE_GUEST','ROLE_USER','ROLE_ADMIN','ROLE_MODERATOR','ROLE_JURNALIST','ROLE_AUTHOR'))
);