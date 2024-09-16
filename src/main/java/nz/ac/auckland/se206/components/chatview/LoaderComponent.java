package nz.ac.auckland.se206.components.chatview;

import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class LoaderComponent extends Pane {
  public LoaderComponent() {
    int height = 26;
    int radius = 2;
    int delay = 400;
    int spacing = 10;
    double starting = (250 - 16) / 2 - spacing;
    for (int i = 0; i < 3; i++) {
      // Creates a circle component and adds it to the pane
      Circle circle = new Circle(radius);

      // Sets the position of the circle in the center
      circle.setCenterX(starting + i * spacing);

      circle.setCenterY(height / 2);
      getChildren().add(circle);

      this.setAnimation(circle, delay * i);
    }

    this.setPrefHeight(26);
  }

  private void setAnimation(Circle circle, int delayMs) {
    Task<Void> task =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            Thread.sleep(delayMs);
            return null;
          }
        };

    task.setOnSucceeded(
        event -> {
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
