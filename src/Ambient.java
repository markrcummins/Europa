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

  ////COPIED CODE, MIGHT NOT NEED//////
  private double convToHue(int color)
  {
    double hueVal = 300.0;

    hueVal = 300.0 + (double) color * (0.6);
    System.out.println("convToHue function hueVal value: " + hueVal);
    return hueVal;
  }


  public Color getColorObject()
  {
    Color ambColor;
    double hueVal, saturation, brightness;

    hueVal = convToHue(this.colorVariance);
    saturation = 1.0;
    brightness = (double) this.brightnessVariance / 100;

    ambColor = Color.hsb(hueVal, saturation, brightness);

    return ambColor;
  }

  private Color inputToColor(int num) {
    Color colorOutput = Color.web("#000000");

    if (num < 10 && num >= 0) {
      // Be VERY violet
      colorOutput = Color.web("#FF00FF");
    }
    if (num < 20 && num >= 10) {
      // Be less violet
      colorOutput = Color.web("#FE2EF7");
    }
    if (num < 30 && num >= 20) {
      // Be even LESS violet
      colorOutput = Color.web("#FA58F4");
    }
    if (num < 40 && num >= 30) {
      // Be barely violet
      colorOutput = Color.web("#F781F3");
    }
    if (num < 50 && num >= 40) {
      // Basically white
      colorOutput = Color.web("#F5A9F2");
    }
    if (num < 60 && num >= 50) {
      // Barely Red
      colorOutput = Color.web("##F5A9A9");
    }
    if (num < 70 && num >= 60) {
      // Be  even less red
      colorOutput = Color.web("#F78181");
    }
    if (num < 80 && num >= 70) {
      // Be less red
      colorOutput = Color.web("#FA5858");
    }
    if (num < 90 && num >= 80) {
      //Red
      colorOutput = Color.web("#FE2E2E");
    }
    if (num < 100 && num >= 90) {
      // Bright red lipstick
      colorOutput = Color.web("#FF0000");
    }
    return colorOutput;
  }

  @Override
  public String toString()
  {
    return "AmbientObject [color = " + colorVariance + ", brightness = " + brightnessVariance + "]";
  }

}

