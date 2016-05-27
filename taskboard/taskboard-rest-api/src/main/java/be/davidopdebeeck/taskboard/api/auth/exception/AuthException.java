package be.davidopdebeeck.taskboard.api.auth.exception;

import be.davidopdebeeck.taskboard.core.Project;

public class AuthException extends  RuntimeException
{

    private Project project;

    public AuthException( Project project, String message )
    {
        super( message );
        this.project = project;
    }

    public Project getProject()
    {
        return project;
    }
}
