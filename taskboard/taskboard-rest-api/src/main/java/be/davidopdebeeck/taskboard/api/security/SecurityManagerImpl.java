package be.davidopdebeeck.taskboard.api.security;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SecurityManagerImpl implements SecurityManager
{

    Map<String, List<String>> map;

    public SecurityManagerImpl()
    {
        this.map = new HashMap<>();
    }

    @Override
    public String save( String key )
    {
        UUID uuid = UUID.randomUUID();

        List<String> values = map.get( key );

        if ( values == null )
            values = new ArrayList<>();

        values.add( uuid.toString() );

        map.put( key, values );
        return uuid.toString();
    }

    @Override
    public boolean validate( String key, String value )
    {
        List<String> values = map.get( key );
        return ( values != null && values.contains( value ) );
    }

}
