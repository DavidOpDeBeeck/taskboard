package be.davidopdebeeck.taskboard.dao;

import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.core.Project;

import java.util.Set;

/**
 * Defines the behaviour of a ProjectDAO
 */
public interface ProjectDAO extends CrudDAO<Project, String>
{
    /**
     * Adds a lane to the project
     *
     * @param project The project to add the lane to
     * @param lane    The lane to add to the project
     * @return If the lane is successfully added to the project
     */
    boolean addLane( Project project, Lane lane );

    /**
     * Removes a lane from the project
     *
     * @param project The project to remove the lane from
     * @param lane    The lane to remove from the project
     * @return If the lane is successfully remove to the project
     */
    boolean removeLane( Project project, Lane lane );

    /**
     * Gets the lanes from a project
     *
     * @param project The project to get the lanes from
     * @return A set containing the lanes from the specified project
     */
    Set<Lane> getLanes( Project project );
}
