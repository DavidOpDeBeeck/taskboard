package be.davidopdebeeck.taskboard.api.security.exception;

public class TokenInvalidException extends Exception
{
    public TokenInvalidException()
    {
        super( "The provided token was invalid." );
    }
}

