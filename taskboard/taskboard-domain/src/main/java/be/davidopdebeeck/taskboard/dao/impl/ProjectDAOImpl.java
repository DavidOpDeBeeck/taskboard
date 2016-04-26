package be.davidopdebeeck.taskboard.dao.impl;

import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.core.Project;
import be.davidopdebeeck.taskboard.dao.LaneDAO;
import be.davidopdebeeck.taskboard.dao.ProjectDAO;
import be.davidopdebeeck.taskboard.dao.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Implements the ProjectDAO behaviour with SQL
 */
public class ProjectDAOImpl extends JdbcTemplateDAO implements ProjectDAO
{

    @Override
    public boolean addLane( Project project, Lane lane )
    {
        return jdbcTemplate.update( "insert into project_has_lane values(?,?)", project.getId(), lane.getId() ) > 0;
    }

    @Override
    public boolean removeLane( Project project, Lane lane )
    {
        return jdbcTemplate.update( "delete from project_has_lane WHERE project_id=? and lane_id=?", project.getId(), lane.getId() ) > 0;
    }

    @Override
    public Set<Lane> getLanes( Project project )
    {
        String sql = "SELECT * FROM project_has_lane phl INNER JOIN lane l ON (phl.lane_id = l.id) WHERE project_id=?";
        return jdbcTemplate.query( sql, preparedStatement -> preparedStatement.setString( 1, project.getId() ), rs -> {
            Set<Lane> lanes = new HashSet<>();
            while ( rs.next() )
                lanes.add( Converter.toLane( rs ) );
            return lanes;
        } );
    }

    @Override
    public Project create( Project project )
    {
        String id = project.getId();
        String title = project.getTitle();
        jdbcTemplate.update( "insert into project values(?,?)", id, title );
        return project;
    }

    @Override
    public Project update( Project project )
    {
        String id = project.getId();
        String title = project.getTitle();
        jdbcTemplate.update( "update project set title=? WHERE id=?", title, id );
        return project;
    }

    @Autowired
    private LaneDAO laneDAO;

    @Override
    public void remove( Project project )
    {
        String id = project.getId();
        jdbcTemplate.update( "delete from project_has_lane WHERE project_id=?", id );

        if ( project.getLanes() != null )
        {
            for ( Lane lane : project.getLanes() )
                laneDAO.remove( lane );
        }

        jdbcTemplate.update( "delete from project WHERE id=?", id );
    }

    @Override
    public Project getById( String id )
    {
        return jdbcTemplate.query( "SELECT * FROM project WHERE id=?", preparedStatement -> preparedStatement.setString( 1, id ), rs -> {
            if ( rs.next() )
            {
                Project project = Converter.toProject( rs );
                project.setLanes( getLanes( project ) );
                return project;
            }
            return null;
        } );
    }

    @Override
    public Collection<Project> getAll()
    {
        return jdbcTemplate.query( "SELECT * FROM project", rs -> {
            Set<Project> projects = new HashSet<>();
            while ( rs.next() )
                projects.add( Converter.toProject( rs ) );
            return projects;
        } );
    }

}
