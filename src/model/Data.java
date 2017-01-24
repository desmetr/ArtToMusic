package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import utilities.Globals;

public class Data 
{	
	public static void readMidiNoteNumbers()
	{
		String csvFile = Globals.getInstance().pathToData + "midiNoteNumbers.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile)))
        {
            while ((line = br.readLine()) != null) 
            {
                // use comma as separator
                String[] result = line.split(cvsSplitBy);
                Globals.getInstance().midiNoteNumbers.put(result[0], Integer.parseInt(result[1]));
            }
        } 
        catch (IOException e) {	e.printStackTrace();	}
	}
	
	public static void readNoteFrequencies()
	{
		String csvFile = Globals.getInstance().pathToData + "noteFrequencies.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile)))
        {
            while ((line = br.readLine()) != null) 
            {
                // use comma as separator
                String[] result = line.split(cvsSplitBy);
                Globals.getInstance().noteFrequencies.add(Float.parseFloat(result[1]));
            }
        } 
        catch (IOException e) {	e.printStackTrace();	}
	}
	
	public static void readKeysOfDegrees()
	{
		String csvFile = Globals.getInstance().pathToData + "keysOfDegrees.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile)))
        {
            while ((line = br.readLine()) != null) 
            {
                // use comma as separator
                String[] result = line.split(cvsSplitBy);
                Globals.getInstance().keysOfDegrees.put(Integer.parseInt(result[0]), getChordKey(result[1]));
            }
        } 
        catch (IOException e) {	e.printStackTrace();	}
	}
	
	private static Globals.ChordKeys getChordKey(String key)
	{
		if (key.equalsIgnoreCase("major"))
			return Globals.ChordKeys.MAJOR;
		else if (key.equalsIgnoreCase("minor"))
			return Globals.ChordKeys.MINOR;
		else if (key.equalsIgnoreCase("diminished"))
			return Globals.ChordKeys.DIMINISHED;
		return null;
	}
}
