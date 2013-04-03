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
("Project Nr. 01", "Some description for #01", 10.02, '2013-02-03 12:00:00', '2013-02-03 12:01:00', 1, 1, 1),
("Project Nr. 02", "Some description for #02", 2100.02, '2013-04-20 00:00:00', '2013-04-21 23:59:59', 1, 1, 1);

INSERT INTO funding_amount (amount, reward, project_id) VALUES
(10.01, "Some text for #01", 1),
(20.02, "Another reward for #01", 1),
(10.01, "Some text for #02", 2),
(20.02, "Another reward for #02", 2);

INSERT INTO funds (user_id, fa_id) VALUES
(1, 1),
(2, 2);