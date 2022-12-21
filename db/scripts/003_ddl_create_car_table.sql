CREATE TABLE IF NOT EXISTS car (
   id serial PRIMARY KEY,
   name text,
   engine_id int unique references engine(id)
);