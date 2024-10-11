package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nz.ac.auckland.se206.App;
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
  @FXML private static Pane mapOverlay;

  private static Parent overlay;

  private static boolean mapHandler = false;

  /**
   * Opens the map overlay on the suspect room interface. If the overlay is already open, it will
   * not be opened again.
   *
   * @param event the mouse event that triggered the method
   * @throws IOException if there is an I/O error
   */
  public static void openMap(MouseEvent event) throws IOException {
    // Load the overlay
    if (!mapHandler) {
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      Pane mainPane = (Pane) stage.getScene().lookup("#suspectRoom");
      System.out.println(stage);
      System.out.println(mainPane);
      // FXML if needed

      // Set the overlay to the top left corner
      overlay.setLayoutX(0);
      overlay.setLayoutY(100.0);
      SuspectRoomController.mapOverlay =
          (Pane) overlay; // Change to another overlay variable if needed
      mainPane.getChildren().add(overlay);
    }
    mapHandler = true;
  }

  /**
   * Closes the map overlay on the suspect room interface. If the overlay is not open, this method
   * will not do anything.
   */
  public static void closeMap() {
    mapHandler = false;
    if (SuspectRoomController.mapOverlay != null
        && SuspectRoomController.mapOverlay.getParent() != null) {
      ((Pane) SuspectRoomController.mapOverlay.getParent())
          .getChildren()
          .remove(SuspectRoomController.mapOverlay);
    }
  }

  @FXML private Pane headerContainer;
  @FXML private Pane chatContainer;
  @FXML private ImageView imageContainer;
  private ChatComponent chatComponent;

  public void initialize() throws IOException {
    overlay = App.loadFxmlLoader("mapSuspects").load();
  }

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
    } else if (imagePath.equals("/images/VP.png")) {
      imageContainer.setFitWidth(789);
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
