package model.graphics;

import utilities.Globals;

public class Pixel
{
    private int red;
    private int green;
    private int blue;
    
    private String hex;
    
    private double hue;
    private double saturation;
    private double value;
    
    private double cyan;
    private double magenta;
    private double yellow;
    private double key;

	public Pixel(double rValues, double gValues, double bValues)
    {
        red = (int) rValues;
        green = (int) gValues;
        blue = (int) bValues;
        
    	rgb2hex();
    	rgb2hsv();
    	rgb2cmyk();
    }

    public int getRed()     				{   return red;    		}
    public int getGreen()   				{   return green;  	 	}
    public int getBlue()    				{   return blue;   	 	}
    public String getHexRepresentation() 	{	return hex;			}
    public double getHue() 					{	return hue;			}
	public double getSaturation() 			{	return saturation;	}
	public double getValue() 				{	return value;		}
	public double getCyan() 				{	return cyan;		}
	public double getMagenta() 				{	return magenta;		}
	public double getYellow() 				{	return yellow;		}
	public double getKey() 					{	return key;			}

	public void print()
    {
        System.out.println(red + ", " + green + ", " + blue);
    }
   
    public String toString()
    {
    	return "RGB(" + Globals.decimalFormat.format(red) + ", " + Globals.decimalFormat.format(green) + ", " + Globals.decimalFormat.format(blue) + ") - "
    			+ "HSV(" + Globals.decimalFormat.format(hue) + ", " + Globals.decimalFormat.format(saturation) + ", " + Globals.decimalFormat.format(value) + ")) - "
    			+ "CMYK(" + Globals.decimalFormat.format(cyan) + ", " + Globals.decimalFormat.format(magenta) + ", " + Globals.decimalFormat.format(yellow) + ", " + Globals.decimalFormat.format(key) + ")\n";
    }
    
    public boolean equals(Pixel other) 
    {
    	if ((this.red == other.getRed()) && (this.green == other.getGreen()) && (this.blue == other.getBlue()))
    		return true;
    	else
    		return false;
    }
    
    private void rgb2hex()
    {
    	String hexR = Integer.toHexString(red);
        if (hexR.length() == 1)
    		hexR = "0" + hexR;
        String hexG = Integer.toHexString(green);
    	if (hexG.length() == 1)
    		hexG = "0" + hexG;
    	String hexB = Integer.toHexString(blue);
    	if (hexB.length() == 1)
    		hexB = "0" + hexB;
    	
    	hex = "0x" + hexR + hexG + hexB;
    }
    
    // Based on formulas here: http://www.rapidtables.com/convert/color/rgb-to-hsv.htm
    private void rgb2hsv()
    {
        double min, max, delta;

        min = red < green ? red : green;
        min = min  < blue ? min  : blue;

        max = red > green ? red : green;
        max = max  > blue ? max  : blue;

        value = max / 255;
        
        delta = max - min;
        if (delta < 0.00001)
        {
            saturation = 0.0;
            hue = 0.0;
        }
        
        if (max > 0.0) 
            saturation = (delta / max);
        else 
        {
            // if max is 0, then r = g = b = 0              
            saturation = 0.0;
            hue = 0.0;
        }
        
        if (red >= max)
        	hue = (green - blue) / delta;
            
        else
        {
        	if (green >= max)
        		hue = 2.0 + (blue - red) / delta;
            else
            	hue = 4.0 + (red - green) / delta;
        }
        
        hue *= 60.0; // degrees

        if (hue < 0.0)
            hue += 360.0;
    }
    
    // Based on formulas here: http://www.rapidtables.com/convert/color/rgb-to-cmyk.htm
    private void rgb2cmyk()
    {
    	double red_ = red / 255.0;
    	double green_ = green / 255.0;
    	double blue_ = blue / 255.0;
    	
    	double max = red_ > green_ ? red_ : green_;
        max = max  > blue_ ? max  : blue_;
    	
        key = 1.0 - max;
        
        cyan = (1.0 - red_ - key) / (1.0 - key);
        magenta = (1.0 - green_ - key) / (1.0 - key);
        yellow = (1.0 - blue_ - key) / (1.0 - key);
    }
}
