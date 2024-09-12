package nz.ac.auckland.se206.components.shredderclue;

import javafx.scene.shape.Rectangle;

public class ShredderBox extends Rectangle implements Positionable {
  Coordinate center;

  public ShredderBox() {
    super();

    this.getStyleClass().add("paper-slot"); // Sets up stylesheet
  }

  public void setCenter() {
    double x = this.getLayoutX() + this.getWidth() / 2;
    double y = this.getLayoutY() + this.getHeight() / 2;

    this.center = new Coordinate(x, y);

    System.out.println("Center: " + this.center.getX() + ", " + this.center.getY());
  }

  public Coordinate getCenter() {
    return this.center;
  }

  public Coordinate getTopLeft() {
    System.out.println("Top left: " + this.getLayoutX() + ", " + this.getLayoutY());
    return new Coordinate(this.getLayoutX(), this.getLayoutY());
  }
}
