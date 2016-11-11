package model.music;

import java.io.IOException;
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

import utilities.ArtToMusicLogger;
import utilities.Globals;

/**
 * Created by rafael on 22.10.16.
 */
public class MusicData
{
    private static Map<Globals.NoteLength, Double> getNoteOffsets()
    {
        Map<Globals.NoteLength, Double> noteOffsets = new HashMap<Globals.NoteLength, Double>();
        
		try 
		{
			SAXBuilder parser = new SAXBuilder();
			Document document = parser.build(Globals.getInstance().pathToMusic + "NoteOffsets.xml");
			
			XPathExpression<Element> xpath = XPathFactory.instance().compile("//note", Filters.element());
			List<Element> noteValues = xpath.evaluate(document);
			for (Element value : noteValues)
			{
				if (value.getAttributeValue("length").equalsIgnoreCase("whole"))
					noteOffsets.put(Globals.NoteLength.WHOLE, Double.parseDouble(value.getAttributeValue("offset")));
				
				else if (value.getAttributeValue("length").equalsIgnoreCase("half"))
					noteOffsets.put(Globals.NoteLength.HALF, Double.parseDouble(value.getAttributeValue("offset")));
				
				else if (value.getAttributeValue("length").equalsIgnoreCase("quarter"))
					noteOffsets.put(Globals.NoteLength.QUARTER, Double.parseDouble(value.getAttributeValue("offset")));
				
				else if (value.getAttributeValue("length").equalsIgnoreCase("quarter-dotted"))
					noteOffsets.put(Globals.NoteLength.QUARTER_DOTTED, Double.parseDouble(value.getAttributeValue("offset")));
				
				else if (value.getAttributeValue("length").equalsIgnoreCase("eighth"))
					noteOffsets.put(Globals.NoteLength.EIGHTH, Double.parseDouble(value.getAttributeValue("offset")));
				
				else if (value.getAttributeValue("length").equalsIgnoreCase("sixteenth"))
					noteOffsets.put(Globals.NoteLength.SIXTEENTH, Double.parseDouble(value.getAttributeValue("offset")));
				
				else if (value.getAttributeValue("length").equalsIgnoreCase("thirty-second"))
					noteOffsets.put(Globals.NoteLength.THIRTY_SECOND, Double.parseDouble(value.getAttributeValue("offset")));
			}
		}
		catch (JDOMException e) {	e.printStackTrace();	} 
		catch (IOException e) 	{	e.printStackTrace();	}

        return noteOffsets;
    }

    public static Vector<Note> generate(String path)
    {
    	ArtToMusicLogger.getInstance().info("Generating " + path);
    	
        Map<Globals.NoteLength, Double> noteOffsets = getNoteOffsets();
        Vector<Note> notes = new Vector<Note>();
        
		try 
		{
			SAXBuilder parser = new SAXBuilder();
			Document document = parser.build(path);
			
			XPathExpression<Element> xpath = XPathFactory.instance().compile("//note", Filters.element());
			List<Element> notesXML = xpath.evaluate(document);
			
			for (Element value : notesXML)
			{
				if (value.getAttributeValue("length").equalsIgnoreCase("whole"))
					notes.add(new Note(Globals.NoteLength.WHOLE, Integer.parseInt(value.getAttributeValue("midivalue")), noteOffsets.get(Globals.NoteLength.WHOLE)));
				
				else if (value.getAttributeValue("length").equalsIgnoreCase("half"))
					notes.add(new Note(Globals.NoteLength.HALF, Integer.parseInt(value.getAttributeValue("midivalue")), noteOffsets.get(Globals.NoteLength.HALF)));
				
				else if (value.getAttributeValue("length").equalsIgnoreCase("quarter"))
					notes.add(new Note(Globals.NoteLength.QUARTER, Integer.parseInt(value.getAttributeValue("midivalue")), noteOffsets.get(Globals.NoteLength.QUARTER)));
				
				else if (value.getAttributeValue("length").equalsIgnoreCase("quarter-dotted"))
					notes.add(new Note(Globals.NoteLength.QUARTER_DOTTED, Integer.parseInt(value.getAttributeValue("midivalue")), noteOffsets.get(Globals.NoteLength.QUARTER_DOTTED)));
				
				else if (value.getAttributeValue("length").equalsIgnoreCase("eighth"))
					notes.add(new Note(Globals.NoteLength.EIGHTH, Integer.parseInt(value.getAttributeValue("midivalue")), noteOffsets.get(Globals.NoteLength.EIGHTH)));
				
				else if (value.getAttributeValue("length").equalsIgnoreCase("sixteenth"))
					notes.add(new Note(Globals.NoteLength.SIXTEENTH, Integer.parseInt(value.getAttributeValue("midivalue")), noteOffsets.get(Globals.NoteLength.SIXTEENTH)));
				
				else if (value.getAttributeValue("length").equalsIgnoreCase("thirty-second"))
					notes.add(new Note(Globals.NoteLength.THIRTY_SECOND, Integer.parseInt(value.getAttributeValue("midivalue")), noteOffsets.get(Globals.NoteLength.THIRTY_SECOND)));
			}
		} 
		catch (JDOMException e)	{	e.printStackTrace();	}
		catch (IOException e) 	{	e.printStackTrace();	}

        return notes;
    }
}
