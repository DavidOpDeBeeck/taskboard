package be.davidopdebeeck.taskboard.core;

import java.util.Set;
import java.util.UUID;

/**
 * Lane class
 */
public class Lane extends Identifiable
{

    private String title;
    private int sequence;
    private boolean completed;
    private Set<Task> tasks;

    /**
     * Default constructor
     */
    public Lane()
    {
        this( null, 0, false );
    }

    /**
     * Lane constructor that takes a title, sequence and completed status
     *
     * @param title     The title of the lane
     * @param sequence  The sequence of the lane
     * @param completed Defines if tasks are completed when in this lane
     */
    public Lane( String title, int sequence, boolean completed )
    {
        this( UUID.randomUUID(), title, sequence, completed );
    }

    /**
     * Lane constructor that takes a UUID, title, sequence and completed status
     *
     * @param id        The unique identifier of the lane
     * @param title     The title of the lane
     * @param sequence  The sequence of the lane
     * @param completed Defines if tasks are completed when in this lane
     */
    public Lane( UUID id, String title, int sequence, boolean completed )
    {
        super( id );
        this.title = title;
        this.sequence = sequence;
        this.completed = completed;
    }

    /**
     * Sets the title of the lane
     *
     * @param title The title of the lane
     */
    public void setTitle( String title )
    {
        this.title = title;
    }

    /**
     * Sets the sequence of the lane
     *
     * @param sequence The sequence of the lane
     */
    public void setSequence( int sequence )
    {
        this.sequence = sequence;
    }

    /**
     * Sets if tasks are completed when residing in this lane
     *
     * @param completed If tasks are completed when residing in this lane
     */
    public void setCompleted( boolean completed )
    {
        this.completed = completed;
    }

    /**
     * Sets the tasks of the lane
     *
     * @param tasks The tasks of the lane
     */
    public void setTasks( Set<Task> tasks )
    {
        this.tasks = tasks;
    }

    /**
     * @return The title of the lane
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @return If tasks are completed when residing in this lane
     */
    public boolean isCompleted()
    {
        return completed;
    }

    /**
     * @return The sequence of the lane
     */
    public int getSequence()
    {
        return sequence;
    }

    /**
     * @return The tasks of the lane
     */
    public Set<Task> getTasks()
    {
        return tasks;
    }
}
