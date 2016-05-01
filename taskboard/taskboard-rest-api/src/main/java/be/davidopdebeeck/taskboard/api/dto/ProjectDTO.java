package be.davidopdebeeck.taskboard.api.dto;

public class ProjectDTO
{
    private String title;
    private String password;

    public ProjectDTO() {}

    public void setTitle( String title )
    {
        this.title = title;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public String getTitle()
    {
        return title;
    }

    public String getPassword()
    {
        return password;
    }
}
