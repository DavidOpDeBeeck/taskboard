package be.davidopdebeeck.web;

import be.davidopdebeeck.web.config.Config;
import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class WebdriverService
{

    private String context;
    private WebDriver webDriver;

    public WebdriverService( String context )
    {
        this.context = context;
    }

    public WebDriver init()
    {
        webDriver = WebdriverFactory.get();
        webDriver.get( getUrl() );
        return webDriver;
    }

    private String getUrl()
    {
        return getBaseUrl() + "/" + context;
    }

    private String getBaseUrl()
    {
        return ( Config.SERVER_SSL ? "https" : "http" ) + "://" + Config.SERVER_ADDRESS + ":" + Config.SERVER_PORT;
    }

    public <T> T wait( Function<WebDriver, T> condition )
    {
        return ( new WebDriverWait( webDriver, 10 ) ).until( condition );
    }

    public WebElement wait( final By locator )
    {
        Wait<WebDriver> wait = new FluentWait<>( webDriver ).withTimeout( 10, TimeUnit.SECONDS ).pollingEvery( 5, TimeUnit.SECONDS ).ignoring( NoSuchElementException.class );
        return wait.until( driver -> driver.findElement( locator ) );
    }

}
