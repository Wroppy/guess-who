package nz.ac.auckland.se206.components.accesspadclue;

import javafx.scene.Node;
import nz.ac.auckland.se206.components.shredderclue.Coordinate;

public class Draggable {
  private double orgSceneX, orgSceneY;
  private double orgTranslateX, orgTranslateY;
  private Coordinate anchorPoint;

  public Draggable(Node node, Coordinate anchorPoint) {
    this.anchorPoint = anchorPoint;

    node.setOnMousePressed(
        e -> {
          orgSceneX = e.getSceneX() - node.getLayoutX();
          orgSceneY = e.getSceneY() - node.getLayoutY();
        });

    node.setOnMouseDragged(
        e -> {
          double offsetX = e.getSceneX() - orgSceneX;
          double offsetY = e.getSceneY() - orgSceneY;

          node.setLayoutX(offsetX);
          node.setLayoutY(offsetY);
        });

    node.setOnMouseReleased(
        e -> {
          // Send the node back to the anchor point
          node.setLayoutX(anchorPoint.getX());
          node.setLayoutY(anchorPoint.getY());
        });
  }
}
