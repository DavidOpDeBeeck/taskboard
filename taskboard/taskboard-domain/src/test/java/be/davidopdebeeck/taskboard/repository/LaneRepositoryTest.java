package be.davidopdebeeck.taskboard.repository;

import be.davidopdebeeck.taskboard.config.ApplicationConfig;
import be.davidopdebeeck.taskboard.core.*;
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
public class LaneRepositoryTest
{

    @Autowired
    LaneRepository laneRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Test
    public void testAddTask() throws Exception
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
        lane = laneRepository.save( lane );

        Lane lane2 = laneRepository.findOne( lane.getId() );
        Collection<Task> tasks = lane2.getTasks();

        assertTrue( tasks.contains( task ) );
    }

    @Test
    public void testRemoveTask() throws Exception
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
        lane = laneRepository.save( lane );

        Lane lane2 = laneRepository.findOne( lane.getId() );
        lane2.removeTask( task );
        laneRepository.save( lane );

        Lane lane3 = laneRepository.findOne( lane.getId() );
        Collection<Task> tasks = lane3.getTasks();

        assertFalse( tasks.contains( task ) );
    }

    @Test
    public void testGetProject() throws Exception
    {
        Project project = new Project( "Test Project" );
        Lane lane = new Lane( "ToDo", 0, false );

        project = projectRepository.save( project );
        lane = laneRepository.save( lane );

        project.addLane(lane);
        projectRepository.save( project );

        Project project2 = laneRepository.findProject( lane );

        assertEquals( project, project2 );
    }

    @Test
    public void testCreate() throws Exception
    {
        Lane lane = new Lane( "ToDo", 0, false );

        lane = laneRepository.save( lane );
        Lane lane2 = laneRepository.findOne( lane.getId() );

        assertEquals( lane, lane2 );
    }

    @Test
    public void testUpdate() throws Exception
    {
        Lane lane = new Lane( "ToDo", 0, false );

        lane = laneRepository.save( lane );
        lane.setCompleted( true );
        lane = laneRepository.save( lane );

        lane = laneRepository.save( lane );
        Lane lane2 = laneRepository.findOne( lane.getId() );

        assertEquals( lane.isCompleted(), lane2.isCompleted() );
    }

    @Test
    public void testRemove() throws Exception
    {
        Lane lane = new Lane( "ToDo", 0, false );

        lane = laneRepository.save( lane );

        laneRepository.delete( lane );

        Lane lane2 = laneRepository.findOne( lane.getId() );

        assertNull( lane2 );
    }

    @Test
    public void testGetById() throws Exception
    {
        Lane lane = new Lane( "ToDo", 0, false );

        lane = laneRepository.save( lane );

        Lane lane2 = laneRepository.findOne( lane.getId() );
        Lane fakeLane = laneRepository.findOne( "123" );

        assertEquals( lane, lane2 );
        assertNotEquals( lane, fakeLane );
    }

    @Test
    public void testGetAll() throws Exception
    {
        Lane lane1 = new Lane( "ToDo", 0, false );
        Lane lane2 = new Lane( "ToDo", 0, false );

        lane1 = laneRepository.save( lane1 );
        lane2 = laneRepository.save( lane2 );

        Collection<Lane> lanes = laneRepository.findAll();

        assertTrue( lanes.contains( lane1 ) );
        assertTrue( lanes.contains( lane2 ) );
    }
}