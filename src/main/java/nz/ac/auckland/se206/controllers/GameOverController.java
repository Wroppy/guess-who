package nz.ac.auckland.se206.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.utils.CallBack;

public class GameOverController implements Restartable {
  @FXML private Label result;
  @FXML private TextArea feedbacktxt;
  @FXML private Text feedback;
  @FXML private Label timeUp;

  private static Label resultLabel;
  private static Text feedbackLabel;
  private static TextArea feedbackTextArea;
  private static Label timeUpLabel;

  private CallBack onRestart;

  public void initialize() {
    GameOverController.resultLabel = result;
    GameOverController.feedbackLabel = feedback;
    GameOverController.feedbackTextArea = feedbacktxt;
    GameOverController.timeUpLabel = timeUp;
  }

  public static void showResult() {
    if (GuessingController.getCorrectChoice()) {
      GameOverController.resultLabel.setText("Correct Choice!");
      GameOverController.timeUpLabel.setVisible(false);
    } else {
      GameOverController.resultLabel.setText("Incorrect Choice!");
      GameOverController.feedbackTextArea.setVisible(false);
      GameOverController.feedbackLabel.setVisible(false);
      GameOverController.timeUpLabel.setVisible(false);
    }
  }

  public static Text getFeedbackLabel() {
    return feedbackLabel;
  }

  public static TextArea getFeedbackTextArea() {
    return feedbackTextArea;
  }

  public static Label getResultLabel() {
    return resultLabel;
  }

  public static Label getTimeUpLabel() {
    return timeUpLabel;
  }

  @FXML
  private void handlePlayAgain(ActionEvent event) {
    if (onRestart != null) {
      onRestart.call();
    }
  }

  public void setOnRestart(CallBack onRestart) {
    this.onRestart = onRestart;
  }

  @Override
  public void restart() {}
}
