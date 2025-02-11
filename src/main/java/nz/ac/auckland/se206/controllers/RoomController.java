package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameStateContext;
import nz.ac.auckland.se206.SceneManager.SceneType;
import nz.ac.auckland.se206.SoundManager;
import nz.ac.auckland.se206.components.accesspadclue.AccessPadClue;
import nz.ac.auckland.se206.components.gameheader.GameHeader;
import nz.ac.auckland.se206.components.shredderclue.ShredderClueComponent;
import nz.ac.auckland.se206.utils.EventCallback;

/**
 * Controller class for the room view. Handles user interactions within the room where the user can
 * chat with customers and guess their profession.
 */
public class RoomController implements HeaderableController, Restartable {
  private static boolean isFirstTimeInit = true;
  private static GameStateContext context = new GameStateContext();
  private static boolean accessClue = false;

  private static GameHeader gameHeader;

  private static Pane mapOverlay;
  private static boolean mapHandler = false;
  private static Parent overlay;

  public static GameStateContext getContext() {
    return context;
  }

  public static GameHeader getGameHeader() {
    return gameHeader;
  }

  public static boolean isAccessClue() {
    return accessClue;
  }

  /**
   * Opens the map overlay by loading the map overlay FXML file, setting the overlay to the top left
   * corner, and adding it to the main pane.
   *
   * @param event the mouse event that triggered the opening of the map overlay
   * @throws IOException if there is an I/O error
   */
  public static void openMap(MouseEvent event) throws IOException {
    // Load the overlay
    if (!mapHandler) {
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      Pane mainPane = (Pane) stage.getScene().lookup("#room");
      System.out.println(stage);
      System.out.println(mainPane);
      // Parent overlay = App.loadFxmlLoader("mapSuspects").load(); // Change to the appropriate
      // FXML if needed

      // Set the overlay to the top left corner
      overlay.setLayoutX(0);
      overlay.setLayoutY(100.0);
      RoomController.mapOverlay = (Pane) overlay; // Change to another overlay variable if needed
      mainPane.getChildren().add(overlay);
    }
    mapHandler = true;
  }

  /**
   * Close the map on the ui by hiding it on the other scenes. Only closes the map if it is open.
   */
  public static void closeMap() {
    mapHandler = false;
    if (RoomController.mapOverlay != null && RoomController.mapOverlay.getParent() != null) {
      ((Pane) RoomController.mapOverlay.getParent())
          .getChildren()
          .remove(RoomController.mapOverlay);
    }
  }

  @FXML private Rectangle rectAccess;
  @FXML private Rectangle rectLaptop;
  @FXML private Rectangle rectShredder;
  @FXML private Button btnGuess;

  @FXML private Pane paneHover1;
  @FXML private Pane paneHover2;
  @FXML private Pane paneHover3;
  @FXML private Pane accessUnlock;

  @FXML private Pane headerContainer;
  @FXML private Pane room;

  private AccessPadClue accessPad;
  private Pane shredderClueOverlay;
  private Pane laptopOverlay;

  private boolean firstShredderClue = true;
  private boolean firstAccessPadClue = true;
  private boolean firstLaptopClue = true;

  /**
   * Initializes the room view. If it's the first time initialization, it will provide instructions
   * via text-to-speech.
   *
   * @throws IOException if there is an I/O error
   */
  @FXML
  public void initialize() throws IOException {
    // If it is the first time the room is initialized, play the sound
    if (isFirstTimeInit) {
      isFirstTimeInit = false;
    }

    // Set up the hover handlers for the rectangles
    paneHover1.setVisible(false);
    paneHover2.setVisible(false);
    paneHover3.setVisible(false);

    // Set up the hover handlers for the rectangles
    setHoverHandlers(rectAccess, paneHover1);
    setHoverHandlers(rectLaptop, paneHover2);
    setHoverHandlers(rectShredder, paneHover3);

    // Add the shredder clue overlay
    addShredderClue();
    addAccessPadClue();
    overlay = App.loadFxmlLoader("mapSuspects").load();
  }

  private void addAccessPadClue() {
    accessPad = new AccessPadClue();
    accessPad.setLayoutX(0);
    accessPad.setLayoutY(100);

    this.room.getChildren().add(accessPad);
  }

