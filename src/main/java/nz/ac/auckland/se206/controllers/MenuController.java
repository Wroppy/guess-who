package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameTimer;
import nz.ac.auckland.se206.SceneManager.SceneType;
import nz.ac.auckland.se206.SoundManager;

public class MenuController implements Restartable {
  public static GameTimer gameTimer;

  @FXML private Button btnStart;

  private GuessingController guessingController;

  /** Initializes the menu view. */
  @FXML
  public void initialize() {
    // Any required initialization code can be placed here
    SoundManager.playSound("Welcome.mp3");
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
