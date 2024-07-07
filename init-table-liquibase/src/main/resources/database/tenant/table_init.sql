-- liquibase formatted sql
-- version version 0.0.1

-- changeset example:1
create table member
(
    id        bigint unsigned auto_increment
        primary key,
    name      varchar(255) null,
    create_at datetime     null,
    update_at datetime     null

);

-- changeset example:2
CREATE TABLE person (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100),
                        age INT
);

-- changeset example:3
INSERT INTO member (name, create_at, update_at) VALUES ('John Doe', NOW(), NOW());

