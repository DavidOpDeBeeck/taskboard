USE taskboard;

CREATE TABLE IF NOT EXISTS project (
  id    VARCHAR(255) NOT NULL,
  title VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS lane (
  id        VARCHAR(255) NOT NULL,
  title     VARCHAR(255) NOT NULL,
  completed BOOL          NOT NULL,
  sequence  INTEGER      NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS task (
  id          VARCHAR(255) NOT NULL,
  title       VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL,
  assignee    VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS project_has_lane (
  project_id VARCHAR(255) NOT NULL,
  lane_id    VARCHAR(255) NOT NULL,
  PRIMARY KEY (project_id, lane_id),
  CONSTRAINT fk_project_has_lane_1
  FOREIGN KEY (project_id)
  REFERENCES project (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_project_has_lane_2
  FOREIGN KEY (lane_id)
  REFERENCES lane (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS lane_has_task (
  lane_id VARCHAR(255) NOT NULL,
  task_id VARCHAR(255) NOT NULL,
  PRIMARY KEY (lane_id, task_id),
  CONSTRAINT fk_lane_has_task_1
  FOREIGN KEY (lane_id)
  REFERENCES lane (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_lane_has_task_2
  FOREIGN KEY (task_id)
  REFERENCES task (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);
