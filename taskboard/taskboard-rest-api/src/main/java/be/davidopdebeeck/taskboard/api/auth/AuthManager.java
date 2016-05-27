package be.davidopdebeeck.taskboard.api.auth;

public interface AuthManager
{

    String save( String key );
    boolean validate( String key, String value );

}
