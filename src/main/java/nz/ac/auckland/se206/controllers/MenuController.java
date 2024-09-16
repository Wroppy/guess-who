package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.SceneType;

public class MenuController {

  @FXML private Button btnStart;

  /** Initializes the menu view. */
  @FXML
  public void initialize() {
    // Any required initialization code can be placed here
  }

  /** Handles the start button click event. */
  @FXML
  public void onStartButtonClick() {
    App.changeScene(SceneType.CRIME);
  }
}
