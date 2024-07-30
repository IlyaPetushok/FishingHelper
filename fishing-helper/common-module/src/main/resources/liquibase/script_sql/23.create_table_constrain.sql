create table constrain
(
    user_id_constr       integer not null,
    privileges_id_constr integer not null,
    constraint fk_user foreign key (user_id_constr) references users (id_user),
    constraint fl_priveleges foreign key (privileges_id_constr) references privileges (id_privileges)
);