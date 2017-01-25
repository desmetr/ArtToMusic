package utilities;

import utilities.Globals.Chord;
import utilities.Globals.ChordName;

public class Pair 
{
	private ChordName key;
	private ChordName degree; 
	
	public Pair(ChordName key, ChordName degree)
	{
		this.key = key;
		this.degree = degree;
	}
	
	public ChordName getKey() 		{	return key;		}
	public ChordName getDegree() 	{	return degree;	}
}
