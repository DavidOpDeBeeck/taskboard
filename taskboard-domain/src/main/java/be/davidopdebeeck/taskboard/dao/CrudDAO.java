package be.davidopdebeeck.taskboard.dao;

import java.util.Collection;

/**
 * Defines the default behaviour of a CRUD-based DAO
 *
 * @param <OBJ> The type of the object the DAO handles
 * @param <KEY> The type of the unique identifier the object the DAO handles has
 */
public interface CrudDAO <OBJ, KEY>
{
    /**
     * Creates an object in the database and returns the same object with a new id
     *
     * @param object The object to create in the database
     * @return The objects that was added to the database
     */
    OBJ create( OBJ object );

    /**
     * Updates an object in the database and returns the same object
     *
     * @param object The object to update in the database
     */
    OBJ update( OBJ object );

    /**
     * Removes an object from the database
     *
     * @param object The object to remove from the database
     */
    void remove( OBJ object );

    /**
     * Gets an object by its unique identifier
     *
     * @param id The unique identifier of the object
     * @return The object with the specified unique identifier
     */
    OBJ getById( KEY id );

    /**
     * Gets all the objects
     *
     * @return A collection containing all the objects
     */
    Collection<OBJ> getAll();
}
