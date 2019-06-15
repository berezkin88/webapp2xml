drop table ORDERENTITY;

create table ORDERENTITY (
	id smallserial not null primary key,
	productId int not null,
	quantity int not null,
	cartId int not null
);

insert into ORDERENTITY(productid, quantity, cartid) values (11, 1, 111);
insert into ORDERENTITY(productid, quantity, cartid) values (22, 2, 222);
insert into ORDERENTITY(productid, quantity, cartid) values (33, 3, 333);