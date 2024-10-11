package nz.ac.auckland.se206.components.shredderclue;

import javafx.scene.shape.Rectangle;

/** Represents a box in the paper shredder where paper can be placed. */
public class ShredderBox extends Rectangle implements Positionable {
  private Coordinate center;
  private int order;

  /**
   * Creates a new shredder box with the given order it is relative to the other Shredder boxes.
   *
   * @param order The order of the box
   */
  public ShredderBox(int order) {
    super();

    this.getStyleClass().add("paper-slot"); // Sets up stylesheet

    this.order = order;
  }

  /**
   * Calculates and sets the center of the box to avoid recalculating it every time it is needed.
   */
  public void setCenter() {
    double x = this.getLayoutX() + this.getWidth() / 2;
    double y = this.getLayoutY() + this.getHeight() / 2;

    this.center = new Coordinate(x, y);
  }

  /** Moves the box to the given coordinate. */
  @Override
  public Coordinate getCenterCooridinate() {
    return this.center;
  }

  /** Moves the box to the given coordinate. */
  @Override
  public Coordinate getTopLeftCooridinate() {
    System.out.println("Top left: " + this.getLayoutX() + ", " + this.getLayoutY());
    return new Coordinate(this.getLayoutX(), this.getLayoutY());
  }

  /**
   * Sets the order of the box.
   *
   * @return The order of the box
   */
  public int getOrder() {
    return this.order;
  }

  /**
   * Checks if the paper is correct for the box.
   *
   * @param paper The paper to check
   * @return True if the paper is correct, false otherwise
   */
  public boolean isPaperCorrect(ShredderPaper paper) {
    return paper != null && paper.getOrder() == this.order;
  }
}
