package model.music;

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
}
