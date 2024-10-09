package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.SceneManager.SceneType;
import nz.ac.auckland.se206.components.chatview.ChatComponent;
import nz.ac.auckland.se206.components.gameheader.GameHeader;

/**
 * Controls the suspect room interface, managing the header, image display, and chat components for
 * each suspect.
 *
 * <p>This class implements the HeaderableController and Restartable interfaces, allowing for header
 * setup and restart functionality. It configures the room based on the selected suspect and manages
 * the chat interactions.
 */
public class SuspectRoomController implements HeaderableController, Restartable {
  public static GameHeader gameHeader1;
  public static GameHeader gameHeader2;
  public static GameHeader gameHeader3;

  @FXML private Pane headerContainer;
  @FXML private Pane chatContainer;
  @FXML private ImageView imageContainer;

  private ChatComponent chatComponent;

  public void initialize() {}

  /**
   * Configures the room based on the specified scene type.
   *
   * @param sceneType The type of scene to set up, determining which suspect's image and chat
   *     component to display.
   */
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
    chatComponent = new ChatComponent(sceneType);
    chatContainer.getChildren().add(chatComponent);
  }

  /**
   * Sets up the image for the room based on the provided image path.
   *
   * @param imagePath The path to the image resource to be displayed.
   */
  public void setupImage(String imagePath) {

    // Sets up the image for the room with the correct dimensions
    Image image = new Image(getClass().getResourceAsStream(imagePath));
    imageContainer.setImage(image);
    if (imagePath == "/images/bob_bar.png") {
      imageContainer.setFitWidth(840);
      imageContainer.setFitHeight(499);
    } else if (imagePath == "/images/VP.png") {
      imageContainer.setFitWidth(750);
      imageContainer.setFitHeight(499);
    } else {
      imageContainer.setFitWidth(830);
      imageContainer.setFitHeight(499);
    }
  }

  @Override
  public void setupHeader(SceneType sceneType) {
    // Sets up the game header
    if (sceneType == SceneType.SUSPECT_1) {
      gameHeader1 = new GameHeader(sceneType); // Creates a new game header
      this.headerContainer.getChildren().add(gameHeader1);
    } else if (sceneType == SceneType.SUSPECT_2) {
      gameHeader2 = new GameHeader(sceneType); // Creates a new game header
      this.headerContainer.getChildren().add(gameHeader2);
    } else if (sceneType == SceneType.SUSPECT_3) {
      // Creates a new game header
      gameHeader3 = new GameHeader(sceneType);
      this.headerContainer.getChildren().add(gameHeader3);
    }
  }

  @Override
  public void restart() {
    // Restarts the chat component
    chatComponent.restart();

    // Restarts the game header
    if (gameHeader1 != null) {
      System.out.println("Restarting gameHeader1");
      gameHeader1.restartTalkedTo();
    }

    // Restarts the game header
    if (gameHeader2 != null) {
      System.out.println("Restarting gameHeader2");
      gameHeader2.restartTalkedTo();
    }

    // Restarts the game header
    if (gameHeader3 != null) {
      System.out.println("Restarting gameHeader3");
      gameHeader3.restartTalkedTo();
    }
  }
}
