package be.davidopdebeeck.taskboard.dao.impl;

import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.core.Project;
import be.davidopdebeeck.taskboard.core.Task;
import be.davidopdebeeck.taskboard.dao.LaneDAO;
import be.davidopdebeeck.taskboard.dao.TaskDAO;
import be.davidopdebeeck.taskboard.dao.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class LaneDAOImpl extends JdbcTemplateDAO implements LaneDAO
{
    @Override
    public boolean addTask( Lane lane, Task task )
    {
        return jdbcTemplate.update( "insert into lane_has_task values(?,?)", lane.getId(), task.getId() ) > 0;
    }

    @Override
    public boolean removeTask( Lane lane, Task task )
    {
        return jdbcTemplate.update( "delete from lane_has_task WHERE lane_id=? and task_id=?", lane.getId(), task.getId() ) > 0;
    }

    @Override
    public Set<Task> getTasks( Lane lane )
    {
        String sql = "SELECT * FROM lane_has_task lht INNER JOIN task t ON (lht.task_id = t.id) WHERE lane_id=?";
        return jdbcTemplate.query( sql, preparedStatement -> preparedStatement.setString( 1, lane.getId() ), rs -> {
            Set<Task> lanes = new HashSet<>();
            while ( rs.next() )
                lanes.add( Converter.toTask( rs ) );
            return lanes;
        } );
    }

    @Override
    public Project getProject( Lane lane )
    {
        return jdbcTemplate.query( "SELECT * FROM project_has_lane phl INNER JOIN project p ON (phl.project_id = p.id AND lane_id=?)", preparedStatement -> preparedStatement.setString( 1, lane.getId() ), rs -> {
            if ( rs.next() )
                return Converter.toProject( rs );
            return null;
        } );
    }

    @Override
    public Lane create( Lane lane )
    {
        String id = lane.getId();
        String title = lane.getTitle();
        int sequence = lane.getSequence();
        boolean completed = lane.isCompleted();
        jdbcTemplate.update( "insert into lane values(?,?,?,?)", id, title, completed, sequence );
        return lane;
    }

    @Override
    public Lane update( Lane lane )
    {
        String id = lane.getId();
        String title = lane.getTitle();
        int sequence = lane.getSequence();
        boolean completed = lane.isCompleted();
        jdbcTemplate.update( "update lane set title=?, sequence=?, completed=? WHERE id=?", title, sequence, completed, id );
        return lane;
    }

    @Autowired
    private TaskDAO taskDAO;

    @Override
    public void remove( Lane lane )
    {
        String id = lane.getId();
        jdbcTemplate.update( "delete from project_has_lane WHERE lane_id=?", lane.getId() );
        jdbcTemplate.update( "delete from lane_has_task WHERE lane_id=?", id );

        if ( lane.getTasks() != null )
        {
            for ( Task task : lane.getTasks() )
                taskDAO.remove( task );
        }

        jdbcTemplate.update( "delete from lane WHERE id=?", id );
    }

    @Override
    public Lane getById( String id )
    {
        return jdbcTemplate.query( "SELECT * FROM lane WHERE id=?", preparedStatement -> preparedStatement.setString( 1, id ), rs -> {
            if ( rs.next() )
            {
                Lane lane = Converter.toLane( rs );
                lane.setTasks( getTasks( lane ) );
                return lane;
            }
            return null;
        } );
    }

    @Override
    public Collection<Lane> getAll()
    {
        return jdbcTemplate.query( "SELECT * FROM lane", rs -> {
            Set<Lane> lanes = new HashSet<>();
            while ( rs.next() )
                lanes.add( Converter.toLane( rs ) );
            return lanes;
        } );
    }

}
