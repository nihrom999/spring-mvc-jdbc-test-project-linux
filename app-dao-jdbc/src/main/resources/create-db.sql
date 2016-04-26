CREATE MEMORY TEMPORARY TABLE department
(
  department_id int(10) NOT NULL AUTO_INCREMENT,
  name varchar(80) NOT NULL,
  PRIMARY KEY (department_id)
);

CREATE MEMORY TEMPORARY TABLE employee
(
  employee_id int(10) NOT NULL AUTO_INCREMENT,
  first_name varchar(45) NOT NULL,
  last_name varchar(45) NOT NULL,
  date_of_birth date NOT NULL,
  salary double DEFAULT NULL,
  department_id int(10) NOT NULL,
  PRIMARY KEY (employee_id),
  FOREIGN KEY (department_id) REFERENCES department (department_id) ON DELETE CASCADE ON UPDATE NO ACTION
);