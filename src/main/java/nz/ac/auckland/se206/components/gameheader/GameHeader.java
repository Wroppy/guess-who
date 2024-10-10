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
import nz.ac.auckland.se206.controllers.GuessingController;
import nz.ac.auckland.se206.controllers.LaptopController;
import nz.ac.auckland.se206.controllers.MenuController;
import nz.ac.auckland.se206.controllers.RoomController;
import nz.ac.auckland.se206.controllers.SuspectRoomController;

/**
 * GameHeader component that displays the room label, room combo box, guess button, and information
 * button.
 */
public class GameHeader extends Pane {
  private static HashMap<SceneType, Boolean> talkedTo = new HashMap<SceneType, Boolean>();

  /**
   * Set the talked to status of the scene
   *
   * @param scene The scene to set the status of
   */
  public static void setTalkedTo(SceneType scene) {
    talkedTo.put(scene, true);
  }

  @FXML private Label roomLabel;
  @FXML private ComboBox<SceneType> roomComboBox;
  @FXML private Button guessBtn;
  @FXML private Button informationBtn;
  @FXML private Label timerLabel;

  private SceneType currentScene;

  private RoomController roomController;
  private Map<String, String> suspectMap = new HashMap<>();

  /**
   * Constructor for the GameHeader component that sets the current scene type
   *
   * @param sceneType The current scene type
   */
  public GameHeader(SceneType sceneType) {
    this(sceneType, null);
  }

