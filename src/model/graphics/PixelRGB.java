package model.graphics;

/**
 * Created by rafael on 15.10.16.
 */
public class PixelRGB
{
    private int red;
    private int green;
    private int blue;

    public PixelRGB(double rValues, double gValues, double bValues)
    {
        this.red = (int) rValues;
        this.green = (int) gValues;
        this.blue = (int) bValues;
    }

    public int getRed()     {   return red;     }
    public int getGreen()   {   return green;   }
    public int getBlue()    {   return blue;    }

    public void setRed(int red)         {   this.red = red;     }
    public void setGreen(int green)     {   this.green = green; }
    public void setBlue(int blue)       {   this.blue = blue;   }

    public void print()
    {
        System.out.println(red + ", " + green + ", " + blue);
    }
    
    public String toString()
    {
    	return "(" + String.valueOf(red) + ", " + String.valueOf(green) + ", " + String.valueOf(blue) + ")";
    }
}
