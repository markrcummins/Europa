import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.control.Slider;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleStringProperty;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;
import java.io.*;
import java.util.concurrent.TimeUnit.*;

import java.net.URL;

public class Main extends Application {


  public static void main(String... args) {
    launch(args);
  }

  Button button1;
  Button button2;
  Button choiceButton;
  Button settingsButton;
  Button confirmButton;
  ChoiceBox<String> choiceBox;
  Scene scene;
  Scene choiceScene;
  Scene chosenScene;
  Scene colorScene;

  Scanner scan;

  Color colorOutput;
  boolean validColorInput;
  boolean validBrightnessInput;
  Group root;
  //int colorInput;
  TextField brightnessInput;
  double redNumber;
  double purpleNumber;

  private Ambient ambient = new Ambient(88, 88);
  private Stage window;
  private WebData wd = new WebData();
  private Rectangle colorRectangle;

  Slider redThreshold = new Slider( 0,100, 8);
  Label redCaption = new Label("Infield/Outfield Red Light Threshold: ");

  Slider purpleThreshold = new Slider(0, 100, 7);
  Label purpleCaption = new Label("Infield/Outfield Purple Light Threshold");
  
  private StringProperty rawTime = new SimpleStringProperty("0000");
  private IntegerProperty rawData = new SimpleIntegerProperty(0);
  private IntegerProperty rawRed = new SimpleIntegerProperty(0);
  private IntegerProperty rawPurple = new SimpleIntegerProperty(0);

  @Override
  public void start(Stage primaryStage) throws Exception {

    wd.getKingRiverData();
    rawTime.setValue(String.valueOf(wd.getTime()));
    rawData.setValue((Double.valueOf(wd.getData())));
    rawRed.setValue(Double.valueOf(wd.getRed()));
    rawPurple.setValue(Double.valueOf(wd.getPurple()));

    //updateAmbient()  MAKE THIS METHOD
    // update() MAKE THIS METHOD

    window = primaryStage;
    window.setTitle("Europa Ambient Device");
    //Temporary curly brace


    brightnessInput = new TextField();

    button1 = new Button("Submit");

    choiceButton = new Button("Enter Option");
    choiceBox = new ChoiceBox();
    choiceBox.getItems().add("King's River Inflow/Outflow");
    choiceBox.getItems().add("BitCoin Tracker");
    choiceBox.getItems().add("Ethereum Tracker");
    choiceBox.getItems().add("DogeCoin Tracker");

    choiceBox.setValue("King's River Inflow/Outflow");

    Group root = new Group();
    Scene appScene = new Scene(root, 300, 300);
    colorRectangle = new Rectangle(300.0, 300.0);
    GridPane base = new GridPane();

    appScene.setRoot(base);
    GridPane.setConstraints(colorRectangle, 0, 0);
    base.getChildren().add(colorRectangle);
    colorRectangle.setFill(ambient.getColorObject());

    // SECOND SCENE THAT SHOWS BASED ON CHOICE
    choiceButton.setOnAction(e -> {
      choiceScene = getChoice(choiceBox);
      window.setScene(choiceScene);

      settingsButton.setOnAction(k -> {
        wd.setRed(redNumber);
        wd.setPurple(purpleNumber);
        System.out.println(wd.getRed());
        System.out.println(wd.getPurple());
        update();
        window.setScene(confirmScene());

        confirmButton.setOnAction(m -> {
          window.setScene(appScene);
        });
      });
    });





    //FIRST SCENE THAT SHOWS
    VBox layout = new VBox(10);
    layout.setPadding(new Insets(20, 20, 20, 20));
    layout.getChildren().addAll(choiceBox, choiceButton);
    scene = new Scene(layout, 300, 250);
    window.setScene(scene);
    window.show();
  }

  /********** STATIC METHODS **********/

