package nz.ac.auckland.se206.tasks;

import javafx.concurrent.Task;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionRequest;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionResult;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.chat.openai.Choice;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;

/**
 * A background task for running a chat completion request using the GPT model.
 *
 * <p>This class extends the JavaFX Task class to execute chat message requests without blocking the
 * JavaFX application thread. It handles the interaction with the chat completion API, processes the
 * user message, and retrieves the model's response.
 */
public class RunGptTask extends Task<Void> {
  private ChatMessage msg;
  private ChatCompletionRequest req;
  private ChatMessage result;

  public RunGptTask(ChatMessage msg, ChatCompletionRequest req) {
    this.msg = msg;
    this.req = req;
  }

  @Override
  protected Void call() throws Exception {
    // Add the user message to the request
    req.addMessage(msg);
    try {
      // Execute the request
      ChatCompletionResult chatCompletionResult = req.execute();
      Choice result = chatCompletionResult.getChoices().iterator().next();
      req.addMessage(result.getChatMessage());

      // Set the result
      this.result = result.getChatMessage();

      return null;
    } catch (ApiProxyException e) {
      e.printStackTrace();
      this.result = new ChatMessage("user", "I'm sorry, I'm not too sure what to say.");
      return null;
    }
  }

  public ChatMessage getResult() {
    return this.result;
  }
}
