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