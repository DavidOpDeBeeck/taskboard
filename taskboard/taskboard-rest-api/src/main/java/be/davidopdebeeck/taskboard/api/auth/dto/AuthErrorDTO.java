package be.davidopdebeeck.taskboard.api.auth.dto;

import be.davidopdebeeck.taskboard.core.Project;
import org.springframework.http.HttpStatus;


public class AuthErrorDTO
{

    private Project project;
    private String message;

    public AuthErrorDTO() { }

    public AuthErrorDTO( Project project, String message )
    {
        this.project = project;
        this.message = message;
    }

    public String getTitle()
    {
        return project.getTitle();
    }

    public int getCode()
    {
        return 401;
    }

    public String getMessage()
    {
        return message;
    }
}
