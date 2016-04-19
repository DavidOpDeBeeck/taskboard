package be.davidopdebeeck.taskboard.core;

import java.util.UUID;

/**
 * Task class
 */
public class Task extends Identifiable
{

    private String title;
    private String description;
    private String assignee;

    /**
     * Empty constructor
     */
    public Task()
    {
        this( null );
    }

    /**
     * Task constructor that takes a title
     *
     * @param title The title of the task
     */
    public Task( String title )
    {
        this( title, null, null );
    }

    /**
     * Task constructor that takes a title and a description
     *
     * @param title       The title of the task
     * @param description The description of the task
     */
    public Task( String title, String description )
    {
        this( title, description, null );
    }

    /**
     * Task constructor tat takes a title, a description and an assignee
     *
     * @param title       The title of the task
     * @param description The description of the task
     * @param assignee    The name of the assigned person of the task
     */
    public Task( String title, String description, String assignee )
    {
        this( UUID.randomUUID(), title, description, assignee );
    }

    /**
     * Task constructor tat takes a UUID, title, a description and an assignee
     *
     * @param id          The unique identifier of the task
     * @param title       The title of the task
     * @param description The description of the task
     * @param assignee    The name of the assigned person of the task
     */
    public Task( UUID id, String title, String description, String assignee )
    {
        super( id );
        this.title = title;
        this.description = description;
        this.assignee = assignee;
    }

    /**
     * Sets the title of the task
     *
     * @param title The title of the task
     */
    public void setTitle( String title )
    {
        this.title = title;
    }

    /**
     * Sets the description of the task
     *
     * @param description The description of the task
     */
    public void setDescription( String description )
    {
        this.description = description;
    }

    /**
     * Sets the name of the assigned person of the task
     *
     * @param assignee The name of the assigned person of the task
     */
    public void setAssignee( String assignee )
    {
        this.assignee = assignee;
    }

    /**
     * @return The title of the task
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @return The description of the task
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @return The name of the assigned person of the task
     */
    public String getAssignee()
    {
        return assignee;
    }
}
