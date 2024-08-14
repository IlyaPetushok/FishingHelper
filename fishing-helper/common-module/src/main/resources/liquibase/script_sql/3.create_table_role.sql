create table role( 
	id_role integer generated always as identity,
	name character varying(30) unique,
	primary key(id_role),
	constraint cheak_list_role check (name in ('GUEST','USER','ADMIN','MODERATOR','JOURNALIST','AUTHOR'))
);