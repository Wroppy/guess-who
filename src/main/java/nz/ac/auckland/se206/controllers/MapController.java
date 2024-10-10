package nz.ac.auckland.se206.controllers;


import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.SceneType;

public class MapController {
    /**
   * Close the map.
   *
   * @param event The mouse event.
   */
  public void closeMap() {
    RoomController.closeMap();
    SuspectRoomController.closeMap();
  }
  //get rectangle id from click
  public void switchScene(MouseEvent event) {
    System.out.println("Switching scene");
    Rectangle clickedRectangle = (Rectangle) event.getSource();
    String id = clickedRectangle.getId();
    System.out.println(id);
    if(id.equals("Dominic")){
      if (MenuController.gameTimer != null){
      SuspectRoomController.gameHeader1
          .getTimerLabel()
          .setText(
              MenuController.gameTimer.formatTime(MenuController.gameTimer.getTimeRemaining()));
      MenuController.gameTimer.setTimerLabel2(SuspectRoomController.gameHeader1.getTimerLabel());
      App.changeScene(SceneType.SUSPECT_1);
      closeMap();
      }
    } else if(id.equals("VP")){
      if (MenuController.gameTimer != null){
        SuspectRoomController.gameHeader2
            .getTimerLabel()
            .setText(
                MenuController.gameTimer.formatTime(MenuController.gameTimer.getTimeRemaining()));
        MenuController.gameTimer.setTimerLabel2(SuspectRoomController.gameHeader2.getTimerLabel());
        App.changeScene(SceneType.SUSPECT_2);
        closeMap();
      }
    } else if(id.equals("Clerk")){
      if (MenuController.gameTimer != null){
        SuspectRoomController.gameHeader3
            .getTimerLabel()
            .setText(
                MenuController.gameTimer.formatTime(MenuController.gameTimer.getTimeRemaining()));
        MenuController.gameTimer.setTimerLabel2(SuspectRoomController.gameHeader3.getTimerLabel());
        App.changeScene(SceneType.SUSPECT_3);
        closeMap();
      }
    }else{
      App.changeScene(SceneType.CRIME);
      closeMap();
    }
  }
}
