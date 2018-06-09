CREATE TABLE "sensors"(
    "id"   Serial PRIMARY KEY,
    "sensor_id" VARCHAR NOT NULL,
    "reading_time" VARCHAR NOT NULL,
    "sensor_type" VARCHAR NOT NULL,
    "measurement1" real NOT NULL,
    "measurement2" real NOT NULL,
    "measurement3" real NOT NULL
);