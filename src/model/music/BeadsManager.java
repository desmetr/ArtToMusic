package model.music;

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
	public void playChord(int bpm, Globals.Chords chord)
	{	
		final AudioContext ac;
		ac = new AudioContext();
		
		WavePlayer tonic; 
		WavePlayer third;
		WavePlayer fifth;
		
		switch (chord)
		{
		case C_MAJOR:
			tonic = new WavePlayer(ac, (float) 523.25, Buffer.SINE);
			tonic = new WavePlayer(ac, (float) 523.25, Buffer.SINE);
			tonic = new WavePlayer(ac, (float) 523.25, Buffer.SINE);
			break;
		case C_SHARP_MAJOR:
			break;
		case D_MAJOR:
			break;
		case D_SHARP_MAJOR:
			break;
		case E_MAJOR:
			break;
		case F_MAJOR:
			break;
		case F_SHARP_MAJOR:
			break;
		case G_MAJOR:
			break;
		case G_SHARP_MAJOR:
			break;
		case A_MAJOR:
			break;
		case A_SHARP_MAJOR:
			break;
		case B_MAJOR:
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
			    		
			    		pitch = Pitch.forceToScale((int) random(12), Pitch.mixolydian);
			    		
			    		float freq = Pitch.mtof(pitch + (int) random(5) * 12 + 32);
			    		
			    		ArtToMusicLogger.getInstance().info(String.valueOf(pitch));
			    		ArtToMusicLogger.getInstance().info(String.valueOf(freq));
			    		ArtToMusicLogger.getInstance().info("-");
			    		
			    		WavePlayer wp1 = new WavePlayer(ac, (float) 523.25, Buffer.SINE);
			    		WavePlayer wp2 = new WavePlayer(ac, (float) 783.99, Buffer.SINE);
			    		Gain g = new Gain(ac, 1, new Envelope(ac, 0));
				        g.addInput(wp1);
				        g.addInput(wp2);
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
