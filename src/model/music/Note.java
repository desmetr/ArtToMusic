package model.music;

import utilities.Globals;

/**
 * Class that contains all information of a Note.
 * 
 * @author rafael
 * @version 1
 * @since 2016.10.21.
 */
public class Note
{
    private Globals.NoteLength length;
    private int pitch;
    private double offset;

    /**
     * Constructor of this class.
     * 
     * @param length
     * @param pitch
     * @param offset
     */
    public Note(Globals.NoteLength length, int pitch, double offset)
    {
        this.length = length;
        this.pitch = pitch;
        this.offset = offset;
    }

    /** 
     * Getter for the length attribute.
     * 
     * @return length
     */
    public Globals.NoteLength getLength()   {   return length;    }
    
    /**
     * Getter for the pitch attribute.
     * 
     * @return pitch
     */
    public int getPitch()                   {   return pitch;     }
    
    /**
     * Getter for the offset attribute.
     * 
     * @return offset
     */
    public double getOffset()               {   return offset;    }
    
    /**
     * Print method to display all info.
     */
    public void print()
    {
    	System.out.println(length + "\t" + pitch + "\t" + offset);
    }
}
