package be.davidopdebeeck.taskboard.service;

import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.core.Project;
import be.davidopdebeeck.taskboard.core.Task;
import be.davidopdebeeck.taskboard.repository.LaneRepository;
import be.davidopdebeeck.taskboard.repository.ProjectRepository;
import be.davidopdebeeck.taskboard.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional( propagation = Propagation.REQUIRES_NEW )
public class TaskBoardImpl implements TaskBoard {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    LaneRepository laneRepository;

    @Autowired
    TaskRepository taskRepository;

    //-------------------------------------------
    // region Project
    //-------------------------------------------


    @Override
    public Project createProject( String title ) {
        return projectRepository.save(new Project(title));
    }

    @Override
    public Project createProject( String title, String plainTextPassword ) {
        return projectRepository.save(new Project(title, plainTextPassword));
    }

    @Override
    public Project updateProject( Project project ) {
        return projectRepository.save(project);
    }

    @Override
    public void removeProject( String id ) {
        projectRepository.delete(projectRepository.findOne(id));
    }

    @Override
    public Project getProjectById( String id ) {
        return projectRepository.findOne(id);
    }

    @Override
    public Collection<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    //-------------------------------------------
    // endregion
    //-------------------------------------------

    //-------------------------------------------
    // region Lane
    //-------------------------------------------

    @Override
    public Lane createLane( String title, int sequence, boolean completed ) {
        return laneRepository.save(new Lane(title, sequence, completed));
    }

    @Override
    public Lane updateLane( Lane lane ) {
        return laneRepository.save(lane);
    }

    @Override
    public Lane getLaneById( String id ) {
        return laneRepository.findOne(id);
    }

    @Override
    public void removeLane( String id ) {
        laneRepository.delete(laneRepository.findOne(id));
    }


    //-------------------------------------------
    // endregion
    //-------------------------------------------

    //-------------------------------------------
    // region Task
    //-------------------------------------------

    @Override
    public Task createTask( String title, String description, String assignee ) {
        return taskRepository.save(new Task(title, description, assignee));
    }

    @Override
    public Task updateTask( Task task ) {
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById( String id ) {
        return taskRepository.findOne(id);
    }

    @Override
    public void removeTask( String id ) {
        taskRepository.delete(taskRepository.findOne(id));
    }

    //-------------------------------------------
    // endregion
    //-------------------------------------------

}
