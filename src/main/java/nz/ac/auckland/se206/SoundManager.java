package nz.ac.auckland.se206;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {
  private static MediaPlayer player;

  public static void playSound(String path) {
    if (player != null) {
      player.stop();
      player.dispose();
    }
    try {
      Media sound = new Media(App.class.getResource("/sounds/" + path).toURI().toString());
      player = new MediaPlayer(sound);
      player.play();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