  private Scene confirmScene() {
    VBox layout = new VBox(10);
    layout.setPadding(new Insets(20, 20, 20, 20));
    confirmButton = new Button("Confirm Ambient Device");
    layout.getChildren().addAll(confirmButton);
    Scene confirmScene = new Scene(layout, 300, 250);
    return confirmScene;
  }
  /*
  private Scene ambientScene() {
    //settingsButton.setOnAction(e -> {
      chosenScene = kingRiverScene(purpleNumber, redNumber, getKingRiverData());
    //});
    return chosenScene;

  }

*/
  private Scene getChoice(ChoiceBox<String> choiceBox) {
    //initialzed with empty scene to make Java happy
    //VBox layout = new VBox();
    Scene userChoice;// = new Scene(layout ,300, 300);

    //Gonna assign choices to a number so that it's easier to set different scenes later on
    if(choiceBox.getValue().equals("King's River Inflow/Outflow")) {
      userChoice = kingRiverSettings();
    }
   else if(choiceBox.getValue().equals("BitCoin Tracker")) {
      //Temporary fix
      //userChoice = bitCoinScene();
      userChoice = kingRiverSettings();
    }
    else if(choiceBox.getValue().equals("Ethereum Tracker")) {
      //Temporary fix
      //userChoice = ethereumScene();
      userChoice = kingRiverSettings();
    }
    else { //if the choice was the DogeCoin Traker
      //Temporary fix
      //userChoice = dogeCoinScene();
      userChoice = kingRiverSettings();
    }

    return userChoice;
  }


  private void kingRiverAmbientInput() {
    int colorInput = 0;
    double inBetweener = 0;
    System.out.println("kingRiverAmbientInput wd.getPurple(): " + wd.getPurple());
    System.out.println("kingRiverAmbientInput wd.getRed(): " + wd.getRed());
    System.out.println("kingRiverAmbientInput wd.getData(): " + wd.getData());
    wd.setRed(redThreshold.getValue());
    wd.setPurple(purpleThreshold.getValue());
    System.out.println("kingRiverAmbientInput redThreshold.getValue(): " + redThreshold.getValue());
    System.out.println("kingRiverAmbientInput purpleThreshold.getValue(): " + purpleThreshold.getValue());
    System.out.println("kingRiverAmbientInput wd.getRed(): " + wd.getRed());
    System.out.println("kingRiverAmbientInput wd.getPurple(): " + wd.getPurple());
    double purpleInput = wd.getData()/wd.getPurple();
    double redInput = wd.getData()/wd.getRed();
    System.out.println("kingRiverAmbientInput purpleInput: " + purpleInput);
    System.out.println("kingRiverAmbientInput redInput: " + redInput);
    //colorInput
    if(purpleInput <= 1) {
      colorInput = 0;
      wd.setColorData(colorInput);
    }
    else if (redInput >= 1) {
      colorInput = 99;
      wd.setColorData(colorInput);
    }
    else {
      inBetweener = purpleInput;
      inBetweener = (inBetweener/(wd.getRed()-wd.getPurple())) * 100;
      System.out.println("Value of inBetweener: " + inBetweener);
      colorInput = (int) inBetweener;
      System.out.println("Value of inBetweener after turning into an int: " + inBetweener);
      System.out.println("kingRiverAmbientInput colorInput: " + colorInput);
      wd.setColorData(colorInput);
    }

    //ambient = new Ambient(colorInput, colorInput);
    //colorRectangle = new Rectangle(scene.getWidth() - 100, scene.getHeight() - 100, inputToColor(ambient.getColor()));
    //root = new Group();
    //root.getChildren().add(colorRectangle);
    //colorScene = new Scene(root, 300, 250, Color.BLACK);
    System.out.println("kingRiverAmbientInput final colorInput: " + wd.getColorData());
    //return colorScene;
  }

  private Scene kingRiverSettings() {


    Label redValue = new Label(Double.toString(redThreshold.getValue()));



    Label purpleValue = new Label(Double.toString(purpleThreshold.getValue()));

    Color textColor = Color.BLACK;
    Group root = new Group();
    Scene settings = new Scene(root, 600, 600);
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10, 10, 10, 10));
    grid.setVgap(10);
    grid.setHgap(70);
    settings.setRoot(grid);

    redCaption.setTextFill(textColor);
    purpleCaption.setTextFill(textColor);
    GridPane.setConstraints(redCaption, 0, 1);
    grid.getChildren().add(redCaption);

    redThreshold.valueProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
        redValue.setText(String.format("%.0f", newValue));
      }
    });

    GridPane.setConstraints(redThreshold, 1, 1);
    grid.getChildren().add(redThreshold);
    redValue.setTextFill(textColor);
    GridPane.setConstraints(redValue, 2, 1);
    grid.getChildren().add(redValue);

    purpleCaption.setTextFill(textColor);
    GridPane.setConstraints(purpleCaption, 0, 2);
    grid.getChildren().add(purpleCaption);

    purpleThreshold.valueProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
        purpleValue.setText(String.format("%.0f", newValue));
      }
    });
    GridPane.setConstraints(purpleThreshold, 1, 2);
    grid.getChildren().add(purpleThreshold);

    purpleValue.setTextFill(textColor);
    GridPane.setConstraints(purpleValue, 2, 2);
    grid.getChildren().add(purpleValue);

    redNumber = redThreshold.getValue();
    System.out.println("redThreshold value: " + redNumber);
    purpleNumber = purpleThreshold.getValue();
    System.out.println("purpleThreshold value: " + purpleNumber);
    settingsButton = new Button("Submit");
    wd.setRed(redNumber);
    wd.setPurple(purpleNumber);
    System.out.println("Red threshold value: " + wd.getRed());
    System.out.println("Purple threshold value: " + wd.getPurple());
    GridPane.setConstraints(settingsButton, 2, 6);
    grid.getChildren().add(settingsButton);

    return settings;

  }
