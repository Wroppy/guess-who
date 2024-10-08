package nz.ac.auckland.se206.utils;

import javafx.event.Event;

/** An interface for handling events in a callback-style manner. */
public interface EventCallback {
  void run(Event e);
}
