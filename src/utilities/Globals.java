package utilities;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

/**
 * All the global variables used. Singleton class.
 * 
 * @author rafael
 * @version 1.0
 * @since 2016.10.22.
 */
public class Globals
{	
	private static Globals instance = null;
	
	public static String pathToImages;
	public static String pathToMusic;
	public static String pathToSamples;
	public static String pathToData;
	
	public static String imageName;
	public static DecimalFormat decimalFormat = new DecimalFormat("0.000");
	
	/**
	 * Returns a Globals instance, creates a new one if it doesn't exists already.
	 * 
	 * @return instance		Globals instance
	 */
	public static Globals getInstance() 
    {
       if (instance == null) 
       {
    	   instance = new Globals();
       }
       return instance;
    }
		
	// Paths
	
	/**
	 * Reads the user specified paths from the configuration file, config.xml.
	 * 
	 * @throws JDOMException
	 * @throws IOException
	 */
	private void getPaths() throws JDOMException, IOException
	{
		SAXBuilder parser = new SAXBuilder();
		Document document = parser.build("config.xml");
		
		XPathExpression<Element> xpathImages = XPathFactory.instance().compile("//path-to-images", Filters.element());
		XPathExpression<Element> xpathMusic = XPathFactory.instance().compile("//path-to-music", Filters.element());
		XPathExpression<Element> xpathSamples = XPathFactory.instance().compile("//path-to-samples", Filters.element());
		XPathExpression<Element> xpathData = XPathFactory.instance().compile("//path-to-data", Filters.element());

		Element pathToImagesElement = xpathImages.evaluateFirst(document);
		if (pathToImagesElement != null)
		    pathToImages = pathToImagesElement.getValue();
		
		Element pathToMusicElement = xpathMusic.evaluateFirst(document);
		if (pathToMusicElement != null)
		    pathToMusic = pathToMusicElement.getValue();

		Element pathToSamplesElement = xpathSamples.evaluateFirst(document);
		if (pathToSamplesElement != null)
		    pathToSamples = pathToSamplesElement.getValue();
		
		Element pathToDataElement = xpathData.evaluateFirst(document);
		if (pathToDataElement != null)
		    pathToData = pathToDataElement.getValue();
	}
	
	// Graphics
	
	// Music
    public static LinkedHashMap<String, Integer> midiNoteNumbers = new LinkedHashMap<String, Integer>();
    public static List<Float> noteFrequencies = new ArrayList<Float>();

    public static LinkedHashMap<Integer, ChordType> keysOfDegrees = new LinkedHashMap<Integer, ChordType>();
    
    public static LinkedHashMap<String, List<Pair>> degrees = new LinkedHashMap<String, List<Pair>>();
    
    public enum NoteLength 	{WHOLE, HALF, QUARTER, QUARTER_DOTTED, EIGHTH, SIXTEENTH, THIRTY_SECOND}
    
    public enum ChordType	{MAJOR, MINOR, DIMINISHED}
    public enum ChordKey 	{C, C_SHARP, D, D_SHARP, E, F, F_SHARP, G, G_SHARP, A, A_SHARP, B}
    public enum Chord		{C_MAJOR, C_MINOR, C_DIMINISHED,
    						 C_SHARP_MAJOR, C_SHARP_MINOR, C_SHARP_DIMINISHED,
    						 D_MAJOR, D_MINOR, D_DIMINISHED,
    						 D_SHARP_MAJOR, D_SHARP_MINOR, D_SHARP_DIMINISHED,
    						 E_MAJOR, E_MINOR, E_DIMINISHED,
    						 F_MAJOR, F_MINOR, F_DIMINISHED,
    						 F_SHARP_MAJOR, F_SHARP_MINOR, F_SHARP_DIMINISHED,
    						 G_MAJOR, G_MINOR, G_DIMINISHED,
    						 G_SHARP_MAJOR, G_SHARP_MINOR, G_SHARP_DIMINISHED,
    						 A_MAJOR, A_MINOR, A_DIMINISHED,
    						 A_SHARP_MAJOR, A_SHARP_MINOR, A_SHARP_DIMINISHED,
    						 B_MAJOR, B_MINOR, B_DIMINISHED,
    }
    
    /**
     * Constructor of the class. Private because it's a singleton.
     */
    private Globals()
	{
		try
		{
			getPaths();
		}
		catch (JDOMException e) {	e.printStackTrace();	} 
		catch (IOException e) 	{	e.printStackTrace();	}
	}
}
