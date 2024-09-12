package nz.ac.auckland.se206.components.shredderclue;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.utils.EventCallback;

public class ShredderPaper extends ImageView implements Moveable {
  private double mouseAnchorX;
  private double mouseAnchorY;
  private int order;

  // Callback functions for the draggable feature
  private EventCallback onMouseClickCallback;
  private EventCallback onMouseDragCallback;
  private EventCallback onMouseReleaseCallback;

  private Node parent;

  public ShredderPaper(Node parent, String path, double width, double height, int order) {
    super();

    // Sets up variables for the draggable feature
    this.parent = parent;
    mouseAnchorY = 0;
    mouseAnchorX = 0;

    // Sets up the image for the paper
    Image image = new Image(App.class.getResource("/images/" + path + ".png").toExternalForm());
    this.setImage(image);

    this.setFitWidth(width);
    this.setFitHeight(height);

    this.makeDraggable();

    this.order = order;
  }

  /** Sets up the draggable handle methods for the paper. */
  private void makeDraggable() {
    this.setOnMouseReleased(e -> onMouseReleased(e));

    this.setOnMouseDragged(e -> onMouseDragged(e));

    this.setOnMousePressed(e -> onMousePress(e));
  }

  /**
   * Given a mouse event, snap the widget to one of the rectangles in its parent.
   *
   * @param e mouse event
   */
  private void onMouseReleased(MouseEvent e) {
    // Calls the callback function
    if (onMouseReleaseCallback == null) {
      return;
    }
    onMouseReleaseCallback.run(e);
  }

  /**
   * Sets the anchor points for when the widget is being initially clicked on.
   *
   * @param e mouse event
   */
  private void onMousePress(MouseEvent e) {
    // Sets the anchor points for the mouse
    mouseAnchorX = e.getSceneX() - this.getLayoutX();
    mouseAnchorY = e.getSceneY() - this.getLayoutY();

    // Calls the callback function
    if (onMouseClickCallback == null) {
      return;
    }
    onMouseClickCallback.run(e);
  }

  /**
   * Given a mouse event, moves this widget around the parent if it is within the bounds of the
   * parent.
   *
   * @param e mouse event
   */
  private void onMouseDragged(MouseEvent e) {
    // Calculates the relative position of the mouse
    double relativeXPos = e.getSceneX() - mouseAnchorX;
    double relativeYPos = e.getSceneY() - mouseAnchorY;

    // Checks that the node is not dragged out of the parent
    double rightBound = parent.getLayoutBounds().getWidth() - this.getLayoutBounds().getWidth();
    double bottomBound = parent.getLayoutBounds().getHeight() - this.getLayoutBounds().getHeight();
    if (relativeXPos < 0) {
      return;
    }
    if (relativeXPos > rightBound) {
      return;
    }
    if (relativeYPos < 0) {
      return;
    }
    if (e.getSceneY() - mouseAnchorY > bottomBound) {
      return;
    }

    // Moves the node
    this.setLayoutX(e.getSceneX() - mouseAnchorX);
    this.setLayoutY(e.getSceneY() - mouseAnchorY);

    // Calls the callback function
    if (onMouseDragCallback == null) {
      return;
    }
    onMouseDragCallback.run(e);
  }

  public void setOnClick(EventCallback callback) {
    this.onMouseClickCallback = callback;
  }

  public void setOnDrag(EventCallback callback) {
    this.onMouseDragCallback = callback;
  }

  public void setOnRelease(EventCallback callback) {
    this.onMouseReleaseCallback = callback;
  }

  @Override
  public Coordinate getCenter() {
    return new Coordinate(
        this.getLayoutX() + this.getFitWidth() / 2, this.getLayoutY() + this.getFitHeight() / 2);
  }

  @Override
  public Coordinate getTopLeft() {
    return new Coordinate(this.getLayoutX(), this.getLayoutY());
  }

  @Override
  public void moveTo(Coordinate pos) {
    this.setLayoutX(pos.getX());
    this.setLayoutY(pos.getY());
  }

  public int getOrder() {
    return order;
  }
}
