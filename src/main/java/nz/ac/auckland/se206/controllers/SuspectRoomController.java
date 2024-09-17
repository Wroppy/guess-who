package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.SceneManager.SceneType;
import nz.ac.auckland.se206.components.chatview.ChatComponent;
import nz.ac.auckland.se206.components.gameheader.GameHeader;

public class SuspectRoomController implements HeaderableController {
  @FXML private Pane headerContainer;
  @FXML private Pane chatContainer;
  @FXML private ImageView imageContainer;

  public static GameHeader gameHeader1;
  public static GameHeader gameHeader2;
  public static GameHeader gameHeader3;

  public void initialize() {}

  public void setupRoom(SceneType sceneType) {
    setupHeader(sceneType);

    // Adds the chat box
    ChatComponent chatComponent = new ChatComponent(sceneType);
    chatContainer.getChildren().add(chatComponent);
  }

  @Override
  public void setupHeader(SceneType sceneType) {
    if (sceneType == SceneType.SUSPECT_1) {
      gameHeader1 = new GameHeader(sceneType);
      this.headerContainer.getChildren().add(gameHeader1);
    } else if (sceneType == SceneType.SUSPECT_2) {
      gameHeader2 = new GameHeader(sceneType);
      this.headerContainer.getChildren().add(gameHeader2);
    } else if (sceneType == SceneType.SUSPECT_3) {
      gameHeader3 = new GameHeader(sceneType);
      this.headerContainer.getChildren().add(gameHeader3);
    }
    // GameHeader gameHeader = new GameHeader(sceneType);
    // this.headerContainer.getChildren().add(gameHeader);
  }

  // public static GameHeader getGameHeader() {
  // return gameHeader;
  // }
}
