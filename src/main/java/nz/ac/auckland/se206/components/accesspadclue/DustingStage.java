package nz.ac.auckland.se206.components.accesspadclue;

/** Represents the stage of the dusting process. */
public enum DustingStage {
  POWDER("Dust"),
  BRUSH("Brush"),
  REVEAL("Done!");

  private final String description;

  /**
   * Create a new DustingStage with a description.
   *
   * @param description The description of the DustingStage
   */
  DustingStage(String description) {
    this.description = description;
  }

  /**
   * Get the description of this DustingStage.
   *
   * @return The description
   */
  public String getDescription() {
    return description;
  }
}
