package be.davidopdebeeck.taskboard.service;

import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.core.Project;
import be.davidopdebeeck.taskboard.core.Task;
import be.davidopdebeeck.taskboard.dao.LaneDAO;
import be.davidopdebeeck.taskboard.dao.ProjectDAO;
import be.davidopdebeeck.taskboard.dao.TaskDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class TaskBoardImpl implements TaskBoard
{

    @Autowired
    ProjectDAO projectDAO;

    @Autowired
    LaneDAO laneDAO;

    @Autowired
    TaskDAO taskDAO;

    //-------------------------------------------
    // region Project
    //-------------------------------------------


    @Override
    public Project createProject( String title )
    {
        return projectDAO.create( new Project( title ) );
    }

    @Override
    public Project createProject( String title, String plainTextPassword )
    {
        return projectDAO.create( new Project( title, plainTextPassword ) );
    }

    @Override
    public Project updateProject( Project project )
    {
        return projectDAO.update( project );
    }

    @Override
    public void removeProject( String id )
    {
        Project project = projectDAO.getById( id );
        projectDAO.remove( project );
    }

    @Override
    public Project getProjectById( String id )
    {
        return projectDAO.getById( id );
    }

    @Override
    public Collection<Project> getAllProjects()
    {
        return projectDAO.getAll();
    }

    @Override
    public Lane addLaneToProject( String projectId, String title, int sequence, boolean completed )
    {
        Project project = projectDAO.getById( projectId );
        Lane lane = new Lane( title, sequence, completed );
        laneDAO.create( lane );
        if ( projectDAO.addLane( project, lane ) )
        {
            updateLanes( project, lane );
            return lane;
        }
        return null;
    }

    @Override
    public boolean removeLaneFromProject( String projectId, String laneId )
    {
        Project project = projectDAO.getById( projectId );
        Lane lane = laneDAO.getById( laneId );
        return projectDAO.removeLane( project, lane );
    }

    //-------------------------------------------
    // endregion
    //-------------------------------------------

    //-------------------------------------------
    // region Lane
    //-------------------------------------------

    @Override
    public Lane updateLane( Lane lane )
    {
        Project project = laneDAO.getProject( lane );

        if ( project == null )
            return null;

        project.setLanes( projectDAO.getLanes( project ) );
        updateLanes( project, lane );
        return laneDAO.update( lane );
    }

    @Override
    public Lane getLaneById( String id )
    {
        return laneDAO.getById( id );
    }

    @Override
    public Task addTaskToLane( String laneId, String title, String description, String assignee )
    {
        Lane lane = laneDAO.getById( laneId );
        Task task = new Task( title, description, assignee );
        taskDAO.create( task );
        laneDAO.addTask( lane, task );
        return task;
    }

    @Override
    public Task addTaskToLane( String laneId, String taskId )
    {
        Lane lane = laneDAO.getById( laneId );
        Task task = taskDAO.getById( taskId );
        laneDAO.addTask( lane, task );
        return task;
    }

    @Override
    public boolean removeTaskFromLane( String laneId, String taskId )
    {
        Lane lane = laneDAO.getById( laneId );
        Task task = taskDAO.getById( taskId );
        return laneDAO.removeTask( lane, task );
    }

    @Override
    public void removeLane( String id )
    {
        laneDAO.remove( laneDAO.getById( id ) );
    }

    //-------------------------------------------
    // endregion
    //-------------------------------------------

    //-------------------------------------------
    // region Task
    //-------------------------------------------

    @Override
    public Task updateTask( Task task )
    {
        return taskDAO.update( task );
    }


    @Override
    public Task getTaskById( String id )
    {
        return taskDAO.getById( id );
    }

    @Override
    public void removeTask( String id )
    {
        taskDAO.remove( taskDAO.getById( id ) );
    }

    //-------------------------------------------
    // endregion
    //-------------------------------------------

    private void updateLanes( Project project, Lane lane )
    {
        int sequence = lane.getSequence();
        for ( Lane l : project.getLanes() )
        {
            if ( !l.equals( lane ) && l.getSequence() == sequence )
            {
                l.setSequence( sequence + 1 );
                laneDAO.update( l );
                sequence += 1;
            }
        }
    }

}
