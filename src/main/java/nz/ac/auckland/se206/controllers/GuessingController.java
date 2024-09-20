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
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionRequest;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.config.ApiProxyConfig;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.SceneType;
import nz.ac.auckland.se206.prompts.PromptEngineering;
import nz.ac.auckland.se206.tasks.RunGptTask;

public class GuessingController implements Restartable {
  private static boolean correctChoice;
  private static String feedback;

  public static String getFeedback() {
    return feedback;
  }

  // getter for correctChoice
  public static boolean getCorrectChoice() {
    return correctChoice;
  }

  @FXML private TextArea explaintxt;
  @FXML private Rectangle bob;
  @FXML private Rectangle vicePresident;
  @FXML private Rectangle third;
  @FXML private Button submitBtn;
  @FXML private Label timerLabel;
  private String explanation;
  private boolean isClicked = false;

  private Rectangle selectedRectangle;
  private ChatCompletionRequest chatCompletionRequest;

  private List<Rectangle> suspectOptions;

  private ChatMessage msg;

  public void initialize() {
    // Add a listener to check if TextArea has text input
    submitBtn.setStyle(
        "-fx-border-color: red; -fx-border-width: 2px; -fx-padding: 4 6; -fx-border-style: solid;"
            + " -fx-background-insets: 0;");

    // Add a listener to check if TextArea has text input
    Timeline timeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                event -> {
                  // If the text area is empty or the user has not clicked on a suspect, disable the
                  // submit button
                  if (explaintxt.getText() == null
                      || explaintxt.getText().trim().isEmpty()
                      || !isClicked) {
                    submitBtn.setDisable(true);
                  } else {
                    submitBtn.setDisable(false);
                  }
                }));

    // Set the cycle count of the timeline to indefinite
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();

    // Add the suspect options to the list
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
    // Check if the user has selected a suspect
    explanation = explaintxt.getText().trim();
    if (explanation.isEmpty()) {

      return;
    }

    // Set the explanation in the chat message
    msg = new ChatMessage("user", explanation);
    App.changeScene(SceneType.PROCESSING_SUBMISSION);

    // Run the GPT model
    setupGpt();
    GameOverController.showResult();

    MenuController.gameTimer.stop();
  }

  public void timeUpExplanation() {
    explanation = explaintxt.getText().trim();
    if (explanation.isEmpty()) {
      return;
    }
    App.changeScene(SceneType.PROCESSING_SUBMISSION);

    msg = new ChatMessage("user", explanation);
    setupGpt();
  }

  public void chooseCriminal(MouseEvent event) {
    isClicked = true;
    MenuController.gameTimer.setSuspectChosenTrue();
    Rectangle clickedRectangle = (Rectangle) event.getSource();

    selectedRectangle = clickedRectangle;
    selectRectangle();

    // Check if the correct suspect was chosen
    if (clickedRectangle == vicePresident) {
      correctChoice = true;
    } else {
      correctChoice = false;
    }
  }

  public Label getTimerLabel() {
    return timerLabel;
  }

  public GuessingController getGuessingController() {
    return this;
  }

  @Override
  public void restart() {
    // Reset the variables
    isClicked = false;
    correctChoice = false;
    explaintxt.clear();
    for (Rectangle rect : suspectOptions) {
      rect.setStroke(Color.RED);
    }
    selectedRectangle = null;
  }

  /**
   * Begins the chat with the GPT model by setting up the GPT model with the suspect type.
   *
   * @param suspectId the ID of the suspect the user is chatting with
   */
  public void setupGpt() {
    // this.setLoading(true);
    try {
      ApiProxyConfig config = ApiProxyConfig.readConfig();

      chatCompletionRequest =
          new ChatCompletionRequest(config)
              .setN(1)
              .setTemperature(0.2)
              .setTopP(0.5)
              .setMaxTokens(100);
      runGpt(new ChatMessage("system", getSystemPrompt()), false);
    } catch (ApiProxyException e) {
      e.printStackTrace();
    }
  }

  /**
   * Runs the GPT model with a given chat message.
   *
   * @param msg the chat message to process
   */
  private void runGpt(ChatMessage msg, boolean isUser) {
    RunGptTask gptTask = new RunGptTask(msg, chatCompletionRequest);

    gptTask.setOnSucceeded(
        event -> {
          if (!isUser) {
            runGpt(this.msg, true);
            return;
          }

          // Set the result
          ChatMessage result = gptTask.getResult();
          feedback = result.getContent();
          GameOverController.getFeedbackTextArea().setText(GuessingController.getFeedback());
          App.changeScene(SceneType.FEEDBACK);
        });

    new Thread(gptTask).start();
  }

  /**
   * Generates the system prompt based on the suspect type.
   *
   * @return the system prompt string
   */
  private String getSystemPrompt() {
    return PromptEngineering.getPrompt("feedback");
  }
}
