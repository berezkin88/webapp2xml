drop table CART;

create table CART (
	id smallserial not null primary key,
	userId int not null,
	status varchar(255),
	timestamp bigint
);

insert into cart(userid, status, timestamp) values (111, 'OPEN', 1560290002235);
insert into cart(userid, status, timestamp) values (222, 'OPEN', 1560290002245);
insert into cart(userid, status, timestamp) values (333, 'OPEN', 1560290002255);