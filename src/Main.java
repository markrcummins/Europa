import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.awt.*;
import java.util.Scanner;
import static java.lang.Math.random;

import java.net.URL;

public class Main extends Application {



  public static void main(String... args) {
    launch(args);
  }
  Button button1;
  Button button2;
  Button button3;
  Scene scene;
  Stage window;
  Scanner scan;

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;
    window.setTitle("thenewboston");

    //Form
    TextField colorInput = new TextField();
    TextField brightnessInput = new TextField();
    TextField soundInput = new TextField();

    button1 = new Button("Enter Color");
    button2 = new Button("Enter Brightness");
    button3 = new Button("Enter Sound");
    button1.setOnAction(e -> isInt(colorInput, colorInput.getText()));
    button2.setOnAction(e -> isInt(brightnessInput, brightnessInput.getText()));
    button3.setOnAction(e -> isInt(soundInput, soundInput.getText()));
    //Layout
    VBox layout = new VBox(10);
    layout.setPadding(new Insets(20, 20,20,20));
    layout.getChildren().addAll(colorInput, button1);
    layout.getChildren().addAll(brightnessInput, button2);
    layout.getChildren().addAll(soundInput, button3);
    scene = new Scene(layout, 300, 250);
    window.setScene(scene);
    window.show();

  }

  private boolean isInt(TextField input, String message) {
    try {

      int range = Integer.parseInt(input.getText());
      if(range < 100 && range >= 0) {
        System.out.println("User inputted: " + range);
        return true;
      }
      else {
        System.out.println("Please input a number between 0 to 99.");
        return false;
      }
    }catch(NumberFormatException e){
      System.out.println("Error: " + message + " is not a number");
      return false;
    }
  }

  //private
}