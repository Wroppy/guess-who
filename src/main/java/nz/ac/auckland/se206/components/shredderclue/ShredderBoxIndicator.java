package nz.ac.auckland.se206.components.shredderclue;

import javafx.scene.image.ImageView;

/** Indicator for which shredder box the user it currently going to be interacting with. */
public class ShredderBoxIndicator implements Moveable {
  private ImageView indicator;

  public ShredderBoxIndicator(ImageView selectIndicator) {
    this.indicator = selectIndicator;
    indicator.setVisible(false);
  }

  @Override
  public Coordinate getCenterCooridinate() {
    return new Coordinate(
        indicator.getX() + indicator.getFitWidth() / 2,
        indicator.getY() + indicator.getFitHeight() / 2);
  }

  @Override
  public Coordinate getTopLeftCooridinate() {
    return new Coordinate(indicator.getX(), indicator.getY());
  }

  @Override
  public void moveTo(Coordinate c) {

    indicator.setLayoutX(c.getxPos());
    indicator.setLayoutY(c.getyPos());
  }

  public void hide() {
    indicator.setVisible(false);
  }

  public void show() {
    indicator.setVisible(true);
  }
}
