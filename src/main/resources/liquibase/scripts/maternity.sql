-- liquibase formatted sql

-- changeset nastya:1

create table users
(
    id                    bigint not null
        primary key,
    chat_id               bigint,
    child_birthday        timestamp,
    name                  varchar(255),
   maternity_hospital_id bigint not null
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
    ADD CONSTRAINT users_maternity_hospital_id_fk FOREIGN KEY (maternity_hospital_id)
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


        -- changeset nastya:2

        create table dailymail
        (
            id                    bigint not null
                primary key,
            info                  text
        );

        alter table dailymail
            owner to student;

            INSERT INTO dailymail (id, info) VALUES (1, 'Информация о первом дне новорожденного');
             INSERT INTO dailymail (id, info) VALUES (2, 'Информация о втором дне новорожденного');
              INSERT INTO dailymail (id, info) VALUES (3, 'Информация о третьем дне новорожденного');
               INSERT INTO dailymail (id, info) VALUES (4, 'Информация о четвертом дне новорожденного');
                INSERT INTO dailymail (id, info) VALUES (5, 'Информация о пятом дне новорожденного');
                 INSERT INTO dailymail (id, info) VALUES (6, 'Информация о шестом дне новорожденного');
                  INSERT INTO dailymail (id, info) VALUES (7, 'Информация о седьмом дне новорожденного');
                   INSERT INTO dailymail (id, info) VALUES (8, 'Информация о восьмом дне новорожденного');
                    INSERT INTO dailymail (id, info) VALUES (9, 'Информация о девятом дне новорожденного');
 INSERT INTO dailymail (id, info) VALUES (10, 'Информация о десятом дне новорожденного');
 INSERT INTO dailymail (id, info) VALUES (11, 'Информация о одиннадцатом дне новорожденного');
 INSERT INTO dailymail (id, info) VALUES (12, 'Информация о двенадцатом дне новорожденного');
  INSERT INTO dailymail (id, info) VALUES (13, 'Информация о тренадцатом дне новорожденного');
  INSERT INTO dailymail (id, info) VALUES (14, 'Информация о четырнадцатом дне новорожденного');
  INSERT INTO dailymail (id, info) VALUES (15, 'Информация о пятнадцатом дне новорожденного');
  INSERT INTO dailymail (id, info) VALUES (16, 'Информация о шестнадцатом дне новорожденного');
  INSERT INTO dailymail (id, info) VALUES (17, 'Информация о семнадцатом дне новорожденного');
  INSERT INTO dailymail (id, info) VALUES (18, 'Информация о восемнадцатом дне новорожденного');
   INSERT INTO dailymail (id, info) VALUES (19, 'Информация о девятнадцатом дне новорожденного');
   INSERT INTO dailymail (id, info) VALUES (20, 'Информация о двадцатом дне новорожденного');
   INSERT INTO dailymail (id, info) VALUES (21, 'Информация о двадцать первом дне новорожденного');
   INSERT INTO dailymail (id, info) VALUES (22, 'Информация о двадцать втором дне новорожденного');
   INSERT INTO dailymail (id, info) VALUES (23, 'Информация о двадцать третьем дне новорожденного');
   INSERT INTO dailymail (id, info) VALUES (24, 'Информация о двадцать четвертом дне новорожденного');
   INSERT INTO dailymail (id, info) VALUES (25, 'Информация о двадцать пятом дне новорожденного');
   INSERT INTO dailymail (id, info) VALUES (26, 'Информация о двадцать шестом дне новорожденного');
   INSERT INTO dailymail (id, info) VALUES (27, 'Информация о двадцать седьмом дне новорожденного');
   INSERT INTO dailymail (id, info) VALUES (28, 'Информация о двадцать восьмом дне новорожденного');
   INSERT INTO dailymail (id, info) VALUES (29, 'Информация о двадцать девятом дне новорожденного');
   INSERT INTO dailymail (id, info) VALUES (30, 'Информация о тридцатом дне новорожденного');
   INSERT INTO dailymail (id, info) VALUES (31, 'Информация о тридцать первом дне новорожденного');



INSERT INTO maternities (id, address, location_map, name) VALUES (3, '101000, г. Москва, ул. Покровка, д. 22а',null, 'МОНИИАГ');
INSERT INTO maternities (id, address, location_map, name) VALUES (2, '129336, г. Москва, ул. Таймырская, д. 6',null, 'Роддом №40');
