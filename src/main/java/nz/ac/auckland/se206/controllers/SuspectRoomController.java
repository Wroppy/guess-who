package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.SceneManager.SceneType;
import nz.ac.auckland.se206.components.gameheader.GameHeader;

public class SuspectRoomController implements HeaderableController {
  @FXML private Pane headerContainer;

  public void initialize() {}

  public void setupRoom(SceneType sceneType) {
    setupHeader(sceneType);
  }

  @Override
  public void setupHeader(SceneType sceneType) {
    GameHeader gameHeader = new GameHeader(sceneType);
    this.headerContainer.getChildren().add(gameHeader);
  }
}
