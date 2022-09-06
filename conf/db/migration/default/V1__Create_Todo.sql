CREATE TABLE todos (
  id SERIAL PRIMARY KEY,
  description TEXT NOT NULL,
  is_completed BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO todos (description, is_completed) VALUES ('shirt', false);
INSERT INTO todos (description, is_completed) VALUES ('watch', true);
INSERT INTO todos (description, is_completed) VALUES ('shoes', false);