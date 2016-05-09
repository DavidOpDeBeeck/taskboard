package be.davidopdebeeck.web;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProjectsWebTest extends WebTest
{

    @Test
    public void testCreateButton()
    {
        webDriver.get( url() );

        wait( By.id( "search-add-field" ) );

        WebElement addField = webDriver.findElement( By.id( "search-add-field" ) );
        WebElement addButton = webDriver.findElement( By.id( "search-add-button" ) );
        List<WebElement> projects = webDriver.findElements( By.className( "project" ) );

        addField.click();
        addField.sendKeys( "Test Project" );
        addButton.click();

        wait( By.className( "project" ) );

        List<WebElement> newProjects = webDriver.findElements( By.className( "project" ) );

        assertEquals( projects.size() + 1, newProjects.size() );
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
            try
            {
                webDriver.wait( 2000 );
            } catch ( InterruptedException e )
            {
                e.printStackTrace();
            }
            addButton.click();
        }

        searchField.click();
        searchField.sendKeys( "Test Project" );

        List<WebElement> projects = webDriver.findElements( By.className( "project" ) );

        List<String> titles = projects.stream().map( project -> project.findElement( By.className( "title" ) ).getText() ).collect( Collectors.toCollection( LinkedList::new ) );

        assertTrue( titles.contains( projectTitles[ 0 ] ) );
        assertTrue( titles.contains( projectTitles[ 1 ] ) );
        assertTrue( !titles.contains( projectTitles[ 2 ] ) );
    }

    @Test
    public void testLinkButton()
    {
        /*webDriver.get( url() );

        wait( By.id( "search-add-field" ) );

        WebElement addField = webDriver.findElement( By.id( "search-add-field" ) );
        WebElement addButton = webDriver.findElement( By.id( "search-add-button" ) );

        addField.click();
        addField.sendKeys( "Test Project" );
        addButton.click();

        wait( By.className( "title" ) );

        WebElement project = webDriver.findElements( By.className( "project" ) ).get( 0 );

        WebElement titleField = project.findElement( By.className( "title" ) );
        String titleOverviewText = titleField.getText();

        WebElement linkButton = project.findElement( By.className( "link" ) );
        linkButton.click();

        wait( By.tagName( "h2" ) );

        String titleText = webDriver.findElement( By.tagName( "h2" ) ).getText();

        assertEquals( titleOverviewText, titleText );*/
    }

    @Override
    protected String context()
    {
        return "#/projects";
    }
}
