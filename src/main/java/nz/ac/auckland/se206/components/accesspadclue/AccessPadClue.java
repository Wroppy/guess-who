package nz.ac.auckland.se206.components.accesspadclue;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.App;

public class AccessPadClue extends Pane {
  private ArrayList<Integer> passCode = new ArrayList<Integer>();

  @FXML private Label errorMessage;
  @FXML private Label passCodeDisplay;
  @FXML private Pane accessPad;
  @FXML private Pane accessUnlock;

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
  //   if (passcode.size() >= 3) {
  //     errorMessage.setText("Enter 3 digits only.");
  //     return;
  //   } else {
  //     errorMessage.setText("");
  //   }
  //   Button clickedButton = (Button) event.getSource();
  //   if (clickedButton == One) {
  //     passcode.add(1);
  //   } else if (clickedButton == Two) {
  //     passcode.add(2);
  //   } else if (clickedButton == Three) {
  //     passcode.add(3);
  //   } else if (clickedButton == Four) {
  //     passcode.add(4);
  //   } else if (clickedButton == Five) {
  //     passcode.add(5);
  //   } else if (clickedButton == Six) {
  //     passcode.add(6);
  //   } else if (clickedButton == Seven) {
  //     passcode.add(7);
  //   } else if (clickedButton == Eight) {
  //     passcode.add(8);
  //   } else if (clickedButton == Nine) {
  //     passcode.add(9);
  //   }
  //   passcodeDisplay.setText("");
  //   for (int i = 0; i < passcode.size(); i++) {
  //     passcodeDisplay.setText(passcodeDisplay.getText() + passcode.get(i));
  //   }
  }

  public void checkPasscode() {
    if (passCode.size() == 3) {
      if (passCode.get(0) == 7 && passCode.get(1) == 2 && passCode.get(2) == 6) {
        accessUnlock.setVisible(false);
        // unlocked = true;
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
    // if (!unlocked) {
      // accessUnlock.setVisible(true);
      // errorMessage.setText("");
      // passCodeDisplay.setText("");
      // passCode.clear();
    // }
  }

  @FXML
  private void onCloseButtonClick() {
    this.setVisible(false);
  }

}
