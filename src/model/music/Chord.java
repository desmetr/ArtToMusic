package model.music;

import java.util.Arrays;

import utilities.Globals;

public class Chord 
{

	private float tonicFrequency;
	private float thirdFrequency;
	private float fifthFrequency;
	private float bassFrequency;
	
	private Globals.ChordKey chordKey;
	private Globals.ChordType chordType;
	private Globals.Chord chord;
	
	public Chord(Globals.ChordKey chordKey, Globals.ChordType chordType, int pitch, Globals.Chord chord, int bassPitch)
	{	
		int indexTonic = pitch * 12 + Arrays.asList(Globals.ChordKey.values()).indexOf(chordKey);
		int indexThird = 0;
		int indexFifth = 0;
		
		int indexBass = bassPitch * 12 + Arrays.asList(Globals.ChordKey.values()).indexOf(chordKey);
		
		// TODO: use vectors of tuples per chordtypes, zie BeadsManager.java
		
		switch (chordType)
		{
			case MAJOR:
				indexThird = pitch * 12 + Arrays.asList(Globals.ChordKey.values()).indexOf(chordKey) + 4;	// We beginnen te tellen vanaf de eerstvolgende noot en niet de tonic.
				indexFifth = pitch * 12 + Arrays.asList(Globals.ChordKey.values()).indexOf(chordKey) + 7;	// We beginnen te tellen vanaf de eerstvolgende noot en niet de tonic.
				break;
			case MINOR:
				indexThird = pitch * 12 + Arrays.asList(Globals.ChordKey.values()).indexOf(chordKey) + 3;	// We beginnen te tellen vanaf de eerstvolgende noot en niet de tonic.
				indexFifth = pitch * 12 + Arrays.asList(Globals.ChordKey.values()).indexOf(chordKey) + 7;	// We beginnen te tellen vanaf de eerstvolgende noot en niet de tonic.
				break;
			case DIMINISHED:
				indexThird = pitch * 12 + Arrays.asList(Globals.ChordKey.values()).indexOf(chordKey) + 3;	// We beginnen te tellen vanaf de eerstvolgende noot en niet de tonic.
				indexFifth = pitch * 12 + Arrays.asList(Globals.ChordKey.values()).indexOf(chordKey) + 6;	// We beginnen te tellen vanaf de eerstvolgende noot en niet de tonic.
				break;
		}
						
		Globals.getInstance();
		tonicFrequency = Globals.noteFrequencies.get(indexTonic);
		thirdFrequency = Globals.noteFrequencies.get(indexThird);
		fifthFrequency = Globals.noteFrequencies.get(indexFifth);
		
		bassFrequency = Globals.noteFrequencies.get(indexBass);
		
		this.chordKey = chordKey;
		this.chordType = chordType;
		this.chord = chord;
	}

	public float getTonicFrequency() 			{	return tonicFrequency;	}
	public float getThirdFrequency()			{	return thirdFrequency;	}
	public float getFifthFrequency() 			{	return fifthFrequency;	}
	public float getBassFrequency()				{	return bassFrequency;	}
	public Globals.ChordKey getChordKey()		{	return chordKey;		}
	public Globals.Chord getChord()				{	return chord;			}		
	
	public String toString()
	{
		return chord.name();
	}
}
