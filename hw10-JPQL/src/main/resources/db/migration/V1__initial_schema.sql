-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
--create table client
--(
--    id   bigserial not null primary key,
--    name varchar(50)
--);

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence client_SEQ start with 1 increment by 1;

create table address
(
    address_id   bigint not null primary key,
    address_street varchar(255)
);
create table clients
(
    client_id   bigint not null primary key,
    client_name varchar(255),
    address_id_fk bigint references address(address_id)
);


create table phone
(
    phone_id  bigint not null primary key,
    phone_number varchar(255),
    client_id bigint references clients(client_id)
);
