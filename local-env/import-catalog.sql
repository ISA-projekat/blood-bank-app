CREATE TABLE appointment (
    id bigint not null auto_increment,
    scheduled_date DATETIME not null,
    duration int not null,
    PRIMARY KEY(id)
);

INSERT INTO appointment (scheduled_date, duration) VALUES ('2022-10-30 11:11:11', 30);
INSERT INTO appointment (scheduled_date, duration) VALUES ('2022-10-30 12:11:11', 20);
INSERT INTO appointment (scheduled_date, duration) VALUES ('2022-10-31 13:11:11', 30);
INSERT INTO appointment (scheduled_date, duration) VALUES ('2022-10-31 14:11:11', 20);
INSERT INTO appointment (scheduled_date, duration) VALUES ('2022-10-30 15:11:11', 30);
