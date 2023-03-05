INSERT INTO USERS(NICK,NOMBRE)VALUES('ALVARO','CHAVES AGUILAR');
INSERT INTO USERS(NICK,NOMBRE)VALUES('IVAN','BARTOLOME CARVAJAL');
INSERT INTO USERS(NICK,NOMBRE)VALUES('DOMINGO','MATEUVE LASCO');
INSERT INTO USERS(NICK,NOMBRE)VALUES('MIGUEL','PIZARRO CABELLO');
INSERT INTO USERS(NICK,NOMBRE)VALUES('RODRIGO','MARTOS SORIA');
INSERT INTO USERS(NICK,NOMBRE)VALUES('MARIA','MATA MILLAN');
INSERT INTO USERS(NICK,NOMBRE)VALUES('SUSANA','MURILLO DA SILVA');
INSERT INTO USERS(NICK,NOMBRE)VALUES('VICENTA','CARBALLO CORRAL');
INSERT INTO USERS(NICK,NOMBRE)VALUES('IRIA','BERMUDEZ CAMPO');
INSERT INTO USERS(NICK,NOMBRE)VALUES('TOMAS','CARRASCO JURADO');
INSERT INTO USERS(NICK,NOMBRE)VALUES('YAIZA','BARRIO LARA');
INSERT INTO USERS(NICK,NOMBRE)VALUES('ALEXANDER','SAIZ MUÑIZ');
INSERT INTO USERS(NICK,NOMBRE)VALUES('JUSTO','CRESPO REBOLLO');
INSERT INTO USERS(NICK,NOMBRE)VALUES('MARTIN','BLAZQUEZ GIL');
INSERT INTO USERS(NICK,NOMBRE)VALUES('MONICA','ALARCON REINA');

insert into BLOGS (TITLE, DATE, USER_ID) values ('Piranha', '2022-07-03', 1);
insert into BLOGS (TITLE, DATE, USER_ID) values ('Two Kilers (Kilerów 2-óch)', '2022-03-01',1);
insert into BLOGS (TITLE, DATE, USER_ID) values ('Shanghai Calling', '2022-07-22', 3);
insert into BLOGS (TITLE, DATE, USER_ID) values ('Personal Property', '2022-08-11', 4);
insert into BLOGS (TITLE, DATE, USER_ID) values ('Zaat', '2022-07-01', '5');
insert into BLOGS (TITLE, DATE, USER_ID) values ('Artist and the Model, The (El artista y la modelo)', '2022-09-14', 6);
insert into BLOGS (TITLE, DATE, USER_ID) values ('Dragon Ball: The Curse Of The Blood Rubies (Doragon bôru: Shenron no densetsu)', '2022-04-09',7);
insert into BLOGS (TITLE, DATE, USER_ID) values ('Alpha and Omega 2: A Howl-iday Adventure (Alpha & Omega 2)', '2022-08-04', 8);
insert into BLOGS (TITLE, DATE, USER_ID) values ('Angie', '2022-09-10', 9);
insert into BLOGS (TITLE, DATE, USER_ID) values ('Rush: Beyond the Lighted Stage', '2022-05-26', 10);

insert into ARTICLES (TITLE, SUMMARY, CONTENT, BLOG_ID) values ('CRANK:VENENO EN LA SANGRE', 'ACCIÓN', 'Sthatham de chulo', 1);
insert into ARTICLES (TITLE, SUMMARY, CONTENT, BLOG_ID) values ('ARTICULO:UN EJEMPLO', 'TERROR', 'Esto es un articulo acerca de Shanghai', 3);
insert into ARTICLES (TITLE, SUMMARY, CONTENT, BLOG_ID) values ('PERSONAL:FACTS', 'DOCUMENTAL', 'Articulo relativo a Personal Property', 4);

insert into projects (description, language, open) values ('in faucibus orci luctus et ultrices posuere cubilia', 'Polish', true);
insert into projects (description, language, open) values ('project 12345', 'English', false);
insert into projects (description, language, open) values ('proyecto en fase beta', 'Spanish', true);

insert into PROJECT_USER (PROJECT_ID, USER_ID) values (1, 2);
insert into PROJECT_USER (PROJECT_ID, USER_ID) values (1, 4);
insert into PROJECT_USER (PROJECT_ID, USER_ID) values (2, 4);
insert into PROJECT_USER (PROJECT_ID, USER_ID) values (3, 1);
insert into PROJECT_USER (PROJECT_ID, USER_ID) values (3, 2);
insert into PROJECT_USER (PROJECT_ID, USER_ID) values (3, 6);
insert into PROJECT_USER (PROJECT_ID, USER_ID) values (3, 5);