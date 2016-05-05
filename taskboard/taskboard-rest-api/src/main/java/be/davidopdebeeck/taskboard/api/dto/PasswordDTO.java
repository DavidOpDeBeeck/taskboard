package be.davidopdebeeck.taskboard.api.dto;

public class PasswordDTO
{
    private String projectId;
    private String password;

    public PasswordDTO() {}

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
