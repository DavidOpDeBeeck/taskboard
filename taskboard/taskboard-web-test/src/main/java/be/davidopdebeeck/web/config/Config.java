package be.davidopdebeeck.web.config;


public class Config
{

    public static final String SERVER_ADDRESS;
    public static final int SERVER_PORT;
    public static final boolean SERVER_SSL;
    public static final String NODE_ADDRESS;
    public static final int NODE_PORT;
    public static final String TYPE;

    static {
        SERVER_ADDRESS = System.getProperty( "webdriver.server.address", "localhost" );
        SERVER_PORT = Integer.parseInt( System.getProperty( "webdriver.server.port", "8000" ) );
        SERVER_SSL = Boolean.parseBoolean( System.getProperty( "webdriver.server.ssl.enabled", "false" ) );
        NODE_ADDRESS = System.getProperty( "webdriver.node.address", null );
        NODE_PORT = Integer.parseInt( System.getProperty( "webdriver.node.port", "0" ) );
        TYPE = System.getProperty( "webdriver.type", "firefox" );
    }

}
