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
      EventCallback handlePress = e -> System.out.println("Handle Press");
      paper.setOnClick(handlePress);

      EventCallback handleRelease = e -> System.out.println("Handle Release");
      paper.setOnRelease(handleRelease);

      EventCallback handleDrag = e -> handleDrag(paper);
      paper.setOnDrag(handleDrag);
    }
  }

  public void handleDrag(ShredderPaper paper) {
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
