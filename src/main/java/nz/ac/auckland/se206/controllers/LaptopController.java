package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import nz.ac.auckland.se206.App;

/**
 * Controller for managing laptop clue interactions in the application.
 *
 * <p>This class handles the display of emails and reports related to performance and termination.
 * It manages the visibility of the email panes and tracks whether an email has been opened.
 */
public class LaptopController {
  private static boolean emailOpened = false;

  public static boolean isEmailOpened() {
    return emailOpened;
  }

  public static void restart() {
    emailOpened = false;
  }

  @FXML private Rectangle performanceEmail; // pane of the perfomance email
  @FXML private Rectangle firingEmail; // pane of the firing email
  @FXML private Rectangle fillerEmail_1;
  @FXML private Rectangle fillerEmail_2;
  @FXML private Pane fillerEmailPane1;
  @FXML private Pane fillerEmailPane2;
  @FXML private Pane performanceReport; // pane of the promotion email
  @FXML private Pane firedEmail; // pane of the email
  @FXML private Button closeButton;

  /**
   * Initializes the controller and sets the visibility of the email panes to false initially.
   *
   * @throws IOException if an error occurs during initialization.
   */
  public void initialize() throws IOException {
    // // set the visibility of the panes to false
    performanceReport.setVisible(false);
    firedEmail.setVisible(false);
    fillerEmailPane1.setVisible(false);
    fillerEmailPane2.setVisible(false);

    Image image = new Image(App.class.getResource("/images/x-button.png").toExternalForm());
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(20);
    imageView.preserveRatioProperty().setValue(true);
    closeButton.setGraphic(imageView);
  }

  /**
   * Handles the click event on email rectangles to display the corresponding report.
   *
   * @param event The mouse event that occurred when a rectangle is clicked.
   */
  public void handleRectangleClick(MouseEvent event) {
    // get the rectangle that was clicked
    Rectangle clickedRectangle = (Rectangle) event.getSource();
    emailOpened = true;

    // set the visibility of the panes based on the clicked rectangle
    if (clickedRectangle == performanceEmail) {
      performanceReport.setVisible(true);
      firedEmail.setVisible(false);
      fillerEmailPane1.setVisible(false);
      fillerEmailPane2.setVisible(false);

    } else if (clickedRectangle == firingEmail) {
      firedEmail.setVisible(true);
      performanceReport.setVisible(false);
      fillerEmailPane1.setVisible(false);
      fillerEmailPane2.setVisible(false);
    } else if (clickedRectangle == fillerEmail_1) {
      fillerEmailPane1.setVisible(true);
      performanceReport.setVisible(false);
      firedEmail.setVisible(false);
      fillerEmailPane2.setVisible(false);
    } else if (clickedRectangle == fillerEmail_2) {
      fillerEmailPane2.setVisible(true);
      performanceReport.setVisible(false);
      firedEmail.setVisible(false);
      fillerEmailPane1.setVisible(false);
    }
  }

  @FXML
  private void onCloseLaptop(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Pane mainPane = (Pane) stage.getScene().lookup("#room");
    mainPane.getChildren().remove(mainPane.lookup("#laptop"));
  }
}
