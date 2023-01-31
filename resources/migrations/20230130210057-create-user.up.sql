CREATE TABLE IF NOT EXISTS users (
  id uuid primary key,
  name varchar(32),
  email varchar(255) unique,
  password varchar(255),
  created_at timestamp with time zone default NOW(),
  updated_at timestamp with time zone default NOW()
);
