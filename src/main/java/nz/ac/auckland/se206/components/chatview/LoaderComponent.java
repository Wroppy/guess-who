package nz.ac.auckland.se206.components.chatview;

import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/** A component that displays a loading animation. */
public class LoaderComponent extends Pane {
  public LoaderComponent() {
    int height = 37;
    int radius = 2;
    int delay = 400;
    int spacing = 10;
    double starting = (120) / 2 - spacing;
    for (int i = 0; i < 3; i++) {
      // Creates a circle component and adds it to the pane
      Circle circle = new Circle(radius);
      circle.setFill(Color.rgb(241, 241, 241));

      // Sets the position of the circle in the center
      circle.setCenterX(starting + i * spacing);

      circle.setCenterY(height / 2);
      getChildren().add(circle);

      this.setAnimation(circle, delay * i);
    }

    this.setPrefHeight(26);
  }

  /**
   * Sets the animation for the loader.
   *
   * @param circle The circle to animate
   * @param delayMs The delay before the animation starts
   */
  private void setAnimation(Circle circle, int delayMs) {
    //  Delays the animation of the loader
    Task<Void> task =
        // Creates a new task that sleeps for the delay time
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            Thread.sleep(delayMs);
            return null;
          }
        };

    // Sets the event handler for when the task is succeeded
    task.setOnSucceeded(
        event -> {
          // Creates a new translate transition for the circle
          TranslateTransition transition = new TranslateTransition();
          transition.setDuration(Duration.millis(400));
          transition.setNode(circle);
          transition.setAutoReverse(true);
          transition.setCycleCount(TranslateTransition.INDEFINITE);
          transition.setByY(4);
          transition.play();
        });

    new Thread(task).start();
  }
}
