import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Slider;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Main extends Application {


  public static void main(String... args) {
    launch(args);
  }

  Button button1;
  Button choiceButton;
  Button settingsButton;
  Button confirmButton;
  ChoiceBox<String> choiceBox;
  Scene scene;
  Scene choiceScene;

  Color colorOutput;
  TextField brightnessInput;
  double redNumber;
  double purpleNumber;
  double bitCoinPivotValue;
  int choice;

  private Ambient ambient = new Ambient(88, 88);
  private Stage window;
  private WebData wd = new WebData();
  private Rectangle colorRectangle;

  //King River Scales
  Slider redThreshold = new Slider( 0,99, 8);
  Label redCaption = new Label("Infield/Outfield Red Light Threshold: ");
  Slider purpleThreshold = new Slider(0, 99, 14);
  Label purpleCaption = new Label("Infield/Outfield Purple Light Threshold");
  Label caption = new Label("Red Threshold > purple threshold");

  //BitCoin Scales
  Slider bitCoinRedThreshold = new Slider(0,99,8);
  Label bitCoinRedCaption = new Label("BitCoin Loss Red Light Threshold");
  Slider bitCoinPurpleThreshold = new Slider(0, 99, 7);
  Label bitCoinPurpleCaption = new Label("BitCoin Gain Purple Threshold");

  @Override
  public void start(Stage primaryStage) throws Exception {

    window = primaryStage;
    window.setTitle("Europa Ambient Device");

    button1 = new Button("Submit");

    choiceButton = new Button("Enter Option");
    choiceBox = new ChoiceBox();
    choiceBox.getItems().add("King's River Inflow/Outflow");
    choiceBox.getItems().add("BitCoin Tracker");
    choiceBox.setValue("King's River Inflow/Outflow");

    // Ambient scene
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


      settingsButton.setOnAction(s -> {

        if(choice == 1 && redThreshold.getValue() < purpleThreshold.getValue()) {
          //System.out.println("hello");

        window.setScene(choiceScene);
        }
        else {
          generalUpdate();
          window.setScene(confirmScene());
          confirmButton.setOnAction(m -> {
            window.setScene(appScene);
          });
        }
      });
    });

    //FIRST SCENE THAT SHOWS
    VBox layout = new VBox(10);
    layout.setPadding(new Insets(20, 20, 20, 20));
    layout.getChildren().addAll(choiceBox, choiceButton);
    scene = new Scene(layout, 400, 350);
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

  private Scene getChoice(ChoiceBox<String> choiceBox) {
    Scene userChoice;

    if(choiceBox.getValue().equals("King's River Inflow/Outflow")) {
      choice = 1;
      userChoice = kingRiverSettings();
    }
   else {
      choice = 0;
      userChoice = bitCoinSettings();
    }
    return userChoice;
  }

  private void generalAmbientInput() {
    if (choice == 1) {
      kingRiverAmbientInput();
    }
    else if(choice == 0) {
      bitCoinAmbientInput();
    }
  }

  private void bitCoinAmbientInput() {
    int colorInput = 0;
    double inBetweener = 0;
    wd.setRed(Math.round(bitCoinRedThreshold.getValue()));
    wd.setPurple(Math.round(bitCoinPurpleThreshold.getValue()));
    //System.out.println("bitCoinAmbientInput bitCoinredThreshold.getValue(): " + bitCoinRedThreshold.getValue());
    //System.out.println("bitCoinAmbientInput bitCoinpurpleThreshold.getValue(): " + bitCoinPurpleThreshold.getValue());
    //System.out.println("bitCoinAmbientInput wd.getRed(): " + wd.getRed());
    //System.out.println("bitCoinAmbientInput wd.getPurple(): " + wd.getPurple());
    double currentValue = wd.getData() - bitCoinPivotValue;
    double purpleInput = currentValue/wd.getPurple();
    double redInput = currentValue/wd.getRed();
    //System.out.println("wd.getData: " + wd.getData());
    //System.out.println("bitCoinPivotValue: "+ bitCoinPivotValue);
    //System.out.println("currentValue: " + currentValue);
    //System.out.println("purpleInput: " + purpleInput);
    //System.out.println("redInput: " + redInput);
    if(purpleInput >= 1) {
      //System.out.println("You reached here!");
      colorInput = 0;
      wd.setColorData(colorInput);
    }
    else if(redInput <= -1) {
      //System.out.println("Youve reached there!");
      colorInput = 99;
      wd.setColorData(colorInput);
    }
    else {
      //System.out.println("You've hit the else statement!");
      inBetweener = currentValue + wd.getRed();
      inBetweener = (inBetweener/(wd.getPurple() + wd.getRed())) * 100;
      //System.out.println("Value of inBetweener: " + inBetweener);
      colorInput =100 - (int)inBetweener;
      //System.out.println("Value of inBetweener after turning into an int: " + inBetweener);
      //System.out.println("bitCoinAmbientInput colorInput: " + colorInput);
      wd.setColorData(colorInput);
    }
    //System.out.println("bitCoinAmbientInput final colorInput: " + wd.getColorData());
  }

  private void kingRiverAmbientInput() {
    int colorInput = 0;
    double inBetweener = 0;
    //System.out.println("kingRiverAmbientInput wd.getPurple(): " + wd.getPurple());
    //System.out.println("kingRiverAmbientInput wd.getRed(): " + wd.getRed());
    //System.out.println("kingRiverAmbientInput wd.getData(): " + wd.getData());
    wd.setRed(Math.round(redThreshold.getValue()));
    wd.setPurple(Math.round(purpleThreshold.getValue()));
    //System.out.println("kingRiverAmbientInput redThreshold.getValue(): " + redThreshold.getValue());
    //System.out.println("kingRiverAmbientInput purpleThreshold.getValue(): " + purpleThreshold.getValue());
    //System.out.println("kingRiverAmbientInput wd.getRed(): " + wd.getRed());
    //System.out.println("kingRiverAmbientInput wd.getPurple(): " + wd.getPurple());
    double purpleInput = wd.getData()/wd.getPurple();
    double redInput = wd.getData()/wd.getRed();
    //System.out.println("kingRiverAmbientInput purpleInput: " + purpleInput);
    //System.out.println("kingRiverAmbientInput redInput: " + redInput);
    if(purpleInput <= 1) {
      colorInput = 0;
      wd.setColorData(colorInput);
    }
    else if (redInput >= 1) {
      colorInput = 99;
      wd.setColorData(colorInput);
    }
    else {
      inBetweener = wd.getData() - wd.getPurple();
      inBetweener = (inBetweener/(wd.getRed() - wd.getPurple())) * 100;
      //System.out.println("Value of inBetweener: " + inBetweener);
      colorInput = 100 - (int)inBetweener;
      //System.out.println("Value of inBetweener after turning into an int: " + inBetweener);
      //System.out.println("kingRiverAmbientInput colorInput: " + colorInput);
      wd.setColorData(colorInput);
    }
    //System.out.println("kingRiverAmbientInput final colorInput: " + wd.getColorData());
  }

  private Scene generalSettings() {
    if (choice == 1) {
      return kingRiverSettings();
    }
    else if (choice == 0) {
      return bitCoinSettings();
    }
    //System.out.println("Something fucked up");
    return null;
  }

  private Scene kingRiverSettings() {
    Label redValue = new Label(Double.toString(redThreshold.getValue()));
    Label purpleValue = new Label(Double.toString(purpleThreshold.getValue()));

    Color textColor = Color.BLACK;
    Group root = new Group();
    Scene settings = new Scene(root, 700, 700);
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10, 10, 10, 10));
    grid.setVgap(10);
    grid.setHgap(70);
    settings.setRoot(grid);

    redCaption.setTextFill(textColor);
    purpleCaption.setTextFill(textColor);
    caption.setTextFill(textColor);
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
    GridPane.setConstraints(caption, 0, 3);
    grid.getChildren().add(caption);

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
    //System.out.println("redThreshold value: " + redNumber);
    purpleNumber = purpleThreshold.getValue();
    //System.out.println("purpleThreshold value: " + purpleNumber);
    settingsButton = new Button("Submit");
    wd.setRed(redNumber);
    wd.setPurple(purpleNumber);
    //System.out.println("Red threshold value: " + wd.getRed());
    //System.out.println("Purple threshold value: " + wd.getPurple());
    GridPane.setConstraints(settingsButton, 2, 10);
    grid.getChildren().add(settingsButton);

    return settings;
  }

  private Scene bitCoinSettings() {
    wd.getBitCoinData();
    bitCoinPivotValue = wd.getData();
    Label bitCoinRedValue = new Label(Double.toString(bitCoinRedThreshold.getValue()));
    Label bitCoinPurpleValue = new Label(Double.toString(bitCoinPurpleThreshold.getValue()));

    Color textColor = Color.BLACK;
    Group root = new Group();
    Scene settings = new Scene(root, 700, 700);
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10, 10, 10, 10));
    grid.setVgap(10);
    grid.setHgap(70);
    settings.setRoot(grid);

    bitCoinRedCaption.setTextFill(textColor);
    bitCoinPurpleCaption.setTextFill(textColor);
    GridPane.setConstraints(bitCoinRedCaption, 0, 1);
    grid.getChildren().add(bitCoinRedCaption);

    bitCoinRedThreshold.valueProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
        bitCoinRedValue.setText(String.format("%.0f", newValue));
      }
    });

    GridPane.setConstraints(bitCoinRedThreshold, 1, 1);
    grid.getChildren().add(bitCoinRedThreshold);
    bitCoinRedValue.setTextFill(textColor);
    GridPane.setConstraints(bitCoinRedValue, 2, 1);
    grid.getChildren().add(bitCoinRedValue);

    bitCoinPurpleCaption.setTextFill(textColor);
    GridPane.setConstraints(bitCoinPurpleCaption, 0, 2);
    grid.getChildren().add(bitCoinPurpleCaption);

    bitCoinPurpleThreshold.valueProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
        bitCoinPurpleValue.setText(String.format("%.0f", newValue));
      }
    });
    GridPane.setConstraints(bitCoinPurpleThreshold, 1, 2);
    grid.getChildren().add(bitCoinPurpleThreshold);

    bitCoinPurpleValue.setTextFill(textColor);
    GridPane.setConstraints(bitCoinPurpleValue, 2, 2);
    grid.getChildren().add(bitCoinPurpleValue);

    redNumber = bitCoinRedThreshold.getValue();
    //System.out.println("bitCoinRedThreshold value: " + redNumber);
    purpleNumber = bitCoinPurpleThreshold.getValue();
    //System.out.println("bitCoinPurpleThreshold value: " + purpleNumber);
    settingsButton = new Button("Submit");
    wd.setRed(redNumber);
    wd.setPurple(purpleNumber);
    //System.out.println("bitCoin Red threshold value: " + wd.getRed());
    //System.out.println("butCoin Purple threshold value: " + wd.getPurple());
    GridPane.setConstraints(settingsButton, 2, 6);
    grid.getChildren().add(settingsButton);

    return settings;
  }

  private boolean isInt(TextField input, String message) {
    try {

      int range = Integer.parseInt(input.getText());
      if(range < 100 && range >= 0) {
        //System.out.println("User inputted: " + range);
        return true;
      }
      else {
        //System.out.println("Please input a number between 0 to 99.");
        return false;
      }
    }catch(NumberFormatException e){
      //System.out.println("Error: " + message + " is not an integer");
      return false;
    }
  }

  private void generalUpdate() {
    if(choice == 1) {
      update();
    }
    else if(choice == 0) {
      bitCoinUpdate();
    }
  }

  private void bitCoinUpdate() {
    Timeline newData = new Timeline(new KeyFrame(Duration.seconds(5), a -> {
      Color oldColor = ambient.getColorObject();
      wd.getBitCoinData();
      //System.out.println("bitCoinPpdate() function, wd.time value: " + wd.getTime());
      //System.out.println("bitCoinUpdate() function, wd.data value: " + wd.getData());
      //System.out.println("bitCoinUpdate() function, wd.red value: " + wd.getRed());
      //System.out.println("bitCoinUpdate() function, wd.purple value: " + wd.getPurple());
      bitCoinAmbientInput();
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

  private void update() {
    Timeline newData = new Timeline(new KeyFrame(Duration.seconds(5), a -> {
      Color oldColor = ambient.getColorObject();
      wd.getKingRiverData();
      //System.out.println("update() function, wd.time value: " + wd.getTime());
      //System.out.println("update() function, wd.data value: " + wd.getData());
      //System.out.println("update() function, wd.red value: " + wd.getRed());
      //System.out.println("update() function, wd.purple value: " + wd.getPurple());
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
      //kingRiverAmbientInput();
      ambient.setColor(wd.getColorData());
      //System.out.println("updateAmbient() function, wd.colorData value: " + wd.getColorData());
    }
}



