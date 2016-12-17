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

import model.Data;

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
    public static LinkedHashMap<Integer, Integer> bpmToMilliSec = new LinkedHashMap<Integer, Integer>();
    public static LinkedHashMap<String, Float> noteFrequencies = new LinkedHashMap<String, Float>();

    public enum NoteLength {WHOLE, HALF, QUARTER, QUARTER_DOTTED, EIGHTH, SIXTEENTH, THIRTY_SECOND}
    public enum Chords {
    	C_MAJOR,
    	C_SHARP_MAJOR,
    	D_MAJOR,
    	D_SHARP_MAJOR,
    	E_MAJOR,
    	F_MAJOR,
    	F_SHARP_MAJOR,
    	G_MAJOR,
    	G_SHARP_MAJOR,
    	A_MAJOR,
    	A_SHARP_MAJOR,
    	B_MAJOR,
    	C_MINOR,
    	C_SHARP_MINOR,
    	D_MINOR,
    	D_SHARP_MINOR,
    	E_MINOR,
    	F_MINOR,
    	F_SHARP_MINOR,
    	G_MINOR,
    	G_SHARP_MINOR,
    	A_MINOR,
    	A_SHARP_MINOR,
    	B_MINOR
    }

    /**
     * Constructor of the class. Private because it's a singleton.
     */
    private Globals()
	{
		try
		{
			getPaths();
			
			/*
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
			
			// 60-69
			bpmToMilliSec.put(60, 1000);
			bpmToMilliSec.put(61, 984);
			bpmToMilliSec.put(62, 968);
			bpmToMilliSec.put(63, 952);
			bpmToMilliSec.put(64, 938);
			bpmToMilliSec.put(65, 923);
			bpmToMilliSec.put(66, 909);
			bpmToMilliSec.put(67, 896);
			bpmToMilliSec.put(68, 882);
			bpmToMilliSec.put(69, 870);
			
			// 70-79
			bpmToMilliSec.put(70, 857);
			bpmToMilliSec.put(71, 845);
			bpmToMilliSec.put(72, 833);
			bpmToMilliSec.put(73, 822);
			bpmToMilliSec.put(74, 811);
			bpmToMilliSec.put(75, 800);
			bpmToMilliSec.put(76, 789);
			bpmToMilliSec.put(77, 779);
			bpmToMilliSec.put(78, 769);
			bpmToMilliSec.put(79, 759);
			
			// 80-89
			bpmToMilliSec.put(80, 750);
			bpmToMilliSec.put(81, 741);
			bpmToMilliSec.put(82, 732);
			bpmToMilliSec.put(83, 723);
			bpmToMilliSec.put(84, 714);
			bpmToMilliSec.put(85, 706);
			bpmToMilliSec.put(86, 698);
			bpmToMilliSec.put(87, 690);
			bpmToMilliSec.put(88, 682);
			bpmToMilliSec.put(89, 674);
			
			// 90-99
			bpmToMilliSec.put(90, 667);
			bpmToMilliSec.put(91, 659);
			bpmToMilliSec.put(92, 652);
			bpmToMilliSec.put(93, 645);
			bpmToMilliSec.put(94, 638);
			bpmToMilliSec.put(95, 632);
			bpmToMilliSec.put(96, 625);
			bpmToMilliSec.put(97, 619);
			bpmToMilliSec.put(98, 612);
			bpmToMilliSec.put(99, 606);
			
			// 100-109
			bpmToMilliSec.put(100, 600);
			bpmToMilliSec.put(101, 594);
			bpmToMilliSec.put(102, 588);
			bpmToMilliSec.put(103, 583);
			bpmToMilliSec.put(104, 577);
			bpmToMilliSec.put(105, 571);
			bpmToMilliSec.put(106, 566);
			bpmToMilliSec.put(107, 561);
			bpmToMilliSec.put(108, 556);
			bpmToMilliSec.put(109, 550);
			
			// 110-119
			bpmToMilliSec.put(110, 545);
			bpmToMilliSec.put(111, 541);
			bpmToMilliSec.put(112, 536);
			bpmToMilliSec.put(113, 531);
			bpmToMilliSec.put(114, 526);
			bpmToMilliSec.put(115, 522);
			bpmToMilliSec.put(116, 517);
			bpmToMilliSec.put(117, 513);
			bpmToMilliSec.put(118, 508);
			bpmToMilliSec.put(119, 504);
			
			// 120-129
			bpmToMilliSec.put(120, 500);
			bpmToMilliSec.put(121, 496);
			bpmToMilliSec.put(122, 492);
			bpmToMilliSec.put(123, 488);
			bpmToMilliSec.put(124, 484);
			bpmToMilliSec.put(125, 480);
			bpmToMilliSec.put(126, 476);
			bpmToMilliSec.put(127, 472);
			bpmToMilliSec.put(128, 469);
			bpmToMilliSec.put(129, 465);
			
			// 130-139
			bpmToMilliSec.put(130, 462);
			bpmToMilliSec.put(131, 458);
			bpmToMilliSec.put(132, 455);
			bpmToMilliSec.put(133, 451);
			bpmToMilliSec.put(134, 448);
			bpmToMilliSec.put(135, 444);
			bpmToMilliSec.put(136, 441);
			bpmToMilliSec.put(137, 438);
			bpmToMilliSec.put(138, 435);
			bpmToMilliSec.put(139, 432);
			
			// 140-149
			bpmToMilliSec.put(140, 429);
			bpmToMilliSec.put(141, 426);
			bpmToMilliSec.put(142, 423);
			bpmToMilliSec.put(143, 420);
			bpmToMilliSec.put(144, 417);
			bpmToMilliSec.put(145, 414);
			bpmToMilliSec.put(146, 411);
			bpmToMilliSec.put(147, 408);
			bpmToMilliSec.put(148, 405);
			bpmToMilliSec.put(149, 403);
			
			// 150-159
			bpmToMilliSec.put(150, 400);
			bpmToMilliSec.put(151, 397);
			bpmToMilliSec.put(152, 395);
			bpmToMilliSec.put(153, 392);
			bpmToMilliSec.put(154, 390);
			bpmToMilliSec.put(155, 387);
			bpmToMilliSec.put(156, 385);
			bpmToMilliSec.put(157, 382);
			bpmToMilliSec.put(158, 380);
			bpmToMilliSec.put(159, 377);
			
			// 160-169
			bpmToMilliSec.put(160, 375);
			bpmToMilliSec.put(161, 373);
			bpmToMilliSec.put(162, 370);
			bpmToMilliSec.put(163, 368);
			bpmToMilliSec.put(164, 366);
			bpmToMilliSec.put(165, 364);
			bpmToMilliSec.put(166, 361);
			bpmToMilliSec.put(167, 359);
			bpmToMilliSec.put(168, 357);
			bpmToMilliSec.put(169, 355);
			
			// 170-180
			bpmToMilliSec.put(170, 353);
			bpmToMilliSec.put(171, 351);
			bpmToMilliSec.put(172, 349);
			bpmToMilliSec.put(173, 347);
			bpmToMilliSec.put(174, 345);
			bpmToMilliSec.put(175, 343);
			bpmToMilliSec.put(176, 341);
			bpmToMilliSec.put(177, 339);
			bpmToMilliSec.put(178, 337);
			bpmToMilliSec.put(179, 335);
			bpmToMilliSec.put(180, 333);*/
		}
		catch (JDOMException e) {	e.printStackTrace();	} 
		catch (IOException e) 	{	e.printStackTrace();	}
	}
}
