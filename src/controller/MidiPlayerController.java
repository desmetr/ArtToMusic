package controller;

import java.util.Vector;

import javax.sound.midi.MidiUnavailableException;

import controller.music.MidiPlayer;
import model.music.MusicData;
import model.music.Note;

/**
 * Created by rafael on 22.10.16.
 */
public class MidiPlayerController
{
    public static void playNotes() throws MidiUnavailableException, InterruptedException
    {
        Vector<Note> notes = MusicData.generateFourBars();

        MidiPlayer midiPlayer = new MidiPlayer();
        midiPlayer.playNotes(120, notes);
    }

    public MidiPlayerController()
    {
//        MidiPlayerView.start(MidiPlayerView.class);
    }
}
