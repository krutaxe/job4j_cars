CREATE TABLE IF NOT EXISTS history_owner (
   car_id int references car(id),
   driver_id int references driver(id),
   startAt date,
   endAt date
);