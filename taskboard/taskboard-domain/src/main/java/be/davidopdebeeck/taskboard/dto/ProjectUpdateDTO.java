package be.davidopdebeeck.taskboard.dto;

public class ProjectUpdateDTO
{
    private String title;
    private boolean secured;
    private String password;

    public ProjectUpdateDTO() {}

    public void setTitle( String title )
    {
        this.title = title;
    }

    public void setSecured( boolean secured )
    {
        this.secured = secured;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public String getTitle()
    {
        return title;
    }

    public boolean isSecured()
    {
        return secured;
    }

    public String getPassword()
    {
        return password;
    }
}
