package nz.ac.auckland.se206.components.shredderclue;

/** Interface for objects that can be moved to a new position. */
public interface Moveable extends Positionable {
  public void moveTo(Coordinate c);
}
