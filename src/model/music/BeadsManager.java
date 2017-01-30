package model.music;

import java.util.List;
import java.util.Map.Entry;

import javafx.beans.value.ObservableValue;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.Bead;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.data.Pitch;
import net.beadsproject.beads.events.KillTrigger;
import net.beadsproject.beads.ugens.Clock;
import net.beadsproject.beads.ugens.Envelope;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.WavePlayer;
import utilities.ArtToMusicLogger;
import utilities.Globals;
import utilities.Pair;

/**
 * This class controls all interaction with the Beads Project.
 * 
 * @author rafael
 * @version 1
 * @since 2016.12.13.
 */

public class BeadsManager
{
	private WavePlayer tonic; 
	private WavePlayer third;
	private WavePlayer fifth;

	private AudioContext ac;
	
	/**
	 * Constructor of this class.
	 */
	public BeadsManager()
	{
	}
	
	/**
	 * Plays a random piece of music, based on the information given by the parameters.
	 * 
	 * @param bpm	the beats-per-minute
	 */
	public void playChord(int bpm, Chord chord, Buffer buffer)
	{	
		ac = new AudioContext();
				
		tonic = new WavePlayer(ac, chord.getTonicFrequency(), buffer);
		third = new WavePlayer(ac, chord.getThirdFrequency(), buffer);
		fifth = new WavePlayer(ac, chord.getFifthFrequency(), buffer);
		
		Clock clock = new Clock(ac, bpmToMilliSec(bpm));
		clock.addMessageListener(
			new Bead()
			{
			    public void messageReceived(Bead message) 
			    {
			    	Clock c = (Clock) message;
			    	
			    	if (c.isBeat()) 
			    	{
			    		if (random(1) < 0.50) 
			    			return;
			    		
			    		Gain g = new Gain(ac, 1, new Envelope(ac, 0));
				        g.addInput(tonic);
				        g.addInput(third);
				        g.addInput(fifth);
				        ac.out.addInput(g);
				        ((Envelope)g.getGainUGen()).addSegment(0.1f, random(200));
				        ((Envelope)g.getGainUGen()).addSegment(0, random(7000), new KillTrigger(g));
			    	}
			    }
			}
		);
		
		ac.out.addDependent(clock);
		ac.start();
	}
	
	public void playChordProgression1251(int bpm, Chord key, Buffer buffer)
	{
		System.out.println("JAJAJ");
		ac = new AudioContext();
		
		// Chord I
		tonic = new WavePlayer(ac, key.getTonicFrequency(), buffer);
		third = new WavePlayer(ac, key.getThirdFrequency(), buffer);
		fifth = new WavePlayer(ac, key.getFifthFrequency(), buffer);
		
		Gain gI = new Gain(ac, 1, new Envelope(ac, 0));
		gI.addInput(tonic);
		gI.addInput(third);
		gI.addInput(fifth);
        ac.out.addInput(gI);
        ((Envelope)gI.getGainUGen()).addSegment(0.1f, bpmToMilliSec(bpm) * 4);
        ((Envelope)gI.getGainUGen()).addSegment(0, bpmToMilliSec(bpm) * 4, new KillTrigger(gI));
        
        ac.start();
        
        // Chord II
        Chord chordII = getChord(key, 2);
        System.out.println(chordII.toString());
        tonic = new WavePlayer(ac, chordII.getTonicFrequency(), buffer);
		third = new WavePlayer(ac, chordII.getThirdFrequency(), buffer);
		fifth = new WavePlayer(ac, chordII.getFifthFrequency(), buffer);
		
		Gain gII = new Gain(ac, 1, new Envelope(ac, 0));
		gII.addInput(tonic);
		gII.addInput(third);
		gII.addInput(fifth);
        ac.out.addInput(gII);
        ((Envelope)gII.getGainUGen()).addSegment(0.1f, bpmToMilliSec(bpm) * 8);
        ((Envelope)gII.getGainUGen()).addSegment(0, bpmToMilliSec(bpm) * 8, new KillTrigger(gII));
        
        ac.start();
        
        // Chord V
        Chord chordV = getChord(key, 5);
        System.out.println(chordV.toString());
        tonic = new WavePlayer(ac, chordV.getTonicFrequency(), buffer);
		third = new WavePlayer(ac, chordV.getThirdFrequency(), buffer);
		fifth = new WavePlayer(ac, chordV.getFifthFrequency(), buffer);
		
		Gain gV = new Gain(ac, 1, new Envelope(ac, 0));
		gV.addInput(tonic);
		gV.addInput(third);
		gV.addInput(fifth);
        ac.out.addInput(gV);
        ((Envelope)gV.getGainUGen()).addSegment(0.1f, bpmToMilliSec(bpm) * 16);
        ((Envelope)gV.getGainUGen()).addSegment(0, bpmToMilliSec(bpm) * 16, new KillTrigger(gV));
        
        ac.start();
        
        // Chord I
        tonic = new WavePlayer(ac, key.getTonicFrequency(), buffer);
		third = new WavePlayer(ac, key.getThirdFrequency(), buffer);
		fifth = new WavePlayer(ac, key.getFifthFrequency(), buffer);
		
		Gain gI_ = new Gain(ac, 1, new Envelope(ac, 0));
		gI_.addInput(tonic);
		gI_.addInput(third);
		gI_.addInput(fifth);
        ac.out.addInput(gI_);
        ((Envelope)gI_.getGainUGen()).addSegment(0.1f, bpmToMilliSec(bpm) * 32);
        ((Envelope)gI_.getGainUGen()).addSegment(0, bpmToMilliSec(bpm) * 32, new KillTrigger(gI_));
        
		ac.start();
	}
	
