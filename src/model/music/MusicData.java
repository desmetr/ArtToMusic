package model.music;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import utilities.ArtToMusicLogger;
import utilities.Globals;

/**
 * Created by rafael on 22.10.16.
 */
public class MusicData
{
	public static ObservableList<ObservableList<Double>> destinationEdgeMatrix = FXCollections.observableArrayList();

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
