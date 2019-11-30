package JavaClasses;

public class Room
{
  private int studentCapacity;
  private boolean hasHDMI, hasVGA, hasProjector;
  private String roomName;
  private PrivateCalendar privateCalendar;

  // Constructor for Room object
  public Room(int studentCapacity, boolean hasHDMI, boolean hasVGA, boolean hasProjector, String roomName)
  {
    this.studentCapacity = studentCapacity;
    this.hasHDMI = hasHDMI;
    this.hasProjector = hasProjector;
    this.hasVGA = hasVGA;
    this.roomName = roomName;
    privateCalendar = new PrivateCalendar();
  }

  // Start of getters for Room object
  // End of getters for Room object

  // Start of setters for Room object
  // End of setters for Room object
}
