CREATE TABLE IF NOT EXISTS auto_post (
	id SERIAL PRIMARY KEY,
	text text,
	created timestamp,
	photo bytea,
	auto_user_id int references auto_user(id),
	car_id int references car(id)
);
