package be.davidopdebeeck.taskboard.dao.util;

import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.core.Project;
import be.davidopdebeeck.taskboard.core.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Class used to convert a ResultSet into a core Object
 */
public class Converter
{
    /**
     * Converts a ResultSet to a project
     *
     * @param set The ResultSet to convert to a project
     * @return A project converted from the ResultSet
     */
    public static Project toProject( ResultSet set ) throws SQLException
    {
        String uuid = set.getString( "id" );
        String title = set.getString( "title" );
        return new Project( UUID.fromString( uuid ), title );
    }

    /**
     * Converts a ResultSet to a lane
     *
     * @param set The ResultSet to convert to a lane
     * @return A lane converted from the ResultSet
     */
    public static Lane toLane( ResultSet set ) throws SQLException
    {
        String uuid = set.getString( "id" );
        String title = set.getString( "title" );
        Integer sequence = set.getInt( "sequence" );
        Boolean completed = set.getBoolean( "completed" );
        return new Lane( UUID.fromString( uuid ), title, sequence, completed );
    }

    /**
     * Converts a ResultSet to a task
     *
     * @param set The ResultSet to convert to a lane
     * @return A task converted from the ResultSet
     */
    public static Task toTask( ResultSet set ) throws SQLException
    {
        String uuid = set.getString( "id" );
        String title = set.getString( "title" );
        String description = set.getString( "description" );
        String assignee = set.getString( "assignee" );
        return new Task( UUID.fromString( uuid ), title, description, assignee );
    }
}
