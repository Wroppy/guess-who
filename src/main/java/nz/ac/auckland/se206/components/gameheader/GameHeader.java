package nz.ac.auckland.se206.components.gameheader;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
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
    roomComboBox.setButtonCell(
        new ListCell<SceneType>() {
          @Override
          protected void updateItem(SceneType item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
              setText("Switch to room...");
            }
          }
        });

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

    addComboBoxItems();

    roomComboBox.setOnAction(e -> onRoomChange(e));
  }

  private void onRoomChange(ActionEvent e) {
    SceneType selectedScene = roomComboBox.getSelectionModel().getSelectedItem();

    if (selectedScene == null) {
      return;
    }

    // Deselect the item and go back to default
    Platform.runLater(() -> clearComboBoxSelection());

    changeScene(selectedScene);
  }

  private void clearComboBoxSelection() {
    roomComboBox.getSelectionModel().clearSelection();
  }

  private void changeLabel(SceneType sceneType) {
    roomLabel.setText("Room: " + sceneType.toString());
  }

  private void changeScene(SceneType sceneType) {
    App.changeScene(sceneType);
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
