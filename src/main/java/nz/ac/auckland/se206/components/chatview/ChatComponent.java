package nz.ac.auckland.se206.components.chatview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionRequest;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.config.ApiProxyConfig;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.SceneType;
import nz.ac.auckland.se206.components.gameheader.GameHeader;
import nz.ac.auckland.se206.prompts.PromptEngineering;
import nz.ac.auckland.se206.tasks.RunGptTask;

/** A component that displays a chat interface for the user to interact with the GPT model. */
public class ChatComponent extends VBox {
  private boolean loading;
  private SceneType sceneType;
  private ChatCompletionRequest chatCompletionRequest;

  @FXML private Label sendMessageLabel;
  @FXML private Pane sendMessageButton;

  @FXML private TextField textInput;
  @FXML private Label suspectNameLabel;
  @FXML private TextArea chatBox;
  @FXML private Label historyLabel;

  @FXML private Button goUp;
  @FXML private Button goDown;
  @FXML private Button goEnd;

  private LoaderComponent loaderComponent;
  private Map<String, String> suspectMap = new HashMap<>();
  private ArrayList<String> chatHistory;
  private int chatIndex = 0;

  /**
   * Creates a new chat component with a given scene type.
   *
   * @param sceneType the scene type of the chat component
   */
  public ChatComponent(SceneType sceneType) {
    this.sceneType = sceneType;
    this.chatHistory = new ArrayList<>();
    this.loading = false;
    try {
      FXMLLoader loader = App.loadFxmlLoader("chat");

      loader.setController(this);
      loader.setRoot(this);

      loader.load();

      setupButton();

      // Sets up the GPT model
      this.setupGpt();

      this.styleWidget();

      setSuspectName();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /** Initializes the chat component. Puts the suspects in a map for easy access. */
  public void initialize() {
    // Any required initialization code can be placed here
    suspectMap.put("Suspect 1", "Dominic");
    suspectMap.put("Suspect 2", "Sebastian");
    suspectMap.put("Suspect 3", "Alexandra");
  }

  /** Styles the chat component with the CSS file. */
  private void styleWidget() {
    String styles = App.getCssUrl("chat-box");

    this.getStylesheets().add(styles);
  }

  /** Sets the suspect's name in the chat component. */
  public void setSuspectName() {
    suspectNameLabel.setText(suspectMap.get(sceneType.toString()));
  }

  /** Sets up the send button with the loader component. */
  private void setupButton() {
    loaderComponent = new LoaderComponent();
    loaderComponent.setPrefWidth(
        sendMessageButton.getPrefWidth() - this.getPadding().getBottom() * 2);
    sendMessageButton.getChildren().add(loaderComponent);
  }

  /**
   * Called when a key is pressed inside the text box.
   *
   * @param e key event of the text box
   */
  @FXML
  private void onKeyPressed(KeyEvent event) {
    // Sends message when the enter key is pressed
    if (event.getCode() == KeyCode.ENTER) {
      this.sendMessage();
    }
  }

  /**
   * Sets the loading state of the chat component.
   *
   * @param loading the loading state of the chat component
   */
  private void setLoading(boolean loading) {
    this.loading = loading;

    // Set the visibility of the loader component based on the loading state
    loaderComponent.setVisible(loading);
    // Hide the send message label while loading
    sendMessageLabel.setVisible(!loading);

    if (loading) {
      setChatboxLoading();
    }
    // Enable or disable navigation buttons based on the loading state
    goUp.setDisable(loading);
    goDown.setDisable(loading);
    goEnd.setDisable(loading);
  }

  /** Sends a message to the GPT model. */
  private void sendMessage() {
    String message = textInput.getText().trim();
    if (message.isEmpty()) {
      return;
    }

    // Checks if the component is loading
    if (loading) {
      return;
    }
    GameHeader.setTalkedTo(sceneType);
    textInput.clear();
    ChatMessage msg = new ChatMessage("user", message);
    runGpt(msg);
  }

  /** Sets the chat box to a loading state, indicating that the GPT model is processing the chat. */
  private void setChatboxLoading() {
    // Clear any existing text in the chat box
    chatBox.clear();
    // Define the duration for each loading dot (0.1 seconds)
    Duration sec = Duration.seconds(0.1);
    Timeline timeline =
        new Timeline(
            new KeyFrame(
                sec, // Duration for each frame
                e -> {
                  // Append a dot to the chat box every 0.1 seconds
                  chatBox.appendText(".");
                }));
    // Set the timeline to repeat 3 times
    timeline.setCycleCount(3);
    timeline.play();
  }

  /**
   * Handler for the send button.
   *
   * @param event the action event triggered by the send button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void handleSendMessageClicked(MouseEvent event) throws ApiProxyException, IOException {
    this.sendMessage();
  }

  /**
   * Runs the GPT model with a given chat message.
   *
   * @param msg the chat message to process
   */
  private void runGpt(ChatMessage msg) {
    this.setLoading(true);
    RunGptTask gptTask = new RunGptTask(msg, chatCompletionRequest);
    // Sets the event handler for when the task is completed
    gptTask.setOnSucceeded(
        event -> {
          // Sets the event handler for when the task is completed
          this.setLoading(false);
          ChatMessage result = gptTask.getResult();
          appendChatMessage(result);
        });

    new Thread(gptTask).start();
  }

  /**
   * Appends a chat message to the chat text area.
   *
   * @param msg the chat message to append
   */
  private void appendChatMessage(ChatMessage msg) {

    chatBox.setText(msg.getContent());

    chatHistory.add(msg.getContent());
    goToChatHistoryEnd();
  }

  public void setText(String text) {
    chatBox.setText(text);
  }

  /**
   * Generates the system prompt based on the suspect type.
   *
   * @return the system prompt string
   */
  private String getSystemPrompt() {
    final String promptId = sceneType.toString().toLowerCase().replace(" ", "-");
    return PromptEngineering.getPrompt(promptId);
  }

  /** Begins the chat with the GPT model by setting up the GPT model with the suspect type. */
  public void setupGpt() {
    try {
      // Reads the API proxy configuration
      ApiProxyConfig config = ApiProxyConfig.readConfig();
      // Sets up the chat completion request
      chatCompletionRequest =
          new ChatCompletionRequest(config)
              .setN(1)
              .setTemperature(0.2)
              .setTopP(0.5)
              .setMaxTokens(200);

      // Runs the GPT model with the system prompt
      runGpt(new ChatMessage("system", getSystemPrompt()));
    } catch (ApiProxyException e) {
      e.printStackTrace();
    }
  }

  /**
   * Restarts the chat component by clearing the chat box and text input and setting up the GPT
   * model.
   */
  public void restart() {
    chatBox.clear();
    setupGpt();
    textInput.clear();
    chatHistory.clear();
  }

  /**
   * Changes the chat to a specific index in the chat history.
   *
   * @param index the index to change the chat to in the chat history
   */
  private void changeChatToIndex(int index) {
    if (index < 0 || index >= chatHistory.size()) {
      return;
    }
    chatIndex = index;
    chatBox.setText(chatHistory.get(chatIndex));
    setHistoryLabel();
  }

  /**
   * Navigates back in the chat history by one message. If the chat is at the beginning of the chat
   * history, it does nothing.
   */
  public void goBackInChatHistory() {
    if (loading) {
      return;
    }
    changeChatToIndex(chatIndex - 1);
  }

  public void goForwardInChatHistory() {
    if (loading) {
      return;
    }
    changeChatToIndex(chatIndex + 1);
  }

  public void goToChatHistoryEnd() {
    if (loading) {
      return;
    }
    changeChatToIndex(chatHistory.size() - 1);
  }

  private void setHistoryLabel() {
    historyLabel.setText((chatIndex + 1) + "/" + chatHistory.size());
  }
}
