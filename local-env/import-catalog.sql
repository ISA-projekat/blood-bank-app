CREATE TABLE address (
    id bigint not null auto_increment,
    country varchar(255) not null,
    city varchar(255) not null,
    street varchar(255) not null,
    number varchar(255) not null,
    PRIMARY KEY(id)
);

INSERT INTO address (country, city, street, number) VALUES ('Srbija', 'Novi Sad', 'Gunduliceva', '12');
INSERT INTO address (country, city, street, number) VALUES ('Srbija', 'Beograd', 'Knez Mihailova', '23');
INSERT INTO address (country, city, street, number) VALUES ('Indonesia', 'Jakarta', 'Java', '19');
INSERT INTO address (country, city, street, number) VALUES ('Srbija', 'Novi Sad', 'Milana Savica', '12');

CREATE TABLE blood_bank (
    id bigint not null auto_increment,
    address_id bigint,
    name varchar(255) not null,
    description varchar(255) not null,
    rating float,
    start_time time,
    end_time time,
    equipment_sets bigint DEFAULT 5,
    PRIMARY KEY(id),
    CONSTRAINT FOREIGN KEY (address_id) REFERENCES address(id)
);

INSERT INTO blood_bank (name, description, rating, start_time, end_time, address_id) VALUES ('Happy day', 'description for bank1', 2.5, '08:00:00', '20:00:00', 2);
INSERT INTO blood_bank (name, description, rating, start_time, end_time, address_id) VALUES ('There will be blood', 'description for bank2', 5.7, '08:00:00', '20:00:00', 2);
INSERT INTO blood_bank (name, description, rating, start_time, end_time, address_id) VALUES ('Bloody mary', 'description for bank3', 4.8, '08:00:00', '20:00:00', 1);
INSERT INTO blood_bank (name, description, rating, start_time, end_time, address_id) VALUES ('First needle', 'description for bank4', 9.6, '08:00:00', '20:00:00', 3);

CREATE TABLE appointment_slot (
    id bigint not null auto_increment,
    date_range varchar(255) not null,
    blood_bank_id bigint,
    status enum('FREE', 'TAKEN') DEFAULT 'FREE',
    PRIMARY KEY(id),
    CONSTRAINT FOREIGN KEY (blood_bank_id) REFERENCES blood_bank(id)
);

