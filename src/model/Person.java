package model;

import java.util.ArrayList;

public class Person
{
  // Field variables
  private String name;
  private int viaID;
  private ArrayList<String> assignedCourses;
  private boolean isTeacher;
  private PrivateCalendar privateCalendar;
  private ArrayList<Exam> exams;
  // Field variables

  // Constructor for Person object
  public Person(String name, int viaID, ArrayList<String> assignedCourses, boolean isTeacher)
  {
    this.name = name;
    this.viaID = viaID;
    this.assignedCourses = assignedCourses;
    this.isTeacher = isTeacher;
    privateCalendar = new PrivateCalendar();
    exams = new ArrayList<>();
  }



  // Start of getters for Person object
  public String getName()
  {
    return name;
  }

  public int getViaID()
  {
    return viaID;
  }

  public ArrayList<String> getAssignedCourses()
  {
    return assignedCourses;
  }

  public PrivateCalendar getPrivateCalendar()
  {
    return privateCalendar;
  }

  public ArrayList<Exam> getExams()
  {
    return exams;
  }

  public boolean isTeacher()
  {
    return isTeacher;
  }
  // End of getters for Person object



  // Start of setters for Person object
  public void addExam(Exam exam)
  {
    exams.add(exam);
  }
  // End of setters for Person object



  //Start of logic
  public boolean isAssignedCourse(String courseName) {
    for (String assignedCourse : assignedCourses) {
      if (assignedCourse.equals(courseName)) {
        return true;
      }
    }
    return false;
  }

  public boolean isAvailable(Date day) {
    return !(privateCalendar.isBooked(day));
  }

  public void reserveDate(Date day) {
    privateCalendar.makeReservation(day);
  }

  public void reservePersonalDate(Date day) {
    if(isTeacher) {
      day.changePersonalDate(true);
      privateCalendar.makeReservation(day);
    }
  }

  public Date getNextAvailableDate(Date date) {
    return privateCalendar.getNextAvailableDate(date);
  }

  public boolean equals(Object obj){
    if(!(obj instanceof Person)){
      return false;
    }
    Person other = (Person) obj;
    return other.name.equals(this.name) && other.viaID == this.viaID && other.assignedCourses.equals(this.assignedCourses) && other.isTeacher == this.isTeacher && other.privateCalendar.equals(this.privateCalendar) && other.exams.equals(this.exams);
  }

  //May need change for UI
  @Override public String toString() {
    return "Person{" + "name='" + name + '\'' + ", viaID=" + viaID
        + ", assignedCourses=" + assignedCourses + ", isTeacher=" + isTeacher
        + ", privateCalendar=" + privateCalendar + ", exams=" + exams + '}';
  }

  //End of logic
}
