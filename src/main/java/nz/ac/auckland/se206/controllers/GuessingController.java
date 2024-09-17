package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.SceneType;

public class GuessingController {
  @FXML private TextArea explaintxt;
  @FXML private Rectangle bob;
  @FXML private Rectangle vicePresident;
  @FXML private Rectangle third;

  private String explanation;
  private boolean correctChoice;

  public void explanationScene(MouseEvent event) throws IOException {
    explanation = explaintxt.getText().trim();
    App.changeScene(SceneType.FEEDBACK);
  }

  public void choiceCriminal(MouseEvent event) {
    Rectangle clickedRectangle = (Rectangle) event.getSource();
    if (clickedRectangle == vicePresident) {
      correctChoice = true;
    } else {
      correctChoice = false;
    }
  }
}
