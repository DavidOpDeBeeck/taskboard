package be.davidopdebeeck.taskboard.api.dto;

public class PasswordValidateDTO
{
    private String projectId;
    private String password;

    public PasswordValidateDTO() {}

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