  /**
   * Constructor for the GameHeader component that sets the current scene type and room controller
   *
   * @param sceneType The current scene type
   * @param roomController The room controller
   */
  public GameHeader(SceneType sceneType, RoomController roomController) {
    super();
    this.roomController = roomController;
    currentScene = sceneType;

    // Load the FXML file
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

  /**
   * Initialize the GameHeader component by setting up the combo box and adding the suspects to the
   * map.
   */
  public void initialize() {
    // Add the suspects to the hashmap
    suspectMap.put("Suspect 1", "Dominic Sterling");
    suspectMap.put("Suspect 2", "Sebastian Kensington");
    suspectMap.put("Suspect 3", "Alexandra Johnson");
    suspectMap.put("Crime Scene", "Crime Scene");
    talkedTo.put(SceneType.SUSPECT_1, false);
    talkedTo.put(SceneType.SUSPECT_2, false);
    talkedTo.put(SceneType.SUSPECT_3, false);

    // Set up the combo box
    setupComboBox();
    Timeline timeline =
        new Timeline(
            new KeyFrame(
                // Check if all suspects have been talked to and if the player has found a clue
                Duration.seconds(1),
                event -> {
                  if (talkedTo.get(SceneType.SUSPECT_1)
                      && talkedTo.get(SceneType.SUSPECT_2)
                      && talkedTo.get(SceneType.SUSPECT_3)
                      && (LaptopController.isEmailOpened()
                          || ShredderClueComponent.isPaperClue()
                          || AccessPadClue.isUnlocked())) {
                    guessBtn.setDisable(false);
                    informationBtn.setVisible(false);
                  }
                }));

    // Set the cycle count to indefinite
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();

    colourHeader();
  }

  /** Set the colour of the header based on the current scene. */
  private void colourHeader() {
    // Set the background colour of the header based on the current scene
    switch (currentScene) {
      case CRIME:
        // Set the background colour of the header based on the current scene
        this.setStyle("-fx-background-color: linear-gradient(to right, #cee3ec, #7c9fb0);");
        break;
      case SUSPECT_1:
        // Set the background colour of the header based on the current scene
        this.setStyle("-fx-background-color: linear-gradient(to right, #cdc2a4, #313130);");
        break;
      case SUSPECT_2:
        // Set the background colour of the header based on the current scene
        this.setStyle("-fx-background-color: linear-gradient(to right, #ead8c1, #483223);");
        break;
      case SUSPECT_3:
        // Set the background colour of the header based on the current scene
        this.setStyle("-fx-background-color: linear-gradient(to right, #dab576, #503218);");
        break;
      default:
        break;
    }
  }

  /**
   * Set up the combo box by setting the button cell, converter, adding the items, and setting the
   * action for when the combo box is changed.
   */
  private void setupComboBox() {
    // Set the button cell to display the default text
    roomComboBox.setButtonCell(
        new ListCell<SceneType>() {
          @Override
          protected void updateItem(SceneType item, boolean empty) {
            // Call the super method
            super.updateItem(item, empty);
            if (empty || item == null) {
              setText("Switch to room...");
            }
          }
        });

    // Set the converter to display the suspect names
    roomComboBox.setConverter(
        new StringConverter<SceneType>() {
          // Convert the object to a string
          @Override
          public String toString(SceneType object) {
            return suspectMap.get(object.toString());
          }

          // Not used
          @Override
          public SceneType fromString(String string) {
            return null;
          }
        });

    addComboBoxItems();

    // Set the action for when the combo box is changed
    // roomComboBox.setOnAction(e -> onRoomChange(e));
  }

  /**
   * Change the scene based on the selected scene in the combo box
   *
   * @param e The action event
   */
  // private void onRoomChange(ActionEvent e) {
  //   SceneType selectedScene = roomComboBox.getSelectionModel().getSelectedItem();

  //   if (selectedScene == null) {
  //     return;
  //   }

  //   // Deselect the item and go back to default
  //   Platform.runLater(() -> clearComboBoxSelection());

  //   if (roomController != null) {
  //     roomController.getAccessPad().setVisible(false);
  //     roomController.getShredderClueOverlay().setVisible(false);
  //     roomController.removeLaptopOverlay();
  //   }

  //   // Set the timer label based on the selected scene
  //   if (MenuController.gameTimer != null && selectedScene == SceneType.SUSPECT_1) {
  //     SuspectRoomController.gameHeader1
  //         .getTimerLabel()
  //         .setText(
  //             MenuController.gameTimer.formatTime(MenuController.gameTimer.getTimeRemaining()));
  //     MenuController.gameTimer.setTimerLabel2(SuspectRoomController.gameHeader1.getTimerLabel());
  //   } else if (MenuController.gameTimer != null && selectedScene == SceneType.SUSPECT_2) {
  //     // Set the timer label based on the selected scene
  //     SuspectRoomController.gameHeader2
  //         .getTimerLabel()
  //         .setText(
  //             MenuController.gameTimer.formatTime(MenuController.gameTimer.getTimeRemaining()));
  //     MenuController.gameTimer.setTimerLabel2(SuspectRoomController.gameHeader2.getTimerLabel());
  //   } else if (MenuController.gameTimer != null && selectedScene == SceneType.SUSPECT_3) {
  //     // Set the timer label based on the selected scene
  //     SuspectRoomController.gameHeader3
  //         .getTimerLabel()
  //         .setText(
  //             MenuController.gameTimer.formatTime(MenuController.gameTimer.getTimeRemaining()));
  //     MenuController.gameTimer.setTimerLabel2(SuspectRoomController.gameHeader3.getTimerLabel());
  //   }

  //   changeScene(selectedScene);
  // }

  /**
   * Clear the selection of the combo box by setting the selection to null.
   *
   * @param e The mouse event
   */
  private void clearComboBoxSelection() {
    roomComboBox.getSelectionModel().clearSelection();
  }

  /**
   * Change the label based on the scene type.
   *
   * @param sceneType The scene type to change the label to.
   */
  private void changeLabel(SceneType sceneType) {
    if(sceneType == SceneType.CRIME){
      roomLabel.setText("Crime Scene");
    }else if (sceneType == SceneType.SUSPECT_1) {
      roomLabel.setText("Dominic Sterling");
    }else if (sceneType == SceneType.SUSPECT_2) {
      roomLabel.setText("Sebastian Kensington");
    }else if (sceneType == SceneType.SUSPECT_3) {
      roomLabel.setText("Alexandra Johnson");
    }
  }

  /**
   * Change the scene based on the scene type.
   *
   * @param sceneType The scene type to change the scene to.
   */
  private void changeScene(SceneType sceneType) {
    App.changeScene(sceneType);
  }

  /** Add the items to the combo box. */
  private void addComboBoxItems() {
    SceneType[] sceneTypes = {
      SceneType.CRIME, SceneType.SUSPECT_1, SceneType.SUSPECT_2, SceneType.SUSPECT_3
    };

    for (SceneType sceneType : sceneTypes) {
      roomComboBox.getItems().add(sceneType);
    }
  }

  /**
   * Set the scene type of the current scene.
   *
   * @param sceneType The scene type to set the current scene to.
   */
  public void setScene(SceneType sceneType) {
    currentScene = sceneType;
    changeLabel(sceneType);
  }

  /**
   * Get the timer label.
   *
   * @return The timer label.
   */
  public Label getTimerLabel() {
    return timerLabel;
  }

  /**
   * Get the room combo box.
   *
   * @param event The mouse event.
   * @throws IOException If the FXML file cannot be loaded.
   */
  public void guessingStage(MouseEvent event) throws IOException {
    if (MenuController.gameTimer != null) {
      MenuController.gameTimer.getTimerLabel3().setText("01:00");
      MenuController.gameTimer.setTimeRemaining(60);
      MenuController.gameTimer.setFirstFiveMinutesFalse();
    }
    App.changeScene(SceneType.PLAYER_EXPLANATION);
    GuessingController.defocusTextBox();
  }

  /** Restart the talked to status of the suspects. */
  public void restartTalkedTo() {
    talkedTo.put(SceneType.SUSPECT_1, false);
    talkedTo.put(SceneType.SUSPECT_2, false);
    talkedTo.put(SceneType.SUSPECT_3, false);

    informationBtn.setVisible(true);
    guessBtn.setDisable(true);
  }

  /**
   * Give information to the player.
   *
   * @param event The mouse event.
   * @throws IOException If the FXML file cannot be loaded.
   */
  public void giveInformation(MouseEvent event) throws IOException {
    SoundManager.playSound("Interact.mp3");
  }

  //getter for hashmap
  public static HashMap<SceneType, Boolean> getTalkedTo() {
    return talkedTo;
  }

  /**
   * Handle the map icon click event.
   *
   * @param event The mouse event.
   * @throws IOException If the FXML file cannot be loaded.
   */
  public void handleMapClick(MouseEvent event) throws IOException {
    if(currentScene == SceneType.CRIME){
      RoomController.openMap(event);
    }else{
      SuspectRoomController.openMap(event);
    }
  }
}
