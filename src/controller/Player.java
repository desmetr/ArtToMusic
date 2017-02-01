package controller;

import java.util.List;

import javafx.concurrent.Task;
import model.music.Chord;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.events.KillTrigger;
import net.beadsproject.beads.ugens.Envelope;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.WavePlayer;

public class Player extends Task<Object> 
{
	private List<Chord> chords;
	
	private WavePlayer tonic;
	private WavePlayer third;
	private WavePlayer fifth;
	private AudioContext ac;
	private Gain masterGain;
	private Envelope envelope;
	
	private float bpmInMilliSec;
	
	public Player(List<Chord> chords, int bpm) 
	{
		super();
		this.chords = chords;
		this.bpmInMilliSec = 60000 / bpm;
		
		ac = new AudioContext();
	}

	@Override
	protected Object call() throws Exception
	{		
		for (Chord chord : chords)
		{
			System.out.println("new chord play");
			tonic = new WavePlayer(ac, chord.getTonicFrequency(), Buffer.SINE);
			third = new WavePlayer(ac, chord.getThirdFrequency(), Buffer.SINE);
			fifth = new WavePlayer(ac, chord.getFifthFrequency(), Buffer.SINE);
			
			envelope = new Envelope(ac, (float) 0.0);
			masterGain = new Gain(ac, 1, envelope);
			
			masterGain.addInput(tonic);
			masterGain.addInput(third);
			masterGain.addInput(fifth);
	        
			ac.out.addInput(masterGain);
	        
	        envelope.addSegment(0.5f, bpmInMilliSec);
//	        envelope.addSegment(0.4f, 10 + bpmInMilliSec);
//	        envelope.addSegment(0.0f, 10 + bpmInMilliSec);
	        
	        envelope.addSegment(0, bpmInMilliSec, new KillTrigger(masterGain));
	        
	        ac.start();
	        System.out.println(bpmInMilliSec);
	        Thread.sleep((long) bpmInMilliSec);
		}
		return null;
	}
}
