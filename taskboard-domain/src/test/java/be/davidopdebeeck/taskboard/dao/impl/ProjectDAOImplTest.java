package be.davidopdebeeck.taskboard.dao.impl;

import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.core.Project;
import be.davidopdebeeck.taskboard.dao.LaneDAO;
import be.davidopdebeeck.taskboard.dao.ProjectDAO;
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
public class ProjectDAOImplTest
{

    @Autowired
    ProjectDAO projectDAO;

    @Autowired
    LaneDAO laneDAO;

    @Test
    public void testAddLane() throws Exception
    {
        Project project = new Project( "Test Project" );

        Lane lane = new Lane( "ToDo", 0, false );

        projectDAO.create( project );

        laneDAO.create( lane );

        projectDAO.addLane( project, lane );

        Collection<Lane> lanes = projectDAO.getLanes( project );

        assertTrue( lanes.contains( lane ) );

        projectDAO.remove( project );
    }

    @Test
    public void testRemoveLane() throws Exception
    {
        Project project = new Project( "Test Project" );

        Lane lane = new Lane( "ToDo", 0, false );

        projectDAO.create( project );

        laneDAO.create( lane );

        projectDAO.addLane( project, lane );

        Collection<Lane> lanes = projectDAO.getLanes( project );

        assertTrue( lanes.contains( lane ) );

        projectDAO.removeLane( project, lane );

        lanes = projectDAO.getLanes( project );

        assertFalse( lanes.contains( lane ) );

        projectDAO.remove( project );
    }

    @Test
    public void testGetLanes() throws Exception
    {
        Project project = new Project( "Test Project" );

        Lane lane1 = new Lane( "ToDo", 0, false );
        Lane lane2 = new Lane( "Done", 1, true );

        projectDAO.create( project );

        laneDAO.create( lane1 );
        laneDAO.create( lane2 );

        projectDAO.addLane( project, lane1 );
        projectDAO.addLane( project, lane2 );

        Collection<Lane> lanes = projectDAO.getLanes( project );

        assertTrue( lanes.contains( lane1 ) );
        assertTrue( lanes.contains( lane2 ) );

        projectDAO.remove( project );
    }

    @Test
    public void testCreate() throws Exception
    {
        Project project = new Project( "Test Project" );

        projectDAO.create( project );

        Project newProject = projectDAO.getById( project.getId() );

        assertEquals( project, newProject );

        projectDAO.remove( project );
    }

    @Test
    public void testUpdate() throws Exception
    {
        Project project = new Project( "Test Project" );

        projectDAO.create( project );

        project.setTitle( "Test Project #2" );

        projectDAO.update( project );

        Project newProject = projectDAO.getById( project.getId() );

        assertEquals( "Test Project #2", newProject.getTitle() );

        projectDAO.remove( project );
    }

    @Test
    public void testRemove() throws Exception
    {
        Project project = new Project( "Test Project" );

        projectDAO.create( project );

        Project newProject = projectDAO.getById( project.getId() );

        assertEquals( project, newProject );

        projectDAO.remove( project );

        newProject = projectDAO.getById( project.getId() );

        assertNull( newProject );
    }

    @Test
    public void testGetById() throws Exception
    {
        Project project = new Project( "Test Project" );

        projectDAO.create( project );

        Project newProject = projectDAO.getById( project.getId() );
        Project fakeProject = projectDAO.getById( "123" );

        assertEquals( project, newProject );
        assertNotEquals( project, fakeProject );

        projectDAO.remove( project );
    }

    @Test
    public void testGetAll() throws Exception
    {
        Project project1 = new Project( "Test Project #1" );
        Project project2 = new Project( "Test Project #2" );

        projectDAO.create( project1 );
        projectDAO.create( project2 );

        Collection<Project> projects = projectDAO.getAll();

        assertTrue( projects.contains( project1 ) );
        assertTrue( projects.contains( project2 ) );

        projectDAO.remove( project1 );
        projectDAO.remove( project2 );
    }
}