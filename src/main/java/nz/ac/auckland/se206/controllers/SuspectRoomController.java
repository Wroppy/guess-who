package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.SceneManager.SceneType;
import nz.ac.auckland.se206.components.chatview.ChatComponent;
import nz.ac.auckland.se206.components.gameheader.GameHeader;

public class SuspectRoomController implements HeaderableController, Restartable {
  @FXML private Pane headerContainer;
  @FXML private Pane chatContainer;
  @FXML private ImageView imageContainer;

  public static GameHeader gameHeader1;
  public static GameHeader gameHeader2;
  public static GameHeader gameHeader3;

  public void initialize() {}

  public void setupRoom(SceneType sceneType) {
    setupHeader(sceneType);

    if (sceneType == SceneType.SUSPECT_1) {
      setupImage("/images/bob_bar.png");
    } else if (sceneType == SceneType.SUSPECT_2) {
      setupImage("/images/VP.png");
    } else if (sceneType == SceneType.SUSPECT_3) {
      setupImage("/images/file-clerk.png");
    }

    // Adds the chat box
    ChatComponent chatComponent = new ChatComponent(sceneType);
    chatContainer.getChildren().add(chatComponent);
  }

  public void setupImage(String imagePath) {
    Image image = new Image(getClass().getResourceAsStream(imagePath));
    imageContainer.setImage(image);
    if(imagePath == "/images/bob_bar.png") {
      imageContainer.setFitWidth(840);
      imageContainer.setFitHeight(499);
    } else if(imagePath == "/images/VP.png") {
      imageContainer.setFitWidth(750);
      imageContainer.setFitHeight(499);
    } else {
      imageContainer.setFitWidth(830);
      imageContainer.setFitHeight(499);
    }
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

  @Override
  public void restart() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'restart'");
  }

  // public static GameHeader getGameHeader() {
  // return gameHeader;
  // }
}
