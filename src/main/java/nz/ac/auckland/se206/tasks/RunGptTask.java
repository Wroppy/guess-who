package nz.ac.auckland.se206.tasks;

import javafx.concurrent.Task;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionRequest;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionResult;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.chat.openai.Choice;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;

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
    req.addMessage(msg);
    try {
      ChatCompletionResult chatCompletionResult = req.execute();
      Choice result = chatCompletionResult.getChoices().iterator().next();
      req.addMessage(result.getChatMessage());

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
