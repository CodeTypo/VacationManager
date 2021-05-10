CREATE DATABASE IF NOT EXISTS vacationmanager;
USE vacationmanager;

CREATE TABLE IF NOT EXISTS employees(
    e_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    e_employee_login VARCHAR(100) NOT NULL,
	e_employee_password VARCHAR(100) NOT NULL,
	e_is_admin BOOLEAN
);

INSERT INTO employees (e_employee_login,e_employee_password,e_is_admin) VALUES ("Uzytkownik1", "User1", false);
INSERT INTO employees (e_employee_login,e_employee_password,e_is_admin) VALUES ("AdminAntek", "Admin1", true);

CREATE TABLE IF NOT EXISTS vacations(
    v_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    v_employee_id INT ,
	v_begin_date DATE,
    v_end_date DATE,
    v_approved BOOLEAN,
	FOREIGN KEY (v_employee_id) REFERENCES employees (e_id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS details(
    d_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	d_employee_id INTEGER NOT NULL,
	d_first_name VARCHAR(50) NOT NULL,
    d_last_name VARCHAR(50) NOT NULL,
    d_email VARCHAR(100) NOT NULL,
    d_available_vacation_days INT DEFAULT 20,
	FOREIGN KEY (d_employee_id) REFERENCES employees (e_id) ON DELETE RESTRICT ON UPDATE CASCADE
);
INSERT INTO details (d_employee_id, d_first_name,d_last_name,d_email) VALUES (1,"Dawid", "Kupczyk","dawid.a.kupczyk@gmail.com");
INSERT INTO details (d_employee_id, d_first_name,d_last_name,d_email) VALUES (2,"Jakub", "Skurjat","skurjatjakub@o2.pl");