package nz.ac.auckland.se206.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class LaptopController {
  @FXML private Rectangle performanceEmail; // pane of the perfomance email
  @FXML private Rectangle firingEmail; // pane of the firing email
  @FXML private Pane performanceReport; // pane of the promotion email
  @FXML private Pane firedEmail; // pane of the email 

  public void initialize() throws IOException {
    // // set the visibility of the panes to false
    performanceReport.setVisible(false);
    firedEmail.setVisible(false);
  }

  public void handleRectangleClick(MouseEvent event) {
    Rectangle clickedRectangle = (Rectangle) event.getSource();

    if(clickedRectangle == performanceEmail) {
      performanceReport.setVisible(true);
      firedEmail.setVisible(false);

    } else if(clickedRectangle == firingEmail) {
      firedEmail.setVisible(true);
      performanceReport.setVisible(false);
    }
  }
  @FXML
  private void closeLaptop(ActionEvent event) {
    System.out.println("Closing laptop");
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Pane mainPane = (Pane) stage.getScene().lookup("#room");
    mainPane.getChildren().remove(mainPane.lookup("#laptop"));
  }
}
