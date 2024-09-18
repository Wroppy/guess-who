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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.SceneType;
import nz.ac.auckland.se206.controllers.RoomController;

public class GameHeader extends Pane {
  @FXML private Label roomLabel;
  @FXML private ComboBox<SceneType> roomComboBox;
  @FXML private static Button guessButton;

  private SceneType currentScene;
  private RoomController roomController;

  public GameHeader(SceneType sceneType) {
    this(sceneType, null);
  }

  public GameHeader(SceneType sceneType, RoomController roomController) {
    super();
    this.roomController = roomController;
    currentScene = sceneType;

    try {
      FXMLLoader loader = App.loadFxmlLoader("game-header");
      loader.setRoot(this);
      loader.setController(this);
      loader.load();

      changeLabel(sceneType);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void initialize() {
    setupComboBox();

    colourHeader();
  }

  private void colourHeader() {
    switch (currentScene) {
      case CRIME:
        this.setStyle("-fx-background-color: linear-gradient(to right, #cee3ec, #7c9fb0);");
        break;
      case SUSPECT_1:
        this.setStyle("-fx-background-color: linear-gradient(to right, #cdc2a4, #313130);");
        break;
      case SUSPECT_2:
        this.setStyle("-fx-background-color: linear-gradient(to right, #ead8c1, #483223);");
        break;
      case SUSPECT_3:
        this.setStyle("-fx-background-color: linear-gradient(to right, #dab576, #503218);");
        break;
      default:
        break;
    }
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

    if (roomController != null) {
      roomController.getAccessPad().setVisible(false);
      roomController.getAccessUnlock().setVisible(false);
      roomController.getShredderClueOverlay().setVisible(false);
      roomController.removeLaptopOverlay();
    }

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

  public void guessingStage(MouseEvent event) throws IOException {
    App.changeScene(SceneType.PLAYER_EXPLANATION);
  }
}
