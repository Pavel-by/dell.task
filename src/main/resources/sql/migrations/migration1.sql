CREATE TABLE IF NOT EXISTS containers(
    hash char(64) PRIMARY KEY,
    creation_time timestamp DEFAULT current_timestamp NOT NULL,
    last_modified_time timestamp DEFAULT current_timestamp NOT NULL,
    objects_count integer,
    container_name varchar(256)
);