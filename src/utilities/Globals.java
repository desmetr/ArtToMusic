package utilities;

import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

/**
 * Created by rafael on 22.10.16.
 */
public class Globals
{	
	private static Globals instance = null;
	
	public static String pathToImages;
	public static String pathToMusic;
	
	private Globals()
	{
		try
		{
			getPaths();
		}
		catch (JDOMException e) {	e.printStackTrace();	} 
		catch (IOException e) 	{	e.printStackTrace();	}
	}

	public static Globals getInstance() 
    {
       if (instance == null) 
       {
    	   instance = new Globals();
       }
       return instance;
    }
		
	// Paths
	private void getPaths() throws JDOMException, IOException
	{
		SAXBuilder parser = new SAXBuilder();
		Document document = parser.build("config.xml");
		
		XPathExpression<Element> xpathImages = XPathFactory.instance().compile("//path-to-images", Filters.element());
		XPathExpression<Element> xpathMusic = XPathFactory.instance().compile("//path-to-music", Filters.element());

		Element pathToImagesElement = xpathImages.evaluateFirst(document);
		if (pathToImagesElement != null)
		    pathToImages = pathToImagesElement.getValue();
		
		Element pathToMusicElement = xpathMusic.evaluateFirst(document);
		if (pathToMusicElement != null)
		    pathToMusic = pathToMusicElement.getValue();
	}
	
	// Graphics
	
	// Music
    public enum NoteLength {WHOLE, HALF, QUARTER, QUARTER_DOTTED, EIGHTH, SIXTEENTH, THIRTY_SECOND}
}
