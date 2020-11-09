CREATE SEQUENCE hibernate_sequence
  INCREMENT BY 1
  MINVALUE 1
  NO MAXVALUE
  START WITH 1
  CACHE 1
  NO CYCLE;


CREATE TABLE t_customers(
	id NUMERIC,
	customer_number NUMERIC,
	day_part NUMERIC,
	first_seen TIMESTAMP,
	tts NUMERIC
);

