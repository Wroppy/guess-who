package nz.ac.auckland.se206;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Parent;

/**
 * Manages the scenes in the application. It has the functionality to add scenetype into a map and
 * get the scenetype from the map. Scenetype is enum with corresponding string representations.
 */
public class SceneManager {
  private static Map<SceneType, Parent> sceneMap = new HashMap<>();

  public static void addScene(SceneType sceneType, Parent parent) {
    sceneMap.put(sceneType, parent);
  }

  public static Parent getScene(SceneType sceneType) {
    return sceneMap.get(sceneType);
  }

  /** Enum representing the different types of scenes available. */
  public enum SceneType {
    INTRO("Introduction"),
    CRIME("Crime Scene"),
    SUSPECT_1("Suspect 1"),
    SUSPECT_2("Suspect 2"),
    SUSPECT_3("Suspect 3"),
    PLAYER_EXPLANATION("Player Explanation"),

    FEEDBACK("Player Feedback"),
    PROCESSING_SUBMISSION("Processing Submission");

    private String name;

    SceneType(String name) {
      this.name = name;
    }

    public String toString() {
      return name;
    }
  }
}
