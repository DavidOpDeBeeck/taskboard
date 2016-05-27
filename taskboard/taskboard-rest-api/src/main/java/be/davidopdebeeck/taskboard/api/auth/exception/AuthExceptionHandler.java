package be.davidopdebeeck.taskboard.api.auth.exception;

import be.davidopdebeeck.taskboard.api.auth.dto.AuthErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthExceptionHandler
{

    @ExceptionHandler( TokenInvalidException.class )
    public ResponseEntity<AuthErrorDTO> handle( TokenInvalidException e )
    {
        return new ResponseEntity<>( new AuthErrorDTO( e.getProject(), e.getMessage() ), HttpStatus.OK );
    }

    @ExceptionHandler( TokenNotFoundException.class )
    public ResponseEntity<AuthErrorDTO> handle( TokenNotFoundException e )
    {
        return new ResponseEntity<>( new AuthErrorDTO( e.getProject(), e.getMessage() ), HttpStatus.OK );
    }

}
