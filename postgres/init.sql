DROP DATABASE IF EXISTS home;
CREATE DATABASE home;

CREATE TABLE distance_distribution (
   calendar_date DATE NOT NULL,
   total_trip_count INT,
   mean_distance NUMERIC (15, 2),
   stddev NUMERIC (15, 2),
   min_distance NUMERIC (15, 2),
   max_distance NUMERIC (15, 2),
   CONSTRAINT distance_distribution_pk PRIMARY KEY (calendar_date)
);

CREATE TABLE pickup_by_hour (
    hour_of_day           INT,
    total_trips           INT
);