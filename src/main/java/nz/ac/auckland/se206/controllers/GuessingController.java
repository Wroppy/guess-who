package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.SceneType;

public class GuessingController {
  @FXML private TextArea explaintxt;
  @FXML private Rectangle bob;
  @FXML private Rectangle vicePresident;
  @FXML private Rectangle third;
  @FXML private Button submitBtn;
  private String explanation;
  private static boolean correctChoice;
  private boolean isClicked = false;

  public void initialize() {
    // Add a listener to check if TextArea has text input
    submitBtn.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-padding: 0; -fx-border-style: solid;");
    Timeline timeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                event -> {
                  if (explaintxt.getText() == null
                      || explaintxt.getText().trim().isEmpty()
                      || !isClicked) {
                    submitBtn.setDisable(true);
                  } else {
                    submitBtn.setDisable(false);
                  }
                }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  public void explanationScene(MouseEvent event) throws IOException {
    explanation = explaintxt.getText().trim();
    App.changeScene(SceneType.FEEDBACK);
  }

  public void choiceCriminal(MouseEvent event) {
    isClicked = true;
    Rectangle clickedRectangle = (Rectangle) event.getSource();
    if (clickedRectangle == vicePresident) {
      correctChoice = true;
    } else {
      correctChoice = false;
    }
  }

  // getter for correctChoice
  public static boolean getCorrectChoice() {
    return correctChoice;
  }
}
