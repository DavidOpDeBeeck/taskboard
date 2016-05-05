package be.davidopdebeeck.taskboard.api.security;

public interface SecurityManager
{

    String save( String key );
    boolean validate( String key, String value );

}
