package nz.ac.auckland.se206.components.shredderclue;

public class Coordinate {
  private double xPos;
  private double yPos;

  public Coordinate(double x, double y) {
    this.xPos = x;
    this.yPos = y;
  }

  /**
   * Get the distance between this coordinate and another.
   *
   * @param other The other coordinate
   * @return The distance between the two coordinates
   */
  public double getDistance(Coordinate other) {
    return Math.sqrt(Math.pow(xPos - other.xPos, 2) + Math.pow(yPos - other.yPos, 2));
  }

  public double getxPos() {
    return xPos;
  }

  public double getyPos() {
    return yPos;
  }

  /**
   * Add another coordinate to this one.
   *
   * @param other The other coordinate
   * @return The resulting coordinate
   */
  public Coordinate subtract(Coordinate other) {
    xPos -= other.xPos;
    yPos -= other.yPos;

    return this;
  }

  public boolean isInsideRectangle(Coordinate topLeft, Coordinate bottomRight) {
    return xPos >= topLeft.xPos
        && xPos <= bottomRight.xPos
        && yPos >= topLeft.yPos
        && yPos <= bottomRight.yPos;
  }

  @Override
  public String toString() {
    return "(" + xPos + ", " + yPos + ")";
  }
}
