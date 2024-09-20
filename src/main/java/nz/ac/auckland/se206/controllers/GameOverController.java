package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.SoundManager;

public class GameOverController {
  @FXML private Label result;
  @FXML private TextArea feedbacktxt;
  @FXML private Text feedback;
  @FXML private Button playAgain;
  @FXML private ImageView showCat;
  @FXML private ImageView showCat2;
  @FXML private Label timeUp;

  private static ImageView catImage;
  private static ImageView catImage2;
  private static Label resultLabel;
  private static Text feedbackLabel;
  private static TextArea feedbackTextArea;
  private static Button playAgainButton;

  private static Label timeUpLabel;

  public void initialize() {
    GameOverController.resultLabel = result;
    GameOverController.feedbackLabel = feedback;
    GameOverController.feedbackTextArea = feedbacktxt;
    GameOverController.playAgainButton = playAgain;
    showCat.setVisible(false);
    showCat2.setVisible(false);
    GameOverController.catImage = showCat;
    GameOverController.catImage2 = showCat2;

    GameOverController.timeUpLabel = timeUp;
  }

  public static void showResult() {
    if (GuessingController.getCorrectChoice()) {
      GameOverController.resultLabel.setText("Correct Choice!");
      GameOverController.resultLabel.setLayoutY(106);

      playAgainButton.setLayoutX(335);
      playAgainButton.setLayoutY(507);
      GameOverController.timeUpLabel.setVisible(false);
      SoundManager.playSound("GuessCorrect.mp3");
    } else {
      GameOverController.resultLabel.setText("Incorrect Choice!");
      GameOverController.resultLabel.setFont(Font.font("System", 28));
      GameOverController.resultLabel.setLayoutY(250);
      GameOverController.feedbackTextArea.setVisible(false);
      GameOverController.feedbackLabel.setVisible(false);
      GameOverController.catImage.setVisible(true);
      GameOverController.catImage2.setVisible(true);

      playAgainButton.setLayoutX(335);
      playAgainButton.setLayoutY(450);

      SoundManager.playSound("GuessIncorrect.mp3");
    }
    GameOverController.timeUpLabel.setVisible(false);
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
}
