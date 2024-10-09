package nz.ac.auckland.se206.components.shredderclue;

import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.utils.EventCallback;

public class ShredderClueComponent extends Pane {
  private static boolean paperClue = false;

  /**
   * Returns whether the paper clue has been completed.
   *
   * @return True if the paper clue has been completed, false otherwise
   */
  public static boolean isPaperClue() {
    return paperClue;
  }

  // Sets the size of the clue rectangles, with aspect ratio 2200:283
  private final double clueHeight = 220;
  private final double clueWidth = clueHeight * 413 / 1130;
  private final int clues = 6;
  private final int indicatorOffset = 6;

  @FXML private Pane shredderPane;
  @FXML private ImageView selectIndicator;
  @FXML private Label completedMessage;
  @FXML private Label confidentialLabel;
  @FXML private Button closeButton;

  private EventCallback onClose;

  private ShredderBoxIndicator indicator;

  // Map for each rectangle to the paper that is placed on it
  private Map<ShredderBox, ShredderPaper> paperMap;

  public ShredderClueComponent(EventCallback onClose) {
    paperClue = false;
    paperMap = new HashMap<>();
    this.onClose = onClose;

    // Load the FXML file of the clue
    try {
      FXMLLoader loader = App.loadFxmlLoader("shredder-clue");

      loader.setRoot(this);
      loader.setController(this);

      loader.load();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void setupIndicator() {
    selectIndicator.setFitHeight(clueHeight + indicatorOffset * 2);
    indicator = new ShredderBoxIndicator(selectIndicator);
  }

  /**
   * Initializes the shredder clue component. Sets the height and width of the component, creates
   * the rectangles and the shredded paper, and sets the complete message to be invisible.
   */
  @FXML
  public void initialize() {
    this.setHeight(400);
    this.setWidth(600);
    this.createRectangles();
    this.createShreddedPaper();

    this.getStylesheets().add(App.getCssUrl("shredder-clue"));
    setupIndicator();

    // Sets the complete message to be invisible
    completedMessage.setVisible(false);
    String message = App.getData("shredder-message.txt");
    completedMessage.setText(message);
    confidentialLabel.setVisible(false);
  }

  /**
   * Creates 6 rectangles that represent position that the shredded clue can be placed. Centered
   * horizontally in the middle of the image.
   */
  private void createRectangles() {
    // Calculates the spacing for the rectangles
    final int gap = 5; // Gap between each rectangle
    final double startingX = (this.getWidth() - (clues * clueWidth + (clues - 1) * gap)) / 2;
    final double step = clueWidth + gap;
    final double y = (this.getHeight() - (clueHeight)) / 2;

    System.out.println(clueWidth + " " + clueHeight);

    // Creates 6 rectangles
    for (int i = 0; i < clues; i++) {
      ShredderBox rect = new ShredderBox(i);
      // Sets height
      rect.setWidth(clueWidth);
      rect.setHeight(clueHeight);

      // Adds to the layout and calculates x position
      this.getChildren().add(rect);
      double x = startingX + i * step;

      // Set the position of the rectangle
      rect.setLayoutX(x);
      rect.setLayoutY(y);

      rect.setCenter(); // Required for the draggable paper to find the center of the rectangle

      // Add the rectangle to the map
      paperMap.put(rect, null);
    }
  }

  /** Creates the shredded paper draggable widgets for the clue. */
  private void createShreddedPaper() {
    for (int i = 0; i < clues; i++) {
      // Creates a new paper
      String path = "document/shredded/paper-" + i;
      ShredderPaper paper = new ShredderPaper(this, path, clueWidth, clueHeight, i);

      this.getChildren().add(paper);

      // Sets a random position for the paper
      double x = Math.random() * (this.getWidth() - clueWidth - 10);
      double y = Math.random() * (this.getHeight() - clueHeight - 10);
      paper.moveTo(new Coordinate(x, y));

      // Sets the event handlers for the paper
      EventCallback handlePress = e -> handlePress(paper);
      paper.setOnClick(handlePress);

      EventCallback handleRelease = e -> handleRelease(paper);
      paper.setOnRelease(handleRelease);

      EventCallback handleDrag = e -> handleDrag(paper);
      paper.setOnDrag(handleDrag);
    }
  }

  /**
   * Handles the press event for the paper. Shows the indicator to its closest shredder box and
   * moves the selected paper to the front.
   *
   * @param paper The paper that is being pressed
   */
  private void handlePress(ShredderPaper paper) {
    indicator.show();

    // Moves the paper to the front so that it is not hidden by other papers
    paper.toFront();

    ShredderBox closest = findClosestRectangle(paper);
    highlightBox(closest);
  }

  /**
   * Handles the release event for the paper. If the paper is released on a rectangle, it will be
   * placed in the rectangle. If the rectangle is not empty, the papers will be swapped.
   *
   * @param paper The paper that is being released
   */
  private void handleRelease(ShredderPaper paper) {
    indicator.hide();

    Coordinate currentPos =
        paper.getTopLeftCooridinate(); // For when a paper needs to be switched back

    // Finds  the closest rectangle to the paper
    ShredderBox box = findClosestRectangle(paper);

    // Checks to make sure that the paper is not already in the rectangle
    // If it is, move the paper back to the original box
    if (paperMap.get(box) == paper) {
      paper.moveTo(findPaperParent(paper).getTopLeftCooridinate());
      return;
    }

    // If the rectangle is empty, place the paper in the rectangle
    if (paperMap.get(box) == null) {
      // Makes sure that if the paper was in a rectangle, it is removed from the rectangle
      ShredderBox paperParent = findPaperParent(paper);

      // If the pressed paper was in the rectangle, removed it from the rectangle
      if (paperParent != null) {
        paperMap.put(paperParent, null);
      }

      this.movePaper(paper, box); // Move the paper to the new rectangle
      return;
    }

    // If the rectangle is not empty, swap the papers
    // Note that box is the rectangle that the paper is being placed in
    ShredderPaper otherPaper = paperMap.get(box);

    // Current parent of this paper
    ShredderBox paperParent = findPaperParent(paper);

    movePaper(paper, box); // Move the dragged paper to the new rectangle

    // If the paper moved was in a rectangle, move the other paper to the old rectangle
    if (paperParent != null) {
      movePaper(otherPaper, paperParent);
    } else {
      // Otherwise move the paper to the previous position of the paper
      otherPaper.moveTo(currentPos);
    }
  }

  /**
   * Returns the rectangle that the paper is in, or null if the paper is not in any rectangle.
   *
   * @param paper The paper to find the parent of
   * @return The rectangle that the paper is in, or null if the paper is not in any rectangle
   */
  private ShredderBox findPaperParent(ShredderPaper paper) {
    for (ShredderBox box : paperMap.keySet()) {
      if (paperMap.get(box) == paper) {
        return box;
      }
    }
    return null;
  }

  /**
   * Moves the paper to the box and updates the map.
   *
   * @param paper The paper to move
   * @param box The box to move the paper to
   */
  private void movePaper(ShredderPaper paper, ShredderBox box) {
    paperMap.put(box, paper);
    paper.moveTo(box.getTopLeftCooridinate());

    // Checks if the papers are in the correct position
    if (!arePapersCorrect()) {
      return;
    }

    System.out.println("correct order");
    showCompletedMessage();
  }

  /**
   * Handles the drag event for the paper. Shows the indicator to its closest shredder box.
   *
   * @param paper The paper that is being dragged
   */
  private void handleDrag(ShredderPaper paper) {
    ShredderBox closest = findClosestRectangle(paper);
    highlightBox(closest);
  }

  /**
   * Finds the closest rectangle to the paper.
   *
   * @param paper The paper to find the closest rectangle to
   * @return The closest rectangle to the paper
   */
  private ShredderBox findClosestRectangle(ShredderPaper paper) {
    ShredderBox closest = null;
    double minDistance = Double.MAX_VALUE;

    // Loops through each rectangle and finds the closest one
    for (ShredderBox rect : paperMap.keySet()) {
      double distance = paper.getCenterCooridinate().getDistance(rect.getCenterCooridinate());
      if (distance < minDistance) {
        minDistance = distance;
        closest = rect;
      }
    }

    return closest;
  }

  /**
   * Highlights the box by moving the indicator to the top left of the box.
   *
   * @param box The box to highlight
   */
  private void highlightBox(ShredderBox box) {
    indicator.moveTo(
        box.getTopLeftCooridinate().subtract(new Coordinate(indicatorOffset, indicatorOffset)));
  }

  /**
   * Checks if the papers are in the correct position.
   *
   * @return True if the papers are in the correct position, false otherwise
   */
  private boolean arePapersCorrect() {
    for (ShredderBox box : paperMap.keySet()) {
      if (!box.isPaperCorrect(paperMap.get(box))) {
        return false;
      }
    }
    return true;
  }

  /** Shows the completed message and the confidential label. */
  private void showCompletedMessage() {
    paperClue = true;
    hideShreddedPieces();
    completedMessage.toFront();
    completedMessage.setVisible(true);
    confidentialLabel.setVisible(true);
    confidentialLabel.toFront();
  }

  /** Hides the completed message and the box. */
  public void hideShreddedPieces() {
    for (ShredderBox box : paperMap.keySet()) {
      ShredderPaper paper = paperMap.get(box);
      if (paper != null) {
        box.setVisible(false);
        paper.setVisible(false);
      }
    }
  }

  @FXML
  public void handleClose(ActionEvent event) {
    Platform.runLater(() -> onClose.run(event));
  }

  /** Shows the clue component. */
  public void show() {
    this.setVisible(true);
  }

  /** Hides the clue component. */
  public void hide() {
    this.setVisible(false);
  }
}
