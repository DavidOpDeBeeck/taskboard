package be.davidopdebeeck.taskboard.repository;

import be.davidopdebeeck.taskboard.config.ApplicationConfig;
import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.core.Project;
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
public class ProjectRepositoryTest
{

    @Autowired
    LaneRepository laneRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Test
    public void testAddLane() throws Exception
    {
        Project project = new Project( "Test Project" );
        Lane lane = new Lane( "ToDo", 0, false );

        project = projectRepository.save( project );
        lane = laneRepository.save( lane );

        project.addLane( lane );

        projectRepository.save( project );
        Project project2 = projectRepository.findOne(project.getId());

        Collection<Lane> lanes = project2.getLanes();

        assertTrue( lanes.contains( lane ) );
    }

    @Test
    public void testRemoveLane() throws Exception
    {
        Project project = new Project( "Test Project" );
        Lane lane = new Lane( "ToDo", 0, false );

        project = projectRepository.save( project );
        lane = laneRepository.save( lane );

        project.addLane( lane );

        projectRepository.save( project );
        Project project2 = projectRepository.findOne(project.getId());

        project2.removeLane( lane );

        projectRepository.save( project );
        Project project3 = projectRepository.findOne(project.getId());

        Collection<Lane> lanes = project3.getLanes();

        assertFalse( lanes.contains( lane ) );
    }

    @Test
    public void testGetLanes() throws Exception
    {
        Project project = new Project( "Test Project" );
        Lane lane1 = new Lane( "ToDo", 0, false );
        Lane lane2 = new Lane( "Done", 1, true );

        project = projectRepository.save( project );
        lane1 = laneRepository.save( lane1 );
        lane2 = laneRepository.save( lane2 );

        project.addLane(lane1);
        project.addLane(lane2);

        projectRepository.save( project );
        Project project2 = projectRepository.findOne(project.getId());

        Collection<Lane> lanes = project2.getLanes( );

        assertTrue( lanes.contains( lane1 ) );
        assertTrue( lanes.contains( lane2 ) );
    }

    @Test
    public void testCreate() throws Exception
    {
        Project project = new Project( "Test Project" );

        project = projectRepository.save( project );
        Project project2 = projectRepository.findOne( project.getId() );

        assertEquals( project, project2 );
    }

    @Test
    public void testUpdate() throws Exception
    {
        Project project = new Project( "Test Project" );

        project = projectRepository.save( project );

        project.setTitle( "Test Project #2" );

        projectRepository.save( project );
        Project project2 = projectRepository.findOne( project.getId() );

        assertEquals( "Test Project #2", project2.getTitle() );
    }

    @Test
    public void testRemove() throws Exception
    {
        Project project = new Project( "Test Project" );

        project = projectRepository.save( project );

        Project project2 = projectRepository.findOne( project.getId() );

        assertEquals( project, project2 );

        projectRepository.delete( project );

        project2 = projectRepository.findOne( project.getId() );

        assertNull( project2 );
    }

    @Test
    public void testGetById() throws Exception
    {
        Project project = new Project( "Test Project" );

        project = projectRepository.save( project );

        Project newProject = projectRepository.findOne( project.getId() );
        Project fakeProject = projectRepository.findOne( "123" );

        assertEquals( project, newProject );
        assertNotEquals( project, fakeProject );
    }

    @Test
    public void testGetAll() throws Exception
    {
        Project project1 = new Project( "Test Project #1" );
        Project project2 = new Project( "Test Project #2" );

        project1 = projectRepository.save( project1 );
        project2 = projectRepository.save( project2 );

        Collection<Project> projects = projectRepository.findAll();

        assertTrue( projects.contains( project1 ) );
        assertTrue( projects.contains( project2 ) );
    }
}