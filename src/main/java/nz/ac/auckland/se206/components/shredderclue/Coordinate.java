package nz.ac.auckland.se206.components.shredderclue;

public class Coordinate {
  private double x;
  private double y;

  public Coordinate(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getDistance(Coordinate other) {
    return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public Coordinate subtract(Coordinate other) {
    x -= other.x;
    y -= other.y;

    return this;
  }
}
