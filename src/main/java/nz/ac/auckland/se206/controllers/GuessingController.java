package nz.ac.auckland.se206.controllers;

import java.io.IOException;

import org.apache.http.impl.client.SystemDefaultCredentialsProvider;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GuessingController {
  @FXML private TextArea explaintxt;
  @FXML private Rectangle bob;
  @FXML private Rectangle vicePresident;
  @FXML private Rectangle third;

  private String explanation;
  private boolean correctChoice;

  public void explanationScene(MouseEvent event) throws IOException {
      explanation = explaintxt.getText().trim();
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/guessing_screen.fxml"));
      Parent labelsRoot = loader.load();

      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      Scene labelsScene = new Scene(labelsRoot);
      stage.setScene(labelsScene);
      stage.show();
  }

  public void choiceCriminal(MouseEvent event){
    Rectangle clickedRectangle = (Rectangle) event.getSource();
    if(clickedRectangle == vicePresident){
      correctChoice = true;
    } else {
      correctChoice = false;
    }
    
  }
}
