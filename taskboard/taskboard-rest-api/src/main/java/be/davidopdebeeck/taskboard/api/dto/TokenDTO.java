package be.davidopdebeeck.taskboard.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude( JsonInclude.Include.NON_NULL )
public class TokenDTO
{
    private String token;
    private boolean success;

    public TokenDTO() {}

    public void setToken( String token )
    {
        this.token = token;
    }

    public void setSuccess( boolean success )
    {
        this.success = success;
    }

    public String getToken()
    {
        return token;
    }

    public boolean isSuccess()
    {
        return success;
    }
}
