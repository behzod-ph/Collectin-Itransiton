insert into users(name,email,roles,password,language,creation_date,created_by)
values ('admin','admin@gmail.com',3,'$2a$08$vvovKuHMqhavQ7905J.SGObyIS6E9d/s5CxvWbkLSTSTDiOnEZZdi','ENG',now(),'anonymous'),
('John','john@gmail.com',1,'$2a$08$vvovKuHMqhavQ7905J.SGObyIS6E9d/s5CxvWbkLSTSTDiOnEZZdi','ENG',now(),'anonymous'),
('Eric','eric@gmail.com',1,'$2a$08$vvovKuHMqhavQ7905J.SGObyIS6E9d/s5CxvWbkLSTSTDiOnEZZdi','ENG',now(),'anonymous'),
('Morgana','morgana@gmail.com',1,'$2a$08$vvovKuHMqhavQ7905J.SGObyIS6E9d/s5CxvWbkLSTSTDiOnEZZdi','ENG',now(),'anonymous'),
('Phil','phil@gmail.com',1,'$2a$08$vvovKuHMqhavQ7905J.SGObyIS6E9d/s5CxvWbkLSTSTDiOnEZZdi','ENG',now(),'anonymous'),
('Alisa','alisa@gmail.com',1,'$2a$08$vvovKuHMqhavQ7905J.SGObyIS6E9d/s5CxvWbkLSTSTDiOnEZZdi','ENG',now(),'anonymous'),
('Henry','henry@gmail.com',1,'$2a$08$vvovKuHMqhavQ7905J.SGObyIS6E9d/s5CxvWbkLSTSTDiOnEZZdi','ENG',now(),'anonymous'),
('Stranger','stranger@gmail.com',1,'$2a$08$vvovKuHMqhavQ7905J.SGObyIS6E9d/s5CxvWbkLSTSTDiOnEZZdi','ENG',now(),'anonymous');


insert into topic(name, creation_date,created_by)
values ('art',now(),'anonymous'),
('coin',now(),'anonymous'),
('book',now(),'anonymous'),
('writings',now(),'anonymous'),
('jewelry',now(),'anonymous'),
('gems',now(),'anonymous'),
('car',now(),'anonymous'),
('stamp',now(),'anonymous'),
('toy',now(),'anonymous'),
('artefact',now(),'anonymous'),
('gun',now(),'anonymous'),
('drinks',now(),'anonymous'),
('others',now(),'anonymous');

insert into tag(name,creation_date,created_by)
values ('new',now(),'anonymous'),
('old',now(),'anonymous'),
('ancient',now(),'anonymous'),
('90''s',now(),'anonymous'),
('80''s',now(),'anonymous'),
('70''s',now(),'anonymous'),
('60''s',now(),'anonymous'),
('50''s',now(),'anonymous'),
('trending',now(),'anonymous'),
('unique',now(),'anonymous'),
('rare',now(),'anonymous'),
('expensive',now(),'anonymous'),
('glamorous',now(),'anonymous'),
('cheap',now(),'anonymous');

insert into collection(name,description,user_id,topic_id,creation_date,created_by) values
('Programming books','Programming books collected over years',2,3,now(),'anonymous'),
('Shakespeare books','Shakespeare books only',3,3,now(),'anonymous'),
('Ancient Guns','Rare guns',4,11,now(),'anonymous'),
('Paintings of Picasso','This collection is personal ',5,1,now(),'anonymous'),
('My Toys','Collected toys since  my childhood',6,9,now(),'anonymous');

insert into field(name,type,creation_date,created_by,collection_id)
values ('name',2,now(),'anonymous',1),
('author',2,now(),'anonymous',1),
('published year',1,now(),'anonymous',1)
;

insert into item(name,collection_id,creation_date,created_by) values ('Effective Java 4-edition',1,now(),'anonymous');
insert into field_value(value,field_id,item_id,creation_date,created_by)
values ('Effective Java 4-edition',1,1,now(),'anonymous'),
       ('Josh Bloch',2,1,now(),'anonymous'),
       ('2016',3,1,now(),'anonymous');

insert into item_tags (item_id, tags_id)
values (1,1),(1,9);
