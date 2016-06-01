package be.davidopdebeeck.web;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class ProjectsWebTest
{

    private static WebDriver webDriver;
    private static WebdriverService service;

    @BeforeClass
    public static void beforeClass()
    {
        service = new WebdriverService( "#/projects" );
        webDriver = service.init();
    }

    @AfterClass
    public static void afterClass()
    {
        webDriver.close();
    }

    @Test
    public void testCreateButton()
    {
        service.wait( By.id( "search-add-field" ) );

        WebElement addField = webDriver.findElement( By.id( "search-add-field" ) );
        WebElement addButton = webDriver.findElement( By.id( "search-add-button" ) );

        int projectCount = webDriver.findElements( By.className( "project" ) )
                .size();

        addField.click();
        addField.sendKeys( "Test Project" );
        addButton.click();

        service.wait( d -> webDriver.findElements( By.className( "project" ) )
                .size() >= projectCount + 1 );

        assertTrue( projectCount < webDriver.findElements( By.className( "project" ) )
                .size() );
    }

    @Test
    public void testSearchField()
    {
        service.wait( By.id( "search-add-field" ) );

        WebElement searchField = webDriver.findElement( By.id( "search-add-field" ) );
        WebElement addButton = webDriver.findElement( By.id( "search-add-button" ) );

        int projectCount = webDriver.findElements( By.className( "project" ) )
                .size();

        searchField.click();
        searchField.sendKeys( "Test Project #1" );
        addButton.click();
        searchField.clear();

        service.wait( d -> webDriver.findElements( By.className( "project" ) )
                .size() >= projectCount + 1 );

        searchField.click();
        searchField.sendKeys( "Test Project" );

        service.wait( d -> webDriver.findElements( By.className( "project" ) )
                .size() >= 1 );

        final List<WebElement> filteredProjects = webDriver.findElements( By.className( "project" ) );

        Set<String> titles = filteredProjects.stream()
                .map( project -> project.findElement( By.className( "title" ) )
                        .getText() )
                .collect( Collectors.toSet() );

        assertTrue( titles.contains( "Test Project #1" ) );
    }

    @Test
    public void testSearchField2()
    {
        service.wait( By.id( "search-add-field" ) );

        WebElement searchField = webDriver.findElement( By.id( "search-add-field" ) );
        WebElement addButton = webDriver.findElement( By.id( "search-add-button" ) );

        int projectCount = webDriver.findElements( By.className( "project" ) )
                .size();

        searchField.click();
        searchField.sendKeys( "Project #1" );
        addButton.click();
        searchField.clear();

        service.wait( d -> webDriver.findElements( By.className( "project" ) )
                .size() >= projectCount + 1 );

        searchField.click();
        searchField.sendKeys( "Test Project" );

        service.wait( d -> webDriver.findElements( By.className( "project" ) )
                .size() >= 1 );

        final List<WebElement> filteredProjects = webDriver.findElements( By.className( "project" ) );

        Set<String> titles = filteredProjects.stream()
                .map( project -> project.findElement( By.className( "title" ) )
                        .getText() )
                .collect( Collectors.toSet() );

        assertTrue( !titles.contains( "Project #1" ) );
    }

}
