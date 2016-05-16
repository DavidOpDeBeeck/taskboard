package be.davidopdebeeck.taskboard.core;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Identifiable class
 */
@MappedSuperclass
public abstract class Identifiable
{

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    protected String id;

    /**
     * Default constructor
     */
    public Identifiable() {}

    /**
     * Sets the id of the identifiable
     * @param id The id of the identifiable
     */
    public void setId( String id ) {
        this.id = id;
    }

    /**
     * @return The unique id of the identifiable
     */
    public String getId()
    {
        return id;
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
