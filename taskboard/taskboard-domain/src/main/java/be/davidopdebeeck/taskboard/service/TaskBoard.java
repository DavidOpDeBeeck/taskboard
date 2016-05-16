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
     * Creates a new project with the specified title and password in the database
     *
     * @param title             The title of the project that will be created
     * @param plainTextPassword The plaintext password of the project that will be created
     */
    Project createProject( String title, String plainTextPassword );

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

    //-------------------------------------------
    // endregion
    //-------------------------------------------

    //-------------------------------------------
    // region Lane
    //-------------------------------------------

    /**
     * Creates a new lane
     *
     * @param title     The title of the lane
     * @param sequence  The sequence of the lane
     * @param completed Defines if tasks are completed when in this lane
     * @return The lane that is created
     */
    Lane createLane( String title, int sequence, boolean completed );

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
     * Creates a new task
     *
     * @param title       The title of the task
     * @param description The identifier of the task
     * @param assignee    The assignee of the task
     * @return The task that is created
     */
    Task createTask( String title, String description, String assignee );

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
