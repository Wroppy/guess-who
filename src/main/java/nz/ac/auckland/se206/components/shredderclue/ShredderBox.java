package nz.ac.auckland.se206.components.shredderclue;

import javafx.scene.shape.Rectangle;

/** Represents a box in the paper shredder where paper can be placed. */
public class ShredderBox extends Rectangle implements Positionable {
  Coordinate center;

  public ShredderBox() {
    super();

    this.getStyleClass().add("paper-slot"); // Sets up stylesheet
  }

  /**
   * Calculates and sets the center of the box to avoid recalculating it every time it is needed.
   */
  public void setCenter() {
    double x = this.getLayoutX() + this.getWidth() / 2;
    double y = this.getLayoutY() + this.getHeight() / 2;

    this.center = new Coordinate(x, y);

    System.out.println("Center: " + this.center.getX() + ", " + this.center.getY());
  }

  @Override
  public Coordinate getCenter() {
    return this.center;
  }

  @Override
  public Coordinate getTopLeft() {
    System.out.println("Top left: " + this.getLayoutX() + ", " + this.getLayoutY());
    return new Coordinate(this.getLayoutX(), this.getLayoutY());
  }
}
