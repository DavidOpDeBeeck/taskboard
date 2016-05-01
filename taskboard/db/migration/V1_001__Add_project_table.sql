CREATE TABLE IF NOT EXISTS project (
  id        VARCHAR(255) NOT NULL,
  title     VARCHAR(255) NOT NULL,
  password  VARCHAR(255),
  salt      VARCHAR(255),
  PRIMARY KEY (id)
);