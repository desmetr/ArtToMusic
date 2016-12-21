package model.music;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.graphics.Pixel;
import net.beadsproject.beads.data.Buffer;
import utilities.ArtToMusicLogger;
import utilities.Globals;

/**
 * This class contains all information needed to create a musical pattern.
 * 
 * @author rafael
 * @version 1
 * @since 2016.10.22.
 */
public class MusicData
{
	public static ObservableList<ObservableList<Double>> destinationEdgeMatrix = FXCollections.observableArrayList();
	public static ObservableList<ObservableList<Pixel>> destinationRGBValuesMatrix = FXCollections.observableArrayList();

	private static ObservableList<Pixel> grayList = FXCollections.observableArrayList();
	private static ObservableList<Pixel> blackList = FXCollections.observableArrayList();
	private static ObservableList<Pixel> whiteList = FXCollections.observableArrayList();
	private static ObservableList<Pixel> redList = FXCollections.observableArrayList();
	private static ObservableList<Pixel> greenList = FXCollections.observableArrayList();
	private static ObservableList<Pixel> blueList = FXCollections.observableArrayList();
	
	public static DoubleProperty destinationMeanR = new SimpleDoubleProperty(0.0);
	public static DoubleProperty destinationMeanG = new SimpleDoubleProperty(0.0);
	public static DoubleProperty destinationMeanB = new SimpleDoubleProperty(0.0);
	public static DoubleProperty destinationMedianR = new SimpleDoubleProperty(0.0);
	public static DoubleProperty destinationMedianG = new SimpleDoubleProperty(0.0);
	public static DoubleProperty destinationMedianB = new SimpleDoubleProperty(0.0);
	
