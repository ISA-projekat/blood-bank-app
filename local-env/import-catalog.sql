CREATE TABLE address (
    id bigint not null auto_increment,
    country varchar(255) not null,
    city varchar(255) not null,
    street varchar(255) not null,
    number varchar(255) not null,
    PRIMARY KEY(id)
);

INSERT INTO address (country, city, street, number) VALUES ('country2', 'city2', 'street2', 'number2');
INSERT INTO address (country, city, street, number) VALUES ('country3', 'city3', 'street3', 'number3');
INSERT INTO address (country, city, street, number) VALUES ('country4', 'city4', 'street4', 'number4');

CREATE TABLE blood_bank (
    id bigint not null auto_increment,
    address_id bigint,
    name varchar(255) not null,
    description varchar(255) not null,
    rating float,
    start_time time,
    end_time time,
    PRIMARY KEY(id),
    CONSTRAINT FOREIGN KEY (address_id) REFERENCES address(id)
);

INSERT INTO blood_bank (name, description, rating, start_time, end_time, address_id) VALUES ('bank1', 'description for bank1', 2.5, '08:00:00', '20:00:00', 2);
INSERT INTO blood_bank (name, description, rating, start_time, end_time, address_id) VALUES ('bank2', 'description for bank2', 0.0, '08:00:00', '20:00:00', 2);
INSERT INTO blood_bank (name, description, rating, start_time, end_time, address_id) VALUES ('bank3', 'description for bank3', 0.0, '08:00:00', '20:00:00', 1);
INSERT INTO blood_bank (name, description, rating, start_time, end_time, address_id) VALUES ('bank4', 'description for bank4', 0.0, '08:00:00', '20:00:00', 3);

CREATE TABLE user (
    id bigint not null auto_increment,
    address_id bigint,
    email varchar(255),
    password varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    jmbg varchar(13),
    phone_number varchar(255),
    occupation varchar(255),
    active Boolean,
    penalties int,
    gender enum('MALE', 'FEMALE'),
    role enum('SYS_ADMIN', 'BLOOD_BANK_ADMIN', 'REGISTERED', 'UNREGISTERED'),
    blood_bank_id bigint,
    PRIMARY KEY(id),
    CONSTRAINT FOREIGN KEY (blood_bank_id) REFERENCES blood_bank(id),
    CONSTRAINT FOREIGN KEY (address_id) REFERENCES address(id)
);

INSERT INTO user (email, password, first_name, last_name, blood_bank_id, address_id) VALUES ('user1@gmail.com', '123', 'Name1', 'LastName1', 2, 3);
INSERT INTO user (email, first_name, last_name, address_id) VALUES ('user2@gmail.com', 'Name2', 'LastName2', 2);
INSERT INTO user (email, first_name, last_name, blood_bank_id) VALUES ('user3@gmail.com', 'Name2', 'LastName3', 1);

CREATE TABLE appointment_details (
    id bigint not null auto_increment,
    description varchar(255),
    PRIMARY KEY(id)
);

INSERT INTO appointment_details (description) VALUES ("Description for the first appointment");

CREATE TABLE appointment (
    id bigint not null auto_increment,
    appointment_details_id bigint,
    scheduled_date DATETIME not null,
    duration int not null,
    PRIMARY KEY(id),
    CONSTRAINT FOREIGN KEY (appointment_details_id) REFERENCES appointment_details(id)
);

INSERT INTO appointment (scheduled_date, duration, appointment_details_id) VALUES ('2022-10-30 11:11:11', 30, 1);
INSERT INTO appointment (scheduled_date, duration) VALUES ('2022-10-30 12:11:11', 20);
INSERT INTO appointment (scheduled_date, duration) VALUES ('2022-10-31 13:11:11', 30);
INSERT INTO appointment (scheduled_date, duration) VALUES ('2022-10-31 14:11:11', 20);
INSERT INTO appointment (scheduled_date, duration) VALUES ('2022-10-30 15:11:11', 30);

CREATE TABLE survey (
    id bigint not null auto_increment,
    user_id bigint,
    survey_date DATETIME not null,
    weight_over50kg Boolean not null,
    common_cold Boolean not null,
    skin_diseases Boolean not null,
    blood_pressure_problems Boolean not null,
    antibiotics Boolean not null,
    menstrual_cycle Boolean not null,
    dental_intervention Boolean not null,
    tattoo_piercing Boolean not null,
    PRIMARY KEY(id),
    CONSTRAINT FOREIGN KEY (user_id) REFERENCES user(id)
);

INSERT INTO survey (user_id, survey_date, weight_over50kg, common_cold, skin_diseases, blood_pressure_problems, antibiotics, menstrual_cycle, dental_intervention, tattoo_piercing) VALUES (1, '2022-10-30 15:11:11', true, false, false, false, false, false, true, false);
INSERT INTO survey (user_id, survey_date, weight_over50kg, common_cold, skin_diseases, blood_pressure_problems, antibiotics, menstrual_cycle, dental_intervention, tattoo_piercing) VALUES (2, '2022-10-31 15:11:11', true, true, true, true, true, false, true, false);