/*
  private Scene bitCoinScene(int purpleRange, int redRange, int data) {
    double purpleInput = data/purpleRange;
    double redInput = data/redRange;
    //colorInput
    if(purpleInput <= 1) {
      colorInput = 0;
    }
    else if (redInput >= 1) {
      colorInput = 99;
    }
    else { colorInput = (int) data;

    }

    ambient = new Ambient(colorInput, colorInput);
    colorRectangle = new Rectangle(scene.getWidth() - 100, scene.getHeight() - 100, inputToColor(ambient.getColor()));
    root = new Group();
    root.getChildren().add(colorRectangle);
    colorScene = new Scene(root, 300, 250, Color.BLACK);
    return colorScene;
  }

  private Scene ethereumScene(int purpleRange, int redRange, int data) {
    double purpleInput = data/purpleRange;
    double redInput = data/redRange;
    //colorInput
    if(purpleInput <= 1) {
      colorInput = 0;
    }
    else if (redInput >= 1) {
      colorInput = 99;
    }
    else { colorInput = (int) data;

    }

    ambient = new Ambient(colorInput, colorInput);
    colorRectangle = new Rectangle(scene.getWidth() - 100, scene.getHeight() - 100, inputToColor(ambient.getColor()));
    root = new Group();
    root.getChildren().add(colorRectangle);
    colorScene = new Scene(root, 300, 250, Color.BLACK);
    return colorScene;
  }

  private Scene dogeCoinScene(int purpleRange, int redRange, int data) {
    double purpleInput = data/purpleRange;
    double redInput = data/redRange;
    //colorInput
    if(purpleInput <= 1) {
      colorInput = 0;
    }
    else if (redInput >= 1) {
      colorInput = 99;
    }
    else { colorInput = (int) data;

    }

    ambient = new Ambient(colorInput, colorInput);
    colorRectangle = new Rectangle(scene.getWidth() - 100, scene.getHeight() - 100, inputToColor(ambient.getColor()));
    root = new Group();
    root.getChildren().add(colorRectangle);
    colorScene = new Scene(root, 300, 250, Color.BLACK);


    //layout.getChildren().addAll(colorInput, brightnessInput, button1);
    //layout.getChildren().addAll(brightnessInput, button2);

    return colorScene;
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
  */
  private void update() { //copy of updateALLL
    Timeline newData = new Timeline(new KeyFrame(Duration.seconds(5), a -> {
      Color oldColor = ambient.getColorObject();
      wd.getKingRiverData();
      rawTime.setValue(String.valueOf(wd.getTime()));
      rawData.setValue(Double.valueOf(wd.getData()));
      //rawRed.setValue(Double.valueOf(wd.getRed()));
      //rawPurple.setValue(Double.valueOf(wd.getPurple()));
      System.out.println("update() function, wd.time value: " + wd.getTime());
      System.out.println("update() function, wd.data value: " + wd.getData());
      System.out.println("update() function, wd.red value: " + wd.getRed());
      System.out.println("update() function, wd.purple value: " + wd.getPurple());
      //rawColorData.setValue(Integer.valueOf(wd.getColorData()));

      kingRiverAmbientInput();
      updateAmbient();

      Color newColor = ambient.getColorObject();
      FillTransition fill = new FillTransition(Duration.seconds(5), colorRectangle, oldColor, newColor);
      fill.setCycleCount(4); //Don't know what this does.
      fill.setAutoReverse(true); //Also don't know what does
      fill.play(); //what does this do?
      colorRectangle.setFill(ambient.getColorObject());

    }));

    newData.setCycleCount(Animation.INDEFINITE);
    newData.play();
  }

    private void updateAmbient() {
      kingRiverAmbientInput();
      ambient.setColor(wd.getColorData());
      System.out.println("updateAmbient() function, wd.colorData value: " + wd.getColorData());

    }
}



