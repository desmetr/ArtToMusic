package model.music;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

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

public class MusicData
{
	public static ObservableList<ObservableList<Double>> destinationEdgeMatrix = FXCollections.observableArrayList();
	public static ObservableList<ObservableList<Pixel>> destinationRGBValuesMatrix = FXCollections.observableArrayList();

	private static ObservableList<Pixel> grayList = FXCollections.observableArrayList();
//	private static ObservableList<Pixel> blackList = FXCollections.observableArrayList();
//	private static ObservableList<Pixel> whiteList = FXCollections.observableArrayList();
//	private static ObservableList<Pixel> redList = FXCollections.observableArrayList();
//	private static ObservableList<Pixel> greenList = FXCollections.observableArrayList();
//	private static ObservableList<Pixel> blueList = FXCollections.observableArrayList();
	
	public static DoubleProperty destinationMeanR = new SimpleDoubleProperty(0.0);
	public static DoubleProperty destinationMeanG = new SimpleDoubleProperty(0.0);
	public static DoubleProperty destinationMeanB = new SimpleDoubleProperty(0.0);
	public static DoubleProperty destinationMedianR = new SimpleDoubleProperty(0.0);
	public static DoubleProperty destinationMedianG = new SimpleDoubleProperty(0.0);
	public static DoubleProperty destinationMedianB = new SimpleDoubleProperty(0.0);
	
	private static BeadsManager beadsManager;
	
	private static int bpm;
	
    public static void printEdgeMatrix()
    {
    	ArtToMusicLogger.getInstance().info(Globals.imageName);
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
        
    public static void setTempo()
    {
//    	printEdgeMatrix();
    	
    	ArrayList<Double> differentValues = new ArrayList<>();
    	
		for (int i = 0; i < destinationEdgeMatrix.size(); i++)
		{
			for (int j = 0; j < destinationEdgeMatrix.get(i).size(); j++)
			{
				if (!differentValues.contains(destinationEdgeMatrix.get(i).get(j)))
				{
					differentValues.add(destinationEdgeMatrix.get(i).get(j));
				}
			}
		}
		
		// TODO: write this down orderly: 
		// no edges: 0 <= differentValues.size() < 50 = [50bpm, 60bpm]
		// few edges: 50 <= differentValues.size() < 150 = [61bpm, 80bpm]
		// moderately number of edges: 150 <= differentValues.size() < 500 = [81bp, 120bpm]
		// lot of edges: 500 <= differentValues.size() < 1000 = [121bpm, 150bpm]
		
		if ((0 <= differentValues.size()) && (differentValues.size() < 50))
			bpm = ThreadLocalRandom.current().nextInt(50, 60 + 1);
		else if ((50 <= differentValues.size()) && (differentValues.size() < 150))
			bpm = ThreadLocalRandom.current().nextInt(61, 80 + 1);
		else if ((150 <= differentValues.size()) && (differentValues.size() < 500))
			bpm = ThreadLocalRandom.current().nextInt(81, 120 + 1);
		else if ((500 <= differentValues.size()) && (differentValues.size() < 1000)) 
			bpm = ThreadLocalRandom.current().nextInt(121, 150 + 1);
    }
    
    public static void analyseRGB() throws InterruptedException
    {
//    	int numberOfPixels = 0;
//    
//    	for (ObservableList<Pixel> pixels : destinationRGBValuesMatrix)
//    	{
//    		for (Pixel pixel : pixels)
//    		{
//    			if ((pixel.getRed() == pixel.getGreen()) && (pixel.getRed() == pixel.getBlue()))
//    				grayList.add(pixel);
//    			
//    			numberOfPixels++;
//    		}
//    	}
    	
    	Chord chord;
    	Buffer buffer = Buffer.SINE;
    	Globals.ChordProgression chordProgression;
    	
    	System.out.println(Globals.imageName);
    	System.out.println(destinationMeanR.doubleValue());
    	System.out.println(destinationMeanG.doubleValue());
    	System.out.println(destinationMeanB.doubleValue());
    	
    	Thread.sleep(1000);
    	
    	beadsManager = new BeadsManager();
    	
    	if ((destinationMeanR.doubleValue() == destinationMeanG.doubleValue()) && (destinationMeanR.doubleValue() == destinationMeanB.doubleValue()))
    	{
    		System.out.println("All equal -> I_III_VI_II_V");
    		chordProgression = Globals.ChordProgression.I_III_VI_II_V;
    		chord = new Chord(Globals.ChordKey.G, Globals.ChordType.MAJOR, 4, Globals.Chord.C_MAJOR);
    		
    		beadsManager.playChordProgression(chordProgression, bpm, chord, Buffer.SINE);
    	}
    	else if (Math.max(destinationMeanR.doubleValue(), Math.max(destinationMeanG.doubleValue(), destinationMeanB.doubleValue())) == destinationMeanR.doubleValue())
    	{
    		System.out.println("More R -> I_II_V_I");
    		chordProgression = Globals.ChordProgression.I_II_V_I;
    		chord = new Chord(Globals.ChordKey.C, Globals.ChordType.DIMINISHED, 4, Globals.Chord.C_MAJOR);
    		
    		beadsManager.playChordProgression(chordProgression, bpm, chord, Buffer.SINE);
    	}
    	else if (Math.max(destinationMeanR.doubleValue(), Math.max(destinationMeanG.doubleValue(), destinationMeanB.doubleValue())) == destinationMeanG.doubleValue())
    	{
    		System.out.println("More G -> I_III_IV_VI");
    		chordProgression = Globals.ChordProgression.I_III_IV_VI;
    		chord = new Chord(Globals.ChordKey.E, Globals.ChordType.MINOR, 4, Globals.Chord.C_MAJOR);
    		
    		beadsManager.playChordProgression(chordProgression, bpm, chord, Buffer.SINE);
    	}
    	else if (Math.max(destinationMeanR.doubleValue(), Math.max(destinationMeanG.doubleValue(), destinationMeanB.doubleValue())) == destinationMeanB.doubleValue())
    	{
    		System.out.println("More B -> I_VI_II_IV");
    		chordProgression = Globals.ChordProgression.I_VI_II_IV;
    		chord = new Chord(Globals.ChordKey.A_SHARP, Globals.ChordType.DIMINISHED, 4, Globals.Chord.C_MAJOR);
    		
    		beadsManager.playChordProgression(chordProgression, bpm, chord, Buffer.SINE);
    	}
    }
    
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
