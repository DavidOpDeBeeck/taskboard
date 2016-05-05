package be.davidopdebeeck.taskboard.dao.impl;

import be.davidopdebeeck.taskboard.core.Project;
import be.davidopdebeeck.taskboard.core.Task;
import be.davidopdebeeck.taskboard.dao.TaskDAO;
import be.davidopdebeeck.taskboard.dao.util.Converter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TaskDAOImpl extends JdbcTemplateDAO implements TaskDAO
{

    @Override
    public Collection<Task> getByAssignee( Project project, String assignee )
    {
        return jdbcTemplate.query( "SELECT t.id, t.title, t.description, t.assignee FROM project p\n" +
                "INNER JOIN project_has_lane pl\n" +
                " ON (p.id = pl.project_id)\n" +
                "INNER JOIN lane_has_task lt\n" +
                " ON (pl.lane_id = lt.lane_id)\n" +
                "INNER JOIN task t\n" +
                " ON (t.id = lt.task_id)\n" +
                "WHERE p.id=? AND t.assignee=?", preparedStatement -> {
            preparedStatement.setString( 1, project.getId() );
            preparedStatement.setString( 2, assignee );
        }, rs -> {
            Set<Task> tasks = new HashSet<>();
            while ( rs.next() )
                tasks.add( Converter.toTask( rs ) );
            return tasks;
        } );
    }

    @Override
    public Project getProject( Task task )
    {
        return jdbcTemplate.query( "SELECT p.* FROM lane_has_task lht INNER JOIN lane l ON (lht.lane_id=l.id AND task_id=?) INNER JOIN project_has_lane phl ON (lht.lane_id=l.id AND phl.lane_id=l.id) INNER JOIN project p ON (phl.project_id = p.id)", preparedStatement -> preparedStatement
                .setString( 1, task.getId() ), rs -> {
            if ( rs.next() )
                return Converter.toProject( rs );
            return null;
        } );
    }

    @Override
    public Task create( Task task )
    {
        String id = task.getId();
        String title = task.getTitle();
        String description = task.getDescription();
        String assignee = task.getAssignee();
        jdbcTemplate.update( "insert into task values(?,?,?,?)", id, title, description, assignee );
        return task;
    }

    @Override
    public Task update( Task task )
    {
        String id = task.getId();
        String title = task.getTitle();
        String description = task.getDescription();
        String assignee = task.getAssignee();
        jdbcTemplate.update( "update task set title=?, description=?, assignee=? WHERE id=?", title, description, assignee, id );
        return task;
    }

    @Override
    public void remove( Task task )
    {
        String id = task.getId();
        jdbcTemplate.update( "delete from lane_has_task WHERE task_id=?", task.getId() );
        jdbcTemplate.update( "delete from task WHERE id=?", id );
    }

    @Override
    public Task getById( String id )
    {
        return jdbcTemplate.query( "SELECT * FROM task WHERE id=?", preparedStatement -> preparedStatement.setString( 1, id ), rs -> {
            if ( rs.next() )
                return Converter.toTask( rs );
            return null;
        } );
    }

    @Override
    public Collection<Task> getAll()
    {
        return jdbcTemplate.query( "SELECT * FROM task", rs -> {
            Set<Task> tasks = new HashSet<>();
            while ( rs.next() )
                tasks.add( Converter.toTask( rs ) );
            return tasks;
        } );
    }
}
