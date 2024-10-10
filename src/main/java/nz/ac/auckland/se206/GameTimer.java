package nz.ac.auckland.se206;

import java.util.HashMap;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import nz.ac.auckland.se206.SceneManager.SceneType;
import nz.ac.auckland.se206.components.accesspadclue.AccessPadClue;
import nz.ac.auckland.se206.components.gameheader.GameHeader;
import nz.ac.auckland.se206.components.shredderclue.ShredderClueComponent;
import nz.ac.auckland.se206.controllers.GameOverController;
import nz.ac.auckland.se206.controllers.GuessingController;
import nz.ac.auckland.se206.controllers.LaptopController;

/**
 * Class for a countdown timer for the game.
 *
 * <p>The GameTimer manages the remaining time for the game, updating associated UI labels every
 * second. Depending on different conditions, different actions will be triggered.
 */
public class GameTimer {
  private static final int TIME_LIMIT = 300; // should 5 minutes in seconds

  private int timeRemaining;
  private Label timerLabel1;
  private Label timerLabel2;
  private Label timerLabel3;
  private GameStateContext context;
  private volatile boolean running; // Flag to control the running state
  private boolean firstFiveMinutes = true;
  private boolean isSuspectChosen = false;
  private GuessingController guessingController;
  private HashMap<SceneType, Boolean> talkedTo = new HashMap<SceneType, Boolean>();

  /**
   * Constructs a GameTimer instance with the specified parameters.
   *
   * @param timerLabel1 The label used to display the timer's remaining time.
   * @param context The context that manages the game's state.
   * @param guessingController The controller responsible for managing guessing game interactions.
   */
  public GameTimer(
      Label timerLabel1, GameStateContext context, GuessingController guessingController) {
    this.timerLabel1 = timerLabel1;
    this.context = context;
    this.timeRemaining = TIME_LIMIT;
    this.running = true; // Initialize the flag to true
    this.timerLabel3 = guessingController.getTimerLabel();
    this.guessingController = guessingController;
    talkedTo = GameHeader.getTalkedTo();
  }

  /** Starts the countdown timer, updating the timer labels every second and triggering actions. */
  public void start() {
    Task<Void> task =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            while (timeRemaining >= 0 && running) {
              // Update the timer label on the JavaFX Application Thread
              Platform.runLater(() -> timerLabel1.setText(formatTime(timeRemaining)));
              if (timerLabel2 != null) {
                Platform.runLater(() -> timerLabel2.setText(formatTime(timeRemaining)));
              }
              if (timerLabel3 != null) {
                Platform.runLater(() -> timerLabel3.setText(formatTime(timeRemaining)));
              }
              Thread.sleep(1000);
              timeRemaining--;
            }
            if(!talkedTo.get(SceneType.SUSPECT_1) || !talkedTo.get(SceneType.SUSPECT_2) || !talkedTo.get(SceneType.SUSPECT_3) || (!LaptopController.isEmailOpened() && !ShredderClueComponent.isPaperClue() && !AccessPadClue.isUnlocked())) {
              Platform.runLater(() -> App.changeScene(SceneType.FEEDBACK));
              GameOverController.getFeedbackLabel().setVisible(false);
              GameOverController.getFeedbackTextArea().setVisible(false);
            } else if (running && firstFiveMinutes) {
              Platform.runLater(() -> context.setState(context.getGuessingState()));
              Platform.runLater(() -> App.changeScene(SceneType.PLAYER_EXPLANATION));
              // Playing corresponding sound
              SoundManager.playSound("5MinuteUp.mp3");

              setTimeRemaining(60);
              setFirstFiveMinutesFalse();
              start();
            } else if (running && !firstFiveMinutes && !isSuspectChosen) {
              // If the time is up, change the state to GameOverState
              Platform.runLater(() -> context.setState(context.getGameOverState()));
              GameOverController.getFeedbackTextArea().setVisible(false);
              GameOverController.getFeedbackLabel().setVisible(false);
              GameOverController.getResultLabel().setVisible(false);

              // Show the time up label
              GameOverController.getTimeUpLabel().setVisible(true);
              GameOverController.getTimeUpLabel().setFont(Font.font("Verdana", 33));
              SoundManager.playSound("TimeUpLost.mp3");
              GameOverController.getVp().setVisible(false);
              GameOverController.getVpPin().setVisible(false);
              Platform.runLater(() -> App.changeScene(SceneType.FEEDBACK));

              // Show the time up explanation
            } else if (running && !firstFiveMinutes && isSuspectChosen) {
              Platform.runLater(() -> context.setState(context.getGuessingState()));

              // Playing corresponding sound
              SoundManager.playSound("TimeUpWritten.mp3");
              Platform.runLater(() -> GameOverController.showResult());
              Platform.runLater(() -> guessingController.timeUpExplanation());
            }

            return null;
          }
        };
    new Thread(task).start();
  }

  public void setTimerLabel2(Label timerLabel2) {
    this.timerLabel2 = timerLabel2;
  }

  /**
   * Formats the given time in seconds into a string representation of minutes and seconds.
   *
   * @param seconds The time in seconds to be formatted.
   * @return A string representing the formatted time in "MM:SS" format.
   */
  public String formatTime(int seconds) {
    int minutes = seconds / 60;
    int secs = seconds % 60;
    return String.format("%02d:%02d", minutes, secs);
  }

  public int getTimeRemaining() {
    return timeRemaining;
  }

  public void stop() {
    running = false; // Set the flag to false to stop the timer
  }

  public void setTimeRemaining(int timeRemaining) {
    this.timeRemaining = timeRemaining;
  }

  public void setFirstFiveMinutesFalse() {
    firstFiveMinutes = false;
  }

  public void setTimerLabel1(Label timerLabel1) {
    this.timerLabel1 = timerLabel1;
  }

  public void setTimerLabel3(Label timerLabel3) {
    this.timerLabel3 = timerLabel3;
  }

  public Label getTimerLabel3() {
    return timerLabel3;
  }

  public void setSuspectChosenTrue() {
    isSuspectChosen = true;
  }
}
