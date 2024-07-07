-- liquibase formatted sql
-- version 0.0.1

-- changeset example:4
ALTER TABLE member ADD COLUMN email VARCHAR(255);
-- changeset kms0428:5
ALTER TABLE member ADD COLUMN contact VARCHAR(255);