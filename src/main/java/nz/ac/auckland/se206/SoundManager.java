package nz.ac.auckland.se206;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Class responsible for handling audio playback within the application.
 *
 * <p>This class provides methods to play sounds by loading audio files from the resources. It
 * ensures that only one sound is played at a time by stopping any currently playing sound before
 * starting a new one.
 */
public class SoundManager {
  private static MediaPlayer player;

  public static void playSound(String path) {
    // Stop the current sound if it is playing
    if (player != null) {
      player.stop();
      player.dispose();
    }

    // Play the new sound
    try {
      Media sound = new Media(App.class.getResource("/sounds/" + path).toURI().toString());
      player = new MediaPlayer(sound);
      player.play();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
