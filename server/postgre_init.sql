create table users (
  id        serial primary key,
  username  varchar(50) unique not null,
  name      varchar(100) not null,
  password  char(128) not null,
  email     varchar(100) unique not null
);
