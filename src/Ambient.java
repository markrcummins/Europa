import javafx.scene.paint.Color;

public class Ambient
{
  private int colorVariance;
  private int brightnessVariance;

  //Default Constructor
  public Ambient() {
    this.colorVariance = 0;
    this.brightnessVariance = 0;
  }

  public Ambient(int colorVariance, int brightnessVariance) {
    if (colorVariance < 0 || colorVariance > 99) {
      throw new IllegalArgumentException("Color input must be from 0 - 99.");
    }
    if(brightnessVariance < 0 || brightnessVariance > 99) {
      throw new IllegalArgumentException("Brightness input must be from 0 - 99");
    }
    this.colorVariance = colorVariance;
    this.brightnessVariance = brightnessVariance;
  }

  public int getColor() {
    return colorVariance;
  }
  public int getBrightness() {
    return brightnessVariance;
  }
  public void setColor(int num) {
    colorVariance = num;
  }
  public void setBrightness(int num) {
    brightnessVariance = num;
  }


  private double convToHue(int color)
  {
    double hueVal = 300.0;

    hueVal = 300.0 + (double) color * (0.6);
    //System.out.println("convToHue function hueVal value: " + hueVal);
    return hueVal;
  }


  public Color getColorObject()
  {
    Color ambColor;
    double hueVal, saturation, brightness, opaque;

    hueVal = convToHue(this.colorVariance);
    saturation = 1.0;
    brightness = this.colorVariance - 49.5;
    brightness = Math.round(((Math.abs(brightness)*2)+1)/100);
    //System.out.println("Brightness value: " + brightness);

    ambColor = Color.hsb(hueVal, saturation, brightness);

    return ambColor;
  }


}

