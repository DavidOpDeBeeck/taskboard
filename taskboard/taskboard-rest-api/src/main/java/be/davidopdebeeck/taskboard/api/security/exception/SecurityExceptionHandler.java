package be.davidopdebeeck.taskboard.api.security.exception;

import be.davidopdebeeck.taskboard.api.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SecurityExceptionHandler
{

    @ExceptionHandler( TokenInvalidException.class )
    public ResponseEntity<ErrorDTO> handle( TokenInvalidException e )
    {
        return new ResponseEntity<>( new ErrorDTO( HttpStatus.UNAUTHORIZED, e.getMessage() ), HttpStatus.OK );
    }

    @ExceptionHandler( TokenNotFoundException.class )
    public ResponseEntity<ErrorDTO> handle( TokenNotFoundException e )
    {
        return new ResponseEntity<>( new ErrorDTO( HttpStatus.UNAUTHORIZED, e.getMessage() ), HttpStatus.OK );
    }

}
