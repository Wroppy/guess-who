package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameStateContext;
import nz.ac.auckland.se206.SceneManager.SceneType;
import nz.ac.auckland.se206.components.gameheader.GameHeader;
import nz.ac.auckland.se206.components.shredderclue.ShredderClueComponent;

/**
 * Controller class for the room view. Handles user interactions within the room where the user can
 * chat with customers and guess their profession.
 */
public class RoomController implements HeaderableController {

  @FXML private Rectangle rectAccess;
  @FXML private Rectangle rectLaptop;
  @FXML private Button btnGuess;
  @FXML private ImageView rectHover1;
  @FXML private ImageView rectHover2;

  @FXML private Pane headerContainer;
  @FXML private Pane shredderClueOverlay;
  @FXML private Pane room;
  @FXML private Pane accessPad;

  private static boolean isFirstTimeInit = true;
  private static GameStateContext context = new GameStateContext();

  private GameHeader gameHeader;

  /**
   * Initializes the room view. If it's the first time initialization, it will provide instructions
   * via text-to-speech.
   */
  @FXML
  public void initialize() {
    if (isFirstTimeInit) {
      isFirstTimeInit = false;
    }

    rectHover1.setVisible(false);
    rectHover2.setVisible(false);

    rectAccess.setOnMouseEntered(
        (MouseEvent event) -> {
          rectHover1.setVisible(true); // Show the image when the mouse enters the rectangle
        });

    rectAccess.setOnMouseExited(
        (MouseEvent event) -> {
          rectHover1.setVisible(false); // Hide the image when the mouse exits the rectangle
        });

    rectLaptop.setOnMouseEntered(
        (MouseEvent event) -> {
          rectHover2.setVisible(true);
        });

    rectLaptop.setOnMouseExited(
        (MouseEvent event) -> {
          rectHover2.setVisible(false);
        });

    // lblProfession.setText(context.getProfessionToGuess());

    ShredderClueComponent shredderClueComponent = new ShredderClueComponent();
    this.shredderClueOverlay.getChildren().add(shredderClueComponent);

    shredderClueComponent.hide();
    accessPad.setVisible(false);
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
  private void onCloseButtonClick() {
    accessPad.setVisible(false);
  }

  @FXML
  private void handleAcessPadClick() {
    accessPad.setVisible(true);
  }

  @Override
  public void setupHeader(SceneType sceneType) {
    gameHeader = new GameHeader(sceneType);
    this.headerContainer.getChildren().add(gameHeader);
  }

  @FXML
  private void showLaptop(MouseEvent event) throws IOException {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Pane mainPane = (Pane) stage.getScene().lookup("#room");
    Parent overlay = App.loadFxmlLoader("laptop-clue").load();
    overlay.setLayoutX(140.0);
    overlay.setLayoutY(247.0);
    mainPane.getChildren().add(overlay);
  }
}
