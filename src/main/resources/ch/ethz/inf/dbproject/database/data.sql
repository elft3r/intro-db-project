INSERT INTO user (username, password) VALUES
("sandro", "bla"),
("jochen", "bla");

INSERT INTO category (name) VALUES
("Software"),
("Hardware");

INSERT INTO city (name) VALUES
("Zuerich"),
("Bern"),
("Basel");

INSERT INTO project (title, description, goal, start_date, end_date, city_id, category_id, owner_id) VALUES
("Project Nr. 01", "Some description for #01", 100.00, '2013-02-03 12:00:00', '2013-02-03 12:01:00', 1, 1, 1),
("Project Nr. 02", "Some description for #02", 2000.00, '2013-04-20 00:00:00', '2013-04-21 23:59:59', 1, 1, 1);

INSERT INTO funding_amount (amount, reward, project_id) VALUES
(10.00, "Some text for #01", 1),
(20.00, "Another reward for #01", 1),
(100.00, "Some text for #02", 2),
(250.00, "Another reward for #02", 2);

INSERT INTO stretched_goal (goal, bonus, project_id) VALUES
(150.00, "Meet the president", 1),
(2500.00, "Meet the president", 2);


INSERT INTO funds (user_id, fa_id) VALUES
(1, 1),
(2, 2);