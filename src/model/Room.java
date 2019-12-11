package model;

import java.io.*;
import java.util.ArrayList;

public class Room implements Serializable
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

  public void setProjector(boolean state){
    hasProjector = state;
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
    return other.studentCapacity == this.studentCapacity && other.hasHDMI == this.hasHDMI
       && other.hasVGA == this.hasVGA && other.hasProjector == this.hasProjector
        && other.roomName.equals(this.roomName) && other.privateCalendar.equals(this.privateCalendar);
  }

  @Override public String toString()
  {
    return "Room{" + "studentCapacity=" + studentCapacity + ", hasHDMI="
        + hasHDMI + ", hasVGA=" + hasVGA + ", hasProjector=" + hasProjector
        + ", roomName='" + roomName + '\'' + ", privateCalendar="
        + privateCalendar + '}';
  }


  //Start of save
  private static final String FILENAME = "src/savefiles/roomData.bin";

  public static void saveToBinary(ArrayList<Room> fileList) {

    ObjectOutputStream out = null;

    try {
      File file = new File(FILENAME);
      FileOutputStream fos = new FileOutputStream(file);
      out = new ObjectOutputStream(fos);

      for (Object room : fileList) {
        out.writeObject(room);
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  //End of save

  //Start of load
  public static ArrayList<Room> loadFromBinary()
  {
    File file = new File(FILENAME);
    ArrayList<Room> rooms = new ArrayList<>();
    boolean check = false;
    ObjectInputStream in = null;

    try
    {
      FileInputStream fis = new FileInputStream(file);
      in = new ObjectInputStream(fis);
      do {
        try
        {
          rooms.add((Room) in.readObject());
        }
        catch (Exception e)
        {
          check = true;
        }
      }
      while (!check);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    finally
    {
      try {
        in.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    return rooms;
  }

  //End of load

  //End of logic
}
