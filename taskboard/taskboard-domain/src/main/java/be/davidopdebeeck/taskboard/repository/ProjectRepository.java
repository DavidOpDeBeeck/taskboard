package be.davidopdebeeck.taskboard.repository;

import be.davidopdebeeck.taskboard.core.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {}
