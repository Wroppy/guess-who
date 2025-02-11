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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.components.shredderclue.Coordinate;
import nz.ac.auckland.se206.utils.CoordinateCallback;
import nz.ac.auckland.se206.utils.EventCallback;

/** A component that displays the access pad clue for the user to interact with. */
public class AccessPadClue extends Pane {
  private static boolean unlocked = false;

  /**
   * Checks if the access pad is unlocked.
   *
   * @return True if the access pad is unlocked, false otherwise
   */
  public static boolean isUnlocked() {
    return unlocked;
  }

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

  private final int width = 137;
  private ImageView currentlySelected;

  private DustingStage dustingStage = DustingStage.POWDER;

  @FXML private ImageView border;
  @FXML private Pane accessPadUnlocked;
  @FXML private Button closeButton;

  /** Creates a new access pad clue component. */
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

  /** Initializes the access pad clue component. */
  @FXML
  private void initialize() {

    // Create event callbacks for mouse click and release events on the dust and brush
    EventCallback dustClick = e -> onDustClick(e);
    EventCallback brushClick = e -> onBrushClick(e);

    EventCallback dustRelease = e -> onDustMouseRelease(e);
    EventCallback brushRelease = e -> onBrushMouseRelease(e);

    // Make the dust and brush draggable with the defined click and release handlers
    setDraggable(dust, dustClick, dustRelease);
    setDraggable(brush, brushClick, brushRelease);

    // Set the opacity of the keypad powder to 0 (invisible)
    keypadPowder.setOpacity(0);

    // Hide any existing fingerprint prints
    hidePrints();

    // Sets the opacity of the fingerprint to 0.5
    fingerprint1.setOpacity(0.5);
    fingerprint2.setOpacity(0.5);
    fingerprint3.setOpacity(0.5);
    fingerprint4.setOpacity(0.5);

    // Update the label and progress bar
    changeLabel();
    changeProgressBar(0);

    // Load an image for the close button and set its properties
    Image image = new Image(App.class.getResource("/images/x-button.png").toExternalForm());
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(20);
    imageView.preserveRatioProperty().setValue(true);
    closeButton.setGraphic(imageView);
  }

  /** Clears the passcode by resetting all the arrays and the text inputs. */
  @FXML
  private void clearPasscode() {
    passCode.clear();
    passCodeDisplay.setText("");
    errorMessage.setText("");
  }

  /**
   * Gets the passcode from the button clicked, and appends it to the passcode display.
   *
   * @param event The event triggered by the button
   */
  public void getPasscode(ActionEvent event) {
    // Check if the passcode is correct
    if (passCode.size() >= 3) {
      errorMessage.setText("Enter 3 digits only.");
      return;
    }
    // Check if the passcode is correct
    errorMessage.setText("");
    Button clickedButton = (Button) event.getSource();

    String buttonText = clickedButton.getText().toLowerCase();

    // Append the text to the passcode display
    passCodeDisplay.appendText(buttonText);
    passCode.add(Integer.parseInt(buttonText));
  }

  /** Checks if the passcode is correct. */
  public void checkPasscode() {
    // Check if the passcode is correct
    if (passCode.size() == 3) {
      // Check if the passcode is correct
      if (passCode.get(0) == 7 && passCode.get(1) == 2 && passCode.get(2) == 6) {
        accessUnlock.setVisible(false);
        unlocked = true;
        hideTools();
      } else {
        // Incorrect passcode
        errorMessage.setText("Incorrect. Try again.");
        passCodeDisplay.setText("");
        passCode.clear();
      }
    } else {
      errorMessage.setText("Enter 3 digits.");
    }
  }

  /** Handles the click event of the access pad. */
  @FXML
  private void handleAcessPadClick() {
    // Check if the access pad is unlocked
    accessPad.setVisible(true);
    if (!unlocked) {
      // Show the access unlock pane
      accessUnlock.setVisible(true);
      errorMessage.setText("");
      passCodeDisplay.setText("");
      passCode.clear();
    }
  }

  /** Handles the click event of the close button. */
  @FXML
  private void onCloseButtonClick() {
    this.setVisible(false);
  }

  /**
   * Sets the dusting stage and changes the label to the description of the stage.
   *
   * @param stage The dusting stage
   */
  public void setStage(DustingStage stage) {
    this.dustingStage = stage;
    changeLabel();
  }

  /**
   * Sets the draggable property of the node.
   *
   * @param node The node to set the draggable property
   * @param onMouseClick The callback function for when the node is clicked
   * @param onMouseRelease The callback function for when the node is released
   */
  private void setDraggable(Node node, EventCallback onMouseClick, EventCallback onMouseRelease) {
    Coordinate anchor = new Coordinate(node.getLayoutX(), node.getLayoutY());

    CoordinateCallback onMouseMove = c -> onMove(c);

    new Draggable(node, anchor, onMouseClick, onMouseRelease, onMouseMove);
  }

  /**
   * Handles the click event of the dust.
   *
   * @param event The event triggered by the dust
   */
  private void onDustClick(Event event) {
    currentlySelected = dust;
  }

  /**
   * Handles the click event of the brush.
   *
   * @param event The event triggered by the brush
   */
  private void onBrushClick(Event event) {
    currentlySelected = brush;
  }

  /**
   * Handles the release event of the dust.
   *
   * @param event The event triggered by the dust
   */
  private void onDustMouseRelease(Event event) {
    currentlySelected = null;
  }

  /**
   * Handles the release event of the brush.
   *
   * @param event The event triggered by the brush
   */
  private void onBrushMouseRelease(Event event) {
    currentlySelected = null;
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
    progressPane.setPrefWidth(width * progress / 100);
  }

  /** Shows the tools for the access pad. The tools include the dusting powder and the brush. */
  public void showTools() {
    int offset = 68;
    fingerPrintingPane.setVisible(true);
    // Moves the border, accessPadUnlocked, closeButton and accessUnlock to the right
    border.setLayoutX(border.getLayoutX() + offset);
    accessPadUnlocked.setLayoutX(accessPadUnlocked.getLayoutX() + offset);
    closeButton.setLayoutX(closeButton.getLayoutX() + offset);
    accessUnlock.setLayoutX(accessUnlock.getLayoutX() + offset);
  }

  /** Hides the tools for the access pad. The tools include the dusting powder and the brush. */
  public void hideTools() {
    int offset = 68;
    fingerPrintingPane.setVisible(false);

    // Moves the border, accessPadUnlocked, closeButton and accessUnlock to the left
    border.setLayoutX(border.getLayoutX() - offset);
    accessPadUnlocked.setLayoutX(accessPadUnlocked.getLayoutX() - offset);
    closeButton.setLayoutX(closeButton.getLayoutX() - offset);
    accessUnlock.setLayoutX(accessUnlock.getLayoutX() - offset);
  }

  /** Shows the fingerprints on the keypad. */
  private void showPrints() {
    fingerprint1.setVisible(true);
    fingerprint2.setVisible(true);
    fingerprint3.setVisible(true);
    fingerprint4.setVisible(true);
  }

  /** Hides the fingerprints on the keypad. */
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
}
