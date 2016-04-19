CREATE TABLE IF NOT EXISTS task (
  id          VARCHAR(255) NOT NULL,
  title       VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL,
  assignee    VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);