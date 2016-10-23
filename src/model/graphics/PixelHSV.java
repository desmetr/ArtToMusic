package model.graphics;

/**
 * Created by rafael on 18.10.16.
 */
public class PixelHSV
{
    private float hue;
    private float saturation;
    private float value;

    public PixelHSV()
    {
        this.hue = (float) 0.0;
        this.saturation = (float) 0.0;
        this.value = (float) 0.0;
    }

    public PixelHSV(float hue, float saturation, float value)
    {
        this.hue = hue;
        this.saturation = saturation;
        this.value = value;
    }

    public float getHue()           {   return hue;         }
    public float getValue()         {   return value;       }
    public float getSaturation()    {   return saturation;  }

    public void setHue(float hue)                   {   this.hue = hue;                }
    public void setSaturation(float saturation)     {   this.saturation = saturation;  }
    public void setValue(float value)               {   this.value = value;            }

    public void print()
    {
        System.out.println(hue + ", " + saturation + ", " + value);
    }
}
