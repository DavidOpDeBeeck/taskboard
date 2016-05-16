package be.davidopdebeeck.taskboard.repository;

import be.davidopdebeeck.taskboard.core.Project;
import be.davidopdebeeck.taskboard.core.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

    @Query( "SELECT t FROM Task t WHERE t.assignee = :assignee" )
    List<Task> findByAssignee( @Param( "assignee" ) String assignee );

    @Query( "SELECT p FROM Project p WHERE (SELECT l FROM Lane l WHERE :task MEMBER OF l.tasks) MEMBER OF p.lanes" )
    Project findProject( @Param( "task" ) Task task );
}
