package nz.ac.auckland.se206.components.chatview;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nz.ac.auckland.se206.App;

public class ChatComponent extends VBox {
  private boolean loading;

  @FXML private Pane sendMessageButton;

  @FXML private TextField textInput;
  @FXML private TextArea chatBox;

  private LoaderComponent loaderComponent;

  public ChatComponent() {
    this.loading = false;

    try {
      FXMLLoader loader = App.loadFxmlLoader("chat");

      loader.setController(this);
      loader.setRoot(this);

      loader.load();

      setupButton();

      // TODO: Set up the GPT model for the suspect
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

    // Adds it to the center of the layout
    // loaderComponent.setLayoutX((sendMessageButton.getWidth() - loaderComponent.getWidth()) / 2);
    // loaderComponent.setLayoutY((sendMessageButton.getHeight() - loaderComponent.getHeight()) /
    // 2);
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

  private void setLoading(boolean loading) {
    this.loading = loading;

    // Shows or hides loading for user experience
    if (loading) {

    } else {

    }
  }
}