INSERT INTO appointment_slot (date_range, blood_bank_id, status) VALUES ('{"start": "2022-12-20T16:48:00.000Z", "end": "2022-12-20T16:49:00.000Z"}', 1, 'TAKEN');
INSERT INTO appointment_slot (date_range, blood_bank_id, status) VALUES ('{"start": "2022-12-20T16:50:00.000Z", "end": "2022-12-20T17:00:00.000Z"}', 2, 'TAKEN');
INSERT INTO appointment_slot (date_range, blood_bank_id, status) VALUES ('{"start": "2022-12-20T16:50:00.000Z", "end": "2022-12-20T17:00:00.000Z"}', 2, 'TAKEN');
INSERT INTO appointment_slot (date_range, blood_bank_id, status) VALUES ('{"start": "2022-12-21T16:50:00.000Z", "end": "2022-12-21T17:00:00.000Z"}', 2, 'FREE');
INSERT INTO appointment_slot (date_range, blood_bank_id, status) VALUES ('{"start": "2022-12-22T16:50:00.000Z", "end": "2022-12-22T17:00:00.000Z"}', 2, 'TAKEN');
INSERT INTO appointment_slot (date_range, blood_bank_id, status) VALUES ('{"start": "2022-12-20T16:50:00.000Z", "end": "2022-12-20T17:00:00.000Z"}', 2, 'TAKEN');
INSERT INTO appointment_slot (date_range, blood_bank_id, status) VALUES ('{"start": "2023-08-25T16:50:00.000Z", "end": "2023-08-25T17:00:00.000Z"}', 2, 'FREE');
INSERT INTO appointment_slot (date_range, blood_bank_id, status) VALUES ('{"start": "2022-12-26T16:50:00.000Z", "end": "2022-12-26T17:00:00.000Z"}', 2, 'FREE');
INSERT INTO appointment_slot (date_range, blood_bank_id, status) VALUES ('{"start": "2022-12-27T16:50:00.000Z", "end": "2022-12-27T17:00:00.000Z"}', 2, 'FREE');
INSERT INTO appointment_slot (date_range, blood_bank_id, status) VALUES ('{"start": "2022-12-28T16:50:00.000Z", "end": "2022-12-28T17:00:00.000Z"}', 2, 'FREE');
INSERT INTO appointment_slot (date_range, blood_bank_id, status) VALUES ('{"start": "2022-12-29T16:50:00.000Z", "end": "2022-12-29T17:00:00.000Z"}', 2, 'FREE');
INSERT INTO appointment_slot (date_range, blood_bank_id, status) VALUES ('{"start": "2022-12-21T16:40:00.000Z", "end": "2022-12-21T16:50:00.000Z"}', 2, 'FREE');
INSERT INTO appointment_slot (date_range, blood_bank_id, status) VALUES ('{"start": "2022-12-22T16:00:00.000Z", "end": "2022-12-22T16:10:00.000Z"}', 2, 'FREE');
INSERT INTO appointment_slot (date_range, blood_bank_id, status) VALUES ('{"start": "2022-12-23T16:50:00.000Z", "end": "2022-12-23T17:00:00.000Z"}', 3, 'FREE');
INSERT INTO appointment_slot (date_range, blood_bank_id, status) VALUES ('{"start": "2022-12-24T16:50:00.000Z", "end": "2022-12-24T17:00:00.000Z"}', 3, 'FREE');
INSERT INTO appointment_slot (date_range, blood_bank_id, status) VALUES ('{"start": "2022-12-25T16:50:00.000Z", "end": "2022-12-25T17:00:00.000Z"}', 3, 'FREE');

CREATE TABLE appointment_details (
    id bigint not null auto_increment,
    description varchar(255),
    PRIMARY KEY(id)
);

INSERT INTO appointment_details (description) VALUES ("Description for the first appointment");

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
    first_time Boolean,
    penalties int DEFAULT 0,
    gender enum('MALE', 'FEMALE'),
    role enum('SYS_ADMIN', 'BLOOD_BANK_ADMIN', 'REGISTERED', 'UNREGISTERED'),
    blood_bank_id bigint,
    PRIMARY KEY(id),
    CONSTRAINT FOREIGN KEY (blood_bank_id) REFERENCES blood_bank(id),
    CONSTRAINT FOREIGN KEY (address_id) REFERENCES address(id)
);

