package be.davidopdebeeck.web;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Before;
import org.junit.Test;

public class ProjectWebTest extends WebTest
{

    @Before
    public void setUp() throws ConfigurationException
    {
        super.setUp();
    }

    @Test
    public void testGoBackButton()
    {

    }

    @Test
    public void testAddLaneButton()
    {

    }

    @Override
    protected String context()
    {
        return "/projects/";
    }
}
