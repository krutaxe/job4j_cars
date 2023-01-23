CREATE TABLE IF NOT EXISTS auto_user (
	id SERIAL PRIMARY KEY,
	login TEXT not null unique ,
   password TEXT not null
);
