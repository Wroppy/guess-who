package nz.ac.auckland.se206.components.shredderclue;

import javafx.scene.image.ImageView;

/** Indicator for which shredder box the user it currently going to be interacting with. */
public class ShredderBoxIndicator implements Moveable {
  private ImageView indicator;

  /**
   * Creates a new shredder box indicator with the given image view.
   *
   * @param selectIndicator The image view to use as the indicator
   */
  public ShredderBoxIndicator(ImageView selectIndicator) {
    this.indicator = selectIndicator;
    indicator.setVisible(false);
  }

  /** Moves the indicator to the center of the given box. */
  @Override
  public Coordinate getCenterCooridinate() {
    return new Coordinate(
        indicator.getX() + indicator.getFitWidth() / 2,
        indicator.getY() + indicator.getFitHeight() / 2);
  }

  /** Moves the indicator to the top left of the given box. */
  @Override
  public Coordinate getTopLeftCooridinate() {
    return new Coordinate(indicator.getX(), indicator.getY());
  }

  /** Moves the indicator to the given coordinate. */
  @Override
  public void moveTo(Coordinate c) {

    indicator.setLayoutX(c.getHorizontalPosition());
    indicator.setLayoutY(c.getVerticalPosition());
  }

  /** Hides the indicator by setting the visibility to false. */
  public void hide() {
    indicator.setVisible(false);
  }

  /** Shows the indicator by setting the visiibility to true. */
  public void show() {
    indicator.setVisible(true);
  }
}