	/**
	 * Computes the number of milliseconds between beats, based on the BPM.
	 * @param bpm
	 * @return number of milliseconds
	 */
	public float bpmToMilliSec(int bpm)
	{
		return 60000 / bpm;
	}
	
	/**
	 * Generates a random number, in the range given by the parameter.
	 * 
	 * @param x
	 * @return random number
	 */
	public static float random(double x)
	{
		return (float)(Math.random() * x);
	}
	
	public Chord getChord(Chord key, int degree)
	{			
		Globals.ChordKey chordKey = null;
		Globals.ChordName chordName = null;
		Globals.Chord chord = null;
		
		switch (key.getChord())
		{
			case C_MAJOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case C_MINOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case C_DIMINISHED:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case C_SHARP_MAJOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case C_SHARP_MINOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case C_SHARP_DIMINISHED:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case D_MAJOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case D_MINOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case D_DIMINISHED:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case D_SHARP_MAJOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case D_SHARP_MINOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case D_SHARP_DIMINISHED:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case E_MAJOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case E_MINOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case E_DIMINISHED:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case F_MAJOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case F_MINOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case F_DIMINISHED:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case F_SHARP_MAJOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case F_SHARP_MINOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case F_SHARP_DIMINISHED:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case G_MAJOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case G_MINOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case G_DIMINISHED:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case G_SHARP_MAJOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case G_SHARP_MINOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case G_SHARP_DIMINISHED:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case A_MAJOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case A_MINOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case A_DIMINISHED:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case A_SHARP_MAJOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case A_SHARP_MINOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case A_SHARP_DIMINISHED:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case B_MAJOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case B_MINOR:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
			case B_DIMINISHED:
				chordKey = null;
				chordName = null;
				chord = null;
				break;
		}
		
		return new Chord(chordName, chordKey, 4, chord);
	}
	
	private Chord getDegree(Globals.ChordName chordName, Globals.ChordKey chordKey, int degree)
	{
		Globals.ChordName newChordName = null;
		switch (degree)
		{
			case 2:
				// The second degree is always a major step from the root for now, TODO: add minor second degree?
				newChordName = getFromList(Globals.degree2major, chordName);
				return new Chord(newChordName, Globals.ChordKey.MINOR, 4, getChordEnumElement(newChordName, Globals.ChordKey.MINOR));
			case 3:
				switch (chordKey)
				{
				case MAJOR:
					newChordName = getFromList(Globals.degree3major, chordName);
					return new Chord(newChordName, Globals.ChordKey.MINOR, 4, getChordEnumElement(newChordName, Globals.ChordKey.MINOR));
				case MINOR:
				case DIMINISHED:
					newChordName = getFromList(Globals.degree3minor, chordName);
					return new Chord(newChordName, Globals.ChordKey.MINOR, 4, getChordEnumElement(newChordName, Globals.ChordKey.MINOR));
				}
				break;
			case 4:
				// TODO: add minor fourth degree?
				newChordName = getFromList(Globals.degree4major, chordName);
				return new Chord(newChordName, Globals.ChordKey.MAJOR, 4, getChordEnumElement(newChordName, Globals.ChordKey.MAJOR));
			case 5:
				switch (chordKey)
				{
				case MAJOR:
				case MINOR:
					newChordName = getFromList(Globals.degree5major, chordName);
					return new Chord(newChordName, Globals.ChordKey.MAJOR, 4, getChordEnumElement(newChordName, Globals.ChordKey.MAJOR));
				case DIMINISHED:
					newChordName = getFromList(Globals.degree5minor, chordName);
					return new Chord(newChordName, Globals.ChordKey.MAJOR, 4, getChordEnumElement(newChordName, Globals.ChordKey.MAJOR));
				}
				break;
			case 6:
				// TODO: add minor sixth degree?
				newChordName = getFromList(Globals.degree6major, chordName);
				return new Chord(newChordName, Globals.ChordKey.MINOR, 4, getChordEnumElement(newChordName, Globals.ChordKey.MINOR));
			case 7:
				// TODO add major/minor seventh?
				newChordName = getFromList(Globals.degree7minor, chordName);
				return new Chord(newChordName, Globals.ChordKey.DIMINISHED, 4, getChordEnumElement(newChordName, Globals.ChordKey.DIMINISHED));
		}
		return null;
	}
	
