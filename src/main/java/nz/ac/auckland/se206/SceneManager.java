package nz.ac.auckland.se206;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Parent;

public class SceneManager {
  public enum SceneType {
    INTRO,
    CRIME,
    SUSPECT_1,
    SUSPECT_2,
    SUSPECT_3,
    PLAYER_EXPLANATION,
    FEEDBACK
  }

  private static Map<SceneType, Parent> sceneMap = new HashMap<>();

  public static void addScene(SceneType sceneType, Parent parent) {
    sceneMap.put(sceneType, parent);
  }

  public static Parent getScene(SceneType sceneType) {
    return sceneMap.get(sceneType);
  }
}
