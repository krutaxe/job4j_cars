CREATE TABLE IF NOT EXISTS car (
   id serial PRIMARY KEY,
   name text,
   engine_id int references engine(id)
);