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

public class Globals
{	
	private static Globals instance = null;
	
	public static String pathToImages;
	public static String pathToMusic;
	public static String pathToSamples;
	public static String pathToData;
	public static String pathToSource;
	public static String pathToSegmentationFile;
	
	public static String imageName;
	public static DecimalFormat decimalFormat = new DecimalFormat("0.000");
	
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
		XPathExpression<Element> xpathSamples = XPathFactory.instance().compile("//path-to-samples", Filters.element());
		XPathExpression<Element> xpathData = XPathFactory.instance().compile("//path-to-data", Filters.element());
		XPathExpression<Element> xpathSource = XPathFactory.instance().compile("//path-to-source", Filters.element());
		XPathExpression<Element> xpathSegmentation = XPathFactory.instance().compile("//path-to-segmentation-file", Filters.element());

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
		
		Element pathToSourceElement = xpathSource.evaluateFirst(document);
		if (pathToSourceElement != null)
		    pathToSource = pathToSourceElement.getValue();
		
		Element pathToSegmentationElement = xpathSegmentation.evaluateFirst(document);
		if (pathToSegmentationElement != null)
		    pathToSegmentationFile = pathToSegmentationElement.getValue();
	}
	
	// Graphics
	
	// Music
    public static LinkedHashMap<String, Integer> midiNoteNumbers = new LinkedHashMap<String, Integer>();
    public static List<Float> noteFrequencies = new ArrayList<Float>();

    public static LinkedHashMap<Integer, ChordType> keysOfDegrees = new LinkedHashMap<Integer, ChordType>();
    
    public static LinkedHashMap<String, List<Pair>> degrees = new LinkedHashMap<String, List<Pair>>();
    
    public static LinkedHashMap<Globals.ChordKey, LinkedHashMap<Globals.ChordType, Globals.Chord>> chords = new LinkedHashMap<Globals.ChordKey, LinkedHashMap<Globals.ChordType, Globals.Chord>>();
    
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
    
    public enum ChordProgression {I_II_V_I, I_III_IV_VI, I_VI_II_IV, I_III_VI_II_V} 
    
    private Globals()
	{
		try
		{
			getPaths();
			makeChords();
		}
		catch (JDOMException e) {	e.printStackTrace();	} 
		catch (IOException e) 	{	e.printStackTrace();	}
	}
    
    private void makeChords()
    {
    	LinkedHashMap<Globals.ChordType, Globals.Chord> C = new LinkedHashMap<Globals.ChordType, Globals.Chord>();
    	C.put(ChordType.MAJOR, Chord.C_MAJOR);
    	C.put(ChordType.MINOR, Chord.C_MINOR);
    	C.put(ChordType.DIMINISHED, Chord.C_DIMINISHED);
    	
    	LinkedHashMap<Globals.ChordType, Globals.Chord> C_sharp = new LinkedHashMap<Globals.ChordType, Globals.Chord>();
    	C_sharp.put(ChordType.MAJOR, Chord.C_SHARP_MAJOR);
    	C_sharp.put(ChordType.MINOR, Chord.C_SHARP_MINOR);
    	C_sharp.put(ChordType.DIMINISHED, Chord.C_SHARP_DIMINISHED);
    	
    	LinkedHashMap<Globals.ChordType, Globals.Chord> D = new LinkedHashMap<Globals.ChordType, Globals.Chord>();
    	D.put(ChordType.MAJOR, Chord.D_MAJOR);
    	D.put(ChordType.MINOR, Chord.D_MINOR);
    	D.put(ChordType.DIMINISHED, Chord.D_DIMINISHED);
    	
    	LinkedHashMap<Globals.ChordType, Globals.Chord> D_sharp = new LinkedHashMap<Globals.ChordType, Globals.Chord>();
    	D_sharp.put(ChordType.MAJOR, Chord.D_SHARP_MAJOR);
    	D_sharp.put(ChordType.MINOR, Chord.D_SHARP_MINOR);
    	D_sharp.put(ChordType.DIMINISHED, Chord.D_SHARP_DIMINISHED);
    	
    	LinkedHashMap<Globals.ChordType, Globals.Chord> E = new LinkedHashMap<Globals.ChordType, Globals.Chord>();
    	E.put(ChordType.MAJOR, Chord.E_MAJOR);
    	E.put(ChordType.MINOR, Chord.E_MINOR);
    	E.put(ChordType.DIMINISHED, Chord.E_DIMINISHED);
    	
    	LinkedHashMap<Globals.ChordType, Globals.Chord> F = new LinkedHashMap<Globals.ChordType, Globals.Chord>();
    	F.put(ChordType.MAJOR, Chord.F_MAJOR);
    	F.put(ChordType.MINOR, Chord.F_MINOR);
    	F.put(ChordType.DIMINISHED, Chord.F_DIMINISHED);
    	
    	LinkedHashMap<Globals.ChordType, Globals.Chord> F_sharp = new LinkedHashMap<Globals.ChordType, Globals.Chord>();
    	F_sharp.put(ChordType.MAJOR, Chord.F_SHARP_MAJOR);
    	F_sharp.put(ChordType.MINOR, Chord.F_SHARP_MINOR);
    	F_sharp.put(ChordType.DIMINISHED, Chord.F_SHARP_DIMINISHED);
    	
    	LinkedHashMap<Globals.ChordType, Globals.Chord> G = new LinkedHashMap<Globals.ChordType, Globals.Chord>();
    	G.put(ChordType.MAJOR, Chord.G_MAJOR);
    	G.put(ChordType.MINOR, Chord.G_MINOR);
    	G.put(ChordType.DIMINISHED, Chord.G_DIMINISHED);
    	
    	LinkedHashMap<Globals.ChordType, Globals.Chord> G_sharp = new LinkedHashMap<Globals.ChordType, Globals.Chord>();
    	G_sharp.put(ChordType.MAJOR, Chord.G_SHARP_MAJOR);
    	G_sharp.put(ChordType.MINOR, Chord.G_SHARP_MINOR);
    	G_sharp.put(ChordType.DIMINISHED, Chord.G_SHARP_DIMINISHED);
    	
    	LinkedHashMap<Globals.ChordType, Globals.Chord> A = new LinkedHashMap<Globals.ChordType, Globals.Chord>();
    	A.put(ChordType.MAJOR, Chord.A_MAJOR);
    	A.put(ChordType.MINOR, Chord.A_MINOR);
    	A.put(ChordType.DIMINISHED, Chord.A_DIMINISHED);
    	
    	LinkedHashMap<Globals.ChordType, Globals.Chord> A_sharp = new LinkedHashMap<Globals.ChordType, Globals.Chord>();
    	A_sharp.put(ChordType.MAJOR, Chord.A_SHARP_MAJOR);
    	A_sharp.put(ChordType.MINOR, Chord.A_SHARP_MINOR);
    	A_sharp.put(ChordType.DIMINISHED, Chord.A_SHARP_DIMINISHED);
    	
    	LinkedHashMap<Globals.ChordType, Globals.Chord> B = new LinkedHashMap<Globals.ChordType, Globals.Chord>();
    	B.put(ChordType.MAJOR, Chord.B_MAJOR);
    	B.put(ChordType.MINOR, Chord.B_MINOR);
    	B.put(ChordType.DIMINISHED, Chord.B_DIMINISHED);
    	
    	chords.put(ChordKey.C, C);
    	chords.put(ChordKey.C_SHARP, C_sharp);
    	chords.put(ChordKey.D, D);
    	chords.put(ChordKey.D_SHARP, D_sharp);
    	chords.put(ChordKey.E, E);
    	chords.put(ChordKey.F, F);
    	chords.put(ChordKey.F_SHARP, F_sharp);
    	chords.put(ChordKey.G, G);
    	chords.put(ChordKey.G_SHARP, G_sharp);
    	chords.put(ChordKey.A, A);
    	chords.put(ChordKey.A_SHARP, A_sharp);
    	chords.put(ChordKey.B, B);
    }
}
