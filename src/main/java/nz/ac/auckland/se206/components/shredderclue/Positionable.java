package nz.ac.auckland.se206.components.shredderclue;

/** Interface for objects that can be in a layout. */
public interface Positionable {
  public Coordinate getCenter();

  public Coordinate getTopLeft();
}
