package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class GameOverController {
  @FXML private Label result;
  @FXML private TextArea feedbacktxt;
  @FXML private Text feedback;

  private static Label resultLabel;
  private static Text feedbackLabel;
  private static TextArea feedbackTextArea;

  public void initialize() {
    GameOverController.resultLabel = result;
    GameOverController.feedbackLabel = feedback;
    GameOverController.feedbackTextArea = feedbacktxt;
  }

  public static void showResult() {
    if (GuessingController.getCorrectChoice()) {
      GameOverController.resultLabel.setText("Correct Choice!");
    } else {
      GameOverController.resultLabel.setText("Incorrect Choice!");
      GameOverController.feedbackTextArea.setVisible(false);
      GameOverController.feedbackLabel.setVisible(false);
    }

  }
}
