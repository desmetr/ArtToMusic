package model.music;

import java.util.Arrays;

import utilities.Globals;

public class Chord 
{

	private float tonicFrequency;
	private float thirdFrequency;
	private float fifthFrequency;
	
	private Globals.ChordName chordName;
	private Globals.ChordKey chordKey;
	private Globals.Chord chord;
	
	public Chord(Globals.ChordName chordName, Globals.ChordKey chordKey, int pitch, Globals.Chord chord)
	{	
		int indexTonic = pitch * 12 + Arrays.asList(Globals.ChordName.values()).indexOf(chordName);
		int indexThird = 0;
		int indexFifth = 0;
		
		// TODO: use vectors of tuples per chordtypes, zie BeadsManager.java
		
		switch (chordKey)
		{
			case MAJOR:
				indexThird = pitch * 12 + Arrays.asList(Globals.ChordName.values()).indexOf(chordName) + 4;	// We beginnen te tellen vanaf de eerstvolgende noot en niet de tonic.
				indexFifth = pitch * 12 + Arrays.asList(Globals.ChordName.values()).indexOf(chordName) + 7;	// We beginnen te tellen vanaf de eerstvolgende noot en niet de tonic.
				break;
			case MINOR:
				indexThird = pitch * 12 + Arrays.asList(Globals.ChordName.values()).indexOf(chordName) + 3;	// We beginnen te tellen vanaf de eerstvolgende noot en niet de tonic.
				indexFifth = pitch * 12 + Arrays.asList(Globals.ChordName.values()).indexOf(chordName) + 7;	// We beginnen te tellen vanaf de eerstvolgende noot en niet de tonic.
				break;
			case DIMINISHED:
				indexThird = pitch * 12 + Arrays.asList(Globals.ChordName.values()).indexOf(chordName) + 3;	// We beginnen te tellen vanaf de eerstvolgende noot en niet de tonic.
				indexFifth = pitch * 12 + Arrays.asList(Globals.ChordName.values()).indexOf(chordName) + 6;	// We beginnen te tellen vanaf de eerstvolgende noot en niet de tonic.
				break;
		}
						
		tonicFrequency = Globals.getInstance().noteFrequencies.get(indexTonic);
		thirdFrequency = Globals.getInstance().noteFrequencies.get(indexThird);
		fifthFrequency = Globals.getInstance().noteFrequencies.get(indexFifth);
		
		this.chordName = chordName;
		this.chordKey = chordKey;
		this.chord = chord;
	}

	public float getTonicFrequency() 			{	return tonicFrequency;	}
	public float getThirdFrequency()			{	return thirdFrequency;	}
	public float getFifthFrequency() 			{	return fifthFrequency;	}
	public Globals.ChordName getChordName()		{	return chordName;		}
	public Globals.Chord getChord()				{	return chord;			}		
	
	public String toString()
	{
		return chordName.name() + " ";
	}
}
