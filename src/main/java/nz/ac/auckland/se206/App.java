package nz.ac.auckland.se206;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nz.ac.auckland.se206.SceneManager.SceneType;
import nz.ac.auckland.se206.controllers.ChatController;
import nz.ac.auckland.se206.controllers.GuessingController;
import nz.ac.auckland.se206.controllers.HeaderableController;
import nz.ac.auckland.se206.controllers.MenuController;
import nz.ac.auckland.se206.controllers.SuspectRoomController;
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

  public static FXMLLoader loadFxmlLoader(final String fxml) throws IOException {
    return new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml"));
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

    Parent root = SceneManager.getScene(SceneType.INTRO);
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    stage.setOnCloseRequest(event -> handleWindowClose(event));
    root.requestFocus();

    stage.setOnCloseRequest(
        event -> {
          if (MenuController.gameTimer != null) {
            MenuController.gameTimer.stop();
          }
          Platform.exit();
          System.exit(0);
        });
  }

  private void handleWindowClose(WindowEvent event) {
    FreeTextToSpeech.deallocateSynthesizer();
  }

  public static String getCssUrl(String filename) {
    return App.class.getResource("/css/" + filename + ".css").toExternalForm();
  }

  /**
   * Given a filename, returns the text data from the file as a string.
   *
   * @param filename
   * @return
   */
  public static String getData(String filename) {
    URL url = App.class.getResource("/data/" + filename);
    try {

      return new String(Files.readAllBytes(Paths.get(url.toURI())), "UTF-8");
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void changeScene(SceneType sceneType) {
    Parent root = SceneManager.getScene(sceneType);
    scene.setRoot(root);
    Stage stage = (Stage) scene.getWindow();
    stage.sizeToScene();
  }

  private void setupScenes() {
    try {


      FXMLLoader loader = loadFxmlLoader("introduction-scene");
      Parent root = loader.load();
      MenuController menuController = loader.getController();
      // Parent root = loadFxml("introduction-scene");
      SceneManager.addScene(SceneType.INTRO, root);

      FXMLLoader rootFXML = loadFxmlLoader("room");
      root = rootFXML.load();
      ((HeaderableController) rootFXML.getController()).setupHeader(SceneType.CRIME);
      SceneManager.addScene(SceneType.CRIME, root);

      // Suspect 1 room
      rootFXML = loadFxmlLoader("suspect-room");
      root = rootFXML.load();
      ((SuspectRoomController) rootFXML.getController()).setupRoom(SceneType.SUSPECT_1);
      SceneManager.addScene(SceneType.SUSPECT_1, root);

      // Suspect 2 room
      rootFXML = loadFxmlLoader("suspect-room");
      root = rootFXML.load();
      ((SuspectRoomController) rootFXML.getController()).setupRoom(SceneType.SUSPECT_2);
      SceneManager.addScene(SceneType.SUSPECT_2, root);

      // Suspect 3 room
      rootFXML = loadFxmlLoader("suspect-room");
      root = rootFXML.load();
      ((SuspectRoomController) rootFXML.getController()).setupRoom(SceneType.SUSPECT_3);
      SceneManager.addScene(SceneType.SUSPECT_3, root);

      FXMLLoader loader2 = loadFxmlLoader("guessing_screen");
      root = loader2.load();
      GuessingController guessingController = loader2.getController();
      menuController.setGuessingController(guessingController);
      // root = loadFxml("guessing_screen");
      SceneManager.addScene(SceneType.PLAYER_EXPLANATION, root);

      root = loadFxml("game-over");
      SceneManager.addScene(SceneType.FEEDBACK, root);

      root = loadFxml("proccessing-submission");
      SceneManager.addScene(SceneType.PROCESSING_SUBMISSION, root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
