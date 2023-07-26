-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
--create table client
--(
--    id   bigserial not null primary key,
--    name varchar(50)
--);

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence client_SEQ start with 1 increment by 1;

create table clients
(
    id   bigint not null primary key,
    name varchar(255),
    id_address bigint
);

create table phone
(
    id   bigint not null primary key,
    Phone_number varchar(255),
    client_id bigint
);

create table address
(
    id   bigint not null primary key,
    Street varchar(255)
);

alter table clients
    add constraint fc_clients_address foreign key (id_address) references address;

alter table phone
       add constraint fk_phones_client foreign key (client_id) references clients;