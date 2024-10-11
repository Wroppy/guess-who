package nz.ac.auckland.se206.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.SceneType;

/** Controller for the map view that allows the user to navigate to different rooms. */
public class MapController {
  @FXML private Button closeButton;

  /**
   * Initializes the map after its root element has been completely processed and sets the image for
   * the close button.
   */
  public void initialize() {
    // Any required initialization code can be placed here
    Image image = new Image(App.class.getResource("/images/x-button.png").toExternalForm());
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(20);
    imageView.preserveRatioProperty().setValue(true);

    closeButton.setGraphic(imageView);
  }

  /** Close the map. */
  @FXML
  private void closeMap(ActionEvent e) {
    RoomController.closeMap();
    SuspectRoomController.closeMap();
  }

  private void closeTheMap() {
    RoomController.closeMap();
    SuspectRoomController.closeMap();
  }

  /**
   * Switches the scene based on the rectangle that was clicked.
   *
   * @param event the mouse event that triggered the method
   */
  public void switchScene(MouseEvent event) {
    // get the rectangle that was clicked
    Rectangle clickedRectangle = (Rectangle) event.getSource();
    String id = clickedRectangle.getId();
    System.out.println(id);
    // set the visibility of the panes based on the clicked rectangle
    if (id.equals("Dominic")) {
      if (MenuController.gameTimer != null) {
        // set the timer label for the game header
        SuspectRoomController.gameHeader1
            .getTimerLabel()
            .setText(
                // format the time remaining
                MenuController.gameTimer.formatTime(MenuController.gameTimer.getTimeRemaining()));
        MenuController.gameTimer.setTimerLabel2(SuspectRoomController.gameHeader1.getTimerLabel());
        App.changeScene(SceneType.SUSPECT_1);
        closeTheMap();
      }
      // if the rectangle id is VP
    } else if (id.equals("VP")) {
      // if the game timer is not null
      if (MenuController.gameTimer != null) {
        SuspectRoomController.gameHeader2
            .getTimerLabel()
            .setText(
                // format the time remaining
                MenuController.gameTimer.formatTime(MenuController.gameTimer.getTimeRemaining()));
        MenuController.gameTimer.setTimerLabel2(SuspectRoomController.gameHeader2.getTimerLabel());
        // change the scene to suspect 2
        App.changeScene(SceneType.SUSPECT_2);
        closeTheMap();
      }
    } else if (id.equals("Clerk")) {
      // if the game timer is not null
      if (MenuController.gameTimer != null) {
        // set the timer label for the game header
        SuspectRoomController.gameHeader3
            .getTimerLabel()
            .setText(
                // format the time remaining
                MenuController.gameTimer.formatTime(MenuController.gameTimer.getTimeRemaining()));
        MenuController.gameTimer.setTimerLabel2(SuspectRoomController.gameHeader3.getTimerLabel());
        App.changeScene(SceneType.SUSPECT_3);
        closeTheMap();
      }
      // if the rectangle id is the map
    } else {
      App.changeScene(SceneType.CRIME);
      closeTheMap();
    }
  }
}
