package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class GameOverController {
  @FXML private Label result;
  @FXML private TextArea feedbacktxt;
  @FXML private Text feedback;

  public void initialize() {
    if (GuessingController.getCorrectChoice()) {
      result.setText("Correct Choice!");
    } else {
      result.setText("Incorrect Choice!");
      feedbacktxt.setVisible(false);
      feedback.setVisible(false);
    }
  }
}
