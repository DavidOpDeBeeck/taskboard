package be.davidopdebeeck.taskboard.core;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Task class
 */
@Entity
@Table( name = "task" )
public class Task extends Identifiable
{

    @NotBlank( message = "Title should not be empty!")
    @Column( name = "title", nullable = false )
    private String title;

    @Column( name = "description" )
    private String description;

    @Column( name = "assignee" )
    private String assignee;

    /**
     * Empty constructor
     */
    public Task() {}

    /**
     * Task constructor that takes a title
     *
     * @param title The title of the task
     */
    public Task( String title )
    {
        this.title = title;
    }

    /**
     * Task constructor that takes a title and a description
     *
     * @param title       The title of the task
     * @param description The description of the task
     */
    public Task( String title, String description )
    {
        this( title );
        this.description = description;
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
        this( title, description );
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
