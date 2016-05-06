package be.davidopdebeeck.taskboard.api.dto;

import org.springframework.http.HttpStatus;


public class ErrorDTO
{
    private HttpStatus code;
    private String message;

    public ErrorDTO() { }

    public ErrorDTO( HttpStatus code, String message )
    {
        this.code = code;
        this.message = message;
    }

    public void setCode( HttpStatus code )
    {
        this.code = code;
    }

    public void setMessage( String message )
    {
        this.message = message;
    }

    public int getCode()
    {
        return code.value();
    }

    public String getMessage()
    {
        return message;
    }
}
