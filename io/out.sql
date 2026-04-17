-- CREATE TABLE COMMAND --
DROP TABLE IF EXISTS employees;
CREATE TABLE employees (inte_id(auto_inc,pk),String(50)first_name(notnull),String(50)last_name(notnull),Datedob(notnull),double(10,2)salary,referencedepartmentsusingintdepartment_id
);

DROP TABLE IF EXISTS employees;

INSERT INTO employees(first_name,last_name,dob,department_id) VALUES ('Jane','Doe','2026-03-21',3);

SELECT * FROM employees;

SELECT first_name,last_name FROM employees WHERE e_id = 1;

SELECT name FROM departments GROUP BY name HAVING department_id < 5;

SELECT * FROM employees ORDER BY salary;

SELECT * FROM employees ORDER BY salary;

SELECT * FROM employees LIMIT 1;

SELECT employees.* departments.department_name  FROM employees JOIN departments ON department_id;

DELETE FROM employees WHERE e_id = 5 AND name = 'Stephaine';

UPDATE employees SET salary=75000 WHERE e_id = 1;

.fullschema

.tables