  private void addShredderClue() {
    EventCallback onClose = e -> shredderClueOverlay.setVisible(false);

    ShredderClueComponent shredderClueComponent = new ShredderClueComponent(onClose);

    shredderClueOverlay = new Pane();
    shredderClueOverlay.setPrefWidth(789);
    shredderClueOverlay.setPrefHeight(599 - 100);
    shredderClueOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8);");
    this.room.getChildren().add(shredderClueOverlay);
    shredderClueOverlay.setLayoutX(0);
    shredderClueOverlay.setLayoutY(100);

    shredderClueOverlay.getChildren().add(shredderClueComponent);

    // Center the shredder clue component
    double x = (789 - shredderClueComponent.getPrefWidth()) / 2;
    double y = (599 - 100 - shredderClueComponent.getPrefHeight()) / 2;
    shredderClueComponent.setLayoutX(x);
    shredderClueComponent.setLayoutY(y);

    shredderClueOverlay.setVisible(false);
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    System.out.println("Key " + event.getCode() + " pressed");
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {
    System.out.println("Key " + event.getCode() + " released");
  }

  /**
   * Handles the mouse clicked event on the access pad, which displays the access pad clue.
   *
   * @param event the mouse event
   */
  @FXML
  private void handleShredderClueClicked(MouseEvent event) {
    if (firstShredderClue) {
      SoundManager.playSound("Shredder.mp3");
      firstShredderClue = false;
    }

    shredderClueOverlay.setVisible(true);
  }

  /**
   * Handles mouse clicks on rectangles representing people in the room.
   *
   * @param event the mouse event triggered by clicking a rectangle
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void handleRectangleClick(MouseEvent event) throws IOException {
    Rectangle clickedRectangle = (Rectangle) event.getSource();
    context.handleRectangleClick(event, clickedRectangle.getId());
  }

  /**
   * Handles the guess button click event.
   *
   * @param event the action event triggered by clicking the guess button
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void handleGuessClick(ActionEvent event) throws IOException {
    context.handleGuessClick();
  }

  @FXML
  private void setHoverHandlers(Rectangle rectangle, Pane hoverPane) {
    rectangle.setOnMouseEntered(event -> hoverPane.setVisible(true));
    rectangle.setOnMouseExited(event -> hoverPane.setVisible(false));
  }

  @Override
  public void setupHeader(SceneType sceneType) {
    gameHeader = new GameHeader(sceneType, this);
    this.headerContainer.getChildren().add(gameHeader);
  }

  @FXML
  private void showLaptop(MouseEvent event) throws IOException {
    // If it is the first time the laptop is clicked, play the sound
    if (firstLaptopClue) {
      SoundManager.playSound("Laptop.mp3");
      firstLaptopClue = false;
    }
    // Load the laptop overlay
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Pane mainPane = (Pane) stage.getScene().lookup("#room");
    Parent overlay = App.loadFxmlLoader("laptop-clue").load();

    // Set the overlay to the top left corner
    overlay.setLayoutX(0);
    overlay.setLayoutY(100.0);
    this.laptopOverlay = (Pane) overlay;
    mainPane.getChildren().add(overlay);
  }

  @FXML
  private void handleAcessPadClick(MouseEvent event) {
    if (firstAccessPadClue) {
      SoundManager.playSound("AccessPad.mp3");
      firstAccessPadClue = false;
    }

    accessPad.setVisible(true);
  }

  public Pane getAccessPad() {
    return accessPad;
  }

  public Pane getAccessUnlock() {
    return accessPad.getAccessUnlock();
  }

  public Pane getShredderClueOverlay() {
    return shredderClueOverlay;
  }

  public Pane getLaptopOverlay() {
    return laptopOverlay;
  }

  @FXML
  public void removeLaptopOverlay() {
    if (laptopOverlay != null && laptopOverlay.getParent() != null) {
      ((Pane) laptopOverlay.getParent()).getChildren().remove(laptopOverlay);
    }
  }

  @Override
  public void restart() {
    // Recreates the access pad clue
    this.room.getChildren().remove(accessPad);
    addAccessPadClue();

    // Recreates the shredder clue overlay
    this.room.getChildren().remove(shredderClueOverlay);
    addShredderClue();

    LaptopController.restart();
    gameHeader.restartTalkedTo();

    // Remove the laptop overlay if it is present
    if (laptopOverlay != null && laptopOverlay.getParent() != null) {
      ((Pane) laptopOverlay.getParent()).getChildren().remove(laptopOverlay);
    }
  }
}
