package utilities;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;

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

		Element pathToImagesElement = xpathImages.evaluateFirst(document);
		if (pathToImagesElement != null)
		    pathToImages = pathToImagesElement.getValue();
		
		Element pathToMusicElement = xpathMusic.evaluateFirst(document);
		if (pathToMusicElement != null)
		    pathToMusic = pathToMusicElement.getValue();
	}
	
	// Graphics
	
	// Music
    public static LinkedHashMap<String, Integer> midiNoteNumbers = new LinkedHashMap<String, Integer>();

    public enum NoteLength {WHOLE, HALF, QUARTER, QUARTER_DOTTED, EIGHTH, SIXTEENTH, THIRTY_SECOND}
    
    // Initialize
    private Globals()
	{
		try
		{
			getPaths();
			
			// Put values in dict.
			
			// Octave -1
			midiNoteNumbers.put("C-1", 0);
			midiNoteNumbers.put("C#-1", 1);
			midiNoteNumbers.put("D-1", 2);
			midiNoteNumbers.put("D#-1", 3);
			midiNoteNumbers.put("E-1", 4);
			midiNoteNumbers.put("F-1", 5);
			midiNoteNumbers.put("F#-1", 6);
			midiNoteNumbers.put("G-1", 7);
			midiNoteNumbers.put("G#-1", 8);
			midiNoteNumbers.put("A-1", 9);
			midiNoteNumbers.put("A#-1", 10);
			midiNoteNumbers.put("B-1", 11);
			
			// Octave 0
			midiNoteNumbers.put("C0", 12);
			midiNoteNumbers.put("C0", 13);
			midiNoteNumbers.put("D0", 14);
			midiNoteNumbers.put("D0", 15);
			midiNoteNumbers.put("E0", 16);
			midiNoteNumbers.put("F0", 17);
			midiNoteNumbers.put("F0", 18);
			midiNoteNumbers.put("G0", 19);
			midiNoteNumbers.put("G0", 20);
			midiNoteNumbers.put("A0", 21);
			midiNoteNumbers.put("A0", 22);
			midiNoteNumbers.put("B0", 23);
			
			// Octave 1
			midiNoteNumbers.put("C1", 24);
			midiNoteNumbers.put("C1", 25);
			midiNoteNumbers.put("D1", 26);
			midiNoteNumbers.put("D1", 27);
			midiNoteNumbers.put("E1", 28);
			midiNoteNumbers.put("F1", 29);
			midiNoteNumbers.put("F1", 30);
			midiNoteNumbers.put("G1", 31);
			midiNoteNumbers.put("G1", 32);
			midiNoteNumbers.put("A1", 33);
			midiNoteNumbers.put("A1", 34);
			midiNoteNumbers.put("B1", 35);
			
			// Octave 2
			midiNoteNumbers.put("C2", 36);
			midiNoteNumbers.put("C2", 37);
			midiNoteNumbers.put("D2", 38);
			midiNoteNumbers.put("D2", 39);
			midiNoteNumbers.put("E2", 40);
			midiNoteNumbers.put("F2", 41);
			midiNoteNumbers.put("F2", 42);
			midiNoteNumbers.put("G2", 43);
			midiNoteNumbers.put("G2", 44);
			midiNoteNumbers.put("A2", 45);
			midiNoteNumbers.put("A2", 46);
			midiNoteNumbers.put("B2", 47);
			
			// Octave 3
			midiNoteNumbers.put("C3", 48);
			midiNoteNumbers.put("C3", 49);
			midiNoteNumbers.put("D3", 50);
			midiNoteNumbers.put("D3", 51);
			midiNoteNumbers.put("E3", 52);
			midiNoteNumbers.put("F3", 53);
			midiNoteNumbers.put("F3", 54);
			midiNoteNumbers.put("G3", 55);
			midiNoteNumbers.put("G3", 56);
			midiNoteNumbers.put("A3", 57);
			midiNoteNumbers.put("A3", 58);
			midiNoteNumbers.put("B3", 59);
			
			// Octave 4
			midiNoteNumbers.put("C4", 60);
			midiNoteNumbers.put("C4", 61);
			midiNoteNumbers.put("D4", 62);
			midiNoteNumbers.put("D4", 63);
			midiNoteNumbers.put("E4", 64);
			midiNoteNumbers.put("F4", 65);
			midiNoteNumbers.put("F4", 66);
			midiNoteNumbers.put("G4", 67);
			midiNoteNumbers.put("G4", 68);
			midiNoteNumbers.put("A4", 69);
			midiNoteNumbers.put("A4", 70);
			midiNoteNumbers.put("B4", 71);
			
			// Octave 5
			midiNoteNumbers.put("C5", 72);
			midiNoteNumbers.put("C5", 73);
			midiNoteNumbers.put("D5", 74);
			midiNoteNumbers.put("D5", 75);
			midiNoteNumbers.put("E5", 76);
			midiNoteNumbers.put("F5", 77);
			midiNoteNumbers.put("F5", 78);
			midiNoteNumbers.put("G5", 79);
			midiNoteNumbers.put("G5", 80);
			midiNoteNumbers.put("A5", 81);
			midiNoteNumbers.put("A5", 82);
			midiNoteNumbers.put("B5", 83);
			
			// Octave 6
			midiNoteNumbers.put("C6", 84);
			midiNoteNumbers.put("C6", 85);
			midiNoteNumbers.put("D6", 86);
			midiNoteNumbers.put("D6", 87);
			midiNoteNumbers.put("E6", 88);
			midiNoteNumbers.put("F6", 89);
			midiNoteNumbers.put("F6", 90);
			midiNoteNumbers.put("G6", 91);
			midiNoteNumbers.put("G6", 92);
			midiNoteNumbers.put("A6", 93);
			midiNoteNumbers.put("A6", 94);
			midiNoteNumbers.put("B6", 95);
			
			// Octave 7
			midiNoteNumbers.put("C7", 96);
			midiNoteNumbers.put("C7", 97);
			midiNoteNumbers.put("D7", 98);
			midiNoteNumbers.put("D7", 99);
			midiNoteNumbers.put("E7", 100);
			midiNoteNumbers.put("F7", 101);
			midiNoteNumbers.put("F7", 102);
			midiNoteNumbers.put("G7", 103);
			midiNoteNumbers.put("G7", 104);
			midiNoteNumbers.put("A7", 105);
			midiNoteNumbers.put("A7", 106);
			midiNoteNumbers.put("B7", 107);
			
			// Octave 8
			midiNoteNumbers.put("C8", 108);
			midiNoteNumbers.put("C8", 109);
			midiNoteNumbers.put("D8", 110);
			midiNoteNumbers.put("D8", 111);
			midiNoteNumbers.put("E8", 112);
			midiNoteNumbers.put("F8", 113);
			midiNoteNumbers.put("F8", 114);
			midiNoteNumbers.put("G8", 115);
			midiNoteNumbers.put("G8", 116);
			midiNoteNumbers.put("A8", 117);
			midiNoteNumbers.put("A8", 118);
			midiNoteNumbers.put("B8", 119);
			
			// Octave 9
			midiNoteNumbers.put("C9", 120);
			midiNoteNumbers.put("C9", 121);
			midiNoteNumbers.put("D9", 122);
			midiNoteNumbers.put("D9", 123);
			midiNoteNumbers.put("E9", 124);
			midiNoteNumbers.put("F9", 125);
			midiNoteNumbers.put("F9", 126);
			midiNoteNumbers.put("G9", 127);			
		}
		catch (JDOMException e) {	e.printStackTrace();	} 
		catch (IOException e) 	{	e.printStackTrace();	}
	}
}
