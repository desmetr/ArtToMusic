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
	
	private Globals()
	{
		try
		{
			getPathToImages();
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
	
	public static String pathToImages;
	
	// Paths
	private void getPathToImages() throws JDOMException, IOException
	{
		SAXBuilder parser = new SAXBuilder();
		Document document = parser.build("config.xml");
		
		XPathExpression<Element> xpath = XPathFactory.instance().compile("//path-to-images", Filters.element());

		Element pathToImagesElement = xpath.evaluateFirst(document);
		if (pathToImagesElement != null) {
		    pathToImages = pathToImagesElement.getValue();
		}
	}
	
	// Graphics
	
	// Music
    public enum NoteLength {WHOLE, HALF, QUARTER, QUARTER_DOTTED, EIGHTH, SIXTEENTH, THIRTY_SECOND}
}
