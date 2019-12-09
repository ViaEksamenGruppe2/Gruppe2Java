package view;

import model.*;

import java.util.ArrayList;

public class Controller implements ExamCalendarController
{
  // Start of field variables
  private ArrayList<Person> persons;
  private ArrayList<Exam> exams;
  private ArrayList<Room> rooms;
  // End of field variables

  // CONSTRUCTOR
  public Controller()
  {
    persons = new ArrayList<>();
    exams = new ArrayList<>();
    rooms = new ArrayList<>();
    Exam exam1 = new Exam("Sej", 20, true, true);
    Exam exam2 = new Exam("uiawd", 30, false, true);
    Exam exam3 = new Exam("rgegf", 24, true, false);
    exams.add(exam1);
    exams.add(exam2);
    exams.add(exam3);
  }

  // Start of all getters
  public PrivateCalendar getPrivateCalendar(Object obj)
  {
    if (obj instanceof Person)
    {
      return ((Person) obj).getPrivateCalendar();
    }
    else if (obj instanceof Exam)
    {
      return ((Exam) obj).getPrivateCalendar();
    }
    else if (obj instanceof Room)
    {
      return ((Room) obj).getPrivateCalendar();
    }
    return null;
  };
  public ArrayList<Person> getAllPersonsAssignedExam(Exam exam)
  {
    ArrayList<Person> allPersons = exam.getAllStudents();
    for (int i = 0; i < exam.getTeacher().size(); i++)
    {
      allPersons.add(exam.getTeacher().get(i));
    }
    return allPersons;
  };

  public ArrayList<Person> getPersons()
  {
    return persons;
  }
  public ArrayList<Room> getRooms()
  {
    return rooms;
  };
  public ArrayList<Exam> getExams()
  {
    return exams;
  };
  // End of all getters

  // Start of all setters
  public void addPerson(String name, String VIAID, ArrayList<String> assignedCourses, boolean isTeacher)
  {
    Person person = new Person(name, VIAID, assignedCourses, isTeacher);
    persons.add(person);
  };
  public void addExam(String courseName, double duration, Room priorityRoom, boolean isGroupExam, boolean isWrittenExam)
  {
    Exam exam = new Exam(courseName, duration, priorityRoom, isGroupExam, isWrittenExam);
    exams.add(exam);
  };
  public void addExam(String courseName, double duration, boolean isGroupExam, boolean isWrittenExam)
  {
    Exam exam = new Exam(courseName, duration, isGroupExam,isWrittenExam);
    exams.add(exam);
  };
  public void addRoom(int studentCapacity, boolean hasHDMI, boolean hasVGA, boolean hasProjector, String roomName)
  {
    Room room = new Room(studentCapacity, hasHDMI, hasVGA, hasProjector, roomName);
    rooms.add(room);
  };
  // End of all setters


  // Start of logic
  public void removePerson(Person person)
  {
    persons.remove(person);
  };
  public void removeExam(Exam exam)
  {
    exams.remove(exam);
  };
  public void removeRoom(Room room)
  {
    rooms.remove(room);
  };
  // End of logic
}
