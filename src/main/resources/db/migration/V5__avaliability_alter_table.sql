ALTER TABLE availabilities
ALTER COLUMN "start" TYPE timestamp without time zone
USING current_date + start;

ALTER TABLE availabilities
ALTER COLUMN "end" TYPE timestamp without time zone
USING current_date + "end";