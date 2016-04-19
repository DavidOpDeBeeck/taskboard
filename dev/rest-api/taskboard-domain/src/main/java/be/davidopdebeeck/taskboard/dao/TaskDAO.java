package be.davidopdebeeck.taskboard.dao;

import be.davidopdebeeck.taskboard.core.Project;
import be.davidopdebeeck.taskboard.core.Task;

import java.util.Collection;

/**
 * Defines the behaviour of a TaskDAO
 */
public interface TaskDAO extends CrudDAO<Task, String>
{
    /**
     * Gets the tasks from a project from an assignee
     *
     * @param project  The project to get the tasks from
     * @param assignee The assignee to get the tasks from
     * @return All the tasks from a project from the assignee
     */
    Collection<Task> getByAssignee( Project project, String assignee );
}
