package nz.ac.auckland.se206.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.App;

public class ShredderClueComponent extends Pane {
  public ShredderClueComponent() {
    try {
      FXMLLoader loader = App.loadFxmlLoader("shredder-clue");

      loader.setRoot(this);
      loader.setController(this);

      loader.load();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
