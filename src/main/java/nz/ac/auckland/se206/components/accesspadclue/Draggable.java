package nz.ac.auckland.se206.components.accesspadclue;

import javafx.application.Platform;
import javafx.scene.Node;
import nz.ac.auckland.se206.components.shredderclue.Coordinate;
import nz.ac.auckland.se206.utils.CoordinateCallback;
import nz.ac.auckland.se206.utils.EventCallback;

public class Draggable {
  private double orgSceneX;
  private double orgSceneY;

  public Draggable(
      Node node,
      Coordinate anchorPoint,
      EventCallback onMouseClick,
      EventCallback onMouseRelease,
      CoordinateCallback onMouseDrag) {

    node.setOnMousePressed(
        e -> {
          System.out.println("Moving to");
          orgSceneX = e.getSceneX() - node.getLayoutX();
          orgSceneY = e.getSceneY() - node.getLayoutY();
          Platform.runLater(() -> onMouseClick.run(e));
        });

    node.setOnMouseDragged(
        e -> {
          double offsetX = e.getSceneX() - orgSceneX;
          double offsetY = e.getSceneY() - orgSceneY;

          node.setLayoutX(offsetX);
          node.setLayoutY(offsetY);

          Coordinate coordinate = new Coordinate(e.getSceneX(), e.getSceneY());

          Platform.runLater(() -> onMouseDrag.run(coordinate));
        });

    node.setOnMouseReleased(
        e -> {
          // Send the node back to the anchor point
          node.setLayoutX(anchorPoint.getxPos());
          node.setLayoutY(anchorPoint.getyPos());

          Platform.runLater(() -> onMouseRelease.run(e));
        });
  }
}
