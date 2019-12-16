package model;

import java.io.*;
import java.util.ArrayList;

public class Person implements Serializable
{
  // Field variables
  private String name;
  private String viaID;
  private String role;
  private ArrayList<String> assignedCourses;
  private boolean isTeacher;
  private PrivateCalendar privateCalendar;
  private ArrayList<Exam> exams;
  // Field variables

  // Constructor for Person object
  public Person(String name, String viaID, ArrayList<String> assignedCourses, boolean isTeacher)
  {
    this.name = name;
    this.viaID = viaID;
    this.assignedCourses = assignedCourses;
    this.isTeacher = isTeacher;
    privateCalendar = new PrivateCalendar();
    exams = new ArrayList<>();
      if (isTeacher)
          role = "Teacher";
      else
          role = "Student";
  }

  // Start of getters for Person object
  public String getName()
  {
    return name;
  }
  public String getRole(){
      return role;
  }
  public String getViaID()
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
    if (!exams.contains(exam))
      exams.add(exam);
  }
  public void setAll(String name, String viaID, ArrayList<String> assignedCourses, boolean isTeacher)
  {

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
    return other.name.equals(this.name) && other.viaID.equals(this.viaID) && other.assignedCourses.equals(this.assignedCourses) && other.isTeacher == this.isTeacher && other.privateCalendar.equals(this.privateCalendar) && other.exams.equals(this.exams);
  }

  //May need change for UI

  @Override public String toString()
  {
    return "Person{" + "name='" + name + '\'' + ", viaID='" + viaID + '\''
        + ", role='" + role + '\'' + ", assignedCourses=" + exams
        + ", privateCalendar=" + privateCalendar;
  }

  //Start of save
  private static final String FILENAME = "src/savefiles/personData.bin";

  public static void saveToBinary(ArrayList<Person> fileList) {

    ObjectOutputStream out = null;

    try {
      File file = new File(FILENAME);
      FileOutputStream fos = new FileOutputStream(file);
      out = new ObjectOutputStream(fos);

      for (Object person : fileList) {
        out.writeObject(person);
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  //End of save

  //Start of load
  public static ArrayList<Person> loadFromBinary()
  {
    File file = new File(FILENAME);
    ArrayList<Person> persons = new ArrayList<>();
    // empty boolean to find out if the file is empty. If it is, then
    // there will be returned an empty ArrayList
    boolean check = false, empty = !file.exists() || file.length() == 0;
    if (!empty)
    {
      ObjectInputStream in = null;

      try
      {
        FileInputStream fis = new FileInputStream(file);
        in = new ObjectInputStream(fis);
        do
        {
          try
          {
            persons.add((Person) in.readObject());
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
        try
        {
          in.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    }
    return persons;
  }

  //End of load
  //End of logic
}
