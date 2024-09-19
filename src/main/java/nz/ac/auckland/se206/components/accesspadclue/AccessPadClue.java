package nz.ac.auckland.se206.components.accesspadclue;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.controllers.RoomController;

public class AccessPadClue extends Pane {
  private ArrayList<Integer> passCode = new ArrayList<Integer>();

  @FXML private Label errorMessage;
  @FXML private TextField passCodeDisplay;
  @FXML private Pane accessPad;
  @FXML private Pane accessUnlock;

  private boolean unlocked = false;

  public AccessPadClue() {
    super();

    try {
      FXMLLoader loader = App.loadFxmlLoader("access-pad-clue");
      loader.setRoot(this);
      loader.setController(this);
      loader.load();

    } catch (IOException e) {
      e.printStackTrace();
    }

    this.setVisible(false);
  }

  @FXML
  private void clearPasscode() {
    passCode.clear();
    passCodeDisplay.setText("");
    errorMessage.setText("");
  }

  public void getPasscode(ActionEvent event) {
    if (passCode.size() >= 3) {
      errorMessage.setText("Enter 3 digits only.");
      return;
    }
    errorMessage.setText("");
    Button clickedButton = (Button) event.getSource();

    String buttonText = clickedButton.getText().toLowerCase();

    passCodeDisplay.appendText(buttonText);
    passCode.add(Integer.parseInt(buttonText));
  }

  public void checkPasscode() {
    if (passCode.size() == 3) {
      if (passCode.get(0) == 7 && passCode.get(1) == 2 && passCode.get(2) == 6) {
        accessUnlock.setVisible(false);
        unlocked = true;
      } else {
        errorMessage.setText("Incorrect. Try again.");
        passCodeDisplay.setText("");
        passCode.clear();
      }
    } else {
      errorMessage.setText("Enter 3 digits.");
    }
  }

  @FXML
  private void handleAcessPadClick() {
    accessPad.setVisible(true);
    if (!unlocked) {
    accessUnlock.setVisible(true);
    errorMessage.setText("");
    passCodeDisplay.setText("");
    passCode.clear();
    }
  }

  @FXML
  private void onCloseButtonClick() {
    this.setVisible(false);
  }
}
