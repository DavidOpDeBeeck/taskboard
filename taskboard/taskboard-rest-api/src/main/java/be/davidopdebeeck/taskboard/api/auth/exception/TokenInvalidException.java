package be.davidopdebeeck.taskboard.api.auth.exception;

import be.davidopdebeeck.taskboard.core.Project;

public class TokenInvalidException extends AuthException
{
    public TokenInvalidException( Project project )
    {
        super( project, "The provided token was invalid." );
    }
}

