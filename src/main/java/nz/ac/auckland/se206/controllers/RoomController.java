package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.GameStateContext;
import nz.ac.auckland.se206.SceneManager.SceneType;
import nz.ac.auckland.se206.components.gameheader.GameHeader;
import nz.ac.auckland.se206.components.shredderclue.ShredderClueComponent;

/**
 * Controller class for the room view. Handles user interactions within the room where the user can
 * chat with customers and guess their profession.
 */
public class RoomController implements HeaderableController {

  @FXML private Rectangle rectCashier;
  @FXML private Rectangle rectPerson1;
  @FXML private Rectangle rectPerson2;
  @FXML private Rectangle rectPerson3;
  @FXML private Rectangle rectWaitress;
  @FXML private Button btnGuess;

  @FXML private Pane headerContainer;
  @FXML private Pane shredderClueOverlay;
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
}
