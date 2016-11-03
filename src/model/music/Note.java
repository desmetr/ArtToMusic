package model.music;

import utilities.Globals;

/**
 * Created by rafael on 21.10.16.
 */
public class Note
{
    private Globals.NoteLength length;
    private int pitch;
    private double offset;

    public Note(Globals.NoteLength length, int pitch, double offset)
    {
        this.length = length;
        this.pitch = pitch;
        this.offset = offset;
    }

    public Globals.NoteLength getLength()   {   return length;    }
    public int getPitch()                   {   return pitch;     }
    public double getOffset()               {   return offset;    }
}