package be.davidopdebeeck.web;


import org.apache.commons.configuration.ConfigurationException;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.TimeUnit;

public abstract class WebTest
{

    @Value( "${server.address}" )
    private String address;

    @Value( "${server.port}" )
    private int port;

    @Value( "${server.ssl.enabled}" )
    private boolean ssl;

    protected WebDriver webDriver;

    @Before
    public void setUp() throws ConfigurationException
    {
        System.setProperty( "webdriver.chrome.driver", "driver/chromedriver.exe" );
        webDriver = new ChromeDriver();
    }

    @After
    public void breakDown()
    {
        webDriver.close();
    }

    protected String url()
    {
        return baseUrl() + "/" + context();
    }

    protected String baseUrl()
    {
        return ( ssl ? "https" : "http" ) + "://" + address + ":" + port;
    }

    protected abstract String context();

    protected WebElement wait( final By locator )
    {
        Wait<WebDriver> wait = new FluentWait<>( webDriver ).withTimeout( 30, TimeUnit.SECONDS ).pollingEvery( 5, TimeUnit.SECONDS ).ignoring( NoSuchElementException.class );
        return wait.until( driver -> driver.findElement( locator ) );
    }
}