	private Globals.ChordName getFromList(List<Pair> list, Globals.ChordName chordName)
	{
		for (Pair chordPair : list)
		{
			if (chordPair.getKey().equals(chordName))
				return chordPair.getDegree();
		}
		return null;	
	}
	
	private Globals.Chord getChordEnumElement(Globals.ChordName chordName, Globals.ChordKey chordKey)
	{
		switch (chordName)
		{
			case C:
				switch (chordKey)
				{
					case MAJOR:
						return Globals.Chord.C_MAJOR;
					case MINOR:
						return Globals.Chord.C_MINOR;
					case DIMINISHED:
						return Globals.Chord.C_DIMINISHED;
				}
				break;
			case C_SHARP:
				switch (chordKey)
				{
					case MAJOR:
						return Globals.Chord.C_SHARP_MAJOR;
					case MINOR:
						return Globals.Chord.C_SHARP_MINOR;
					case DIMINISHED:
						return Globals.Chord.C_SHARP_DIMINISHED;
				}
				break;
			case D:
				switch (chordKey)
				{
					case MAJOR:
						return Globals.Chord.D_MAJOR;
					case MINOR:
						return Globals.Chord.D_MINOR;
					case DIMINISHED:
						return Globals.Chord.D_DIMINISHED;
				}
				break;
			case D_SHARP:
				switch (chordKey)
				{
					case MAJOR:
						return Globals.Chord.D_SHARP_MAJOR;
					case MINOR:
						return Globals.Chord.D_SHARP_MINOR;
					case DIMINISHED:
						return Globals.Chord.D_SHARP_DIMINISHED;
				}
				break;
			case E:
				switch (chordKey)
				{
					case MAJOR:
						return Globals.Chord.E_MAJOR;
					case MINOR:
						return Globals.Chord.E_MINOR;
					case DIMINISHED:
						return Globals.Chord.E_DIMINISHED;
				}
				break;
			case F:
				switch (chordKey)
				{
					case MAJOR:
						return Globals.Chord.F_MAJOR;
					case MINOR:
						return Globals.Chord.F_MINOR;
					case DIMINISHED:
						return Globals.Chord.F_DIMINISHED;
				}
				break;
			case F_SHARP:
				switch (chordKey)
				{
					case MAJOR:
						return Globals.Chord.F_SHARP_MAJOR;
					case MINOR:
						return Globals.Chord.F_SHARP_MINOR;
					case DIMINISHED:
						return Globals.Chord.F_SHARP_DIMINISHED;
				}
				break;
			case G:
				switch (chordKey)
				{
					case MAJOR:
						return Globals.Chord.G_MAJOR;
					case MINOR:
						return Globals.Chord.G_MINOR;
					case DIMINISHED:
						return Globals.Chord.G_DIMINISHED;
				}
				break;
			case G_SHARP:
				switch (chordKey)
				{
					case MAJOR:
						return Globals.Chord.G_SHARP_MAJOR;
					case MINOR:
						return Globals.Chord.G_SHARP_MINOR;
					case DIMINISHED:
						return Globals.Chord.G_SHARP_DIMINISHED;
				}
				break;
			case A:
				switch (chordKey)
				{
					case MAJOR:
						return Globals.Chord.A_MAJOR;
					case MINOR:
						return Globals.Chord.A_MINOR;
					case DIMINISHED:
						return Globals.Chord.A_DIMINISHED;
				}
				break;
			case A_SHARP:
				switch (chordKey)
				{
					case MAJOR:
						return Globals.Chord.A_SHARP_MAJOR;
					case MINOR:
						return Globals.Chord.A_SHARP_MINOR;
					case DIMINISHED:
						return Globals.Chord.A_SHARP_DIMINISHED;
				}
				break;
			case B:
				switch (chordKey)
				{
					case MAJOR:
						return Globals.Chord.B_MAJOR;
					case MINOR:
						return Globals.Chord.B_MINOR;
					case DIMINISHED:
						return Globals.Chord.B_DIMINISHED;
				}
				break;
		}
		return null;
	}
}
