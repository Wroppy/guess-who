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
import javafx.scene.text.Font;
import javafx.util.Duration;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionRequest;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.config.ApiProxyConfig;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.SceneType;
import nz.ac.auckland.se206.prompts.PromptEngineering;
import nz.ac.auckland.se206.tasks.RunGptTask;

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
  private ChatCompletionRequest chatCompletionRequest;
  private static String feedback;

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
    if (explanation.isEmpty()) {
      return;
    }
    ChatMessage msg = new ChatMessage("user", explanation);

    setupGpt();
    runGpt(msg);
    // TODO: Send explanation to GPT

    GameOverController.showResult();

    MenuController.gameTimer.stop();
    App.changeScene(SceneType.FEEDBACK);
  }

  public void timeUpExplanation() {
    explanation = explaintxt.getText().trim();
    if (explanation.isEmpty()) {
      return;
    }
    ChatMessage msg = new ChatMessage("user", explanation);

    setupGpt();
    runGpt(msg);
  }

  public void choiceCriminal(MouseEvent event) {
    isClicked = true;
    MenuController.gameTimer.setSuspectChosenTrue();
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

  /**
   * Begins the chat with the GPT model by setting up the GPT model with the suspect type.
   *
   * @param suspectId the ID of the suspect the user is chatting with
   */
  public void setupGpt() {
    try {
      ApiProxyConfig config = ApiProxyConfig.readConfig();
      chatCompletionRequest =
          new ChatCompletionRequest(config)
              .setN(1)
              .setTemperature(0.2)
              .setTopP(0.5)
              .setMaxTokens(100);
      runGpt(new ChatMessage("system", getSystemPrompt()));
    } catch (ApiProxyException e) {
      e.printStackTrace();
    }
  }

  /**
   * Runs the GPT model with a given chat message.
   *
   * @param msg the chat message to process
   */
  private void runGpt(ChatMessage msg) {
    // this.setLoading(true);
    RunGptTask gptTask = new RunGptTask(msg, chatCompletionRequest);

    gptTask.setOnSucceeded(
        event -> {
          // this.setLoading(false);
          ChatMessage result = gptTask.getResult();
          feedback = result.getContent();
          GameOverController.getFeedbackTextArea().setText(GuessingController.getFeedback());
          GameOverController.getFeedbackTextArea().setFont(Font.font("System", 22));
          // appendChatMessage(result);
        });

    new Thread(gptTask).start();
  }

  /**
   * Generates the system prompt based on the suspect type.
   *
   * @return the system prompt string
   */
  private String getSystemPrompt() {
    // final String promptId = sceneType.toString().toLowerCase().replace(" ", "-");
    return PromptEngineering.getPrompt("feedback");
  }

  public static String getFeedback() {
    return feedback;
  }
}
