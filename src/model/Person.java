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
}
