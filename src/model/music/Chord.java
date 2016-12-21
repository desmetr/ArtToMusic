package model.music;

import java.util.Arrays;

import utilities.Globals;

public class Chord 
{

	private float tonicFrequency;
	private float thirdFrequency;
	private float fifthFrequency;
	
	public Chord(Globals.ChordNames chordName, Globals.ChordKeys chordKey, int pitch)
	{	
		int indexTonic = pitch * 12 + Arrays.asList(Globals.ChordNames.values()).indexOf(chordName);
		
		int indexThird = 0;
		if (chordKey == Globals.ChordKeys.MAJOR)
			indexThird = pitch * 12 + Arrays.asList(Globals.ChordNames.values()).indexOf(chordName) + 4;	// We beginnen te tellen vanaf de eerstvolgende noot en niet de tonic.
		else if (chordKey == Globals.ChordKeys.MINOR || chordKey == Globals.ChordKeys.DIMINISHED)
			indexThird = pitch * 12 + Arrays.asList(Globals.ChordNames.values()).indexOf(chordName) + 3;	// We beginnen te tellen vanaf de eerstvolgende noot en niet de tonic.
		
		int indexFifth = pitch * 12 + Arrays.asList(Globals.ChordNames.values()).indexOf(chordName) + 7;	// We beginnen te tellen vanaf de eerstvolgende noot en niet de tonic.
		if (chordKey == Globals.ChordKeys.DIMINISHED)
			indexFifth = pitch * 12 + Arrays.asList(Globals.ChordNames.values()).indexOf(chordName) + 6;	// We beginnen te tellen vanaf de eerstvolgende noot en niet de tonic.
		
		tonicFrequency = Globals.getInstance().noteFrequencies.get(indexTonic);
		thirdFrequency = Globals.getInstance().noteFrequencies.get(indexThird);
		fifthFrequency = Globals.getInstance().noteFrequencies.get(indexFifth);
	}

	public float getTonicFrequency() 	{	return tonicFrequency;	}
	public float getThirdFrequency()	{	return thirdFrequency;	}
	public float getFifthFrequency() 	{	return fifthFrequency;	}
}
