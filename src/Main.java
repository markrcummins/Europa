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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.awt.*;
import java.util.Scanner;
import static java.lang.Math.random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;
import java.io.*;

import java.net.URL;

public class Main extends Application {



  public static void main(String... args) {
    launch(args);
  }

  Button button1;
  Button button2;
  Button choiceButton;
  ChoiceBox<String> choiceBox;
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
  TextField colorInput;
  TextField brightnessInput;

  @Override
  public void start(Stage primaryStage) throws Exception {

    window = primaryStage;
    window.setTitle("Europa Ambient Device");

    //Form
    colorInput = new TextField();
    brightnessInput = new TextField();

    button1 = new Button("Submit");

    choiceButton = new Button("Enter Option");
    choiceBox = new ChoiceBox();
    choiceBox.getItems().add("King's River Inflow/Outflow");
    choiceBox.getItems().add("BitCoin Tracker");
    choiceBox.getItems().add("Ethereum Tracker");
    choiceBox.getItems().add("DogeCoin Tracker");

    choiceBox.setValue("King's River Inflow/Outflow");

    choiceButton.setOnAction(e -> {
      getChoice(choiceBox);
    });


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

    });

    VBox layout = new VBox(10);
    layout.setPadding(new Insets(20, 20, 20, 20));
    layout.getChildren().addAll(choiceBox, choiceButton);
    scene = new Scene(layout, 300, 250);
    window.setScene(scene);
    window.show();

    final Document doc = Jsoup.connect("http://www.spk-wc.usace.army.mil/fcgi-bin/hourly.py?report=pnf&textonly=true").get();
    //parse the webpage by spaces into one row of the 2d array
    //When there's a newline, parse the next line into the next row
    String str = doc.select("body").text();
    String strArray[] = str.split("\\s+");

    System.out.println("**************************************");
    System.out.println(averageInflow(strArray));
    System.out.println("**************************************");
    System.out.println(averageOutflow(strArray));


  }

  //********** STATIC METHODS **********//

  private Scene getChoice(ChoiceBox<String> choiceBox) {
    //initialzed with empty scene to make Java happy
    //VBox layout = new VBox();
    Scene userChoice;// = new Scene(layout ,300, 300);

    //Gonna assign choices to a number so that it's easier to set different scenes later on
    if(choiceBox.getValue().equals("King's River Inflow/Outflow")) {
      userChoice = kingRiverScene();
    }
   else if(choiceBox.getValue().equals("BitCoin Tracker")) {
      userChoice = bitCoinScene();
    }
    else if(choiceBox.getValue().equals("Ethereum Tracker")) {
      userChoice = ethereumScene();
    }
    else { //if the choice was the DogeCoin Traker
      userChoice = dogeCoinScene();
    }

    return userChoice;
  }

  private Scene kingRiverScene() {
    VBox layout = new VBox(10);
    layout.setPadding(new Insets(20, 20,20,20));
    layout.getChildren().addAll(colorInput, brightnessInput, button1);
    //layout.getChildren().addAll(brightnessInput, button2);
    scene = new Scene(layout, 300, 250);
    return scene;
  }

  private Scene bitCoinScene() {
    VBox layout = new VBox(10);
    layout.setPadding(new Insets(20, 20,20,20));
    layout.getChildren().addAll(colorInput, brightnessInput, button1);
    //layout.getChildren().addAll(brightnessInput, button2);
    scene = new Scene(layout, 300, 250);
    return scene;
  }

  private Scene ethereumScene() {
    VBox layout = new VBox(10);
    layout.setPadding(new Insets(20, 20,20,20));
    layout.getChildren().addAll(colorInput, brightnessInput, button1);
    //layout.getChildren().addAll(brightnessInput, button2);
    scene = new Scene(layout, 300, 250);
    return scene;
  }

  private Scene dogeCoinScene() {
    VBox layout = new VBox(10);
    layout.setPadding(new Insets(20, 20,20,20));
    layout.getChildren().addAll(colorInput, brightnessInput, button1);
    //layout.getChildren().addAll(brightnessInput, button2);
    scene = new Scene(layout, 300, 250);
    return scene;
  }

  private int averageInflow(String array[]) {
    int average;
    int total = 0;
    int counter = 0;
    int i = 56;
    while (i <=620) {
      if(isInt(array[i])) {
        int validInput = Integer.parseInt(array[i]);
        total += validInput;
        i+=12;
        counter++;

      }
      else {
        i+=12;
      }
    }
    average = total/counter;
    return average;
  }

  private int averageOutflow(String array[]) {
    int average;
    int total = 0;
    int counter = 0;
    int k = 55;
    while (k <=619) {
      if(isInt(array[k])) {
        int validInput = Integer.parseInt(array[k]);
        total += validInput;
        System.out.println(array[k]);
        k+=12;
        counter++;

      }
      else {
        System.out.println(array[k]);
        k+=12;
      }
    }
    System.out.println(counter);
    average = total/counter;
    return average;
  }

  private Color inputToColor(int num) {

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
      System.out.println("Error: " + message + " is not an integer");
      return false;
    }
  }

  private boolean isInt(String message) {
    try {
      Integer.parseInt(message);
      return true;

    }
    catch(NumberFormatException e) {
      return false;
    }
  }


  }