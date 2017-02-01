package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utilities.Globals;
import utilities.Globals.ChordKey;
import utilities.Pair;

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
	
	public static void readDegrees()
	{		
		String csvFile = Globals.getInstance().pathToData + "degrees.csv";
        String name = "";
        List<Pair> list = new ArrayList<Pair>();
        String[] result;

        try 
        {
			List<String> lines = Files.readAllLines(Paths.get(csvFile));
			
			for (int i = 0; i < lines.size(); i++)
			{
				switch (i % 13)
				{
					case 0:
						name = lines.get(i).split(" ")[1];
						break;
					case 12:
						result = lines.get(i).split(",");
	                    list.add(new Pair(getChordName(result[0]), getChordName(result[1])));
	                    
	                    Globals.getInstance().degrees.putIfAbsent(name, list);
						list = new ArrayList<Pair>();
						break;
					default:
						result = lines.get(i).split(",");
	                    list.add(new Pair(getChordName(result[0]), getChordName(result[1])));
	                    break;
				}
			}
		}
        catch (IOException e) 	{	e.printStackTrace();	}
	}

	private static Globals.ChordType getChordKey(String key)
	{
		if (key.equalsIgnoreCase("major"))
			return Globals.ChordType.MAJOR;
		else if (key.equalsIgnoreCase("minor"))
			return Globals.ChordType.MINOR;
		else if (key.equalsIgnoreCase("diminished"))
			return Globals.ChordType.DIMINISHED;
		return null;
	}
	
	private static Globals.ChordKey getChordName(String name)
	{
		if (name.equals("C"))
			return ChordKey.C;
		else if (name.equals("C#"))
			return ChordKey.C_SHARP;
		else if (name.equals("D"))
			return ChordKey.D;
		else if (name.equals("D#"))
			return ChordKey.D_SHARP;
		else if (name.equals("E"))
			return ChordKey.E;
		else if (name.equals("F"))
			return ChordKey.F;
		else if (name.equals("F#"))
			return ChordKey.F_SHARP;
		else if (name.equals("G"))
			return ChordKey.G;
		else if (name.equals("G#"))
			return ChordKey.G_SHARP;
		else if (name.equals("A"))
			return ChordKey.A;
		else if (name.equals("A#"))
			return ChordKey.A_SHARP;
		else if (name.equals("B"))
			return ChordKey.B;
		
		return null;
	}
}
