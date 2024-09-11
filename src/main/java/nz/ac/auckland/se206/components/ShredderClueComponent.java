package nz.ac.auckland.se206.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;

public class ShredderClueComponent extends Pane {
  private final double clueHeight = 300;
  private final double clueWidth = 400 * 283 / 2200;
  private final int clues = 6;

  @FXML private Pane shredderPane;

  public ShredderClueComponent() {

    this.setOnMouseClicked(
        e -> {
          System.out.println(e.getX() + " " + e.getY());
        });
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
      Rectangle rect = new Rectangle();
      rect.setWidth(clueWidth);
      rect.setHeight(clueHeight);

      this.getChildren().add(rect);
      double x = startingX + i * step;
      System.out.println("Adding on x: " + x + " y: " + y);
      // Set the position of the rectangle
      rect.setLayoutX(x);
      rect.setLayoutY(y);
    }
  }
}
