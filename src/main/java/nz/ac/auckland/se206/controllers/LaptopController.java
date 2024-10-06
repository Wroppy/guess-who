package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

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

  public void initialize() throws IOException {
    // // set the visibility of the panes to false
    performanceReport.setVisible(false);
    firedEmail.setVisible(false);
    fillerEmailPane1.setVisible(false);
    fillerEmailPane2.setVisible(false);
  }

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
