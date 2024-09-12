package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nz.ac.auckland.se206.SceneManager.SceneType;
import nz.ac.auckland.se206.controllers.ChatController;
import nz.ac.auckland.se206.speech.FreeTextToSpeech;

/**
 * This is the entry point of the JavaFX application. This class initializes and runs the JavaFX
 * application.
 */
public class App extends Application {

  private static Scene scene;

  /**
   * The main method that launches the JavaFX application.
   *
   * @param args the command line arguments
   */
  public static void main(final String[] args) {
    launch();
  }

  /**
   * Sets the root of the scene to the specified FXML file.
   *
   * @param fxml the name of the FXML file (without extension)
   * @throws IOException if the FXML file is not found
   */
  public static void setRoot(String fxml) throws IOException {
    scene.setRoot(loadFxml(fxml));
  }

  /**
   * Loads the FXML file and returns the associated node. The method expects that the file is
   * located in "src/main/resources/fxml".
   *
   * @param fxml the name of the FXML file (without extension)
   * @return the root node of the FXML file
   * @throws IOException if the FXML file is not found
   */
  private static Parent loadFxml(final String fxml) throws IOException {
    return new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml")).load();
  }

  /**
   * Opens the chat view of a specified
   *
   * @param event the mouse event that triggered the method
   * @param profession the profession to set in the chat controller
   * @throws IOException if the FXML file is not found
   */
  public static void openChat(MouseEvent event, String profession) throws IOException {
    FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/chat.fxml"));
    Parent root = loader.load();

    ChatController chatController = loader.getController();
    chatController.setProfession();

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * This method is invoked when the application starts. It loads and shows the "room" scene.
   *
   * @param stage the primary stage of the application
   * @throws IOException if the "src/main/resources/fxml/room.fxml" file is not found
   */
  @Override
  public void start(final Stage stage) throws IOException {
    this.setupScenes();

    Parent root = SceneManager.getScene(SceneType.CRIME);
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    stage.setOnCloseRequest(event -> handleWindowClose(event));
    root.requestFocus();
  }

  private void handleWindowClose(WindowEvent event) {
    FreeTextToSpeech.deallocateSynthesizer();
  }

  public static void changeScene(SceneType sceneType) {
    Parent root = SceneManager.getScene(sceneType);
    scene.setRoot(root);
  }
  
  private void setupScenes() {
    try {
      Parent root = loadFxml("introduction-scene");
      SceneManager.addScene(SceneType.INTRO, root);

      root = loadFxml("room");
      SceneManager.addScene(SceneType.CRIME, root);

      root = loadFxml("suspect-1-room");
      SceneManager.addScene(SceneType.SUSPECT_1, root);

      root = loadFxml("suspect-2-room");
      SceneManager.addScene(SceneType.SUSPECT_2, root);

      root = loadFxml("suspect-3-room");
      SceneManager.addScene(SceneType.SUSPECT_3, root);

      root = loadFxml("explanation-room");
      SceneManager.addScene(SceneType.PLAYER_EXPLANATION, root);

      root = loadFxml("player-feedback");
      SceneManager.addScene(SceneType.FEEDBACK, root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
