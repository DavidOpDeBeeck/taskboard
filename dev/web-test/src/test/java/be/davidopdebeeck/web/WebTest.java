package be.davidopdebeeck.web;


import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.concurrent.TimeUnit;

public abstract class WebTest
{

    private static String address;
    private static int port;
    private static boolean ssl;

    protected WebDriver webDriver;

    @BeforeClass
    public static void initialise() throws ConfigurationException
    {
        PropertiesConfiguration config = new PropertiesConfiguration( "application.properties" );
        config.setReloadingStrategy( new FileChangedReloadingStrategy() );

        address = config.getString( "server.address" );
        port = config.getInt( "server.port" );
        ssl = config.getBoolean( "server.ssl.enabled" );
    }

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