INSERT INTO user (email, password, first_name, last_name, blood_bank_id, address_id, active, role,first_time) VALUES ('user1@gmail.com', '$2a$10$y8lwQhcmGz5WQOGpNcFS9OhpZrjEWQA6CUY5BIZtDjM1WbXJiBPra', 'Name1', 'LastName1', 2, 3, true, 'SYS_ADMIN',false);
INSERT INTO user (email, first_name, last_name, address_id,first_time) VALUES ('user2@gmail.com', 'Name2', 'LastName2', 2,false);
INSERT INTO user (email, first_name, last_name, blood_bank_id,first_time) VALUES ('user3@gmail.com', 'Name2', 'LastName3', 1,false);
INSERT INTO user (email, password, first_name, last_name, active, role, blood_bank_id,first_time) VALUES ('administrator@gmail.com','$2a$10$y8lwQhcmGz5WQOGpNcFS9OhpZrjEWQA6CUY5BIZtDjM1WbXJiBPra', "Marko","Markovic",true, 'BLOOD_BANK_ADMIN', 2,false);
INSERT INTO user (email, first_name, last_name,role,first_time) VALUES ('jovan@gmail.com',"Jovan","Jovanovic",'BLOOD_BANK_ADMIN',false);
INSERT INTO user (email, first_name, last_name,role,first_time) VALUES ('uros@gmail.com',"Uros","Urosevic",'BLOOD_BANK_ADMIN',false);
INSERT INTO user (email, first_name, last_name,role,first_time) VALUES ('djomla@gmail.com',"Djomla","Djomlic",'BLOOD_BANK_ADMIN',false);
INSERT INTO user (email, first_name, last_name,role,first_time) VALUES ('joca@gmail.com',"Jovan","Ivanovic",'BLOOD_BANK_ADMIN',false);
INSERT INTO user (email, first_name, last_name,role,first_time) VALUES ('dule@gmail.com',"Dusan","Urosevic",'BLOOD_BANK_ADMIN',false);
INSERT INTO user (email, first_name, last_name,role,first_time) VALUES ('markos@gmail.com',"Marko","Silva",'BLOOD_BANK_ADMIN',false);
INSERT INTO user (email, password, first_name, last_name, address_id, active, role, jmbg, phone_number, occupation, penalties, gender,first_time) VALUES ("milos.gravara@gmail.com", '$2a$10$y8lwQhcmGz5WQOGpNcFS9OhpZrjEWQA6CUY5BIZtDjM1WbXJiBPra', "Milos", "Gravara", 4, true, 'REGISTERED', '2001000800027', '+381637437123', 'Student', 0, 'MALE',false);

CREATE TABLE appointment (
    id bigint not null auto_increment,
    appointment_details_id bigint,
    user_id bigint,
    appointment_slot_id bigint,
    status enum('SCHEDULED', 'CANCELED', 'FINISHED', 'NOT_ALLOWED') DEFAULT 'SCHEDULED',
    PRIMARY KEY(id),
    CONSTRAINT FOREIGN KEY (appointment_details_id) REFERENCES appointment_details(id),
    CONSTRAINT FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT FOREIGN KEY (appointment_slot_id) REFERENCES appointment_slot(id)
);

INSERT INTO appointment (appointment_slot_id, appointment_details_id, user_id, status) VALUES (1, 1, 2, 'FINISHED');
INSERT INTO appointment (appointment_slot_id, appointment_details_id, user_id, status) VALUES (2, 1, 3, 'FINISHED');
INSERT INTO appointment (appointment_slot_id, user_id, status) VALUES (5, 11, 'SCHEDULED');
INSERT INTO appointment (appointment_slot_id, user_id, status) VALUES (6, 11, 'SCHEDULED');
INSERT INTO appointment (appointment_slot_id, user_id, status) VALUES (3, 2, 'SCHEDULED');


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
INSERT INTO survey (user_id, survey_date, weight_over50kg, common_cold, skin_diseases, blood_pressure_problems, antibiotics, menstrual_cycle, dental_intervention, tattoo_piercing) VALUES (3, '2022-10-31 15:11:11', true, false, false, false, false, false, false, false);
INSERT INTO survey (user_id, survey_date, weight_over50kg, common_cold, skin_diseases, blood_pressure_problems, antibiotics, menstrual_cycle, dental_intervention, tattoo_piercing) VALUES (11, '2022-10-31 15:11:11', true, true, true, true, true, false, true, false);


CREATE TABLE blood_stock (
    id bigint not null auto_increment,
    blood_bank_id bigint,
    type enum('A', 'B', 'AB', 'O'),
    rh_factor enum('PLUS', 'MINUS'),
    quantity float,
    PRIMARY KEY(id),
    CONSTRAINT FOREIGN KEY (blood_bank_id) REFERENCES blood_bank(id)
);

INSERT INTO blood_stock (blood_bank_id, type, rh_factor, quantity) VALUES (2, 'B', 'PLUS', 0.0);
