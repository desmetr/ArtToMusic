package controller;

import java.util.List;

import javafx.concurrent.Task;
import model.music.Chord;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.data.Pitch;
import net.beadsproject.beads.events.KillTrigger;
import net.beadsproject.beads.ugens.Envelope;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.Panner;
import net.beadsproject.beads.ugens.WavePlayer;

public class Player extends Task<Object> 
{
	private List<Chord> chords;
	
	private WavePlayer tonic;
	private WavePlayer third;
	private WavePlayer fifth;
	private WavePlayer bassPlayer;
	
	private AudioContext ac;
	private Gain masterGain;
	private Envelope envelope;
	
	private float bpmInMilliSec;
	private boolean bass;
	
	public Player(List<Chord> chords, int bpm, boolean bass) 
	{
		super();
		this.chords = chords;
		this.bpmInMilliSec = 60000 / bpm;
		this.bass = bass;
		
		ac = new AudioContext();
	}
	
	@Override
	protected Object call() throws Exception
	{		
		play();
		return null;
	}
	
	private void play()
	{
		for (Chord chord : chords)
		{
			System.out.println("new chord " + chord.toString());
			tonic = new WavePlayer(ac, chord.getTonicFrequency(), Buffer.SINE);
			third = new WavePlayer(ac, chord.getThirdFrequency(), Buffer.SINE);
			fifth = new WavePlayer(ac, chord.getFifthFrequency(), Buffer.SINE);
			
			envelope = new Envelope(ac, (float) 0.0);
			masterGain = new Gain(ac, 1, envelope);
			
			masterGain.addInput(tonic);
			masterGain.addInput(third);
			masterGain.addInput(fifth);
	        
			ac.out.addInput(masterGain);
	        
	        envelope.addSegment(0, bpmInMilliSec);
//	        envelope.addSegment(0.1f, bpmInMilliSec);
	        envelope.addSegment(1, bpmInMilliSec, new KillTrigger(masterGain));
	        
	        if (bass)
	        {
				bassPlayer = new WavePlayer(ac, chord.getBassFrequency(), Buffer.SINE);
				masterGain.addInput(bassPlayer);
				Panner p = new Panner(ac, random(1));
				p.addInput(masterGain);
				ac.out.addInput(p);
				
				((Envelope)masterGain.getGainUGen()).addSegment(random(0.1), random(50));
				((Envelope)masterGain.getGainUGen()).addSegment(0, random(400), new KillTrigger(p));
	        }
	        
	        ac.start();
	        
	        try 
	        {
				Thread.sleep((long) bpmInMilliSec);
			} 
	        catch (InterruptedException e) {	e.printStackTrace();	}
	        
	        ac.reset();
		}
	}
	
	private static float random(double x) 
	{
		return (float)(Math.random() * x);
	}
}
