package nz.ac.auckland.se206.components.accesspadclue;

import javafx.application.Platform;
import javafx.scene.Node;
import nz.ac.auckland.se206.components.shredderclue.Coordinate;
import nz.ac.auckland.se206.utils.CoordinateCallback;
import nz.ac.auckland.se206.utils.EventCallback;

/** Makes a node draggable. */
public class Draggable {
  private double orgSceneX;
  private double orgSceneY;

  /**
   * Create a new Draggable object.
   *
   * @param node The node to make draggable
   * @param anchorPoint The point to return the node to when dragging is finished
   * @param onMouseClick The callback to run when the node is clicked
   * @param onMouseRelease The callback to run when the node is released
   * @param onMouseDrag The callback to run when the node is dragged
   */
  public Draggable(
      Node node,
      Coordinate anchorPoint,
      EventCallback onMouseClick,
      EventCallback onMouseRelease,
      CoordinateCallback onMouseDrag) {

    // Set the node to be draggable
    node.setOnMousePressed(
        e -> {
          System.out.println("Moving to");
          orgSceneX = e.getSceneX() - node.getLayoutX();
          orgSceneY = e.getSceneY() - node.getLayoutY();
          Platform.runLater(() -> onMouseClick.run(e));
        });

    // Move the node to the mouse position
    node.setOnMouseDragged(
        e -> {
          double offsetX = e.getSceneX() - orgSceneX;
          double offsetY = e.getSceneY() - orgSceneY;

          node.setLayoutX(offsetX);
          node.setLayoutY(offsetY);

          Coordinate coordinate = new Coordinate(e.getSceneX(), e.getSceneY());

          Platform.runLater(() -> onMouseDrag.run(coordinate));
        });

    // Send the node back to the anchor point
    node.setOnMouseReleased(
        e -> {
          // Send the node back to the anchor point
          node.setLayoutX(anchorPoint.getHorizontalPosition());
          node.setLayoutY(anchorPoint.getVerticalPosition());

          Platform.runLater(() -> onMouseRelease.run(e));
        });
  }
}
