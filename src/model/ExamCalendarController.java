package model;

import java.util.ArrayList;

public interface ExamCalendarController
{
  // Start of all getters
  public Object getPrivateCalendar(Object obj);
  public ArrayList<Person> getAllPersonsAssignedExam(Exam exam);
  public ArrayList<Person> getPersons();
  public ArrayList<Room> getRooms();
  public ArrayList<Exam> getExams();
  // End of all getters

  // Start of all setters
  public ArrayList<Room> setAllRooms(ArrayList<Room> roomsToSet);
  public ArrayList<Exam> setAllExams(ArrayList<Exam> examsToSet);
  public void addPerson(String name, String VIAID, ArrayList<String> assignedCourses, boolean isTeacher);
  public void addExam(String courseName, double duration, Room priorityRoom, boolean isGroupExam, boolean isWrittenExam);
  public void addExam(String courseName, double duration, boolean isGroupExam, boolean isWrittenExam);
  public void addRoom(int studentCapacity, boolean hasHDMI, boolean hasVGA, boolean hasProjector, String roomName);
  // End of all setters


  // Start of logic
  public void removePerson(Person person);
  public void removeExam(Exam exam);
  public void removeRoom(Room room);
  // End of logic
}
