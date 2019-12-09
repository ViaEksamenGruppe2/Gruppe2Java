package model;

import java.util.ArrayList;

public class test
{
  public static void main(String[] args)
  {
    Date date1 = new Date(2,1,2020);
    Exam exam1 = new Exam("SDJ", 10, true, true);
    exam1.getPrivateCalendar().makeReservation(date1);
    Date date2 = new Date(15,1,2020);
    Exam exam2 = new Exam("RWD", 20, false, true);
    ArrayList<String> assignedCourses = new ArrayList<>();
    assignedCourses.add("SDJ");
    assignedCourses.add("RWD");
    assignedCourses.add("SEP");
    Person person1 = new Person("Benj", "20192", assignedCourses, false);
    Person person3 = new Person("Mik", "20232", assignedCourses, false);
    Person teacher = new Person("Michael", "MIVI", assignedCourses, true);
    Room room1 = new Room(10,true,true,true,"FavouriteRoom");
    Person person2 = new Person("Benjamin", "20192", assignedCourses, false);
    Room room2 = new Room(10,true,true,true,"SDJROOM");
    Room room3 = new Room(30,true,true,true,"BestRoom");
    Exam exam3 = new Exam("SEP", 20,room3, false, true);
    exam1.addPerson(teacher);
    exam2.addPerson(teacher);
    exam3.addPerson(teacher);
    ArrayList<Exam> exams = new ArrayList<>();
    exams.add(exam1);
    exams.add(exam2);
    exams.add(exam3);
    ArrayList<Person> persons = new ArrayList<>();
    persons.add(teacher);
    persons.add(person1);
    persons.add(person2);
    persons.add(person3);
    ArrayList<Room> rooms = new ArrayList<>();
    rooms.add(room1);
    rooms.add(room2);
    rooms.add(room3);

    ExamCalendar examCalendar = new ExamCalendar(date1,date2,persons,rooms,exams);
    ArrayList<ArrayList<Object>> calendarschedule = examCalendar.generateExamSchedule();
    System.out.println(calendarschedule.get(0).get(0) + " " + calendarschedule.get(0).get(2));
    System.out.println(calendarschedule.get(1).get(0) + " " + calendarschedule.get(1).get(2));
    System.out.println(calendarschedule.get(2).get(0) + " " + calendarschedule.get(2).get(2));

  }
}
