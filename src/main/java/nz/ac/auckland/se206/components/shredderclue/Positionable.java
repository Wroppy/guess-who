package nz.ac.auckland.se206.components.shredderclue;

/** Interface for objects that can be in a layout. */
public interface Positionable {
  /**
   * Get the center coordinate of this object.
   *
   * @return The center coordinate
   */
  public Coordinate getCenterCooridinate();

  /**
   * Get the top left coordinate of this object.
   *
   * @return The top left coordinate
   */
  public Coordinate getTopLeftCooridinate();
}
