package nz.ac.auckland.se206;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nz.ac.auckland.se206.SceneManager.SceneType;
import nz.ac.auckland.se206.controllers.ChatController;
import nz.ac.auckland.se206.controllers.GameOverController;
import nz.ac.auckland.se206.controllers.GuessingController;
import nz.ac.auckland.se206.controllers.MenuController;
import nz.ac.auckland.se206.controllers.Restartable;
import nz.ac.auckland.se206.controllers.RoomController;
import nz.ac.auckland.se206.controllers.SuspectRoomController;
import nz.ac.auckland.se206.speech.FreeTextToSpeech;

/**
 * This is the entry point of the JavaFX application. This class initializes and runs the JavaFX
 * application.
 */
public class App extends Application {
  private static App app;
  private static Stage stage;

  private static Scene scene;

  public static void restart() {
    try {
      app.start(stage);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static Font getMarkerFont(int size) {
    String filename = App.class.getResource("/fonts/PermanentMarker.ttf").toExternalForm();

    return Font.loadFont(filename, size);
  }

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

  private Map<SceneType, Restartable> controllers;

  /**
   * This method is invoked when the application starts. It loads and shows the "room" scene.
   *
   * @param stage the primary stage of the application
   * @throws IOException if the "src/main/resources/fxml/room.fxml" file is not found
   */
  @Override
  public void start(final Stage stage) throws IOException {
    controllers = new HashMap<>();

    this.initializeScenes();
    this.styleAllButtons();

    App.stage = stage;
    App.app = this;

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

  private void initializeScenes() {
    try {

      FXMLLoader loader = loadFxmlLoader("introduction-scene");
      Parent root = loader.load();
      MenuController menuController = loader.getController();

      SceneManager.addScene(SceneType.INTRO, root);

      controllers.put(SceneType.INTRO, menuController);

      FXMLLoader rootFxml = loadFxmlLoader("room");
      root = rootFxml.load();
      ((RoomController) rootFxml.getController()).setupHeader(SceneType.CRIME);
      SceneManager.addScene(SceneType.CRIME, root);

      controllers.put(SceneType.CRIME, (Restartable) rootFxml.getController());

      // Suspect 1 room
      rootFxml = loadFxmlLoader("suspect-room");
      root = rootFxml.load();
      ((SuspectRoomController) rootFxml.getController()).setupRoom(SceneType.SUSPECT_1);
      SceneManager.addScene(SceneType.SUSPECT_1, root);

      controllers.put(SceneType.SUSPECT_1, (Restartable) rootFxml.getController());

      // Suspect 2 room
      rootFxml = loadFxmlLoader("suspect-room");
      root = rootFxml.load();
      ((SuspectRoomController) rootFxml.getController()).setupRoom(SceneType.SUSPECT_2);
      SceneManager.addScene(SceneType.SUSPECT_2, root);

      controllers.put(SceneType.SUSPECT_2, (Restartable) rootFxml.getController());

      // Suspect 3 room
      rootFxml = loadFxmlLoader("suspect-room");
      root = rootFxml.load();
      ((SuspectRoomController) rootFxml.getController()).setupRoom(SceneType.SUSPECT_3);
      SceneManager.addScene(SceneType.SUSPECT_3, root);

      controllers.put(SceneType.SUSPECT_3, (Restartable) rootFxml.getController());

      FXMLLoader loader2 = loadFxmlLoader("guessing_screen");
      root = loader2.load();
      GuessingController guessingController = loader2.getController();
      menuController.setGuessingController(guessingController);
      // root = loadFxml("guessing_screen");
      SceneManager.addScene(SceneType.PLAYER_EXPLANATION, root);

      controllers.put(SceneType.PLAYER_EXPLANATION, guessingController);

      FXMLLoader loader3 = loadFxmlLoader("game-over");
      root = loader3.load();
      GameOverController gameOverController = loader3.getController();
      SceneManager.addScene(SceneType.FEEDBACK, root);

      controllers.put(SceneType.FEEDBACK, gameOverController);

      gameOverController.setOnRestart(() -> restartGame());

      root = loadFxml("proccessing-submission");
      SceneManager.addScene(SceneType.PROCESSING_SUBMISSION, root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void restartGame() {

    controllers.forEach(
        (sceneType, controller) -> {
          controller.restart();
        });

    changeScene(SceneType.INTRO);
  }

  /** Styles the button with css */
  private void styleAllButtons() {
    String styles = App.getCssUrl("button");
    System.out.println("Styling");

    for (SceneType sceneType : SceneManager.getSceneMap().keySet()) {
      Parent root = SceneManager.getScene(sceneType);

      root.getStylesheets().add(styles);
    }
  }
}
