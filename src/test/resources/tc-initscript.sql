DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id    SERIAL PRIMARY KEY,
  name  VARCHAR(30)
);

INSERT INTO users VALUES (1, 'homie123');

DROP TABLE IF EXISTS messages;

CREATE TABLE messages (
    id  SERIAL PRIMARY KEY,
    senderId INT (30),
    receiverId INT(30),
    message VARCHAR(50)
);

INSERT INTO messages VALUES (1000, 222, 555, 'Hey Nigga');

