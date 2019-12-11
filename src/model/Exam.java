package model;

import java.io.*;
import java.util.ArrayList;

public class Exam implements Serializable
{
  // Field variables
  private String courseName, accentColour, type;
  private double duration;
  private boolean isGroupExam, isWrittenExam;
  private Room priorityRoom;
  private PrivateCalendar privateCalendar;
  private ArrayList<Person> attendees;
  // Field variables

  // Constructor for Exam object
  public Exam(String courseName, double duration, Room priorityRoom, boolean isGroupExam, boolean isWrittenExam)
  {
    this.courseName = courseName;
    this.duration = duration;
    this.priorityRoom = priorityRoom;
    this.isGroupExam = isGroupExam;
    this.isWrittenExam = isWrittenExam;
    privateCalendar = new PrivateCalendar();
    attendees = new ArrayList<>();
    this.accentColour = getAColour();
    if (isWrittenExam)
      type = "Written";
    else
      type = "Oral";
  }
  // Second constructor for Exam object
  public Exam(String courseName, double duration, boolean isGroupExam, boolean isWrittenExam)
  {
    this.courseName = courseName;
    this.duration = duration;
    this.isGroupExam = isGroupExam;
    this.isWrittenExam = isWrittenExam;
    this.priorityRoom = null;
    privateCalendar = new PrivateCalendar();
    attendees = new ArrayList<>();
    this.accentColour = getAColour();
    if (isWrittenExam)
      type = "Written";
    else
      type = "Oral";
  }

  // Start of getters for Exam object
  public String getCourseName()
  {
    return courseName;
  }

  public PrivateCalendar getPrivateCalendar()
  {
    return privateCalendar;
  }

  public Room getPriorityRoom()
  {
    return priorityRoom;
  }

  public String getAccentColour()
  {
    return accentColour;
  }

  public String getType() {
    return type;
  }

  public ArrayList<Person> getAllStudents()
  {
    ArrayList<Person> studentAmount = new ArrayList<Person>();
    for (int i = 0; i < attendees.size(); i++)
    {
      if(!attendees.get(i).isTeacher())
        studentAmount.add(attendees.get(i));
    }
    return studentAmount;
  }
  public ArrayList<Person> getTeacher()
  {
    ArrayList<Person> teacher = new ArrayList<>();
    for (int i = 0; i < attendees.size(); i++)
    {
      if (attendees.get(i).isTeacher())
        teacher.add(attendees.get(i));
    }
    return teacher;
  }

  public boolean isWrittenExam()
  {
    return isWrittenExam;
  }

  public boolean isGroupExam()
  {
    return isGroupExam;
  }

  public void makeReservationForAllStudents(Date date){
      for (int i = 0; i < getAllStudents().size(); i++) {
          getAllStudents().get(i).getPrivateCalendar().makeReservation(date);
      }
  }
  public boolean willThisCauseBack2BackExams(Date date){
      for (int i = 0; i < getAllStudents().size(); i++) {
          Date before = date.copy();
          before.stepBackOneDay();
          Date after = date.copy();
          after.stepForwardOneDay();
          if (getAllStudents().get(i).getPrivateCalendar().isBooked(date)
                  || getAllStudents().get(i).getPrivateCalendar().isBooked(before)
                  || getAllStudents().get(i).getPrivateCalendar().isBooked(after))
              return false;
      }
      return true;
  }
  public int getTotalExamDurationInDays()
  {
    double minutes = duration * getAllStudents().size();
    double minutesInADay = 60 * 7;

    if (isWrittenExam)
    {
      return 1;
    }
    else {
      return (int) Math.ceil(minutes/minutesInADay);
    }
  }

  public boolean hasTeacher()
  {
    for (int i = 0; i < attendees.size(); i++)
    {
      if(attendees.get(i).isTeacher())
      return true;
    }
    return false;
  }

  public int numberOfStudents()
  {
    int counter = 0; // int to count how many attendees is students
    for (int i = 0; i < attendees.size(); i++)
    {
      if(attendees.get(i).isTeacher()) // if attendee isn't a teacher, then increase counter by one
        counter++;
    }
    return counter; // return the counted amount of students
  }
  // End of getters for Exam object

  // Start of setters for Exam object
  public void setPriorityRoom(Room priorityRoom)
  {
    this.priorityRoom = priorityRoom;
  }

  public void addPerson(Person person)
  {
    attendees.add(person);
  }

  public static String getAColour()
  {
    String hex;
    int r, g, b, difference = 110, largest;
    do{
      r = (int)Math.floor(Math.random() * (255 - 110)) + 110;
      g = (int)Math.floor(Math.random() * (255 - 110)) + 110;
      b = (int)Math.floor(Math.random() * (255 - 110)) + 110;
      largest = r;
      if(g > largest)
        largest = g;
      if(b > largest)
        largest = b;
    }
    while((r + g + b) < (difference * 4.1) || largest < 200);
    hex = String.format("#%02x%02x%02x", r, g, b);
    return hex;
  }

  // Start of Logic
  public void removePerson(Person person){
        attendees.remove(person);
    }

public boolean isRoomOkayForExam(Room room){
    if (!isWrittenExam)
        return true;
    else if (room == null)
        return false;
    else
        return room.useableForWrittenEx();
}
public String toString()
  {
    return "courseName= " + courseName + ", accentColour=" + accentColour + ", duration=" + duration + ", isGroupExam="
        + isGroupExam + ", isWrittenExam=" + isWrittenExam + ", priorityRoom="
        + priorityRoom + ", privateCalendar=" + privateCalendar + ", attendees="
        + attendees;
  }

  public boolean equals(Object obj)
  {
    if (!(obj instanceof Exam)){
      return false;
  }
    Exam other = (Exam) obj;
    return other.courseName.equals(this.courseName) && other.duration == this.duration
        && other.privateCalendar.equals(this.privateCalendar) && other.isGroupExam == this.isGroupExam
        && other.isWrittenExam == this.isWrittenExam;
  }


  //Start of save
  private static final String FILENAME = "src/savefiles/examData.bin";

  public static void saveToBinary(ArrayList<Exam> fileList) {

    ObjectOutputStream out = null;

    try {
      File file = new File(FILENAME);
      FileOutputStream fos = new FileOutputStream(file);
      out = new ObjectOutputStream(fos);

      for (Object exam : fileList) {
        out.writeObject(exam);
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  //End of save

  //Start of load
  public static ArrayList<Exam> loadFromBinary()
  {
    File file = new File(FILENAME);
    ArrayList<Exam> exams = new ArrayList<>();
    boolean check = false;
    ObjectInputStream in = null;

    try
    {
      FileInputStream fis = new FileInputStream(file);
      in = new ObjectInputStream(fis);
      do {
        try
        {
          exams.add((Exam) in.readObject());
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
    return exams;
  }

  //End of load

  // End of logic

}


