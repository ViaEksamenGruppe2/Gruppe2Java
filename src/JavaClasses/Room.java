package JavaClasses;

public class Room
{
  // Field variables
  private int studentCapacity;
  private boolean hasHDMI, hasVGA, hasProjector;
  private String roomName;
  private PrivateCalendar privateCalendar;
  // Field variables

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
  public int getCapacity()
  {
    return studentCapacity;
  }

  public boolean hasHDMI()
  {
    return hasHDMI;
  }

  public boolean hasVGA()
  {
    return hasVGA;
  }

  public boolean hasProjector()
  {
    return hasProjector;
  }

  public String getRoomName()
  {
    return roomName;
  }

  public PrivateCalendar getPrivateCalendar()
  {
    return privateCalendar;
  }
  // End of getters for Room object

  // Start of setters for Room object
  public void setHDMI(boolean state)
  {
    hasHDMI = state;
  }

  public void setVGA(boolean state)
  {
    hasVGA = state;
  }
  // End of setters for Room object
}
