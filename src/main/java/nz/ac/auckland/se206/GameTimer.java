package nz.ac.auckland.se206;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.SceneManager.SceneType;

public class GameTimer {
  private static final int TIME_LIMIT = 300; // 2 minutes in seconds
  private int timeRemaining;
  private Label timerLabel1;
  private Label timerLabel2;
  private GameStateContext context;
  private volatile boolean running; // Flag to control the running state
  private boolean firstFiveMinutes = true;

  public GameTimer(Label timerLabel1, GameStateContext context) {
    this.timerLabel1 = timerLabel1;
    this.context = context;
    this.timeRemaining = TIME_LIMIT;
    this.running = true; // Initialize the flag to true
  }

  // This method starts the timer, and updates the timer label every second
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
              Thread.sleep(1000);
              timeRemaining--;
            }
            if (running && firstFiveMinutes) {
              Platform.runLater(() -> context.setState(context.getGuessingState()));
              Platform.runLater(() -> App.changeScene(SceneType.PLAYER_EXPLANATION));
              // Playing corresponding sound

              // setTimeRemaining(10);
              setFirstFiveMinutesFalse();
              start();
            } else if (running && !firstFiveMinutes) {
              Platform.runLater(() -> context.setState(context.getGameOverState()));
            }

            return null;
          }
        };
    new Thread(task).start();
  }

  public void setTimerLabel2(Label timerLabel2) {
    this.timerLabel2 = timerLabel2;
  }

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
}
