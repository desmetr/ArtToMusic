package controller.music;

import model.music.Note;
import utilities.ArtToMusicLogger;

import javax.sound.midi.*;
import java.util.Vector;

// TODO mag weg?

/**
 * Plays music via the Java midi player.
 * 
 * @author rafael
 * @version 1
 * @since 2016.10.12.
 */
public class MidiPlayer
{
    private Synthesizer synth = MidiSystem.getSynthesizer();
    final MidiChannel[] midiChannels = synth.getChannels();
    Instrument[] instruments = synth.getDefaultSoundbank().getInstruments();
    
    private int currentChannel = 0;

    public MidiPlayer() throws MidiUnavailableException
    {
        synth.open();
        synth.loadInstrument(instruments[0]);
        midiChannels[currentChannel].programChange(0, 0);
    }
    
    public void setMidiChannel(int channel)
    {
    	currentChannel = channel;
    	synth.loadInstrument(instruments[0]);
        midiChannels[currentChannel].programChange(0, 0);
    }
    
    public void chooseDrums()
    {
    	currentChannel = 9;
    	synth.loadInstrument(instruments[35]);
        midiChannels[currentChannel].programChange(0, 35);
    }

    public void playNotes(int bpm, Vector<Note> notes) throws InterruptedException
    {
    	ArtToMusicLogger.getInstance().info("Playing notes.");
    	
        for (Note note : notes)
        {
            double time = (note.getOffset() * (60 /(double) bpm));

            midiChannels[currentChannel].noteOn(note.getPitch(), 600);
            Thread.sleep((long) (time * 1000));
            midiChannels[currentChannel].noteOff(note.getPitch());
        }
    }
}
