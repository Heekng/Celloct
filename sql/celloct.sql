create sequence hibernate_sequence start with 1 increment by 1;

create table join_request
(
    join_request_id    bigint not null,
    create_date        timestamp,
    last_modified_date timestamp,
    member_id          bigint not null,
    shop_id            bigint not null,
    primary key (join_request_id)
);

create table manager
(
    manager_id         bigint       not null,
    create_date        timestamp,
    last_modified_date timestamp,
    name               varchar(255) not null,
    member_id          bigint       not null,
    shop_id            bigint       not null,
    primary key (manager_id)
);

create table member
(
    member_id          bigint       not null,
    create_date        timestamp,
    last_modified_date timestamp,
    email              varchar(255) not null,
    name               varchar(255) not null,
    picture            varchar(255),
    role               varchar(255),
    primary key (member_id)
);

create table shop
(
    shop_id            bigint       not null,
    create_date        timestamp,
    last_modified_date timestamp,
    info               varchar(255),
    name               varchar(255) not null,
    phone              varchar(255),
    primary key (shop_id)
);

create table staff
(
    staff_id           bigint       not null,
    create_date        timestamp,
    last_modified_date timestamp,
    employment_date    date         not null,
    name               varchar(255) not null,
    member_id          bigint       not null,
    shop_id            bigint       not null,
    primary key (staff_id)
);

create table work
(
    work_id            bigint    not null,
    create_date        timestamp,
    last_modified_date timestamp,
    note               varchar(255),
    end_date           timestamp not null,
    start_date         timestamp not null,
    work_date          date      not null,
    staff_id           bigint    not null,
    primary key (work_id)
);

create table work_update_request
(
    work_update_request_id bigint    not null,
    create_date            timestamp,
    last_modified_date     timestamp,
    end_date               timestamp not null,
    start_date             timestamp not null,
    work_date              date      not null,
    work_id                bigint    not null,
    primary key (work_update_request_id)
);

create unique index join_request_member_id_shop_id_unique
    on join_request (member_id, shop_id);

create unique index manager_member_id_shop_id_unique
    on manager (member_id, shop_id);

create unique index member_email_unique
    on member (email);

create unique index shop_name_unique
    on shop (name);

create unique index staff_member_id_shop_id_unique
    on staff (member_id, shop_id);

create unique index work_work_date_staff_id_unique
    on work (work_date, staff_id);

create unique index work_update_request_work_id_unique
    on work_update_request (work_id);

alter table work_update_request
    add constraint FK3tijcd7dmbq3l4anj757goan2
        foreign key (work_id)
            references work;

alter table work
    add constraint FKesvlqpl8nihbjsjsnj9q3tja9
        foreign key (staff_id)
            references staff;

alter table staff
    add constraint FKccrc9bpu2yjrae88gmx3k59l6
        foreign key (shop_id)
            references shop;

alter table staff
    add constraint FK7r01wmst7fqgsl08000m9r6t5
        foreign key (member_id)
            references member;

alter table manager
    add constraint FKgv8o9ylwxq2nmbroq6yf54woa
        foreign key (shop_id)
            references shop;

alter table manager
    add constraint FK4bx6rcohffk4achc2skff0mds
        foreign key (member_id)
            references member;

alter table join_request
    add constraint FKgc6qoglxfvhe70riqpqjbcw0d
        foreign key (shop_id)
            references shop;

alter table join_request
    add constraint FKnxpcf89fghgo6atfsyxm15nki
        foreign key (member_id)
            references member;