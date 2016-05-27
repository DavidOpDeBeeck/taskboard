package be.davidopdebeeck.web;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProjectsWebTest extends WebTest
{

    List<WebElement> projects;

    @Before
    public void setUp() throws ConfigurationException
    {
        super.setUp();
        projects = webDriver.findElements( By.className( "project" ) );
    }

    @Test
    public void testCreateButton()
    {
        webDriver.get( url() );

        wait( By.id( "search-add-field" ) );

        WebElement addField = webDriver.findElement( By.id( "search-add-field" ) );
        WebElement addButton = webDriver.findElement( By.id( "search-add-button" ) );

        int projectCount = getProjectCount();

        addField.click();
        addField.sendKeys( "Test Project" );
        addButton.click();

        waitForTotalProjects( projectCount + 1 );

        assertEquals( projectCount + 1, getProjectCount() );
    }

    @Test
    public void testSearchField()
    {
        webDriver.get( url() );

        wait( By.id( "search-add-field" ) );

        WebElement searchField = webDriver.findElement( By.id( "search-add-field" ) );
        WebElement addButton = webDriver.findElement( By.id( "search-add-button" ) );

        String[] projectTitles = { "Test Project #1" , "Test Project #2" , "Project #3" };

        for ( String title : projectTitles )
        {
            searchField.click();
            searchField.sendKeys( title );
            addButton.click();
            waitForTotalProjects( getProjectCount() + 1 );
        }

        searchField.click();
        searchField.sendKeys( "Test Project" );

        waitForTotalProjects( 3 );

        final List<WebElement> filteredProjects = webDriver.findElements( By.className( "project" ) );

        List<String> titles = filteredProjects.stream()
                .map( project -> project.findElement( By.className( "title" ) )
                        .getText() )
                .collect( Collectors.toCollection( LinkedList::new ) );

        assertTrue( titles.contains( projectTitles[ 0 ] ) );
        assertTrue( titles.contains( projectTitles[ 1 ] ) );
        assertTrue( !titles.contains( projectTitles[ 2 ] ) );
    }

    private int getProjectCount()
    {
        return webDriver.findElements( By.className( "project" ) )
                .size();
    }

    private boolean waitForTotalProjects( int count )
    {
        return ( new WebDriverWait( webDriver, 10 ) ).until( (ExpectedCondition<Boolean>) d -> getProjectCount() >= count );
    }


    private boolean waitForNewProjects( int count )
    {
        return ( new WebDriverWait( webDriver, 10 ) ).until( (ExpectedCondition<Boolean>) d -> getProjectCount() >= count );
    }

    @Override
    protected String context()
    {
        return "#/projects";
    }
}
