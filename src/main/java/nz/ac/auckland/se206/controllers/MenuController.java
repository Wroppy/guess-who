package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameTimer;
import nz.ac.auckland.se206.SceneManager.SceneType;
import nz.ac.auckland.se206.SoundManager;

public class MenuController implements Restartable {
  public static GameTimer gameTimer;

  @FXML private Pane menuPane;
  @FXML private Button investigateButton;
  @FXML private Label guessLabel;
  @FXML private Label suspectLabel;

  private GuessingController guessingController;

  /** Initializes the menu view. */
  @FXML
  public void initialize() {
    // Any required initialization code can be placed here
    SoundManager.playSound("Welcome.mp3");

    Font font = App.getMarkerFont(18);
    investigateButton.setFont(font);

    font = App.getMarkerFont(32);
    guessLabel.setFont(font);

    font = App.getMarkerFont(24);
    suspectLabel.setFont(font);

    this.styleScene();
  }

  private void styleScene() {
    String style = App.getCssUrl("menu-scene");
    menuPane.getStylesheets().add(style);
  }

  /** Handles the start button click event. */
  @FXML
  private void onStartButtonClick() {
    // Start the game
    gameTimer =
        new GameTimer(
            // GameTimer takes in the timer label, context, and guessing controller
            RoomController.getGameHeader().getTimerLabel(),
            RoomController.getContext(),
            guessingController);
    gameTimer.start();
    App.changeScene(SceneType.CRIME);
  }

  public void setGuessingController(GuessingController guessingController) {
    this.guessingController = guessingController;
  }

  @Override
  public void restart() {
    gameTimer = null;
  }
}
