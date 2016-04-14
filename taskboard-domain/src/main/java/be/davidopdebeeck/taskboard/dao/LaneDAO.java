package be.davidopdebeeck.taskboard.dao;

import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.core.Project;
import be.davidopdebeeck.taskboard.core.Task;

import java.util.Set;

/**
 * Defines the behaviour of a LaneDAO
 */
public interface LaneDAO extends CrudDAO<Lane, String>
{
    /**
     * Add a task to the lane
     *
     * @param lane The lane to add the task to
     * @param task The task to add to the lane
     * @return If the task is successfully added to the lane
     */
    boolean addTask( Lane lane, Task task );

    /**
     * Removes a task from a lane
     *
     * @param lane The lane to remove the task from
     * @param task The task to remove from the lane
     * @return If the task is successfully removed from the lane
     */
    boolean removeTask( Lane lane, Task task );

    /**
     * Gets all the tasks from a lae
     *
     * @param lane The lane to get the tasks from
     * @return A set containing all the tasks from a lane
     */
    Set<Task> getTasks( Lane lane );

    /**
     * Gets the project from a lane
     *
     * @param lane The lane to get the project from
     * @return The project of the lane
     */
    Project getProject( Lane lane );
}
