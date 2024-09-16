package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.SceneManager.SceneType;
import nz.ac.auckland.se206.components.chatview.ChatComponent;
import nz.ac.auckland.se206.components.gameheader.GameHeader;

public class SuspectRoomController implements HeaderableController {
  @FXML private Pane headerContainer;
  @FXML private Pane chatContainer;
  @FXML private ImageView imageContainer;

  public void initialize() {}

  public void setupRoom(SceneType sceneType) {
    setupHeader(sceneType);

    Image image = new Image(getClass().getResourceAsStream("/images/bobscene.png"));
    imageContainer.setImage(image);
    imageContainer.setFitWidth(539);
    imageContainer.setFitHeight(499);

    // Adds the chat box
    ChatComponent chatComponent = new ChatComponent(sceneType);
    chatContainer.getChildren().add(chatComponent);
  }

  @Override
  public void setupHeader(SceneType sceneType) {
    GameHeader gameHeader = new GameHeader(sceneType);
    this.headerContainer.getChildren().add(gameHeader);
  }
}
