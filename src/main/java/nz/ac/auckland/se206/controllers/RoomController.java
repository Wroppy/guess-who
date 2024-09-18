package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import nz.ac.auckland.se206.utils.EventCallback;

/**
 * Controller class for the room view. Handles user interactions within the room where the user can
 * chat with customers and guess their profession.
 */
public class RoomController implements HeaderableController {

  @FXML private Rectangle rectAccess;
  @FXML private Rectangle rectLaptop;
  @FXML private Rectangle rectShredder;
  @FXML private Button btnGuess;
  @FXML private Button One;
  @FXML private Button Two;
  @FXML private Button Three;
  @FXML private Button Four;
  @FXML private Button Five;
  @FXML private Button Six;
  @FXML private Button Seven;
  @FXML private Button Eight;
  @FXML private Button Nine;
  @FXML private Label errorMessage;
  @FXML private Label passcodeDisplay;
  @FXML private Pane paneHover1;
  @FXML private Pane paneHover2;
  @FXML private Pane paneHover3;

  @FXML private Pane headerContainer;
  @FXML private Pane room;
  @FXML private Pane accessPad;
  @FXML private Pane accessUnlock;

  private ArrayList<Integer> passcode = new ArrayList<Integer>();
  private boolean unlocked = false;

  private Pane shredderClueOverlay;

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

    paneHover1.setVisible(false);
    paneHover2.setVisible(false);
    paneHover3.setVisible(false);

    setHoverHandlers(rectAccess, paneHover1);
    setHoverHandlers(rectLaptop, paneHover2);
    setHoverHandlers(rectShredder, paneHover3);

    // lblProfession.setText(context.getProfessionToGuess());

    addShredderClue();

    accessPad.setVisible(false);
    accessUnlock.setVisible(false);
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
    accessPad.toFront();
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

  @FXML
  private void handleShredderClueClicked(MouseEvent event) {
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
  private void onCloseButtonClick() {
    accessPad.setVisible(false);
  }

  @FXML
  private void handleAcessPadClick() {
    accessPad.setVisible(true);
    if (!unlocked) {
      accessUnlock.setVisible(true);
      errorMessage.setText("");
      passcodeDisplay.setText("");
      passcode.clear();
    }
  }

  @FXML
  private void setHoverHandlers(Rectangle rectangle, Pane hoverPane) {
    rectangle.setOnMouseEntered(event -> hoverPane.setVisible(true));
    rectangle.setOnMouseExited(event -> hoverPane.setVisible(false));
  }

  @Override
  public void setupHeader(SceneType sceneType) {
    gameHeader = new GameHeader(sceneType);
    this.headerContainer.getChildren().add(gameHeader);
  }

  public void getPasscode(ActionEvent event) {
    if (passcode.size() >= 4) {
      errorMessage.setText("Enter 4 digits only.");
      return;
    } else {
      errorMessage.setText("");
    }
    Button clickedButton = (Button) event.getSource();
    if (clickedButton == One) {
      passcode.add(1);
    } else if (clickedButton == Two) {
      passcode.add(2);
    } else if (clickedButton == Three) {
      passcode.add(3);
    } else if (clickedButton == Four) {
      passcode.add(4);
    } else if (clickedButton == Five) {
      passcode.add(5);
    } else if (clickedButton == Six) {
      passcode.add(6);
    } else if (clickedButton == Seven) {
      passcode.add(7);
    } else if (clickedButton == Eight) {
      passcode.add(8);
    } else if (clickedButton == Nine) {
      passcode.add(9);
    }
    passcodeDisplay.setText("");
    for (int i = 0; i < passcode.size(); i++) {
      passcodeDisplay.setText(passcodeDisplay.getText() + passcode.get(i));
    }
  }

  public void checkPasscode() {
    if (passcode.size() == 4) {
      if (passcode.get(0) == 7
          && passcode.get(1) == 2
          && passcode.get(2) == 6
          && passcode.get(3) == 4) {
        accessUnlock.setVisible(false);
        unlocked = true;
      } else {
        errorMessage.setText("Incorrect. Try again.");
        passcodeDisplay.setText("");
        passcode.clear();
      }
    } else {
      errorMessage.setText("Enter 4 digits.");
    }
  }

  public void clearPasscode() {
    passcode.clear();
    passcodeDisplay.setText("");
    errorMessage.setText("");
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

  public Pane getAccessPad() {
    return accessPad;
  }

  public Pane getAccessUnlock() {
    return accessUnlock;
  }

  public Pane getShredderClueOverlay() {
    return shredderClueOverlay;
  }
}
