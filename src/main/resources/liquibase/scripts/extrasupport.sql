             INSERT INTO dailymail (id, info) VALUES (32, 'Информация о тридцать втором дне новорожденного');
              INSERT INTO dailymail (id, info) VALUES (33, 'Информация о тридцать третьем дне новорожденного');
               INSERT INTO dailymail (id, info) VALUES (34, 'Информация о тридцать четвертом дне новорожденного');
                INSERT INTO dailymail (id, info) VALUES (35, 'Информация о тридцать пятом дне новорожденного');
                 INSERT INTO dailymail (id, info) VALUES (36, 'Информация о тридцать шестом дне новорожденного');
                  INSERT INTO dailymail (id, info) VALUES (37, 'Информация о тридцать седьмом дне новорожденного');
                   INSERT INTO dailymail (id, info) VALUES (38, 'Информация о тридцать восьмом дне новорожденного');
                    INSERT INTO dailymail (id, info) VALUES (39, 'Информация о тридцать девятом дне новорожденного');
 INSERT INTO dailymail (id, info) VALUES (40, 'Информация о сороковом дне новорожденного');
 INSERT INTO dailymail (id, info) VALUES (41, 'Информация о сорок первом дне новорожденного');
 INSERT INTO dailymail (id, info) VALUES (42, 'Информация о сорок втором дне новорожденного');
  INSERT INTO dailymail (id, info) VALUES (43, 'Информация о сорок третьем дне новорожденного');
  INSERT INTO dailymail (id, info) VALUES (44, 'Информация о сорок четвертом дне новорожденного');
  INSERT INTO dailymail (id, info) VALUES (45, 'Информация о сорок пятом дне новорожденного');
   INSERT INTO dailymail (id, info) VALUES (46, 'Информация о сорок шестом дне новорожденного');

   -- liquibase formatted sql

   -- changeset nastya:3

   create table extra_support
   (
       id                    bigint not null
           primary key,
       user_id bigint not null
   );

   alter table extra_support
       owner to student;

       ALTER TABLE extra_support
           ADD CONSTRAINT extra_support_user_id_fk FOREIGN KEY (user_id)
               REFERENCES users (id)
               ON DELETE SET NULL
               ON UPDATE NO ACTION
               NOT DEFERRABLE;