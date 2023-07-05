-- liquibase formatted sql

-- changeset nastya:1

create table users
(
    id                    bigint not null
        primary key,
    chat_id               bigint,
    child_birthday        timestamp,
    name                  varchar(255),
   maternity_id bigint not null
);

alter table users
    owner to student;


create table reports
(
    id      bigint not null
        primary key,
    chat_id bigint,
    date    timestamp,
    photo   varchar(255),
    text    varchar(255),
    user_id bigint not null
);

alter table reports
    owner to student;


create table maternities
(
    id           bigint not null
        primary key,
    address      varchar(255),
    location_map varchar(255),
    name         varchar(255)
);

alter table maternities
    owner to student;

ALTER TABLE users
    ADD CONSTRAINT users_maternity_id_fk FOREIGN KEY (maternity_id)
        REFERENCES maternities (id)
        ON DELETE SET NULL
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE reports
    ADD CONSTRAINT reports_user_id_fk FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE SET NULL
        ON UPDATE NO ACTION
        NOT DEFERRABLE;