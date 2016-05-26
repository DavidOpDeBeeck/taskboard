package be.davidopdebeeck.taskboard.dto;

import be.davidopdebeeck.taskboard.core.Lane;
import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.stream.Collectors;

public class ProjectWithLanesDTO extends ProjectDTO {

    private Collection<LaneDTO> lanes;

    public ProjectWithLanesDTO() {}

    public void setLanes( Collection<Lane> lanes ) {
        this.lanes = lanes.stream().map( l -> {
            LaneDTO dto = new LaneDTO();
            BeanUtils.copyProperties( l, dto );
            return dto;
        } ).collect( Collectors.toList() );
    }

    public Collection<LaneDTO> getLanes() {
        return lanes;
    }

}
