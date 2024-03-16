create table member
(
    id        bigint unsigned auto_increment
        primary key,
    name      varchar(255) null,
    create_at datetime     null,
    update_at datetime     null
);