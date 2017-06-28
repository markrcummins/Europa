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
import javafx.scene.layout.StackPane;
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
  Scene scene;
  Scene colorScene;
  Stage window;
  Scanner scan;
  Ambient ambient;
  Rectangle colorRectangle;
  Color colorOutput;
  boolean validColorInput;
  boolean validBrightnessInput;
  Group root;

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;
    window.setTitle("Europa Ambient Device");

    //Form
    TextField colorInput = new TextField();
    TextField brightnessInput = new TextField();

    button1 = new Button("Submit");
    //button2 = new Button("Enter Brightness");
    button1.setOnAction(e -> {
        validColorInput = isInt(colorInput, colorInput.getText());
        validBrightnessInput = isInt(brightnessInput, brightnessInput.getText());
        if(validColorInput && validBrightnessInput) {
          ambient = new Ambient(Integer.parseInt(colorInput.getText()), Integer.parseInt(brightnessInput.getText()));
          System.out.println(ambient.getColor());
          System.out.println(ambient.getBrightness());
          System.out.println(ambient.getColor());
          System.out.println(inputToColor(ambient.getColor()));
          colorRectangle = new Rectangle(scene.getWidth() - 100, scene.getHeight() - 100, inputToColor(ambient.getColor()));
          root = new Group();
          root.getChildren().add(colorRectangle);
          colorScene = new Scene(root, 300, 250, Color.BLACK);
          window.setScene(colorScene);
        }
        //return ambient;
    });
    //button2.setOnAction(e -> isInt(brightnessInput, brightnessInput.getText()));
    //Layout

    VBox layout = new VBox(10);
    layout.setPadding(new Insets(20, 20,20,20));
    layout.getChildren().addAll(colorInput, brightnessInput, button1);
    //layout.getChildren().addAll(brightnessInput, button2);
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

  private Color inputToColor(int num) {

    if(num < 10 && num >= 0) {
      // Be VERY violet
      colorOutput = Color.web("#FF00FF");
    }
    if(num < 20 && num >= 10) {
      // Be less violet
      colorOutput = Color.web("#FE2EF7");
    }
    if(num < 30 && num >= 20) {
      // Be even LESS violet
      colorOutput = Color.web("#FA58F4");
    }
    if(num < 40 && num >= 30) {
      // Be barely violet
      colorOutput = Color.web("#F781F3");
    }
    if(num < 50 && num >= 40) {
      // Basically white
      colorOutput = Color.web("#F5A9F2");
    }
    if(num < 60 && num >= 50) {
      // Barely Red
      colorOutput = Color.web("##F5A9A9");
    }
    if(num < 70 && num >= 60) {
      // Be  even less red
      colorOutput = Color.web("#F78181");
    }
    if(num < 80 && num >= 70) {
      // Be less red
      colorOutput = Color.web("#FA5858");
    }
    if(num < 90 && num >= 80) {
      //Red
      colorOutput = Color.web("#FE2E2E");
    }
    if(num < 100 && num >= 90) {
      // Bright red lipstick
      colorOutput = Color.web("#FF0000");
    }

    return colorOutput;
  }
}