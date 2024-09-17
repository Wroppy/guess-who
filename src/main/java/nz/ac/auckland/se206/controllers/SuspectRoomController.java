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

    if (sceneType == SceneType.SUSPECT_1) {
      setupImage("/images/bobscene.png");
    } else if (sceneType == SceneType.SUSPECT_2) {
      // setupImage("/images/suspect2.png");
    } else if (sceneType == SceneType.SUSPECT_3) {
      // setupImage("/images/suspect3.png");
    }

    // Adds the chat box
    ChatComponent chatComponent = new ChatComponent(sceneType);
    chatContainer.getChildren().add(chatComponent);
  }

  public void setupImage(String imagePath) {
    Image image = new Image(getClass().getResourceAsStream(imagePath));
    imageContainer.setImage(image);
    imageContainer.setFitWidth(545);
    imageContainer.setFitHeight(545);
  }

  @Override
  public void setupHeader(SceneType sceneType) {
    GameHeader gameHeader = new GameHeader(sceneType);
    this.headerContainer.getChildren().add(gameHeader);
  }
}
