package be.davidopdebeeck.web;

import be.davidopdebeeck.web.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class WebdriverFactory
{

    public static WebDriver get()
    {
        try
        {
            switch ( Config.TYPE )
            {
                case "firefox":
                    return new FirefoxDriver();
                case "chrome":
                    return new ChromeDriver();
                case "remote-chrome":
                    return new RemoteWebDriver( new URL( "http://" + Config.NODE_ADDRESS + ":" + Config.NODE_PORT + "/wd/hub" ), DesiredCapabilities.chrome() );
                case "remote-firefox":
                    return new RemoteWebDriver( new URL( "http://" + Config.NODE_ADDRESS + ":" + Config.NODE_PORT + "/wd/hub" ), DesiredCapabilities.firefox() );
            }
        } catch ( Exception ignored ) {}

        throw new RuntimeException( "No valid webdriver type was specified using 'webdriver-type'" );
    }

}
