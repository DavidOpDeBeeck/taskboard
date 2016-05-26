package be.davidopdebeeck.taskboard.dto;

public class LaneUpdateDTO
{

    private String title;
    private int sequence;
    private boolean completed;

    public LaneUpdateDTO() {}

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
