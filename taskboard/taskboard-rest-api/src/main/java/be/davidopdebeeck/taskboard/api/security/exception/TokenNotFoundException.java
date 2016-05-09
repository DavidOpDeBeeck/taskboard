package be.davidopdebeeck.taskboard.api.security.exception;

public class TokenNotFoundException extends RuntimeException
{
    public TokenNotFoundException()
    {
        super( "No token was found in your cookies." );
    }
}
