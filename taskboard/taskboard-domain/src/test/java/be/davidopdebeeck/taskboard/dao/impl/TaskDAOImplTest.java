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
public class TaskDAOImplTest
{

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private LaneDAO laneDAO;

    @Autowired
    private TaskDAO taskDAO;

    @Test
    public void testGetByAssignee() throws Exception
    {
        Project project = new Project( "Test Project" );

        projectDAO.create( project );

        Lane lane = new Lane( "ToDo", 0, false );

        laneDAO.create( lane );

        Task task1 = new Task( "Write Test", "Write a new test", "David" );
        Task task2 = new Task( "Write Test", "Write a new test", "David" );

        taskDAO.create( task1 );
        taskDAO.create( task2 );

        projectDAO.addLane( project, lane );

        laneDAO.addTask( lane, task1 );
        laneDAO.addTask( lane, task2 );

        Collection<Task> tasks = taskDAO.getByAssignee( project, "David" );

        assertTrue( tasks.contains( task1 ) );
        assertTrue( tasks.contains( task2 ) );

        projectDAO.remove( project );
    }

    @Test
    public void testCreate() throws Exception
    {
        Task task = new Task( "Write Test", "Write a new test", "David" );

        taskDAO.create( task );

        Task newTask = taskDAO.getById( task.getId() );

        assertEquals( task, newTask );

        taskDAO.remove( task );
    }

    @Test
    public void testUpdate() throws Exception
    {
        Task task = new Task( "Write Test", "Write a new test", "David" );

        taskDAO.create( task );

        task.setAssignee( "Cedric" );

        taskDAO.update( task );

        Task newTask = taskDAO.getById( task.getId() );

        assertEquals( task.getAssignee(), newTask.getAssignee() );

        taskDAO.remove( task );
    }

    @Test
    public void testRemove() throws Exception
    {
        Task task = new Task( "Write Test", "Write a new test", "David" );

        taskDAO.create( task );

        taskDAO.remove( task );

        Task newTask = taskDAO.getById( task.getId() );

        assertNull( newTask );
    }

    @Test
    public void testGetById() throws Exception
    {
        Task task = new Task( "Write Test", "Write a new test", "David" );

        taskDAO.create( task );

        Task newTask = taskDAO.getById( task.getId() );
        Task fakeTask = taskDAO.getById( "123" );

        assertEquals( task, newTask );
        assertNotEquals( task, fakeTask );

        taskDAO.remove( task );
    }

    @Test
    public void testGetAll() throws Exception
    {
        Task task1 = new Task( "Write Test", "Write a new test", "David" );
        Task task2 = new Task( "Write Test", "Write a new test", "David" );

        taskDAO.create( task1 );
        taskDAO.create( task2 );

        Collection<Task> tasks = taskDAO.getAll();

        assertTrue( tasks.contains( task1 ) );
        assertTrue( tasks.contains( task2 ) );

        taskDAO.remove( task1 );
        taskDAO.remove( task2 );
    }
}