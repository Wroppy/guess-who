package nz.ac.auckland.se206.components.shredderclue;

/** Represents a coordinate on the screen, with an x and y value. */
public class Coordinate {
  private double horizontalPosition;
  private double verticalPosition;

  public Coordinate(double x, double y) {
    this.horizontalPosition = x;
    this.verticalPosition = y;
  }

  /**
   * Get the distance between this coordinate and another.
   *
   * @param other The other coordinate
   * @return The distance between the two coordinates
   */
  public double getDistance(Coordinate other) {
    return Math.sqrt(
        Math.pow(horizontalPosition - other.horizontalPosition, 2)
            + Math.pow(verticalPosition - other.verticalPosition, 2));
  }

  /**
   * Get the x position of this coordinate.
   *
   * @return The x position
   */
  public double getHorizontalPosition() {
    return horizontalPosition;
  }

  /**
   * Get the y position of this coordinate.
   *
   * @return The y position
   */
  public double getVerticalPosition() {
    return verticalPosition;
  }

  /**
   * Add another coordinate to this one.
   *
   * @param other The other coordinate
   * @return The resulting coordinate
   */
  public Coordinate subtract(Coordinate other) {
    horizontalPosition -= other.horizontalPosition;
    verticalPosition -= other.verticalPosition;

    return this;
  }

  /**
   * Checks if this coordinate is inside a rectangle defined by 2 corner coordinates.
   *
   * @param topLeft The top left corner of the rectangle
   * @param bottomRight The bottom right corner of the rectangle
   * @return True if this coordinate is inside the rectangle, false otherwise
   */
  public boolean isInsideRectangle(Coordinate topLeft, Coordinate bottomRight) {
    return horizontalPosition >= topLeft.horizontalPosition
        && horizontalPosition <= bottomRight.horizontalPosition
        && verticalPosition >= topLeft.verticalPosition
        && verticalPosition <= bottomRight.verticalPosition;
  }

  /**
   * Checks if this coordinate is inside a circle defined by a center coordinate and a radius.
   *
   * @param center The center of the circle
   */
  @Override
  public String toString() {
    return "(" + horizontalPosition + ", " + verticalPosition + ")";
  }
}
