package nz.ac.auckland.se206.components.shredderclue;

import javafx.scene.shape.Rectangle;

public class ShredderBox extends Rectangle {
  Coordinate center;

  public ShredderBox() {
    super();

    this.getStyleClass().add("paper-slot"); // Sets up stylesheet
  }

  public void setCenter() {
    double x = this.getLayoutX() + this.getWidth() / 2;
    double y = this.getLayoutY() + this.getHeight() / 2;

    this.center = new Coordinate(x, y);
  }

  public Coordinate getCenter() {
    return center;
  }

  public Coordinate getTopLeft() {
    return new Coordinate(this.getLayoutX(), this.getLayoutY());
  }
}
