drop table USERENTITY;

create table USERENTITY (
	id smallserial not null primary key,
	username varchar(255) not null,
	password varchar(255) not null
);

insert into userentity(username, password) values ('user1', 'pass11');
insert into userentity(username, password) values ('user2', 'pass22');
insert into userentity(username, password) values ('user3', 'pass33');