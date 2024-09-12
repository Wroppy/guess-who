package nz.ac.auckland.se206.components.shredderclue;

import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.utils.EventCallback;

public class ShredderClueComponent extends Pane {
  private final double clueHeight = 300;
  private final double clueWidth = clueHeight * 283 / 2200;
  private final int clues = 6;

  @FXML private Pane shredderPane;
  @FXML private ImageView selectIndicator;

  ShredderBoxIndicator indicator;

  // Map for each rectangle to the paper that is placed on it
  private Map<ShredderBox, ShredderPaper> paperMap = new HashMap<>();

  public ShredderClueComponent() {
    try {
      FXMLLoader loader = App.loadFxmlLoader("shredder-clue");

      loader.setRoot(this);
      loader.setController(this);

      loader.load();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void initialize() {
    this.setHeight(400);
    this.setWidth(500);
    this.createRectangles();
    this.createShreddedPaper();

    this.getStylesheets().add(App.getCssUrl("shredder-clue"));

    indicator = new ShredderBoxIndicator(selectIndicator);
  }

  /**
   * Creates 6 rectangles that represent position that the shredded clue can be placed. Centered
   * horizontally in the middle of the image.
   */
  private void createRectangles() {
    final int gap = 20; // Gap between each rectangle
    final double startingX = (this.getWidth() - (clues * clueWidth + (clues - 1) * gap)) / 2;
    final double step = clueWidth + gap;
    final double y = (this.getHeight() - (clueHeight)) / 2;

    // Creates 6 rectangles
    for (int i = 0; i < clues; i++) {
      ShredderBox rect = new ShredderBox();
      // Sets height
      rect.setWidth(clueWidth);
      rect.setHeight(clueHeight);

      // Adds to the layout and calculates x position
      this.getChildren().add(rect);
      double x = startingX + i * step;

      // Set the position of the rectangle
      rect.setLayoutX(x);
      rect.setLayoutY(y);

      rect.setCenter();

      // Add the rectangle to the map
      paperMap.put(rect, null);
    }
  }

  /** Creates the shredded paper draggable widgets for the clue. */
  public void createShreddedPaper() {
    for (int i = 0; i < clues; i++) {
      String path = "document/shredded/paper-" + i;
      ShredderPaper paper = new ShredderPaper(this, path, clueWidth, clueHeight);

      this.getChildren().add(paper);
      EventCallback handlePress = e -> handlePress();
      paper.setOnClick(handlePress);

      EventCallback handleRelease = e -> handleRelease(paper);
      paper.setOnRelease(handleRelease);

      EventCallback handleDrag = e -> handleDrag(paper);
      paper.setOnDrag(handleDrag);
    }
  }

  private void handlePress() {
    indicator.show();
  }

  private void handleRelease(ShredderPaper paper) {
    indicator.hide();

    Coordinate possiblePos = paper.getTopLeft();

    // Finds  the closest rectangle to the paper
    ShredderBox box = findClosestRectangle(paper);

    // Checks to make sure that the paper is not already in the rectangle
    // If it is, move the paper back to the original box
    if (paperMap.get(box) == paper) {
      paper.moveTo(findPaperParent(paper).getTopLeft());
      return;
    }

    // If the rectangle is empty, place the paper in the rectangle
    if (paperMap.get(box) == null) {
      // Makes sure that if the paper was in a rectangle, it is removed from the rectangle
      ShredderBox paperParent = findPaperParent(paper);
      if (paperParent != null) {
        paperMap.put(paperParent, null);
      }

      this.movePaper(paper, box);

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
      otherPaper.moveTo(possiblePos);
    }
  }

  private ShredderBox findPaperParent(ShredderPaper paper) {
    for (ShredderBox box : paperMap.keySet()) {
      if (paperMap.get(box) == paper) {
        return box;
      }
    }
    return null;
  }

  private void movePaper(ShredderPaper paper, ShredderBox box) {
    paperMap.put(box, paper);
    paper.moveTo(box.getTopLeft());
  }

  private void handleDrag(ShredderPaper paper) {

    ShredderBox closest = findClosestRectangle(paper);
    highlightBox(closest);
  }

  public ShredderBox findClosestRectangle(ShredderPaper paper) {
    ShredderBox closest = null;
    double minDistance = Double.MAX_VALUE;

    // Loops through each rectangle and finds the closest one
    for (ShredderBox rect : paperMap.keySet()) {
      double distance = paper.getCenter().getDistance(rect.getCenter());
      if (distance < minDistance) {
        minDistance = distance;
        closest = rect;
      }
    }

    return closest;
  }

  public void highlightBox(ShredderBox box) {
    indicator.moveTo(box.getTopLeft().subtract(new Coordinate(6, 6)));
  }
}
