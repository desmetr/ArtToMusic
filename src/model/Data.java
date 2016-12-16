package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import utilities.Globals;

public class Data {
// TODO: read midiNoteNumbers.csv
// TODO: read bpmToMillisec.csv
// TODO: read noteFrequencies.csv
	
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
                Globals.getInstance().midiNoteNumbers.put(result[0], Integer.getInteger(result[1]));
                System.out.println(result[0] + "," + result[1]);
            }
        } 
        catch (IOException e) {	e.printStackTrace();	}
	}
	
	public static void readBPMToMillisec()
	{
		String csvFile = Globals.getInstance().pathToData + "bpmToMillisec.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile)))
        {
            while ((line = br.readLine()) != null) 
            {
                // use comma as separator
                String[] result = line.split(cvsSplitBy);
                Globals.getInstance().bpmToMilliSec.put(Integer.getInteger(result[0]), Integer.getInteger(result[1]));
                System.out.println(result[0] + "," + result[1]);
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
                Globals.getInstance().noteFrequencies.put(result[0], Integer.getInteger(result[1]));
                System.out.println(result[0] + "," + result[1]);
            }
        } 
        catch (IOException e) {	e.printStackTrace();	}
	}
}
