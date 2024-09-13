package nz.ac.auckland.se206.components.gameheader;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.SceneType;

public class GameHeader extends Pane {
  @FXML private Label roomLabel;
  @FXML private ComboBox<SceneType> roomComboBox;
  @FXML private Button guessButton;

  private SceneType currentScene;

  public GameHeader(SceneType sceneType) {
    super();

    try {
      FXMLLoader loader = App.loadFxmlLoader("game-header");
      loader.setRoot(this);
      loader.setController(this);
      loader.load();

      currentScene = sceneType;
      changeLabel(sceneType);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void initialize() {
    setupComboBox();
  }

  private void setupComboBox() {
    addComboBoxItems();
    roomComboBox.setConverter(
        new StringConverter<SceneType>() {
          @Override
          public String toString(SceneType object) {
            return object.toString();
          }

          @Override
          public SceneType fromString(String string) {
            return null;
          }
        });

    roomComboBox.setOnAction(e -> onRoomChange(e));
  }

  private void onRoomChange(ActionEvent e) {
    SceneType selectedScene = roomComboBox.getSelectionModel().getSelectedItem();

    changeScene(selectedScene);
  }

  private void changeLabel(SceneType sceneType) {
    roomLabel.setText("Room: " + sceneType.toString());
  }

  private void changeScene(SceneType sceneType) {
    System.out.println("Changed scene to " + sceneType);
    App.changeScene(sceneType);
    System.out.println("Changed scene to " + sceneType);
  }

  private void addComboBoxItems() {
    SceneType[] sceneTypes = {
      SceneType.CRIME, SceneType.SUSPECT_1, SceneType.SUSPECT_2, SceneType.SUSPECT_3
    };

    for (SceneType sceneType : sceneTypes) {
      roomComboBox.getItems().add(sceneType);
    }
  }

  public void setScene(SceneType sceneType) {
    currentScene = sceneType;
    changeLabel(sceneType);
  }
}
