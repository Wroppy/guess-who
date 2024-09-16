package nz.ac.auckland.se206.components.shredderclue;

public class Coordinate {
  private double x;
  private double y;

  public Coordinate(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Get the distance between this coordinate and another.
   * 
   * @param other The other coordinate
   * @return The distance between the two coordinates
   */
  public double getDistance(Coordinate other) {
    return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  /**
   * Add another coordinate to this one.
   * 
   * @param other The other coordinate
   * @return The resulting coordinate
   */
  public Coordinate subtract(Coordinate other) {
    x -= other.x;
    y -= other.y;

    return this;
  }
}
