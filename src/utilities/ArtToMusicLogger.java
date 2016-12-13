package utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The logger class, derived from the SLF4j logger.
 * Can show info, debug, warn and error messages.
 * 
 * @author rafael
 * @version 1.0
 * @since 2016.11.01.
 */

public class ArtToMusicLogger 
{
	private static ArtToMusicLogger instance = null;
	private Logger logger = null;
	   
    private ArtToMusicLogger() 
    {
    	logger = LoggerFactory.getLogger("ArtToMusic");     
    }
	   
    public static ArtToMusicLogger getInstance() 
    {
       if (instance == null) 
       {
    	   instance = new ArtToMusicLogger();
       }
       return instance;
    }
	
    public void info(String message)	{logger.info(message);}	
    public void debug(String message)	{logger.debug(message);}
    public void warn(String message)	{logger.warn(message);}
    public void error(String message)	{logger.error(message);}
}
