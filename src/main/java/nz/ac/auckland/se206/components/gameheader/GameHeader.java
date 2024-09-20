package nz.ac.auckland.se206.components.gameheader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import javafx.util.StringConverter;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.SceneType;
import nz.ac.auckland.se206.SoundManager;
import nz.ac.auckland.se206.components.accesspadclue.AccessPadClue;
import nz.ac.auckland.se206.components.shredderclue.ShredderClueComponent;
import nz.ac.auckland.se206.controllers.LaptopController;
import nz.ac.auckland.se206.controllers.MenuController;
import nz.ac.auckland.se206.controllers.RoomController;
import nz.ac.auckland.se206.controllers.SuspectRoomController;

public class GameHeader extends Pane {
  @FXML private Label roomLabel;
  @FXML private ComboBox<SceneType> roomComboBox;
  @FXML private Button guessBtn;
  @FXML private Button informationBtn;
  @FXML public Label timerLabel;

  private SceneType currentScene;
  private static HashMap<SceneType, Boolean> talkedTo = new HashMap<SceneType, Boolean>();

  private RoomController roomController;
  private Map<String, String> suspectMap = new HashMap<>();

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
    suspectMap.put("Suspect 1", "Dominic Sterling");
    suspectMap.put("Suspect 2", "Sebastian Kensington");
    suspectMap.put("Suspect 3", "Alexandra Johnson");
    suspectMap.put("Crime Scene", "Crime Scene");
    talkedTo.put(SceneType.SUSPECT_1, false);
    talkedTo.put(SceneType.SUSPECT_2, false);
    talkedTo.put(SceneType.SUSPECT_3, false);
    setupComboBox();
    Timeline timeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                event -> {
                  if (talkedTo.get(SceneType.SUSPECT_1)
                      && talkedTo.get(SceneType.SUSPECT_2)
                      && talkedTo.get(SceneType.SUSPECT_3)
                      && (LaptopController.isEmailOpened()
                          || ShredderClueComponent.isPaperClue()
                          || AccessPadClue.isUnlocked())) {
                    guessBtn.setDisable(false);
                    informationBtn.setDisable(true);
                  }
                }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();

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
            return suspectMap.get(object.toString());
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
      // roomController.getAccessUnlock().setVisible(false);
      roomController.getShredderClueOverlay().setVisible(false);
      roomController.removeLaptopOverlay();
    }

    if (MenuController.gameTimer != null && selectedScene == SceneType.SUSPECT_1) {
      SuspectRoomController.gameHeader1
          .getTimerLabel()
          .setText(
              MenuController.gameTimer.formatTime(MenuController.gameTimer.getTimeRemaining()));
      MenuController.gameTimer.setTimerLabel2(SuspectRoomController.gameHeader1.getTimerLabel());
    } else if (MenuController.gameTimer != null && selectedScene == SceneType.SUSPECT_2) {
      SuspectRoomController.gameHeader2
          .getTimerLabel()
          .setText(
              MenuController.gameTimer.formatTime(MenuController.gameTimer.getTimeRemaining()));
      MenuController.gameTimer.setTimerLabel2(SuspectRoomController.gameHeader2.getTimerLabel());
    } else if (MenuController.gameTimer != null && selectedScene == SceneType.SUSPECT_3) {
      SuspectRoomController.gameHeader3
          .getTimerLabel()
          .setText(
              MenuController.gameTimer.formatTime(MenuController.gameTimer.getTimeRemaining()));
      MenuController.gameTimer.setTimerLabel2(SuspectRoomController.gameHeader3.getTimerLabel());
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

  // setter for hashmap
  public static void setTalkedTo(SceneType scene) {
    talkedTo.put(scene, true);
  }

  public Label getTimerLabel() {
    return timerLabel;
  }

  public void guessingStage(MouseEvent event) throws IOException {
    if (MenuController.gameTimer != null) {
      MenuController.gameTimer.getTimerLabel3().setText("00:10");
      MenuController.gameTimer.setTimeRemaining(10);
      MenuController.gameTimer.setFirstFiveMinutesFalse();
    }
    App.changeScene(SceneType.PLAYER_EXPLANATION);
  }

  public void giveInformation(MouseEvent event) throws IOException {
    SoundManager.playSound("Interact.mp3");
  }
}
