package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameTimer;
import nz.ac.auckland.se206.SceneManager.SceneType;

public class MenuController implements Restartable {

  @FXML private Button btnStart;

  private GuessingController guessingController;

  public static GameTimer gameTimer;

  /** Initializes the menu view. */
  @FXML
  public void initialize() {
    // Any required initialization code can be placed here
  }

  /** Handles the start button click event. */
  @FXML
  public void onStartButtonClick() {
    gameTimer =
        new GameTimer(
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
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'restart'");
  }
}
