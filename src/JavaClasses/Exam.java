package JavaClasses;

import java.util.ArrayList;

public class Exam
{
  // Field variables
  private String courseName, accentColour;
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

  public ArrayList<Person> getAllStudents()
  {
    // NEEDS TO ONLY RETURN STUDENTS OF ATTENDEES, NOT TEACHERS.. ADD LOGIC..
    return attendees;
  }

  public double getTotalExamDuration()
  {
    // THIS ISN'T DONE YET. NEEDS TO RETURN DURATION IN MINUTES. OR HOURS... ALSO NEEDS OTHER LOGIC IF IT'S A GROUP EXAM
    return duration * attendees.size();
  }

  public boolean hasTeacher()
  {
    for (int i = 0; i < attendees.size(); i++)
    {
      if(attendees.get(i).isTeacher() == true)
      return true;
    }
    return false;
  }

  public int numberOfStudents()
  {
    int counter = 0; // int to count how many attendees is students
    for (int i = 0; i < attendees.size(); i++)
    {
      if(attendees.get(i).isTeacher() == false) // if attendee isn't a teacher, then increase counter by one
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

  // End of setters for Exam object

  
}
