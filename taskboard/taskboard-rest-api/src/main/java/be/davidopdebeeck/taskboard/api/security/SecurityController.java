package be.davidopdebeeck.taskboard.api.security;

import be.davidopdebeeck.taskboard.api.dto.PasswordDTO;
import be.davidopdebeeck.taskboard.api.dto.TokenDTO;
import be.davidopdebeeck.taskboard.core.Project;
import be.davidopdebeeck.taskboard.service.TaskBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping( "/authenticate" )
public class SecurityController
{

    @Autowired
    TaskBoard taskBoard;

    @Autowired
    SecurityManager securityManager;

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity<TokenDTO> validateProjectPassword( @RequestBody PasswordDTO dto )
    {
        TokenDTO token = new TokenDTO();
        Project project = taskBoard.getProjectById( dto.getProjectId() );

        if ( dto.getPassword() != null && !dto.getPassword().isEmpty() && project.isPasswordValid( dto.getPassword() ) )
        {
            token.setToken( securityManager.save( project.getId() ) );
            token.setSuccess( true );
        } else
        {
            token.setSuccess( false );
        }

        return new ResponseEntity<>( token, HttpStatus.OK );
    }

}
