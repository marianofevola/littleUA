CREATE SCHEMA littleua;
CREATE TABLE littleua.useragent
(
    id        varchar(255) PRIMARY KEY,
    useragent varchar(255),
    updated   timestamp default NOW()
)
