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
    
    public static List<ChordNames> chordNamesAsList = new ArrayList<ChordNames>();
    public static LinkedHashMap<Integer, ChordKeys> keysOfDegrees = new LinkedHashMap<Integer, ChordKeys>();
    
    public enum ChordKeys	{MAJOR, MINOR, DIMINISHED}
    public enum NoteLength 	{WHOLE, HALF, QUARTER, QUARTER_DOTTED, EIGHTH, SIXTEENTH, THIRTY_SECOND}
    public enum ChordNames 	{C, C_SHARP, D, D_SHARP, E, F, F_SHARP, G, G_SHARP, A, A_SHARP, B}
    
    /**
     * Constructor of the class. Private because it's a singleton.
     */
    private Globals()
	{
		try
		{
			getPaths();
			
			chordNamesAsList.add(ChordNames.C);
			chordNamesAsList.add(ChordNames.C_SHARP);
			chordNamesAsList.add(ChordNames.D);
			chordNamesAsList.add(ChordNames.D_SHARP);
			chordNamesAsList.add(ChordNames.E);
			chordNamesAsList.add(ChordNames.F);
			chordNamesAsList.add(ChordNames.F_SHARP);
			chordNamesAsList.add(ChordNames.G);
			chordNamesAsList.add(ChordNames.G_SHARP);
			chordNamesAsList.add(ChordNames.A);
			chordNamesAsList.add(ChordNames.A_SHARP);
			chordNamesAsList.add(ChordNames.B);
		}
		catch (JDOMException e) {	e.printStackTrace();	} 
		catch (IOException e) 	{	e.printStackTrace();	}
	}
}
