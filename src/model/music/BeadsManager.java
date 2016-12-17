package model.music;

import java.util.Map.Entry;

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
	public void playChord(int bpm, Globals.Chords chord, Buffer buffer)
	{	
		ac = new AudioContext();
		
		switch (chord)
		{
			case C_MAJOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("C4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("E4"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("G4"), buffer);
				break;
				
			case C_SHARP_MAJOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("C#4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("F4"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("G#4"), buffer);
				break;
	
			case D_MAJOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("D4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("F#4"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("A4"), buffer);
				break;
				
			case D_SHARP_MAJOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("D#4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("G4"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("A#4"), buffer);
				break;
				
			case E_MAJOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("E4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("G#4"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("B4"), buffer);
				break;
				
			case F_MAJOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("F4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("A4"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("C5"), buffer);
				break;
				
			case F_SHARP_MAJOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("F#4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("A#4"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("C#5"), buffer);
				break;
				
			case G_MAJOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("G4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("B4"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("D5"), buffer);
				break;
				
			case G_SHARP_MAJOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("G#4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("C5"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("D#5"), buffer);
				break;
				
			case A_MAJOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("A4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("C#5"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("E5"), buffer);
				break;
				
			case A_SHARP_MAJOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("A#4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("D5"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("F5"), buffer);
				break;
				
			case B_MAJOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("B4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("D#5"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("F#5"), buffer);
				break;
				
			case C_MINOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("C4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("D#4"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("G4"), buffer);
				break;
				
			case C_SHARP_MINOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("C#4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("E4"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("G#4"), buffer);
				break;
				
			case D_MINOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("D4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("F4"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("A4"), buffer);
				break;
				
			case D_SHARP_MINOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("D#4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("F#4"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("A#4"), buffer);
				break;
				
			case E_MINOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("E4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("G4"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("B4"), buffer);
				break;
				
			case F_MINOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("F4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("G#4"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("C5"), buffer);
				break;
				
			case F_SHARP_MINOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("F#4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("A4"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("C#5"), buffer);
				break;
				
			case G_MINOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("G4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("A#4"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("D5"), buffer);
				break;
				
			case G_SHARP_MINOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("G#4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("B4"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("D#5"), buffer);
				break;
				
			case A_MINOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("A4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("C5"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("E5"), buffer);
				break;
				
			case A_SHARP_MINOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("A#4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("C#5"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("F5"), buffer);
				break;
				
			case B_MINOR:
				tonic = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("B4"), buffer);
				third = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("D5"), buffer);
				fifth = new WavePlayer(ac, Globals.getInstance().noteFrequencies.get("F#5"), buffer);
				break;
				
			default:
				break;
		}
		
		Clock clock = new Clock(ac, Globals.getInstance().bpmToMilliSec.get(bpm));
		clock.addMessageListener(
			//this is the on-the-fly bead
			new Bead()
			{
				//this is the method that we override to make the Bead do something
			    int pitch;
			    public void messageReceived(Bead message) 
			    {
			    	Clock c = (Clock) message;
			    	
			    	if (c.isBeat()) 
			    	{
			    		//choose some nice frequencies
			    		if (random(1) < 0.50) 
			    			return;
			    		
//			    		pitch = Pitch.forceToScale((int) random(12), Pitch.mixolydian);
//			    		float freq = Pitch.mtof(pitch + (int) random(5) * 12 + 32);
//			    		WavePlayer wp1 = new WavePlayer(ac, (float) 523.25, buffer);
//			    		WavePlayer wp2 = new WavePlayer(ac, (float) 783.99, buffer);
			    		
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
}
