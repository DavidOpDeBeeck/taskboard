package be.davidopdebeeck.taskboard.api.auth.dto;

public class AuthRequestDTO
{
    private String projectId;
    private String password;

    public AuthRequestDTO() {}

    public void setProjectId( String projectId )
    {
        this.projectId = projectId;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public String getProjectId()
    {
        return projectId;
    }

    public String getPassword()
    {
        return password;
    }
}
