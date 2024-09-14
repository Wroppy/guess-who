package nz.ac.auckland.se206.components.chatview;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import nz.ac.auckland.se206.App;

public class ChatComponent extends VBox {

  public ChatComponent() {
    try {
      FXMLLoader loader = App.loadFxmlLoader("chat");

      loader.setController(this);
      loader.setRoot(this);

      loader.load();

      // TODO: Set up the GPT model for the suspect
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Called when the button is clicked
   *
   * @param e mouse event
   */
  @FXML
  private void onSendMessage(MouseEvent e) {}

  /**
   * Called when a key is pressed inside the text box
   *
   * @param e key event of the text box
   */
  @FXML
  private void onKeyPressed(KeyEvent e) {}
}
