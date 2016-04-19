package be.davidopdebeeck.taskboard.api.dto;

public class ProjectDTO
{
    private String title;

    public ProjectDTO() {}

    public void setTitle( String title )
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
}
