package be.davidopdebeeck.taskboard.api.dto;

public class LaneDTO
{

    private String title;
    private int sequence;
    private boolean completed;

    public LaneDTO() {}

    public void setTitle( String title )
    {
        this.title = title;
    }

    public void setSequence( int sequence )
    {
        this.sequence = sequence;
    }

    public void setCompleted( boolean completed )
    {
        this.completed = completed;
    }

    public String getTitle()
    {
        return title;
    }

    public int getSequence()
    {
        return sequence;
    }

    public boolean isCompleted()
    {
        return completed;
    }
}
