create schema gift_certificates_db;

create table gift_certificate
(
    id               bigint auto_increment
        primary key,
    name             varchar(45)                         not null,
    description      varchar(250)                        not null,
    price            decimal(10, 2)                      not null,
    duration         int                                 not null,
    create_date      timestamp default CURRENT_TIMESTAMP not null,
    last_update_date timestamp default CURRENT_TIMESTAMP not null
);


create table tag
(
    id   bigint auto_increment
        primary key,
    name varchar(45) not null
);

create table gift_certificate_has_tag
(
    gift_certificate_id bigint not null,
    tag_id              bigint not null,
    primary key (gift_certificate_id, tag_id),
    constraint fk_gift_certificate_has_tag_gift_certificate
        foreign key (gift_certificate_id) references gift_certificate (id)
            on delete cascade on update cascade,
    constraint fk_gift_certificate_has_tag_tag1
        foreign key (tag_id) references tag (id)
            on delete cascade on update cascade
);