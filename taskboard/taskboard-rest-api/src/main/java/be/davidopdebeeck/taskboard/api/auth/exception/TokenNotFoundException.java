package be.davidopdebeeck.taskboard.api.auth.exception;

import be.davidopdebeeck.taskboard.core.Project;

public class TokenNotFoundException extends AuthException
{
    public TokenNotFoundException( Project project )
    {
        super( project, "No token was found in your cookies." );
    }
}
