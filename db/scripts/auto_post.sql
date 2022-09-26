CREATE TABLE IF NOT EXISTS auto_post (
	id SERIAL PRIMARY KEY,
	text text,
	created date,
	auto_user_id int references auto_user(id)
);
