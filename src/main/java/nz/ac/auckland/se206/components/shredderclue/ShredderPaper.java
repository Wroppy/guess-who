package nz.ac.auckland.se206.components.shredderclue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.App;

public class ShredderPaper extends ImageView {
  public ShredderPaper(String path, double width, double height) {
    super();

    Image image = new Image(App.class.getResource("/images/" + path + ".png").toExternalForm());
    this.setImage(image);

    this.setFitWidth(width);
    this.setFitHeight(height); 
  }
}
