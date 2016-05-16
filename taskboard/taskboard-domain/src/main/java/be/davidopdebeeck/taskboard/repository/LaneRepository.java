package be.davidopdebeeck.taskboard.repository;

import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.core.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LaneRepository extends JpaRepository<Lane, String> {

    @Query( "SELECT p FROM Project p WHERE :lane MEMBER OF p.lanes" )
    Project findProject( @Param( "lane" ) Lane lane );

}
