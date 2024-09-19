package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameOverController {
  @FXML private Label result;
  @FXML private TextArea feedbacktxt;
  @FXML private Text feedback;
  @FXML private Button playAgain;
  @FXML private ImageView showCat;
  @FXML private ImageView showCat2;

  private static ImageView catImage;
  private static ImageView catImage2;
  private static Label resultLabel;
  private static Text feedbackLabel;
  private static TextArea feedbackTextArea;
  private static Button playAgainButton;


  public void initialize() {
    GameOverController.resultLabel = result;
    GameOverController.feedbackLabel = feedback;
    GameOverController.feedbackTextArea = feedbacktxt;
    GameOverController.playAgainButton = playAgain;
    showCat.setVisible(false);
    showCat2.setVisible(false);
    GameOverController.catImage = showCat;
    GameOverController.catImage2 = showCat2;

  }

  public static void showResult() {
    if (GuessingController.getCorrectChoice()) {
      GameOverController.resultLabel.setText("Correct Choice!");
      GameOverController.resultLabel.setLayoutY(106);

      playAgainButton.setLayoutX(335);
      playAgainButton.setLayoutY(507);

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
    }
  }
}
