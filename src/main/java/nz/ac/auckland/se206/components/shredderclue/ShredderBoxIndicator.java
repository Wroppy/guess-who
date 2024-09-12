package nz.ac.auckland.se206.components.shredderclue;

import javafx.scene.image.ImageView;

public class ShredderBoxIndicator implements Moveable {
  private ImageView indicator;

  public ShredderBoxIndicator(ImageView selectIndicator) {
    this.indicator = selectIndicator;
  }

  @Override
  public Coordinate getCenter() {
    return new Coordinate(
        indicator.getX() + indicator.getFitWidth() / 2,
        indicator.getY() + indicator.getFitHeight() / 2);
  }

  @Override
  public Coordinate getTopLeft() {
    return new Coordinate(indicator.getX(), indicator.getY());
  }

  @Override
  public void moveTo(Coordinate c) {

    indicator.setLayoutX(c.getX());
    indicator.setLayoutY(c.getY());
  }
}
