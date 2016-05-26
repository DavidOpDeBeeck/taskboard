package be.davidopdebeeck.taskboard.dto;

public class LaneDTO
{

    private String id;
    private String title;
    private int sequence;
    private boolean completed;

    public LaneDTO() {}

    public void setId( String id ) {
        this.id = id;
    }

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

    public String getId() {
        return id;
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
