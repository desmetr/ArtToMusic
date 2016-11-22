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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.graphics.PixelRGB;
import utilities.ArtToMusicLogger;
import utilities.Globals;

/**
 * Created by rafael on 22.10.16.
 */
public class MusicData
{
	public static ObservableList<ObservableList<Double>> destinationEdgeMatrix = FXCollections.observableArrayList();
	public static ObservableList<ObservableList<PixelRGB>> destinationRGBValuesMatrix = FXCollections.observableArrayList();

	private static ObservableList<PixelRGB> grayList = FXCollections.observableArrayList();
	private static ObservableList<PixelRGB> blackList = FXCollections.observableArrayList();
	private static ObservableList<PixelRGB> whiteList = FXCollections.observableArrayList();
	
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
        
    public static void analyseRGB()
    {
    	for (ObservableList<PixelRGB> pixels : destinationRGBValuesMatrix)
    	{
    		for (PixelRGB pixel : pixels)
    		{
    			if ((pixel.getRed() == pixel.getGreen()) && (pixel.getRed() == pixel.getBlue()))
    				grayList.add(pixel);
    			else if ((pixel.getRed() < 40 && pixel.getGreen() < 40 && pixel.getBlue() < 40) &&
    					  Math.abs((pixel.getRed() - pixel.getGreen())) <= 20 &&
    					  Math.abs((pixel.getBlue() - pixel.getGreen())) <= 20 &&
    					  Math.abs((pixel.getRed() - pixel.getBlue())) <= 20)
					blackList.add(pixel);
    			else if ((pixel.getRed() > 245 && pixel.getGreen() > 245 && pixel.getBlue() > 245) &&
    					  Math.abs((pixel.getRed() - pixel.getGreen())) <= 20 &&
    					  Math.abs((pixel.getBlue() - pixel.getGreen())) <= 20 &&
    					  Math.abs((pixel.getRed() - pixel.getBlue())) <= 20)
    				whiteList.add(pixel);
    		}
    	}
    	
    	ArtToMusicLogger.getInstance().info(String.valueOf(grayList.size()));
    	ArtToMusicLogger.getInstance().info(String.valueOf(blackList.size()));
    	ArtToMusicLogger.getInstance().info(String.valueOf(whiteList.size()));
    	
    	Map<PixelRGB, Integer> pixelCount = new HashMap<PixelRGB, Integer>();
    	
    	for (ObservableList<PixelRGB> pixels : destinationRGBValuesMatrix)
    	{
    		for (PixelRGB pixel : pixels)
    		{
    			PixelRGB temp = pixel;
    			Integer count = 0;

    			for (ObservableList<PixelRGB> pixelsInner : destinationRGBValuesMatrix)
    			{
    				for (PixelRGB pixelInner : pixelsInner)
    				{
    					if (temp.equals(pixelInner))
    					{
    						pixelCount.put(pixelInner, ++count);
    					}
    				}
    			}
    		}
    	}
    	
    	for (PixelRGB pixel : pixelCount.keySet())
    	{
    		ArtToMusicLogger.getInstance().info("The pixel " + pixel.toString() + " was found " + String.valueOf(pixelCount.get(pixel)));	
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
