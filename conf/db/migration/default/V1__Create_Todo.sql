CREATE TABLE todo_list (
  id SERIAL PRIMARY KEY,
  title TEXT NOT NULL
);

INSERT INTO todo_list(title) VALUES ('Clothes to buy');
INSERT INTO todo_list(title) VALUES ('Groceries');

CREATE TABLE todo (
  id SERIAL PRIMARY KEY,
  description TEXT NOT NULL,
  is_completed BOOLEAN NOT NULL DEFAULT FALSE,
  todo_list_id INTEGER REFERENCES todo_list ON DELETE CASCADE
);

INSERT INTO todo (description, is_completed, todo_list_id) VALUES ('Shirt', false, 1);
INSERT INTO todo (description, is_completed, todo_list_id) VALUES ('Watch', true, 1);
INSERT INTO todo (description, is_completed, todo_list_id) VALUES ('Shoes', false, 1);

INSERT INTO todo (description, is_completed, todo_list_id) VALUES ('Milk', false, 2);
INSERT INTO todo (description, is_completed, todo_list_id) VALUES ('Bread', true, 2);
INSERT INTO todo (description, is_completed, todo_list_id) VALUES ('Coffee', false, 2);
INSERT INTO todo (description, is_completed, todo_list_id) VALUES ('Bananas', false, 2);
INSERT INTO todo (description, is_completed, todo_list_id) VALUES ('Ground beef', false, 2);
INSERT INTO todo (description, is_completed, todo_list_id) VALUES ('Hamburger buns', false, 2);
