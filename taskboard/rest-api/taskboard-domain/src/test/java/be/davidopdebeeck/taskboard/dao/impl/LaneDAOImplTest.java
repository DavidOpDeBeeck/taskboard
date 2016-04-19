package be.davidopdebeeck.taskboard.dao.impl;

import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.core.Project;
import be.davidopdebeeck.taskboard.core.Task;
import be.davidopdebeeck.taskboard.dao.LaneDAO;
import be.davidopdebeeck.taskboard.dao.ProjectDAO;
import be.davidopdebeeck.taskboard.dao.TaskDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.junit.Assert.*;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( "classpath:spring-config.xml" )
@Transactional
public class LaneDAOImplTest
{

    @Autowired
    ProjectDAO projectDAO;

    @Autowired
    private LaneDAO laneDAO;

    @Autowired
    private TaskDAO taskDAO;

    @Test
    public void testAddTask() throws Exception
    {
        Project project = new Project( "Test Project" );

        projectDAO.create( project );

        Lane lane = new Lane( "ToDo", 0, false );

        laneDAO.create( lane );

        Task task = new Task( "Write Test", "Write a new test", "David" );

        taskDAO.create( task );

        projectDAO.addLane( project, lane );

        laneDAO.addTask( lane, task );

        Collection<Task> tasks = laneDAO.getTasks( lane );

        assertTrue( tasks.contains( task ) );

        projectDAO.remove( project );
    }

    @Test
    public void testRemoveTask() throws Exception
    {
        Project project = new Project( "Test Project" );

        projectDAO.create( project );

        Lane lane = new Lane( "ToDo", 0, false );

        laneDAO.create( lane );

        Task task = new Task( "Write Test", "Write a new test", "David" );

        taskDAO.create( task );

        projectDAO.addLane( project, lane );

        laneDAO.addTask( lane, task );

        Collection<Task> tasks = laneDAO.getTasks( lane );

        assertTrue( tasks.contains( task ) );

        laneDAO.removeTask( lane, task );

        tasks = laneDAO.getTasks( lane );

        assertFalse( tasks.contains( task ) );

        projectDAO.remove( project );
    }

    @Test
    public void testGetProject() throws Exception
    {
        Project project = new Project( "Test Project" );

        projectDAO.create( project );

        Lane lane = new Lane( "ToDo", 0, false );

        laneDAO.create( lane );

        projectDAO.addLane( project, lane );

        Project project2 = laneDAO.getProject( lane );

        assertEquals( project, project2 );

        projectDAO.remove( project );
    }

    @Test
    public void testCreate() throws Exception
    {
        Lane lane = new Lane( "ToDo", 0, false );

        laneDAO.create( lane );

        Lane newLane = laneDAO.getById( lane.getId() );

        assertEquals( lane, newLane );

        laneDAO.remove( lane );
    }

    @Test
    public void testUpdate() throws Exception
    {
        Lane lane = new Lane( "ToDo", 0, false );

        laneDAO.create( lane );

        lane.setCompleted( true );

        laneDAO.update( lane );

        Lane newLane = laneDAO.getById( lane.getId() );

        assertEquals( lane.isCompleted(), newLane.isCompleted() );

        laneDAO.remove( lane );
    }

    @Test
    public void testRemove() throws Exception
    {
        Lane lane = new Lane( "ToDo", 0, false );

        laneDAO.create( lane );

        laneDAO.remove( lane );

        Lane newLane = laneDAO.getById( lane.getId() );

        assertNull( newLane );
    }

    @Test
    public void testGetById() throws Exception
    {
        Lane lane = new Lane( "ToDo", 0, false );

        laneDAO.create( lane );

        Lane newLane = laneDAO.getById( lane.getId() );
        Lane fakeLane = laneDAO.getById( "123" );

        assertEquals( lane, newLane );
        assertNotEquals( lane, fakeLane );

        laneDAO.remove( lane );
    }

    @Test
    public void testGetAll() throws Exception
    {
        Lane lane1 = new Lane( "ToDo", 0, false );
        Lane lane2 = new Lane( "ToDo", 0, false );

        laneDAO.create( lane1 );
        laneDAO.create( lane2 );

        Collection<Lane> lanes = laneDAO.getAll();

        assertTrue( lanes.contains( lane1 ) );
        assertTrue( lanes.contains( lane2 ) );

        laneDAO.remove( lane1 );
        laneDAO.remove( lane2 );
    }
}