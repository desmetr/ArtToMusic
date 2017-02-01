package model.music;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import controller.Player;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.Bead;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.events.KillTrigger;
import net.beadsproject.beads.ugens.Clock;
import net.beadsproject.beads.ugens.Envelope;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.WavePlayer;
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
	private Gain masterGain;
	
	private Player player;
	
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
	// TODO magweg
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
		List<Chord> chords = new ArrayList<Chord>();
		chords.add(getChord(key, 1));
		chords.add(getChord(key, 2));
		chords.add(getChord(key, 5));
		chords.add(getChord(key, 1));
		
		player = new Player(chords, bpm);
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(player);
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
	
	public Chord getChord(Chord chord, int degree)
	{			
		System.out.println("getChord");
		switch (chord.getChord())
		{
			case C_MAJOR:
				return getNextChord(Globals.ChordKey.C, Globals.ChordType.MAJOR, degree);
			case C_MINOR:
				return getNextChord(Globals.ChordKey.C, Globals.ChordType.MINOR, degree);
			case C_DIMINISHED:
				return getNextChord(Globals.ChordKey.C, Globals.ChordType.DIMINISHED, degree);
			case C_SHARP_MAJOR:
				return getNextChord(Globals.ChordKey.C_SHARP, Globals.ChordType.MAJOR, degree);
			case C_SHARP_MINOR:
				return getNextChord(Globals.ChordKey.C_SHARP, Globals.ChordType.MINOR, degree);
			case C_SHARP_DIMINISHED:
				return getNextChord(Globals.ChordKey.C_SHARP, Globals.ChordType.DIMINISHED, degree);
			case D_MAJOR:
				return getNextChord(Globals.ChordKey.D, Globals.ChordType.MAJOR, degree);
			case D_MINOR:
				return getNextChord(Globals.ChordKey.D, Globals.ChordType.MINOR, degree);
			case D_DIMINISHED:
				return getNextChord(Globals.ChordKey.D, Globals.ChordType.DIMINISHED, degree);
			case D_SHARP_MAJOR:
				return getNextChord(Globals.ChordKey.D_SHARP, Globals.ChordType.MAJOR, degree);
			case D_SHARP_MINOR:
				return getNextChord(Globals.ChordKey.D_SHARP, Globals.ChordType.MINOR, degree);
			case D_SHARP_DIMINISHED:
				return getNextChord(Globals.ChordKey.D_SHARP, Globals.ChordType.DIMINISHED, degree);
			case E_MAJOR:
				return getNextChord(Globals.ChordKey.E, Globals.ChordType.MAJOR, degree);
			case E_MINOR:
				return getNextChord(Globals.ChordKey.E, Globals.ChordType.MINOR, degree);
			case E_DIMINISHED:
				return getNextChord(Globals.ChordKey.E, Globals.ChordType.DIMINISHED, degree);
			case F_MAJOR:
				return getNextChord(Globals.ChordKey.F, Globals.ChordType.MAJOR, degree);
			case F_MINOR:
				return getNextChord(Globals.ChordKey.F, Globals.ChordType.MINOR, degree);
			case F_DIMINISHED:
				return getNextChord(Globals.ChordKey.F, Globals.ChordType.DIMINISHED, degree);
			case F_SHARP_MAJOR:
				return getNextChord(Globals.ChordKey.F_SHARP, Globals.ChordType.MAJOR, degree);
			case F_SHARP_MINOR:
				return getNextChord(Globals.ChordKey.F_SHARP, Globals.ChordType.MINOR, degree);
			case F_SHARP_DIMINISHED:
				return getNextChord(Globals.ChordKey.F_SHARP, Globals.ChordType.DIMINISHED, degree);
			case G_MAJOR:
				return getNextChord(Globals.ChordKey.G, Globals.ChordType.MAJOR, degree);
			case G_MINOR:
				return getNextChord(Globals.ChordKey.G, Globals.ChordType.MINOR, degree);
			case G_DIMINISHED:
				return getNextChord(Globals.ChordKey.G, Globals.ChordType.DIMINISHED, degree);
			case G_SHARP_MAJOR:
				return getNextChord(Globals.ChordKey.G_SHARP, Globals.ChordType.MAJOR, degree);
			case G_SHARP_MINOR:
				return getNextChord(Globals.ChordKey.G_SHARP, Globals.ChordType.MINOR, degree);
			case G_SHARP_DIMINISHED:
				return getNextChord(Globals.ChordKey.G_SHARP, Globals.ChordType.DIMINISHED, degree);
			case A_MAJOR:
				return getNextChord(Globals.ChordKey.A, Globals.ChordType.MAJOR, degree);
			case A_MINOR:
				return getNextChord(Globals.ChordKey.A, Globals.ChordType.MINOR, degree);
			case A_DIMINISHED:
				return getNextChord(Globals.ChordKey.A, Globals.ChordType.DIMINISHED, degree);
			case A_SHARP_MAJOR:
				return getNextChord(Globals.ChordKey.A_SHARP, Globals.ChordType.MAJOR, degree);
			case A_SHARP_MINOR:
				return getNextChord(Globals.ChordKey.A_SHARP, Globals.ChordType.MINOR, degree);
			case A_SHARP_DIMINISHED:
				return getNextChord(Globals.ChordKey.A_SHARP, Globals.ChordType.DIMINISHED, degree);
			case B_MAJOR:
				return getNextChord(Globals.ChordKey.B, Globals.ChordType.MAJOR, degree);
			case B_MINOR:
				return getNextChord(Globals.ChordKey.B, Globals.ChordType.MINOR, degree);
			case B_DIMINISHED:
				return getNextChord(Globals.ChordKey.B, Globals.ChordType.DIMINISHED, degree);
		}
		
		return null;
	}
	
	private Chord getNextChord(Globals.ChordKey chordKey, Globals.ChordType chordType, int degree)
	{
		Globals.ChordKey newChordKey = null;
		switch (degree)
		{
			case 1:
				return new Chord(chordKey, chordType, 4, getChordEnumElement(chordKey, chordType));
			case 2:
				// The second degree is always a major step from the root for now, TODO: add minor second degree?
				newChordKey = getFromList(Globals.degrees.get("degree2major"), chordKey);
				return new Chord(newChordKey, Globals.ChordType.MINOR, 4, getChordEnumElement(newChordKey, Globals.ChordType.MINOR));
			case 3:
				switch (chordType)
				{
				case MAJOR:
					newChordKey = getFromList(Globals.degrees.get("degree3major"), chordKey);
					return new Chord(newChordKey, Globals.ChordType.MINOR, 4, getChordEnumElement(newChordKey, Globals.ChordType.MINOR));
				case MINOR:
				case DIMINISHED:
					newChordKey = getFromList(Globals.degrees.get("degree3minor"), chordKey);
					return new Chord(newChordKey, Globals.ChordType.MINOR, 4, getChordEnumElement(newChordKey, Globals.ChordType.MINOR));
				}
				break;
			case 4:
				// TODO: add minor fourth degree?
				newChordKey = getFromList(Globals.degrees.get("degree4major"), chordKey);
				return new Chord(newChordKey, Globals.ChordType.MAJOR, 4, getChordEnumElement(newChordKey, Globals.ChordType.MAJOR));
			case 5:
				switch (chordType)
				{
				case MAJOR:
				case MINOR:
					newChordKey = getFromList(Globals.degrees.get("degree5major"), chordKey);
					return new Chord(newChordKey, Globals.ChordType.MAJOR, 4, getChordEnumElement(newChordKey, Globals.ChordType.MAJOR));
				case DIMINISHED:
					newChordKey = getFromList(Globals.degrees.get("degree5minor"), chordKey);
					return new Chord(newChordKey, Globals.ChordType.MAJOR, 4, getChordEnumElement(newChordKey, Globals.ChordType.MAJOR));
				}
				break;
			case 6:
				// TODO: add minor sixth degree?
				newChordKey = getFromList(Globals.degrees.get("degree6major"), chordKey);
				return new Chord(newChordKey, Globals.ChordType.MINOR, 4, getChordEnumElement(newChordKey, Globals.ChordType.MINOR));
			case 7:
				// TODO add major/minor seventh?
				newChordKey = getFromList(Globals.degrees.get("degree7major"), chordKey);
				return new Chord(newChordKey, Globals.ChordType.DIMINISHED, 4, getChordEnumElement(newChordKey, Globals.ChordType.DIMINISHED));
		}
		return null;
	}
	
	private Globals.ChordKey getFromList(List<Pair> list, Globals.ChordKey chordKey)
	{
		for (Pair chordPair : list)
		{
			if (chordPair.getKey().equals(chordKey))
				return chordPair.getDegree();
		}
		return null;	
	}
	
	// TODO: hashmap<Globals.ChordName, Pair<Globals.ChordKey, Globals.Chord>> zo moeten we maar 1 switch gebruiken
	private Globals.Chord getChordEnumElement(Globals.ChordKey chordKey, Globals.ChordType chordType)
	{
		switch (chordKey)
		{
			case C:
				switch (chordType)
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
				switch (chordType)
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
				switch (chordType)
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
				switch (chordType)
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
				switch (chordType)
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
				switch (chordType)
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
				switch (chordType)
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
				switch (chordType)
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
				switch (chordType)
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
				switch (chordType)
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
				switch (chordType)
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
				switch (chordType)
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
