package be.davidopdebeeck.web;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public abstract class WebTest
{

    private static String serverAddress;
    private static int serverPort;
    private static boolean serverSsl;

    private static String nodeAddress;
    private static int nodePort;

    protected static WebDriver webDriver;

    @BeforeClass
    public static void beforeClass()
    {
        serverAddress = System.getProperty( "webdriver.server.address" );
        serverPort = Integer.parseInt( System.getProperty( "webdriver.server.port" ) );
        serverSsl = Boolean.parseBoolean( System.getProperty( "webdriver.server.ssl.enabled" ) );

        nodeAddress = System.getProperty( "webdriver.node.address" );
        nodePort = Integer.parseInt( System.getProperty( "webdriver.node.port" ) );
        try
        {
            webDriver = new RemoteWebDriver( new URL( "http://" + nodeAddress + ":" + nodePort + "/wd/hub" ), DesiredCapabilities.chrome() );
        } catch ( MalformedURLException ignored ) {}
    }

    @AfterClass
    public static void afterClass()
    {
        webDriver.close();
    }

    protected String url()
    {
        return baseUrl() + "/" + context();
    }

    protected String baseUrl()
    {
        return ( serverSsl ? "https" : "http" ) + "://" + serverAddress + ":" + serverPort;
    }

    protected abstract String context();

    protected WebElement wait( final By locator )
    {
        Wait<WebDriver> wait = new FluentWait<>( webDriver ).withTimeout( 30, TimeUnit.SECONDS ).pollingEvery( 5, TimeUnit.SECONDS ).ignoring( NoSuchElementException.class );
        return wait.until( driver -> driver.findElement( locator ) );
    }
}
