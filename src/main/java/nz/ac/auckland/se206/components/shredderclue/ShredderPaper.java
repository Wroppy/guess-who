package nz.ac.auckland.se206.components.shredderclue;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.utils.EventCallback;

/** Represents a piece of paper that can be dragged around the screen. The paper has a draggable */
public class ShredderPaper extends Pane implements Moveable {
  private double mouseAnchorX;
  private double mouseAnchorY;
  private int order;

  // Callback functions for the draggable feature
  private EventCallback onMouseClickCallback;
  private EventCallback onMouseDragCallback;
  private EventCallback onMouseReleaseCallback;

  private ImageView imageView;
  private Node parent;

  /**
   * Creates a new shredder paper with the given parent, path, width, height and order.
   *
   * @param parent The parent node
   * @param path The path to the image
   * @param width The width of the paper
   * @param height The height of the paper
   * @param order The order of the paper
   */
  public ShredderPaper(Node parent, String path, double width, double height, int order) {
    super();

    // Sets up variables for the draggable feature
    this.parent = parent;
    mouseAnchorY = 0;
    mouseAnchorX = 0;

    // Sets up the image for the paper
    Image image = new Image(App.class.getResource("/images/" + path + ".png").toExternalForm());
    this.imageView = new ImageView(image);

    this.getChildren().add(this.imageView);

    this.imageView.setFitWidth(width);
    this.imageView.setFitHeight(height);

    this.setPrefSize(width + 2, height + 2);
    this.imageView.setLayoutX(1);
    this.imageView.setLayoutY(1);

    this.makeDraggable();

    this.order = order;

    this.getStyleClass().add("shredder-paper");
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
    double relativeX = e.getSceneX() - mouseAnchorX;
    double relativeY = e.getSceneY() - mouseAnchorY;

    // Checks that the node is not dragged out of the parent
    double rightBound = parent.getLayoutBounds().getWidth() - this.getLayoutBounds().getWidth();
    double bottomBound = parent.getLayoutBounds().getHeight() - this.getLayoutBounds().getHeight();
    if (relativeX < 0) {
      return;
    }
    if (relativeX > rightBound) {
      return;
    }
    if (relativeY < 0) {
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

  /**
   * Sets the callback function for when the paper is clicked.
   *
   * @param callback The callback function
   */
  public void setOnClick(EventCallback callback) {
    this.onMouseClickCallback = callback;
  }

  /**
   * Sets the callback function for when the paper is dragged.
   *
   * @param callback The callback function
   */
  public void setOnDrag(EventCallback callback) {
    this.onMouseDragCallback = callback;
  }

  /**
   * Sets the callback function for when the paper is released.
   *
   * @param callback The callback function
   */
  public void setOnRelease(EventCallback callback) {
    this.onMouseReleaseCallback = callback;
  }

  /** Gets the center coordinate of the paper. */
  @Override
  public Coordinate getCenterCooridinate() {
    return new Coordinate(
        this.getLayoutX() + this.getWidth() / 2, this.getLayoutY() + this.getHeight() / 2);
  }

  /** Gets the top left coordinate of the paper. */
  @Override
  public Coordinate getTopLeftCooridinate() {
    return new Coordinate(this.getLayoutX(), this.getLayoutY());
  }

  /** Moves the paper to the given coordinate. */
  @Override
  public void moveTo(Coordinate pos) {
    this.setLayoutX(pos.getHorizontalPosition());
    this.setLayoutY(pos.getVerticalPosition());
  }

  /**
   * Gets the order of the paper.
   *
   * @return The order of the paper
   */
  public int getOrder() {
    return order;
  }
}
