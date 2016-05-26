package be.davidopdebeeck.taskboard.dto;

import be.davidopdebeeck.taskboard.core.Task;
import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.stream.Collectors;

public class LaneWithTasksDTO extends LaneDTO
{
    private Collection<TaskDTO> tasks;

    public LaneWithTasksDTO() {}

    public void setTasks( Collection<Task> lanes ) {
        this.tasks = lanes.stream().map( l -> {
            TaskDTO dto = new TaskDTO();
            BeanUtils.copyProperties( l, dto );
            return dto;
        } ).collect( Collectors.toList() );
    }

    public Collection<TaskDTO> getTasks() {
        return tasks;
    }
}
