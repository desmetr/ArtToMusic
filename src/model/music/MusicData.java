package model.music;

import utilities.Globals;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static utilities.Globals.NoteLength.*;

/**
 * Created by rafael on 22.10.16.
 */
public class MusicData
{
    public static Map<Globals.NoteLength, Double> getNoteOffsets()
    {
        Map<Globals.NoteLength, Double> noteOffsets = new HashMap<Globals.NoteLength, Double>();

        noteOffsets.put(WHOLE, 4.0);
        noteOffsets.put(HALF, 2.0);
        noteOffsets.put(QUARTER, 1.0);
        noteOffsets.put(QUARTER_DOTTED, 1.5);
        noteOffsets.put(EIGHTH, 0.5);
        noteOffsets.put(SIXTEENTH, 0.25);
        noteOffsets.put(THIRTY_SECOND, 0.125);

        return noteOffsets;
    }

    public static Vector<Note> generateFourBars()
    {
        // TODO: data read from file
        Vector<Note> notes = new Vector<Note>();

        notes.add(new Note(QUARTER, 60));
        notes.add(new Note(EIGHTH, 62));
        notes.add(new Note(EIGHTH, 62));
        notes.add(new Note(QUARTER, 64));
        notes.add(new Note(EIGHTH, 64));

        notes.add(new Note(QUARTER_DOTTED, 62));
        notes.add(new Note(EIGHTH, 62));
        notes.add(new Note(SIXTEENTH, 64));
        notes.add(new Note(SIXTEENTH, 65));
        notes.add(new Note(SIXTEENTH, 66));
        notes.add(new Note(SIXTEENTH, 67));
        notes.add(new Note(EIGHTH, 68));
        notes.add(new Note(EIGHTH, 68));

        notes.add(new Note(HALF, 60));
        notes.add(new Note(HALF, 60));

        notes.add(new Note(SIXTEENTH, 68));
        notes.add(new Note(EIGHTH, 65));
        notes.add(new Note(SIXTEENTH, 68));
        notes.add(new Note(SIXTEENTH, 68));
        notes.add(new Note(EIGHTH, 65));
        notes.add(new Note(SIXTEENTH, 68));
        notes.add(new Note(EIGHTH, 65));
        notes.add(new Note(EIGHTH, 65));
        notes.add(new Note(QUARTER, 60));

        return notes;
    }
}
