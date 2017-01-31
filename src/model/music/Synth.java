package model.music;

import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.events.KillTrigger;
import net.beadsproject.beads.ugens.Envelope;
import net.beadsproject.beads.ugens.Function;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.LPRezFilter;
import net.beadsproject.beads.ugens.Static;
import net.beadsproject.beads.ugens.WavePlayer;

public class Synth 
{
	public WavePlayer carrier = null;
	public WavePlayer modulator = null;
	public Envelope e = null;
	public Gain g = null;
	  
	public int pitch = -1;
	public boolean alive = true;
	  
	// our filter and filter envelope
	LPRezFilter lowPassFilter;
	WavePlayer filterLFO;
	  
	Synth(int midiPitch, AudioContext ac, Gain masterGain)
	{
		// get the midi pitch and create a couple holders for the midi pitch
	    pitch = midiPitch;
	    
	    float fundamentalFrequency = (float) (440.0 * Math.pow(2, ((float) midiPitch - 59.0) / 12.0));
	    
	    Static ff = new Static(ac, fundamentalFrequency);
	    
	    // instantiate the modulator WavePlayer
	    modulator = new WavePlayer(ac, (float) (0.5 * fundamentalFrequency), Buffer.SINE);
	    
	    // create our frequency modulation function
	    Function frequencyModulation = new Function(modulator, ff)
	    {
	    	public float calculate() 
	    	{
	    		// the x[1] here is the value of a sine wave oscillating at the fundamental frequency
	    		return (float) ((x[0] * 1000.0) + x[1]);
	    	}
	    };
	    
	    // instantiate the carrier WavePlayer
	    carrier = new WavePlayer(ac, frequencyModulation, Buffer.SINE);
	    
	    // set up the filter and LFO (randomized LFO frequency)
	    filterLFO = new WavePlayer(ac, (float) (1.0 + random(100)), Buffer.SINE);
	    Function filterCutoff = new Function(filterLFO)
	    {
	    	public float calculate() 
	    	{
	    		// set the filter cutoff to oscillate between 1500Hz and 2500Hz
	    		return (float) ((x[0] * 500.0) + 2000.0);
	    	}
	    };
	    
	    lowPassFilter = new LPRezFilter(ac, filterCutoff, (float) 0.96);
	    lowPassFilter.addInput(carrier);
	    
	    // set up and connect the gains
	    e = new Envelope(ac, (float) 0.0);
	    g = new Gain(ac, 1, e);
	    g.addInput(lowPassFilter);
	    masterGain.addInput(g);
	    
	    // create a randomized Gain envelope for this note
	    e.addSegment((float) 0.5, 10 + (int)random(500));
	    e.addSegment((float) 0.4, 10 + (int)random(500));
	    e.addSegment((float) 0.0, 10 + (int)random(500),
	    
	    new KillTrigger(g));
	}
	  
	public void destroyMe()
	{
		carrier.kill();
	    modulator.kill();
	    lowPassFilter.kill();
	    filterLFO.kill();
	    e.kill();
	    g.kill();
	    
	    carrier = null;
	    modulator = null;
	    lowPassFilter = null;
	    filterLFO = null;
	    e = null; 
	    g = null;
	    alive = false;
	}
	
	private float random(double x)
	{
		return (float)(Math.random() * x);
	}
}