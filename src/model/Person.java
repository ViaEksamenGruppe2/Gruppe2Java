package model;

import java.io.*;
import java.util.ArrayList;

public class Person implements Serializable
{
  // Field variables
  private String name;
  private String viaID;
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
  }



  // Start of getters for Person object
  public String getName()
  {
    return name;
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
    return other.name.equals(this.name) && other.viaID.equals(this.viaID) && other.assignedCourses.equals(this.assignedCourses) && other.isTeacher == this.isTeacher && other.privateCalendar.equals(this.privateCalendar) && other.exams.equals(this.exams);
  }

  //May need change for UI
  @Override public String toString() {
    return "Person{" + "name='" + name + '\'' + ", viaID=" + viaID
        + ", assignedCourses=" + assignedCourses + ", isTeacher=" + isTeacher
        + ", privateCalendar=" + privateCalendar + ", exams=" + exams + '}';
  }

  //Start of save
  private static final String filename = "personData.bin";

  public static void savePersonsToBinary(ArrayList<Person> persons) {
    String filename = "personData.bin";

    ObjectOutputStream out = null;

    try {
      File file = new File(filename);
      FileOutputStream fos = new FileOutputStream(file);
      out = new ObjectOutputStream(fos);

      for (Object person : persons) {
        out.writeObject(person);
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  //End of save

  //Start of load
  public static void loadFromBinary(ArrayList<Person> persons) {
    String filename = "personData.bin";

    ObjectInputStream in = null;

    try {
      File file = new File(filename);
      FileInputStream fis = new FileInputStream(file);
      in = new ObjectInputStream(fis);

      for (Object person : persons) {
        Person personsLoad = (Person)in.readObject();
        System.out.println(personsLoad);
      }
    }
    catch (IOException | ClassNotFoundException e)
    {
      e.printStackTrace();
    }

  }

  //End of load
  //End of logic
}
