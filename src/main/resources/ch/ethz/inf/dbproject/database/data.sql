INSERT INTO user (username, password) VALUES
("Sandro", "bla"),
("Yasmin", "bla"),
("Philipp", "bla"),
("Natalie", "bla"),
("Jochen", "bla");

INSERT INTO category (name) VALUES
("Software"),
("Networks"),
("Social Media"),
("Hardware");

INSERT INTO city (name) VALUES
("Zuerich"),
("Bern"),
("Heidelberg"),
("Stuttgart"),
("Basel");

INSERT INTO project (title, description, goal, start_date, end_date, city_id, category_id, owner_id) VALUES
("Project Nr. 01", "Some description for #01", 100.00, '2013-02-03 12:00:00', '2013-02-03 12:01:00', 1, 1, 1),
("Project Nr. 02", "Some description for #02", 2000.00, '2013-04-12 00:00:00', '2013-05-31 23:59:59', 1, 1, 1),
("Social Network", "A social network", 10000.00, '2013-03-19 00:00:00', '2013-06-19 23:59:59', 1, 1, 1),
("Smartphone OS", "A OS for smartphones", 30000.00, '2013-12-28 00:00:00', '2014-02-27 23:59:59', 1, 1, 5),
("New Car", "A new Car", 150000.00, '2013-06-01 00:00:00', '2013-08-31 23:59:59', 1, 1, 5),
("Some useless stuff", "Just give me the money", 100.00, '2013-02-13 00:00:00', '2013-03-31 23:59:59', 1, 1, 5);

INSERT INTO funding_amount (amount, reward, project_id) VALUES
(10.00, "Some text for #01", 1),
(20.00, "Another reward for #01", 1),
(100.00, "Some text for #02", 2),
(250.00, "Another reward for #02", 2),
(500.00, "A test drive in the new car", 5),
(1000.00, "A model of the new car", 5),
(5000.00, "A new car for half the price", 5);

INSERT INTO stretched_goal (goal, bonus, project_id) VALUES
(150.00, "Meet the president", 1),
(2500.00, "Meet the president", 2),
(3000.00, "Get something cool additianl!", 2),
(5000.00, "One trip to the Bahamas", 2),
(200000.00, "One extra feature included in your own new car", 5);


INSERT INTO funds (user_id, fa_id) VALUES
(1, 1),
(2, 2),
(2, 3),
(1, 7),
(1, 5),
(5, 2);

INSERT INTO comment (text, user_id, project_id) VALUES
("First comment", 1, 1),
("Second comment", 2, 1),
("Third comment", 1, 1),
("Fourth comment", 2, 1);
