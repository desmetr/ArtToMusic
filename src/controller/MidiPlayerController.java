package controller;

import controller.music.MidiPlayer;
import model.music.MusicData;
import model.music.Note;
import view.MidiPlayerView;

import javax.sound.midi.MidiUnavailableException;
import java.util.Vector;

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
        MidiPlayerView.launch(MidiPlayerView.class);
    }
}
