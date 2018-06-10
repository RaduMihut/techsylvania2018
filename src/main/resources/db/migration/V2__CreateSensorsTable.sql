CREATE TABLE "sensors"(
    "id"   Serial PRIMARY KEY,
    "sensor_id" VARCHAR NOT NULL,
    "reading_time" VARCHAR NOT NULL,
    "sensor_type" VARCHAR NOT NULL,
    "measurement" real NOT NULL
);