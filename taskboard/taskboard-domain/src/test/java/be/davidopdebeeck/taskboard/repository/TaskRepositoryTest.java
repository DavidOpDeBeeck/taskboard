package be.davidopdebeeck.taskboard.repository;

import be.davidopdebeeck.taskboard.config.ApplicationConfig;
import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.core.Project;
import be.davidopdebeeck.taskboard.core.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.junit.Assert.*;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = ApplicationConfig.class )
@Transactional
public class TaskRepositoryTest
{

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    LaneRepository laneRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Test
    public void testGetByAssignee() throws Exception
    {
        Project project = new Project( "Test Project" );
        Lane lane = new Lane( "ToDo", 0, false );
        Task task1 = new Task( "Write Test", "Write a new test", "David" );
        Task task2 = new Task( "Write Test", "Write a new test", "David" );

        project = projectRepository.save( project );
        lane = laneRepository.save( lane );

        task1 = taskRepository.save( task1 );
        task2 = taskRepository.save( task2 );

        project.addLane( lane );
        projectRepository.save( project );

        lane.addTask(task1);
        lane.addTask(task2);
        laneRepository.save( lane );

        Collection<Task> tasks = taskRepository.findByAssignee( "David" );

        assertTrue( tasks.contains( task1 ) );
        assertTrue( tasks.contains( task2 ) );
    }

    @Test
    public void testGetProject() throws Exception
    {
        Project project = new Project( "Test Project" );
        Lane lane = new Lane( "ToDo", 0, false );
        Task task = new Task( "Write Test", "Write a new test", "David" );

        project = projectRepository.save( project );
        lane = laneRepository.save( lane );
        task = taskRepository.save( task );

        project.addLane(lane);
        projectRepository.save( project );

        lane.addTask(task);
        laneRepository.save( lane );

        Project project2 = taskRepository.findProject( task );

        assertEquals( project, project2 );
    }

    @Test
    public void testCreate() throws Exception
    {
        Task task = new Task( "Write Test", "Write a new test", "David" );

        task = taskRepository.save( task );
        Task task2 = taskRepository.findOne( task.getId() );

        assertEquals( task, task2 );
    }

    @Test
    public void testUpdate() throws Exception
    {
        Task task = new Task( "Write Test", "Write a new test", "David" );

        task = taskRepository.save( task );
        task.setAssignee( "Cedric" );
        task = taskRepository.save( task );

        Task task2 = taskRepository.findOne( task.getId() );
        assertEquals( task.getAssignee(), task2.getAssignee() );
    }

    @Test
    public void testRemove() throws Exception
    {
        Task task = new Task( "Write Test", "Write a new test", "David" );

        task = taskRepository.save( task );
        taskRepository.delete( task );

        Task task2 = taskRepository.findOne( task.getId() );

        assertNull( task2 );
    }

    @Test
    public void testGetById() throws Exception
    {
        Task task = new Task( "Write Test", "Write a new test", "David" );

        task = taskRepository.save( task );

        Task task2 = taskRepository.findOne( task.getId() );
        Task fakeTask = taskRepository.findOne( "123" );

        assertEquals( task, task2 );
        assertNotEquals( task, fakeTask );
    }

    @Test
    public void testGetAll() throws Exception
    {
        Task task1 = new Task( "Write Test", "Write a new test", "David" );
        Task task2 = new Task( "Write Test", "Write a new test", "David" );

        task1 = taskRepository.save( task1 );
        task2 = taskRepository.save( task2 );

        Collection<Task> tasks = taskRepository.findAll();

        assertTrue( tasks.contains( task1 ) );
        assertTrue( tasks.contains( task2 ) );
    }
}