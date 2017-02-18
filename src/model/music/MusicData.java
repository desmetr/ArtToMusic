package model.music;

import java.io.IOException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

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
	
	public static DoubleProperty destinationAHash = new SimpleDoubleProperty(0.0);
	public static DoubleProperty destinationDHash = new SimpleDoubleProperty(0.0);
	public static DoubleProperty destinationPHash = new SimpleDoubleProperty(0.0);
	
	public static DoubleProperty destinationEntropy = new SimpleDoubleProperty(0.0);
	
	private static BeadsManager beadsManager;
	
	private static int bpm;
	private static int size;
	
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
    	size = 1000;
    	double[] differentValues = new double[size];
    	
    	int counter = 0;
    	boolean continueLoop = true;
    	
		for (int i = 0; i < destinationEdgeMatrix.size(); i++)
		{
			if (continueLoop)
			{
				for (int j = 0; j < destinationEdgeMatrix.get(0).size(); j++)
				{
					double temp = destinationEdgeMatrix.get(i).get(j);
					if (!DoubleStream.of(differentValues).anyMatch(x -> x == temp))
					{			
						if (counter == size - 1)
							continueLoop = false;
						else
							differentValues[counter++] = temp;
					}
				}
			}
			else
				break;
		}

		if ((0 <= counter) && (counter < 50))
			bpm = ThreadLocalRandom.current().nextInt(50, 60 + 1);
		else if ((50 <= counter) && (counter < 150))
			bpm = ThreadLocalRandom.current().nextInt(61, 80 + 1);
		else if ((150 <= counter) && (counter < 500))
			bpm = ThreadLocalRandom.current().nextInt(81, 120 + 1);
		else if ((500 <= counter) && (counter < 1000)) 
			bpm = ThreadLocalRandom.current().nextInt(121, 150 + 1);
		
		System.out.println("BPM: " + bpm);
    }
    
    public static void analyseRGB() throws InterruptedException
    {
    	Chord chord = null;
    	Globals.ChordProgression chordProgression = null;
    	
    	Thread.sleep(1000);
    	
    	beadsManager = new BeadsManager();
    	double max = Math.max(destinationMeanR.doubleValue(), Math.max(destinationMeanG.doubleValue(), destinationMeanB.doubleValue()));
    	
    	// All colors are equal.
    	if ((destinationMeanR.doubleValue() == destinationMeanG.doubleValue()) && (destinationMeanR.doubleValue() == destinationMeanB.doubleValue()))
    	{
    		chordProgression = Globals.ChordProgression.I_III_VI_II_V;
    		chord = new Chord(Globals.ChordKey.G, Globals.ChordType.MAJOR, 4, Globals.Chord.C_MAJOR);
    	}
    	// More red.
    	else if (max == destinationMeanR.doubleValue())
    	{
    		chordProgression = Globals.ChordProgression.I_II_V_I;
    		chord = new Chord(Globals.ChordKey.C, Globals.ChordType.DIMINISHED, 4, Globals.Chord.C_MAJOR);
    	}
    	// More green.
    	else if (max == destinationMeanG.doubleValue())
    	{
    		chordProgression = Globals.ChordProgression.I_III_IV_VI;
    		chord = new Chord(Globals.ChordKey.E, Globals.ChordType.MINOR, 4, Globals.Chord.C_MAJOR);
    	}
    	// More blue
    	else if (max == destinationMeanB.doubleValue())
    	{
    		chordProgression = Globals.ChordProgression.I_VI_II_IV;
    		chord = new Chord(Globals.ChordKey.A_SHARP, Globals.ChordType.DIMINISHED, 4, Globals.Chord.C_MAJOR);
    	}
    	
    	beadsManager.playChordProgression(chordProgression, bpm, chord, Buffer.SINE);
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
