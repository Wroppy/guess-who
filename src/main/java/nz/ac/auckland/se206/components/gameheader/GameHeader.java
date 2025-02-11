package nz.ac.auckland.se206.components.gameheader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
   * Set the talked to status of the scene.
   *
   * @param scene The scene to set the status of
   */
  public static void setTalkedTo(SceneType scene) {
    talkedTo.put(scene, true);
  }

  // getter for hashmap
  public static HashMap<SceneType, Boolean> getTalkedTo() {
    return talkedTo;
  }

  @FXML private Label roomLabel;
  @FXML private ComboBox<SceneType> roomComboBox;
  @FXML private Button guessBtn;
  @FXML private Button informationBtn;
  @FXML private Label timerLabel;

  private SceneType currentScene;

  private Map<String, String> suspectMap = new HashMap<>();

  /**
   * Constructor for the GameHeader component that sets the current scene type and room controller.
   *
   * @param sceneType The current scene type
   * @param roomController The room controller
   */
  public GameHeader(SceneType sceneType, RoomController roomController) {
    super();
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
   * Constructor for the GameHeader component that sets the current scene type.
   *
   * @param sceneType The current scene type
   */
  public GameHeader(SceneType sceneType) {
    this(sceneType, null);
  }

  /**
   * Initialize the GameHeader component by setting up the combo box and adding the suspects to the
   * map.
   */
  public void initialize() {
    Image image = new Image(App.class.getResource("/images/GuessColour.png").toExternalForm());
    ImageView imageview = new ImageView(image);
    // set the properties for image.
    imageview.setFitHeight(50);
    imageview.preserveRatioProperty().setValue(true);
    guessBtn.setGraphic(imageview);
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

    String styles = App.getCssUrl("guessingButton");

    this.getStylesheets().add(styles);
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
  }

  /**
   * Change the label based on the scene type.
   *
   * @param sceneType The scene type to change the label to.
   */
  private void changeLabel(SceneType sceneType) {
    // Check the provided scene type and update the room label accordingly
    if (sceneType == SceneType.CRIME) {
      roomLabel.setText("Crime Scene"); // Set label for crime scene
    } else if (sceneType == SceneType.SUSPECT_1) { // Set label for suspect 1
      roomLabel.setText("Dominic Sterling");
    } else if (sceneType == SceneType.SUSPECT_2) {
      roomLabel.setText("Sebastian Kensington"); // Set label for suspect 2
    } else if (sceneType == SceneType.SUSPECT_3) {
      roomLabel.setText("Alexandra Johnson"); // Set label for suspect 3
    }
  }

  /** Add the items to the combo box. */
  private void addComboBoxItems() {
    // Add the scene types to the combo box
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
   * Returns the timer label for the GameHeader.
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

  /**
   * Handle the map icon click event.
   *
   * @param event The mouse event.
   * @throws IOException If the FXML file cannot be loaded.
   */
  public void handleMapClick(MouseEvent event) throws IOException {
    if (currentScene == SceneType.CRIME) {
      RoomController.openMap(event);
    } else {
      SuspectRoomController.openMap(event);
    }
  }
}
