package be.davidopdebeeck.taskboard.core;

import java.util.Set;
import java.util.UUID;

/**
 * Project class
 */
public class Project extends Identifiable
{
    private String title;
    private Set<Lane> lanes;

    /**
     * Empty constructor
     */
    public Project()
    {
        this( null );
    }

    /**
     * Project constructor that takes a title
     *
     * @param title The title of the project
     */
    public Project( String title )
    {
        this( UUID.randomUUID(), title );
    }

    /**
     * Project constructor that takes a UUID and a title
     *
     * @param id    The unique identifier of the project
     * @param title THe title of the project
     */
    public Project( UUID id, String title )
    {
        super( id );
        this.title = title;
    }

    /**
     * Sets the title of the project
     *
     * @param title The title of the project
     */
    public void setTitle( String title )
    {
        this.title = title;
    }

    /**
     * Sets the lanes of the project
     *
     * @param lanes The lanes of the project
     */
    public void setLanes( Set<Lane> lanes )
    {
        this.lanes = lanes;
    }

    /**
     * @return The title of the project
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @return The lanes of the project
     */
    public Set<Lane> getLanes()
    {
        return lanes;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;

        Project project = (Project) o;

        return id.equals( project.id );
    }

    @Override
    public int hashCode()
    {
        return id.hashCode();
    }
}
