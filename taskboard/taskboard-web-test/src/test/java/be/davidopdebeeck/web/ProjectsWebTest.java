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
import java.util.Set;
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

        int projectCount = webDriver.findElements( By.className( "project" ) ).size();

        addField.click();
        addField.sendKeys( "Test Project" );
        addButton.click();

        ( new WebDriverWait( webDriver, 10 ) ).until( ( ExpectedCondition<Boolean> ) d -> webDriver.findElements( By.className( "project" ) ).size() >= projectCount + 1 );

        assertTrue( projectCount < webDriver.findElements( By.className( "project" ) ).size() );
    }

    @Test
    public void testSearchField()
    {
        /*webDriver.get( url() );

        wait( By.id( "search-add-field" ) );

        WebElement searchField = webDriver.findElement( By.id( "search-add-field" ) );
        WebElement addButton = webDriver.findElement( By.id( "search-add-button" ) );

        String[] projectTitles = { "Test Project #1" , "Test Project #2" , "Project #3" };

        int projectCount = webDriver.findElements( By.className( "project" ) ).size();

        for ( String title : projectTitles )
        {
            searchField.click();
            searchField.sendKeys( title );
            addButton.click();
            searchField.clear();
        }

        ( new WebDriverWait( webDriver, 10 ) ).until( ( ExpectedCondition<Boolean> ) d -> webDriver.findElements( By.className( "project" ) ).size() >= projectCount + 3 );

        searchField.click();
        searchField.sendKeys( "Test Project" );

        ( new WebDriverWait( webDriver, 10 ) ).until( ( ExpectedCondition<Boolean> ) d -> webDriver.findElements( By.className( "project" ) ).size() >= 2 );

        final List<WebElement> filteredProjects = webDriver.findElements( By.className( "project" ) );

        Set<String> titles = filteredProjects.stream()
                .map( project -> project.findElement( By.className( "title" ) )
                        .getText() )
                .collect( Collectors.toSet());

        assertTrue( titles.contains( projectTitles[ 0 ] ) );
        assertTrue( titles.contains( projectTitles[ 1 ] ) );
        assertTrue( !titles.contains( projectTitles[ 2 ] ) );*/
    }

    @Override
    protected String context()
    {
        return "#/projects";
    }
}
