package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import utilities.Globals;
import utilities.Globals.ChordName;
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
		readDegree2Minor();
		readDegree2Major();
		readDegree3Minor();
		readDegree3Major();
		readDegree4Minor();
		readDegree4Major();
		readDegree5Minor();
		readDegree5Major();
		readDegree6Minor();
		readDegree6Major();
		readDegree7Minor();
		readDegree7Major();
	}

	public static void readDegree2Minor()
	{
		String csvFile = Globals.getInstance().pathToData + "degree2minor.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile)))
        {
            while ((line = br.readLine()) != null) 
            {
                // use comma as separator
                String[] result = line.split(cvsSplitBy);
                Globals.ChordName key = getChordName(result[0]);
                Globals.ChordName degree = getChordName(result[1]);
                Globals.getInstance().degree2minor.add(new Pair(key, degree));
            }
        } 
        catch (IOException e) {	e.printStackTrace();	}
	}
	
	public static void readDegree2Major()
	{
		String csvFile = Globals.getInstance().pathToData + "degree2major.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile)))
        {
            while ((line = br.readLine()) != null) 
            {
                // use comma as separator
                String[] result = line.split(cvsSplitBy);
                Globals.ChordName key = getChordName(result[0]);
                Globals.ChordName degree = getChordName(result[1]);
                Globals.getInstance().degree2major.add(new Pair(key, degree));            
            }
        } 
        catch (IOException e) {	e.printStackTrace();	}
	}
	
	public static void readDegree3Minor()
	{
		String csvFile = Globals.getInstance().pathToData + "degree3minor.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile)))
        {
            while ((line = br.readLine()) != null) 
            {
                // use comma as separator
                String[] result = line.split(cvsSplitBy);
                Globals.ChordName key = getChordName(result[0]);
                Globals.ChordName degree = getChordName(result[1]);
                Globals.getInstance().degree3minor.add(new Pair(key, degree));
            }
        } 
        catch (IOException e) {	e.printStackTrace();	}
	}
	
	public static void readDegree3Major()
	{
		String csvFile = Globals.getInstance().pathToData + "degree3major.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile)))
        {
            while ((line = br.readLine()) != null) 
            {
                // use comma as separator
                String[] result = line.split(cvsSplitBy);
                Globals.ChordName key = getChordName(result[0]);
                Globals.ChordName degree = getChordName(result[1]);
                Globals.getInstance().degree3major.add(new Pair(key, degree)); 
            }
        } 
        catch (IOException e) {	e.printStackTrace();	}
	}
	
	public static void readDegree4Minor()
	{
		String csvFile = Globals.getInstance().pathToData + "degree4minor.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile)))
        {
            while ((line = br.readLine()) != null) 
            {
                // use comma as separator
                String[] result = line.split(cvsSplitBy);
                Globals.ChordName key = getChordName(result[0]);
                Globals.ChordName degree = getChordName(result[1]);
                Globals.getInstance().degree4minor.add(new Pair(key, degree));  
            }
        } 
        catch (IOException e) {	e.printStackTrace();	}
	}
	
	public static void readDegree4Major()
	{
		String csvFile = Globals.getInstance().pathToData + "degree4major.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile)))
        {
            while ((line = br.readLine()) != null) 
            {
                // use comma as separator
                String[] result = line.split(cvsSplitBy);
                Globals.ChordName key = getChordName(result[0]);
                Globals.ChordName degree = getChordName(result[1]);
                Globals.getInstance().degree4major.add(new Pair(key, degree));
            }
        } 
        catch (IOException e) {	e.printStackTrace();	}
	}
	
	public static void readDegree5Minor()
	{
		String csvFile = Globals.getInstance().pathToData + "degree5minor.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile)))
        {
            while ((line = br.readLine()) != null) 
            {
                // use comma as separator
                String[] result = line.split(cvsSplitBy);
                Globals.ChordName key = getChordName(result[0]);
                Globals.ChordName degree = getChordName(result[1]);
                Globals.getInstance().degree5minor.add(new Pair(key, degree));  
            }
        } 
        catch (IOException e) {	e.printStackTrace();	}
	}
	
	public static void readDegree5Major()
	{
		String csvFile = Globals.getInstance().pathToData + "degree5major.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile)))
        {
            while ((line = br.readLine()) != null) 
            {
                // use comma as separator
                String[] result = line.split(cvsSplitBy);
                Globals.ChordName key = getChordName(result[0]);
                Globals.ChordName degree = getChordName(result[1]);
                Globals.getInstance().degree5major.add(new Pair(key, degree));            
            }
        } 
        catch (IOException e) {	e.printStackTrace();	}
	}
	
	public static void readDegree6Minor()
	{
		String csvFile = Globals.getInstance().pathToData + "degree6minor.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile)))
        {
            while ((line = br.readLine()) != null) 
            {
                // use comma as separator
                String[] result = line.split(cvsSplitBy);
                Globals.ChordName key = getChordName(result[0]);
                Globals.ChordName degree = getChordName(result[1]);
                Globals.getInstance().degree6minor.add(new Pair(key, degree));  
            }
        } 
        catch (IOException e) {	e.printStackTrace();	}
	}
	
	public static void readDegree6Major()
	{
		String csvFile = Globals.getInstance().pathToData + "degree6major.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile)))
        {
            while ((line = br.readLine()) != null) 
            {
                // use comma as separator
                String[] result = line.split(cvsSplitBy);
                Globals.ChordName key = getChordName(result[0]);
                Globals.ChordName degree = getChordName(result[1]);
                Globals.getInstance().degree6major.add(new Pair(key, degree));   
            }
        } 
        catch (IOException e) {	e.printStackTrace();	}
	}
	
	public static void readDegree7Minor()
	{
		String csvFile = Globals.getInstance().pathToData + "degree7minor.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile)))
        {
            while ((line = br.readLine()) != null) 
            {
                // use comma as separator
                String[] result = line.split(cvsSplitBy);
                Globals.ChordName key = getChordName(result[0]);
                Globals.ChordName degree = getChordName(result[1]);
                Globals.getInstance().degree7minor.add(new Pair(key, degree)); 
            }
        } 
        catch (IOException e) {	e.printStackTrace();	}
	}
	
	public static void readDegree7Major()
	{
		String csvFile = Globals.getInstance().pathToData + "degree7major.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile)))
        {
            while ((line = br.readLine()) != null) 
            {
                // use comma as separator
                String[] result = line.split(cvsSplitBy);
                Globals.ChordName key = getChordName(result[0]);
                Globals.ChordName degree = getChordName(result[1]);
                Globals.getInstance().degree7major.add(new Pair(key, degree));   
            }
        } 
        catch (IOException e) {	e.printStackTrace();	}
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
	
	private static Globals.ChordName getChordName(String name)
	{
		if (name.equals("C"))
			return ChordName.C;
		else if (name.equals("C#"))
			return ChordName.C_SHARP;
		else if (name.equals("D"))
			return ChordName.D;
		else if (name.equals("D#"))
			return ChordName.D_SHARP;
		else if (name.equals("E"))
			return ChordName.E;
		else if (name.equals("F"))
			return ChordName.F;
		else if (name.equals("F#"))
			return ChordName.F_SHARP;
		else if (name.equals("G"))
			return ChordName.G;
		else if (name.equals("G#"))
			return ChordName.G_SHARP;
		else if (name.equals("A"))
			return ChordName.A;
		else if (name.equals("A#"))
			return ChordName.A_SHARP;
		else if (name.equals("B"))
			return ChordName.B;
		
		return null;
	}
}