	/**
	 * Prints the matrix containing the information retrieved by the edge detection algorithm.
	 */
    public static void printEdgeMatrix()
    {
		ArtToMusicLogger.getInstance().info("MusicData: destinationEdgeMatrix change notified");
		ArtToMusicLogger.getInstance().info(String.valueOf(destinationEdgeMatrix.size()));
		ArtToMusicLogger.getInstance().info(String.valueOf(destinationEdgeMatrix.get(0).size()));
	
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < destinationEdgeMatrix.size(); i++)
		{
			for (int j = 0; j < destinationEdgeMatrix.get(i).size(); j++)
			{
				sb.append(String.valueOf(Globals.decimalFormat.format(destinationEdgeMatrix.get(i).get(j))) + " ");
			}
			sb.append("\n");
		}
		ArtToMusicLogger.getInstance().info(sb.toString());
    }
        
    /**
     * Counts the number of different pixels in an image, based on the matrix of pixels.
     */
    public static void analyseRGB()
    {
//    	for (ObservableList<Pixel> pixels : destinationRGBValuesMatrix)
//    	{
//    		for (Pixel pixel : pixels)
//    		{
//    			if ((pixel.getRed() == pixel.getGreen()) && (pixel.getRed() == pixel.getBlue()))
//    				grayList.add(pixel);
//    			else if ((pixel.getRed() < 40 && pixel.getGreen() < 40 && pixel.getBlue() < 40) &&
//    					  Math.abs((pixel.getRed() - pixel.getGreen())) <= 20 &&
//    					  Math.abs((pixel.getBlue() - pixel.getGreen())) <= 20 &&
//    					  Math.abs((pixel.getRed() - pixel.getBlue())) <= 20)
//					blackList.add(pixel);
//    			else if ((pixel.getRed() > 245 && pixel.getGreen() > 245 && pixel.getBlue() > 245) &&
//    					  Math.abs((pixel.getRed() - pixel.getGreen())) <= 20 &&
//    					  Math.abs((pixel.getBlue() - pixel.getGreen())) <= 20 &&
//    					  Math.abs((pixel.getRed() - pixel.getBlue())) <= 20)
//    				whiteList.add(pixel);
//    			else if (pixel.getRed() >= 200)
//    				redList.add(pixel);
//    			else if (pixel.getGreen() >= 200)
//    				greenList.add(pixel);
//    			else if (pixel.getBlue() >= 200)
//    				blueList.add(pixel);
//    		}
//    	}
//    	
//    	ArtToMusicLogger.getInstance().info("Gray " + String.valueOf(grayList.size()));
//    	ArtToMusicLogger.getInstance().info("Black " + String.valueOf(blackList.size()));
//    	ArtToMusicLogger.getInstance().info("White " + String.valueOf(whiteList.size()));
//    	ArtToMusicLogger.getInstance().info("Red " + String.valueOf(redList.size()));
//    	ArtToMusicLogger.getInstance().info("Green " + String.valueOf(greenList.size()));
//    	ArtToMusicLogger.getInstance().info("Blue " + String.valueOf(blueList.size()));
    	
    	System.out.println("---");
    	System.out.println(destinationMeanR.doubleValue());
    	System.out.println(destinationMeanG.doubleValue());
    	System.out.println(destinationMeanB.doubleValue());
   
    	int bpm;
    	Chord chord;
    	Buffer buffer;
    	
    	if (destinationMeanR.doubleValue() > destinationMeanG.doubleValue() || destinationMeanR.doubleValue() > destinationMeanB.doubleValue())
    	{
    		System.out.println("More R -> 140, E, Triangle");
    		bpm = 140;
    		chord = new Chord(Globals.ChordNames.E, Globals.ChordKeys.MAJOR, 4);
    		buffer = Buffer.TRIANGLE;
    	}
    	else if (destinationMeanG.doubleValue() > destinationMeanR.doubleValue() || destinationMeanG.doubleValue() > destinationMeanB.doubleValue())
    	{
    		System.out.println("More G -> 80, Bmin, Saw");
    		bpm = 80;
    		chord = new Chord(Globals.ChordNames.G, Globals.ChordKeys.MAJOR, 5);
    		buffer = Buffer.SAW;
    	}
    	else if (destinationMeanB.doubleValue() > destinationMeanR.doubleValue() || destinationMeanB.doubleValue() > destinationMeanR.doubleValue())
    	{
    		System.out.println("More B -> 160, Amin, Square");
    		bpm = 160;
    		chord = new Chord(Globals.ChordNames.A, Globals.ChordKeys.MINOR, 2);
    		buffer = Buffer.SQUARE; 
    	}
    	else
    	{
    		System.out.println("Else -> 120, C, Sine");
    		bpm = 120;
        	chord = new Chord(Globals.ChordNames.C, Globals.ChordKeys.MAJOR, 3);
        	buffer = Buffer.SINE;
    	}
    	
    	BeadsManager beadsManager = new BeadsManager();
    	beadsManager.playChord(bpm, chord, buffer);
    }
    
    /**
     * Generates the vector notes, containing all the information about the different kind of notes.
     * 
     * @param path
     * @return notes	vector of notes
     */
    public static Vector<Note> generate(String path)
    {
    	ArtToMusicLogger.getInstance().info("Generating " + path);
    	
        Vector<Note> notes = new Vector<Note>();
        
		try 
		{
			SAXBuilder parser = new SAXBuilder();
			Document document = parser.build(path);
			
			XPathExpression<Element> xpath = XPathFactory.instance().compile("//note", Filters.element());
			List<Element> notesXML = xpath.evaluate(document);
			
			Double offset = 0.0;
			String noteName = "";
			Integer midiValue = 0;
			
			for (Element value : notesXML)
			{
				offset = Double.parseDouble(value.getChild("duration").getText());
				noteName = value.getChild("pitch").getChildText("step") + value.getChild("pitch").getChildText("octave");
				midiValue = Globals.midiNoteNumbers.get(noteName);
				
				if (value.getChild("type").getText().equalsIgnoreCase("whole"))
					notes.add(new Note(Globals.NoteLength.WHOLE, midiValue, offset));
				
				else if (value.getChild("type").getText().equalsIgnoreCase("half"))
					notes.add(new Note(Globals.NoteLength.HALF, midiValue, offset));
				
				else if (value.getChild("type").getText().equalsIgnoreCase("quarter"))
					notes.add(new Note(Globals.NoteLength.QUARTER, midiValue, offset));
				
				else if (value.getChild("type").getText().equalsIgnoreCase("quarter-dotted"))
					notes.add(new Note(Globals.NoteLength.QUARTER_DOTTED, midiValue, offset));
				
				else if (value.getChild("type").getText().equalsIgnoreCase("eighth"))
					notes.add(new Note(Globals.NoteLength.EIGHTH, midiValue, offset));
				
				else if (value.getChild("type").getText().equalsIgnoreCase("sixteenth"))
					notes.add(new Note(Globals.NoteLength.SIXTEENTH, midiValue, offset));
				
				else if (value.getChild("type").getText().equalsIgnoreCase("thirty-second"))
					notes.add(new Note(Globals.NoteLength.THIRTY_SECOND, midiValue, offset));
			}
		} 
		catch (JDOMException e)	{	e.printStackTrace();	}
		catch (IOException e) 	{	e.printStackTrace();	}

        return notes;
    }
}
