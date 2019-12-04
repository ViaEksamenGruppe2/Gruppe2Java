package model;

import java.util.ArrayList;
import java.util.Objects;

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
    setAccentColour();
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

  public double getTotalExamDuration()
  {
    if(isGroupExam == true){
//Needs to be attended to! after questions to Michael.
    }

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

  public void setAccentColour() // NEEDS TO FILL METHOD BODY
  {
  }
  // End of setters for Exam object

  // Start of Logic
  public void removePerson(Person person){
        attendees.remove(person);
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
    return courseName.equals(other.courseName) && accentColour.equals(other.accentColour) && duration == other.duration
        && privateCalendar.equals(other.privateCalendar) && isGroupExam == other.isGroupExam
        && isWrittenExam == other.isWrittenExam && priorityRoom.equals(other.priorityRoom);
  }

  // End of logic
}
