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
	public void play(int bpm)
	{	
		AudioContext ac = new AudioContext();
		Clock clock = new Clock(ac, Globals.getInstance().bpmToMilliSec.get(bpm));
		
		clock.addMessageListener
		(
			// this is the on-the-fly bead
			new Bead() 
			{
				// this is the method that we override to make the Bead do something
			    int pitch;
			    public void messageReceived(Bead message) 
			    {
			    	Clock c = (Clock)message;
			    	
			    	if (c.isBeat()) 
			    	{
			          //choose some nice frequencies
			          if (random(1) < 0.50) 
			        	  return;
			          
			          pitch = Pitch.forceToScale((int)random(12), Pitch.mixolydian);
			          
			          float freq = Pitch.mtof(pitch + (int)random(5) * 12 + 32);
			          
			          WavePlayer wp = new WavePlayer(ac, freq, Buffer.SINE);
			          Gain g = new Gain(ac, 1, new Envelope(ac, 0));
			          
			          g.addInput(wp);
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
