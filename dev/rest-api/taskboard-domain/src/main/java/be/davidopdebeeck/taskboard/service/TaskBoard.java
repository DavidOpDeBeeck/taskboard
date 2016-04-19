package be.davidopdebeeck.taskboard.service;

import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.core.Project;
import be.davidopdebeeck.taskboard.core.Task;

import java.util.Collection;

/**
 * Defines the behaviour of the taskboard service
 */
public interface TaskBoard
{

    //-------------------------------------------
    // region Project
    //-------------------------------------------

    /**
     * Creates a new project with the specified title in the database
     *
     * @param title The title of the project that will be created
     */
    Project createProject( String title );

    /**
     * Updates a project in the database
     *
     * @param project The project that will be updated
     */
    Project updateProject( Project project );

    /**
     * Removes the project with the specified id
     *
     * @param id THe id of the project that will be removed
     */
    void removeProject( String id );

    /**
     * Gets a project with the specified identifier
     *
     * @param id The identifier of the project
     * @return The project with the specified identifier and null if not found
     */
    Project getProjectById( String id );

    /**
     * Gets all the projects
     *
     * @return A collection containing all the projects
     */
    Collection<Project> getAllProjects();

    /**
     * Adds a lane to a project
     *
     * @param projectId The id of the project to add the lane too
     * @param title     The title of the lane
     * @param sequence  The sequence of the lane
     * @param completed Defines if tasks are completed when in this lane
     * @return The lane that is added, if an error occurs null is returned
     */
    Lane addLaneToProject( String projectId, String title, int sequence, boolean completed );

    /**
     * Removes a lane from a project
     *
     * @param projectId The id of the project to remove the lane from
     * @param laneId    The id of the lane to remove
     * @return If the lane is successfully removed from the project
     */
    boolean removeLaneFromProject( String projectId, String laneId );

    //-------------------------------------------
    // endregion
    //-------------------------------------------

    //-------------------------------------------
    // region Lane
    //-------------------------------------------

    /**
     * Updates a lane in the database
     *
     * @param lane The lane that will be updated
     * @return The updated lane
     */
    Lane updateLane( Lane lane );

    /**
     * Gets a lane with the specified identifier
     *
     * @param id The identifier of the lane
     * @return The lane with the specified identifier and null if not found
     */
    Lane getLaneById( String id );

    /**
     * Adds a task to a lane
     *
     * @param laneId      The identifier of the lane to add the task too
     * @param title       The title of the task
     * @param description The identifier of the task
     * @param assignee    The assignee of the task
     * @return The task that is added, if an error occurs null is returned
     */
    Task addTaskToLane( String laneId, String title, String description, String assignee );

    /**
     * Adds a task to a lane
     *
     * @param laneId The identifier of the lane to add the task too
     * @param taskId The identifier of the task to add the task too
     * @return The task that is added, if an error occurs null is returned
     */
    Task addTaskToLane( String laneId, String taskId );

    /**
     * Removes a task from a lane
     *
     * @param laneId The identifier of the lane to remove the task from
     * @param taskId The identifier of the task to remove from the lane
     * @return If the task is successfully removed
     */
    boolean removeTaskFromLane( String laneId, String taskId );

    /**
     * Removes a lane from the database
     *
     * @param id The id of the lane to remove
     */
    void removeLane( String id );

    //-------------------------------------------
    // endregion
    //-------------------------------------------

    //-------------------------------------------
    // region Task
    //-------------------------------------------

    /**
     * Updates a task in the database
     *
     * @param task The task to update
     * @return The updated task
     */
    Task updateTask( Task task );

    /**
     * Gets a task with the specified identifier
     *
     * @param id The identifier of the task
     * @return The task with the specified identifier
     */
    Task getTaskById( String id );

    /**
     * Removes a task from the database
     *
     * @param id The id of the task to remove
     */
    void removeTask( String id );

    //-------------------------------------------
    // endregion
    //-------------------------------------------

}
