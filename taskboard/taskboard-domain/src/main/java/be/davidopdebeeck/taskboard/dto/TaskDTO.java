package be.davidopdebeeck.taskboard.dto;

public class TaskDTO
{

    private String id;
    private String title;
    private String description;
    private String assignee;

    public TaskDTO() {}

    public void setId( String id )
    {
        this.id = id;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public void setAssignee( String assignee )
    {
        this.assignee = assignee;
    }

    public String getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public String getAssignee()
    {
        return assignee;
    }
}
