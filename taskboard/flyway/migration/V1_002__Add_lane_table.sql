CREATE TABLE IF NOT EXISTS lane (
  id        VARCHAR(255) NOT NULL,
  title     VARCHAR(255) NOT NULL,
  completed BOOL         NOT NULL,
  sequence  INTEGER      NOT NULL,
  PRIMARY KEY (id)
);