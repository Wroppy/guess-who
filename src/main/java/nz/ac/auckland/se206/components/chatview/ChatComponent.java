package nz.ac.auckland.se206.components.chatview;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionRequest;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.config.ApiProxyConfig;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.SceneType;
import nz.ac.auckland.se206.prompts.PromptEngineering;
import nz.ac.auckland.se206.tasks.RunGptTask;

public class ChatComponent extends VBox {
  private boolean loading;
  private SceneType sceneType;
  private ChatCompletionRequest chatCompletionRequest;
  private Label sendMessageLabel;

  @FXML private Pane sendMessageButton;

  @FXML private TextField textInput;
  @FXML private TextArea chatBox;

  private LoaderComponent loaderComponent;

  public ChatComponent(SceneType sceneType) {
    this.sceneType = sceneType;
    this.loading = false;

    try {
      FXMLLoader loader = App.loadFxmlLoader("chat");

      loader.setController(this);
      loader.setRoot(this);

      loader.load();

      setupButton();

      this.setupGpt();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void setupButton() {
    loaderComponent = new LoaderComponent();
    loaderComponent.setPrefWidth(
        sendMessageButton.getPrefWidth() - this.getPadding().getBottom() * 2);
    loaderComponent.setStyle(
        "-fx-border-color: black; -fx-border-width: 1px; -fx-border-style: solid;");
    sendMessageButton.getChildren().add(loaderComponent);
  }

  /**
   * Called when a key is pressed inside the text box
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

  private void setLoading(boolean loading) {
    this.loading = loading;

    // Shows or hides loading for user experience
    if (loading) {

    } else {

    }
  }

  /** Sends a message to the GPT model. */
  private void sendMessage() {
    String message = textInput.getText().trim();
    if (message.isEmpty()) {
      return;
    }
    // Checks if the component is loadwing
    // if (this.isLoading()) {
    // return;
    // }

    textInput.clear();
    ChatMessage msg = new ChatMessage("user", message);
    appendChatMessage(msg);
    runGpt(msg);
  }

  /**
   * Handler for the send button
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

    gptTask.setOnSucceeded(
        event -> {
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
    String heading = msg.getRole().replaceFirst("assistant", sceneType.toString());
    chatBox.appendText(heading + ": " + msg.getContent() + "\n\n");
  }

  public void setText(String text) {
    chatBox.setText(text);
  }

  // public void setName(String name) {
  // this.name = name;
  // }

  /**
   * Generates the system prompt based on the suspect type.
   *
   * @return the system prompt string
   */
  private String getSystemPrompt() {
    final String promptId = sceneType.toString().toLowerCase().replace(" ", "-");
    return PromptEngineering.getPrompt(promptId);
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
}
