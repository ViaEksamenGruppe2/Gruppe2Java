package model;

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



  //Start of logic
  public boolean isAvailable(Date day) {
    return !(privateCalendar.isBooked(day));
  }

  public void reserveDate(Date day) {
    privateCalendar.makeReservation(day);
  }

  public boolean useableForWrittenEx() {
    int writtenSize = 30; //NOT FINAL SIZE EDIT
    return studentCapacity >= writtenSize;
  }

  public boolean equals(Object obj){
    if(!(obj instanceof Room)){
      return false;
    }
    Room other = (Room) obj;
    return other.studentCapacity == this.studentCapacity && other.hasHDMI == this.hasHDMI && other.hasVGA == this.hasVGA && other.hasProjector == this.hasProjector && other.roomName.equals(this.roomName);
  }

  @Override public String toString()
  {
    return "Room{" + "studentCapacity=" + studentCapacity + ", hasHDMI="
        + hasHDMI + ", hasVGA=" + hasVGA + ", hasProjector=" + hasProjector
        + ", roomName='" + roomName + '\'' + ", privateCalendar="
        + privateCalendar + '}';
  }

  //End of logic
}
