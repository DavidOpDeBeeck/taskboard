package be.davidopdebeeck.taskboard.api.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude( JsonInclude.Include.NON_NULL )
public class AuthResponseDTO
{
    private String token;
    private boolean success;

    public AuthResponseDTO() {}

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
