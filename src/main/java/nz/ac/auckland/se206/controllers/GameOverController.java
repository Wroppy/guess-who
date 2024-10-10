package nz.ac.auckland.se206.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.utils.CallBack;

/**
 * Manages the game over screen, displaying results, and feedback for the player.
 *
 * <p>This class handles the presentation of the game results and provides an option to play again.
 * It shows appropriate feedback based on whether the player made the correct choice or not, and can
 * display related images and messages.
 */
public class GameOverController implements Restartable {
  private static ImageView catImage;
  private static ImageView catImage2;
  private static Label resultLabel;
  private static Text feedbackLabel;
  private static TextArea feedbackTextArea;
  private static Button playAgainButton;
  private static ImageView vpStatic;
  private static ImageView vpPinStatic;
  private static Label incorrectGuess;

  private static Label timeUpLabel;

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

  public static ImageView getVp() {
    return vpStatic;
  }

  public static ImageView getVpPin() {
    return vpPinStatic;
  }

  /**
   * Displays the result of the game based on whether the player's choice was correct. Updates the
   * result label, feedback text area, and visibility of UI components based on the outcome of the
   * game.
   */
  public static void showResult() {

    feedbackTextArea.setText("Loading feedback...");
    if (GuessingController.getCorrectChoice()) {
      GameOverController.resultLabel.setText("Correct Choice!");
      GameOverController.resultLabel.setLayoutY(85);

      playAgainButton.setLayoutX(335);
      playAgainButton.setLayoutY(507);
      GameOverController.timeUpLabel.setVisible(false);
      resultLabel.setVisible(true);

      GameOverController.vpStatic.setVisible(true);
      GameOverController.vpPinStatic.setVisible(true);
      GameOverController.incorrectGuess.setVisible(false);

      GameOverController.feedbackTextArea.setFont(Font.font("Bell MT", FontWeight.BOLD, 18));

      GameOverController.feedbackTextArea.setVisible(true);
      // GameOverController.feedbackLabel.setVisible(true);

      GameOverController.catImage.setVisible(false);
      GameOverController.catImage2.setVisible(false);

    } else {

      // Hide the feedback text area and label
      // GameOverController.resultLabel.setText("Incorrect Choice!");
      // GameOverController.resultLabel.setFont(Font.font("System", 28));
      // GameOverController.resultLabel.setLayoutY(250);
      GameOverController.feedbackTextArea.setVisible(false);
      GameOverController.feedbackLabel.setVisible(false);
      GameOverController.timeUpLabel.setVisible(false);
      // resultLabel.setVisible(true);

      // Show the cat images
      GameOverController.catImage.setVisible(false);
      GameOverController.catImage2.setVisible(false);

      playAgainButton.setLayoutX(335);
      playAgainButton.setLayoutY(450);

      GameOverController.vpStatic.setVisible(false);
      GameOverController.vpPinStatic.setVisible(false);
      GameOverController.incorrectGuess.setFont(Font.font("Bebas Neue", 47));
      GameOverController.incorrectGuess.setVisible(true);
      GameOverController.resultLabel.setVisible(false);
    }
    GameOverController.timeUpLabel.setVisible(false);
  }

  @FXML private Label result;
  @FXML private TextArea feedbacktxt;
  @FXML private Text feedback;
  @FXML private Button playAgain;
  @FXML private ImageView showCat;
  @FXML private ImageView showCat2;
  @FXML private Label timeUp;
  @FXML private ImageView vp;
  @FXML private ImageView vpPin;
  @FXML private Label guessIncorrect;
  @FXML private Text gameOver;

  private CallBack onRestart;

  /**
   * Initializes the GameOverController by setting up the static variables and configuring the
   * visibility of certain UI components.
   */
  public void initialize() {
    // Set the static variables
    GameOverController.resultLabel = result;
    GameOverController.feedbackLabel = feedback;
    GameOverController.feedbackTextArea = feedbacktxt;
    GameOverController.playAgainButton = playAgain;
    GameOverController.incorrectGuess = guessIncorrect;

    incorrectGuess.setVisible(false);

    showCat.setVisible(false);
    showCat2.setVisible(false);

    GameOverController.vpStatic = vp;
    GameOverController.vpPinStatic = vpPin;
    vpStatic.setVisible(false);
    vpPinStatic.setVisible(false);

    // Set the static variables
    GameOverController.catImage = showCat;
    GameOverController.catImage2 = showCat2;

    GameOverController.timeUpLabel = timeUp;

    gameOver.setFont(Font.font("Bebas Neue", 70));
  }

  @FXML
  private void onPlayAgainClicked(ActionEvent event) {
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
