package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameTimer;
import nz.ac.auckland.se206.SceneManager.SceneType;

public class MenuController {

  @FXML private Button btnStart;

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
        new GameTimer(RoomController.getGameHeader().getTimerLabel(), RoomController.getContext());
    gameTimer.start();
    App.changeScene(SceneType.CRIME);
  }
}
