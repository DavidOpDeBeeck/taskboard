CREATE TABLE IF NOT EXISTS lane_has_task (
  lane_id VARCHAR(255) NOT NULL,
  task_id VARCHAR(255) NOT NULL,
  PRIMARY KEY (lane_id, task_id),
  CONSTRAINT fk_lane_has_task_1
  FOREIGN KEY (lane_id)
  REFERENCES lane (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT fk_lane_has_task_2
  FOREIGN KEY (task_id)
  REFERENCES task (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);