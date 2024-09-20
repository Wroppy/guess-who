package nz.ac.auckland.se206.components.accesspadclue;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.components.shredderclue.Coordinate;
import nz.ac.auckland.se206.utils.CoordinateCallback;
import nz.ac.auckland.se206.utils.EventCallback;

public class AccessPadClue extends Pane {
  private ArrayList<Integer> passCode = new ArrayList<Integer>();

  @FXML private Label errorMessage;
  @FXML private TextField passCodeDisplay;
  @FXML private Pane accessPad;
  @FXML private Pane accessUnlock;

  @FXML private Label progressLabel;

  @FXML private ImageView dust;
  @FXML private ImageView brush;
  @FXML private ImageView keypadPowder;
  @FXML private Pane fingerPrintingPane;

  @FXML private ImageView fingerprint1;
  @FXML private ImageView fingerprint2;
  @FXML private ImageView fingerprint3;
  @FXML private ImageView fingerprint4;
  @FXML private Pane progressPane;

  private static boolean unlocked = false;
  private final int WIDTH = 137;
  private ImageView currentlySelected;

  private DustingStage dustingStage = DustingStage.POWDER;

  @FXML private ImageView border;
  @FXML private Pane accessPadUnlocked;
  @FXML private Button closeButton;

  public AccessPadClue() {
    super();
    unlocked = false;

    try {
      FXMLLoader loader = App.loadFxmlLoader("access-pad-clue");
      loader.setRoot(this);
      loader.setController(this);
      loader.load();

    } catch (IOException e) {
      e.printStackTrace();
    }

    this.setVisible(false);
  }

  @FXML
  private void initialize() {

    EventCallback dustClick = e -> onDustClick(e);
    EventCallback brushClick = e -> onBrushClick(e);

    EventCallback dustRelease = e -> onDustMouseRelease(e);
    EventCallback brushRelease = e -> onBrushMouseRelease(e);

    setDraggable(dust, dustClick, dustRelease);
    setDraggable(brush, brushClick, brushRelease);

    keypadPowder.setOpacity(0);

    hidePrints();

    // Sets the opacity of the fingerprint to 0.5
    fingerprint1.setOpacity(0.5);
    fingerprint2.setOpacity(0.5);
    fingerprint3.setOpacity(0.5);
    fingerprint4.setOpacity(0.5);

    changeLabel();
    changeProgressBar(0);
  }

  @FXML
  private void clearPasscode() {
    passCode.clear();
    passCodeDisplay.setText("");
    errorMessage.setText("");
  }

  public void getPasscode(ActionEvent event) {
    if (passCode.size() >= 3) {
      errorMessage.setText("Enter 3 digits only.");
      return;
    }
    errorMessage.setText("");
    Button clickedButton = (Button) event.getSource();

    String buttonText = clickedButton.getText().toLowerCase();

    passCodeDisplay.appendText(buttonText);
    passCode.add(Integer.parseInt(buttonText));
  }

  public void checkPasscode() {
    if (passCode.size() == 3) {
      if (passCode.get(0) == 7 && passCode.get(1) == 2 && passCode.get(2) == 6) {
        accessUnlock.setVisible(false);
        unlocked = true;
        hideTools();
      } else {
        errorMessage.setText("Incorrect. Try again.");
        passCodeDisplay.setText("");
        passCode.clear();
      }
    } else {
      errorMessage.setText("Enter 3 digits.");
    }
  }

  @FXML
  private void handleAcessPadClick() {
    accessPad.setVisible(true);
    if (!unlocked) {
      accessUnlock.setVisible(true);
      errorMessage.setText("");
      passCodeDisplay.setText("");
      passCode.clear();
    }
  }

  @FXML
  private void onCloseButtonClick() {
    this.setVisible(false);
  }

  public void setStage(DustingStage stage) {
    this.dustingStage = stage;
    changeLabel();
  }

  private void setDraggable(Node node, EventCallback onMouseClick, EventCallback onMouseRelease) {
    Coordinate anchor = new Coordinate(node.getLayoutX(), node.getLayoutY());

    CoordinateCallback onMouseMove = c -> onMove(c);

    new Draggable(node, anchor, onMouseClick, onMouseRelease, onMouseMove);
  }

  private void onDustClick(Event event) {
    currentlySelected = dust;
  }

