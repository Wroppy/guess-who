package nz.ac.auckland.se206.components.accesspadclue;

public enum DustingStage {
  POWDER("Dust"),
  BRUSH("Brush"),
  REVEAL("Done!");

  private final String description;

  DustingStage(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  
}
