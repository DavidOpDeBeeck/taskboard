package be.davidopdebeeck.taskboard.core;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Lane class
 */
@Entity
@Table( name = "lane" )
public class Lane extends Identifiable {

    @NotBlank( message = "Title should not be empty!")
    @Column( name = "title", nullable = false )
    private String title;

    @NotNull( message = "Sequence should not be empty!")
    @Column( name = "sequence", nullable = false )
    private Integer sequence;

    @NotNull( message = "Completed should not be empty!")
    @Column( name = "completed", nullable = false )
    private Boolean completed;

    @OneToMany( fetch = FetchType.EAGER, orphanRemoval = true )
    @JoinTable(
            name = "lane_has_task",
            joinColumns = {@JoinColumn( name = "lane_id", referencedColumnName = "id" )},
            inverseJoinColumns = {@JoinColumn( name = "task_id", referencedColumnName = "id" )}
    )
    private Set<Task> tasks;

    /**
     * Default constructor
     */
    public Lane() {}

    /**
     * Lane constructor that takes a title, sequence and completed status
     *
     * @param title     The title of the lane
     * @param sequence  The sequence of the lane
     * @param completed Defines if tasks are completed when in this lane
     */
    public Lane( String title, Integer sequence, Boolean completed ) {
        this.title = title;
        this.sequence = sequence;
        this.completed = completed;
    }

    /**
     * Adds a task to a lane
     *
     * @param task The task that will be added to the lane
     * @return If the task is successfully added to the lane
     */
    public boolean addTask( Task task ) {
        if (tasks == null)
            tasks = new HashSet<>();
        return tasks.add(task);
    }

    /**
     * Removed a task from the lane
     *
     * @param task The task that will be removed from the lane
     * @return If the task is successfully removed
     */
    public boolean removeTask( Task task ){
        if (tasks == null)
            return false;
        return tasks.remove(task);
    }

    /**
     * Sets the title of the lane
     *
     * @param title The title of the lane
     */
    public void setTitle( String title ) {
        this.title = title;
    }

    /**
     * Sets the sequence of the lane
     *
     * @param sequence The sequence of the lane
     */
    public void setSequence( int sequence ) {
        this.sequence = sequence;
    }

    /**
     * Sets if tasks are completed when residing in this lane
     *
     * @param completed If tasks are completed when residing in this lane
     */
    public void setCompleted( boolean completed ) {
        this.completed = completed;
    }

    /**
     * Sets the tasks of the lane
     *
     * @param tasks The tasks of the lane
     */
    public void setTasks( Set<Task> tasks ) {
        this.tasks = tasks;
    }

    /**
     * @return The title of the lane
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return If tasks are completed when residing in this lane
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * @return The sequence of the lane
     */
    public int getSequence() {
        return sequence;
    }

    /**
     * @return The tasks of the lane
     */
    public Set<Task> getTasks() {
        return tasks;
    }
}
