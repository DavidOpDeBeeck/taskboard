package be.davidopdebeeck.taskboard.core;

import java.util.UUID;

/**
 * Identifiable class
 */
public abstract class Identifiable
{

    protected final UUID id;

    /**
     * Identifiable constructor that takes an UUID
     *
     * @param id The unique id of the identifiable
     */
    public Identifiable( UUID id )
    {
        this.id = id;
    }

    /**
     * @return The unique id of the identifiable
     */
    public String getId()
    {
        return id.toString();
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;

        Identifiable that = (Identifiable) o;

        return id.equals( that.id );
    }

    @Override
    public int hashCode()
    {
        return id.hashCode();
    }
}
