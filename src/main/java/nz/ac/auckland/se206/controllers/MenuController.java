package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameTimer;
import nz.ac.auckland.se206.SceneManager.SceneType;
import nz.ac.auckland.se206.SoundManager;

/**
 * Controller for the start menu of the game.
 *
 * <p>The MenuController initializes the menu and handles user interactions, such as starting the
 * game. It also starts the game timer and transits to the appropriate scene.
 */
public class MenuController implements Restartable {
  public static GameTimer gameTimer;

  @FXML private Button btnStart;

  private GuessingController guessingController;

  /**
   * Initializes the menu after its root element has been completely processed and plays a welcome
   * sound.
   */
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
