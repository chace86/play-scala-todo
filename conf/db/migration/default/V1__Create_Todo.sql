CREATE TABLE todos (
  id SERIAL PRIMARY KEY,
  description TEXT NOT NULL,
  is_completed BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO todos (id, description, is_completed) VALUES (1, 'shirt', false);
INSERT INTO todos (id, description, is_completed) VALUES (2, 'watch', true);
INSERT INTO todos (id, description, is_completed) VALUES (3, 'shoes', false);