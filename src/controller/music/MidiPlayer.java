package controller.music;

import model.music.Note;
import utilities.ArtToMusicLogger;

import javax.sound.midi.*;
import java.util.Vector;

/**
 * Created by rafael on 12.10.16.
 */
public class MidiPlayer
{
    private Synthesizer synth = MidiSystem.getSynthesizer();
    final MidiChannel[] midiChannels = synth.getChannels();
    Instrument[] instruments = synth.getDefaultSoundbank().getInstruments();

    public MidiPlayer() throws MidiUnavailableException
    {
        synth.open();
        synth.loadInstrument(instruments[80]);
        midiChannels[0].programChange(0, 80);
    }

    public void playNotes(int bpm, Vector<Note> notes) throws InterruptedException
    {
    	ArtToMusicLogger.getInstance().info("Playing notes.");
    	
        for (Note note : notes)
        {
            double time = (note.getOffset() * (60 /(double) bpm));

            midiChannels[0].noteOn(note.getPitch(), 600);
            Thread.sleep((long) (time * 1000));
            midiChannels[0].noteOff(note.getPitch());
        }
    }
}
