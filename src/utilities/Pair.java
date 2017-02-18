package utilities;

import utilities.Globals.ChordKey;

public class Pair 
{
	private ChordKey key;
	private ChordKey degree; 
	
	public Pair(ChordKey key, ChordKey degree)
	{
		this.key = key;
		this.degree = degree;
	}
	
	public ChordKey getKey() 		{	return key;		}
	public ChordKey getDegree() 	{	return degree;	}
	
	public String toString()
	{
		return key.name() + "-" + degree.name();
	}
}
