package view;

import model.Exam;
import model.ExamCalendarController;
import model.Person;
import model.Room;

import java.util.ArrayList;

public class Controller implements ExamCalendarController
{
  // Start of all getters
  public Object getPrivateCalendar(Object obj)
  {

  };
  public ArrayList<Person> getAllPersons(Exam exam)
  {

  };
  public Room getRoom(Exam exam)
  {

  };
  public Exam getExam(Room room)
  {

  };
  // End of all getters

  // Start of all setters
  public Person addPerson(String name, int VIAID, ArrayList<String> assignedCourses, boolean isTeacher)
  {

  };
  public Exam addExam(String courseName, double duration, Room priorityRoom, boolean isGroupExam, boolean isWrittenExam)
  {

  };
  public Exam addExam(String courseName, double duration, boolean isGroupExam, boolean isWrittenExam)
  {

  };
  public Room addRoom(int studentCapacity, boolean hasHDMI, boolean hasVGA, boolean hasProjector, String roomName)
  {

  };
  // End of all setters


  // Start of logic
  public Person removePerson(Person person)
  {

  };
  public Exam removeExam(Exam exam)
  {

  };
  public Room removeRoom(Room room)
  {

  };
  // End of logic
}
