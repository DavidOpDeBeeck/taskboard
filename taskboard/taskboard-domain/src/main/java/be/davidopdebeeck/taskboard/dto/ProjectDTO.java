package be.davidopdebeeck.taskboard.dto;

public class ProjectDTO {

    private String id;
    private String title;
    private boolean secured;

    public ProjectDTO() {}

    public void setId( String id ) {
        this.id = id;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public void setSecured( boolean secured ) {
        this.secured = secured;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isSecured() {
        return secured;
    }
}
