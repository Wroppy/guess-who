package nz.ac.auckland.se206.controllers;

import nz.ac.auckland.se206.SceneManager.SceneType;

/** An interface for controllers that manage headers in different scenes. */
public interface HeaderableController {
  public void setupHeader(SceneType sceneType);
}
