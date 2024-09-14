package nz.ac.auckland.se206.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class LaptopController {
  @FXML private Rectangle perfomanceEmail; // pane of the perfomance email
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
    if(clickedRectangle == perfomanceEmail) {
      performanceReport.setVisible(true);
    } else if(clickedRectangle == firingEmail) {
      firedEmail.setVisible(true);
    }
  }
}
