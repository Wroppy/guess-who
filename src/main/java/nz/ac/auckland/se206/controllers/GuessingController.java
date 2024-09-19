package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
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
  @FXML private Label timerLabel;
  private String explanation;
  private static boolean correctChoice;
  private boolean isClicked = false;

  private Rectangle selectedRectangle;

  List<Rectangle> suspectOptions;

  public void initialize() {
    // Add a listener to check if TextArea has text input
    submitBtn.setStyle(
        "-fx-border-color: red; -fx-border-width: 2px; -fx-padding: 4 6; -fx-border-style: solid;"
            + " -fx-background-insets: 0;");
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

    suspectOptions = new ArrayList<>();
    suspectOptions.add(bob);
    suspectOptions.add(vicePresident);
    suspectOptions.add(third);

    this.setupClickables();
  }

  private void setupClickables() {
    for (Rectangle suspectOption : suspectOptions) {
      suspectOption.setOnMouseEntered(e -> handleMouseEntered(e));
      suspectOption.setOnMouseExited(e -> handleMouseExited(e));
    }
  }

  private void handleMouseEntered(MouseEvent e) {
    Rectangle rect = (Rectangle) e.getSource();

    if (rect == selectedRectangle) {
      return;
    }

    // Changes the border color
    rect.setStroke(Color.GREEN);
  }

  private void handleMouseExited(MouseEvent e) {
    Rectangle rect = (Rectangle) e.getSource();

    if (rect == selectedRectangle) {
      return;
    }

    // Changes the border color
    rect.setStroke(Color.RED);
  }

  private void selectRectangle() {
    for (Rectangle rect : suspectOptions) {
      rect.setStroke(Color.RED);
    }

    selectedRectangle.setStroke(Color.GREEN);
  }

  public void explanationScene(MouseEvent event) throws IOException {
    explanation = explaintxt.getText().trim();

    // TODO: Send explanation to GPT

    GameOverController.showResult();

    App.changeScene(SceneType.FEEDBACK);
  }

  public void choiceCriminal(MouseEvent event) {
    isClicked = true;
    Rectangle clickedRectangle = (Rectangle) event.getSource();

    selectedRectangle = clickedRectangle;
    selectRectangle();

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

  public Label getTimerLabel() {
    return timerLabel;
  }

  public GuessingController getGuessingController() {
    return this;
  }
}
