import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;
import static java.lang.Math.random;

public class Ambient
{
  private int colorVariance;
  private int brightnessVariance;
  // private int soundVariance;

  public Ambient(int colorVariance, int brightnessVariance) {
    this.colorVariance = colorVariance;
    this.brightnessVariance = brightnessVariance;
    //this.soundVariance = soundVariance;
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

}
