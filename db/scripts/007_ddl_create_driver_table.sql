CREATE TABLE IF NOT EXISTS driver (
   id serial PRIMARY KEY,
   name text,
   user_id int unique references auto_user(id)
);