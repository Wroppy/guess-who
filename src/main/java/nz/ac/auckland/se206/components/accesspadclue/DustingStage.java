package nz.ac.auckland.se206.components.accesspadclue;

public enum DustingStage {
  POWDER("Dust the area"),
  BRUSH("Brush the area"),
  REVEAL("Complete");

  private final String description;

  DustingStage(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  
}
