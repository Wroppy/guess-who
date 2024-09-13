package nz.ac.auckland.se206.components.gameheader;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.App;

public class GameHeader extends Pane {
  @FXML private Label roomLabel;
  @FXML private ComboBox<String> roomComboBox;
  @FXML private Button guessButton;

  public GameHeader() {
    super();

    try {
      FXMLLoader loader = App.loadFxmlLoader("game-header");
      loader.setRoot(this);
      loader.setController(this);
      loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
