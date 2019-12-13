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

  @Override public ArrayList<Room> setAllRooms(ArrayList<Room> roomsToSet)
  {
    for (int i = 0; i < roomsToSet.size(); i++)
    {
      rooms.add(roomsToSet.get(i));
    }
    return rooms;
  }

  @Override public ArrayList<Exam> setAllExams(ArrayList<Exam> examsToSet)
  {
    for (int i = 0; i < examsToSet.size(); i++)
    {
      exams.add(examsToSet.get(i));
    }
    return exams;
  }

  @Override public ArrayList<Person> setAllPersons(ArrayList<Person> personsToSet)
  {
    for (int i = 0; i < personsToSet.size(); i++)
    {
      persons.add(personsToSet.get(i));
    }
    return persons;
  }

  public void addPerson(String name, String VIAID, ArrayList<String> assignedCourses, boolean isTeacher)
  {
    Person person = new Person(name, VIAID, assignedCourses, isTeacher);
    persons.add(person);
  };
  public void addExam(String courseName, double duration, Room priorityRoom, boolean isGroupExam, boolean isWrittenExam, boolean seventhSemester)
  {
    Exam exam = new Exam(courseName, duration, priorityRoom, isGroupExam, isWrittenExam, seventhSemester);
    exams.add(exam);
  };
  public void addExam(String courseName, double duration, boolean isGroupExam, boolean isWrittenExam, boolean seventhSemester)
  {
    Exam exam = new Exam(courseName, duration, isGroupExam,isWrittenExam, seventhSemester);
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