  private void onBrushClick(Event event) {
    currentlySelected = brush;
  }

  private void onDustMouseRelease(Event event) {
    currentlySelected = null;
    if (dustingStage == DustingStage.POWDER) {}
  }

  private void onBrushMouseRelease(Event event) {
    currentlySelected = null;
    if (dustingStage == DustingStage.BRUSH) {}
  }

  private void onMove(Coordinate center) {
    handleDustMouseMove(center);
  }

  private void handleDustMouseMove(Coordinate center) {
    if (currentlySelected == null) {
      return;
    }

    double x = 343;
    double y = 144;
    Coordinate keypadDustTopLeft = new Coordinate(x, y);

    Coordinate keypadDustBottomRight =
        new Coordinate(
            x + keypadPowder.getLayoutBounds().getWidth(),
            y + keypadPowder.getLayoutBounds().getHeight());

    System.out.println(keypadDustTopLeft + " " + keypadDustBottomRight);

    if (dustingStage == DustingStage.POWDER) {
      // Check if the dust is on the keypad
      if (!center.isInsideRectangle(keypadDustTopLeft, keypadDustBottomRight)) {
        return;
      }

      if (currentlySelected != dust) {
        return;
      }
      if (keypadPowder.getOpacity() >= 1) {
        dustingStage = DustingStage.BRUSH;
        changeLabel(0);
        changeProgressBar(0);

        return;
      }

      keypadPowder.setOpacity(keypadPowder.getOpacity() + 0.005);
      changeLabel((int) (keypadPowder.getOpacity() * 100));
      changeProgressBar((int) (keypadPowder.getOpacity() * 100));

      //
    } else if (dustingStage == DustingStage.BRUSH) {
      if (currentlySelected != brush) {
        return;
      }

      if (!center.isInsideRectangle(keypadDustTopLeft, keypadDustBottomRight)) {
        return;
      }

      // Decrease the opacity of the dust
      if (keypadPowder.getOpacity() <= 0) {
        dustingStage = DustingStage.REVEAL;

        // Shows the prints
        showPrints();
        changeLabel();
        return;
      }

      keypadPowder.setOpacity(keypadPowder.getOpacity() - 0.005);
      changeLabel((int) ((1 - keypadPowder.getOpacity()) * 100));
      changeProgressBar((int) ((1 - keypadPowder.getOpacity()) * 100));
    }
  }

  private void changeProgressBar(int progress) {
    progressPane.setPrefWidth(WIDTH * progress / 100);
  }

  public void showTools() {
    int offset = 68;
    fingerPrintingPane.setVisible(true);
    // Moves the border, accessPadUnlocked, closeButton and accessUnlock to the right
    border.setLayoutX(border.getLayoutX() + offset);
    accessPadUnlocked.setLayoutX(accessPadUnlocked.getLayoutX() + offset);
    closeButton.setLayoutX(closeButton.getLayoutX() + offset);
    accessUnlock.setLayoutX(accessUnlock.getLayoutX() + offset);
  }

  public void hideTools() {
    int offset = 68;
    fingerPrintingPane.setVisible(false);

    // Moves the border, accessPadUnlocked, closeButton and accessUnlock to the left
    border.setLayoutX(border.getLayoutX() - offset);
    accessPadUnlocked.setLayoutX(accessPadUnlocked.getLayoutX() - offset);
    closeButton.setLayoutX(closeButton.getLayoutX() - offset);
    accessUnlock.setLayoutX(accessUnlock.getLayoutX() - offset);
  }

  private void showPrints() {
    fingerprint1.setVisible(true);
    fingerprint2.setVisible(true);
    fingerprint3.setVisible(true);
    fingerprint4.setVisible(true);
  }

  private void hidePrints() {
    fingerprint1.setVisible(false);
    fingerprint2.setVisible(false);
    fingerprint3.setVisible(false);
    fingerprint4.setVisible(false);
  }

  private void changeLabel() {
    progressLabel.setText("Progress: " + dustingStage.getDescription());
  }

  private void changeLabel(int progress) {
    progressLabel.setText("Progress: " + dustingStage.getDescription() + " " + progress + "%");
  }

  public Pane getAccessUnlock() {
    return accessUnlock;
  }

  public static boolean isUnlocked() {
    return unlocked;
  }
}
