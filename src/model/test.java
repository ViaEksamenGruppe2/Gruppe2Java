package model;

import java.util.ArrayList;

public class test
{
  public static void main(String[] args)
  {
    Date date1 = new Date(2,1,2020);
    Date date2 = new Date(15,1,2020);
    Date datebook = new Date(6,1,2020);

    Room room1 = new Room(10,true,true,true,"FavouriteRoom");
    Room room2 = new Room(30,true,true,true,"SDJROOM");
    Room room3 = new Room(10,true,true,true,"BestRoom");

    Exam exam1 = new Exam("SDJ", 20, true, false);
    Exam exam2 = new Exam("RWD", 800, false, false);
    Exam exam3 = new Exam("SEP", 900,room3, false, false);

    exam1.getPrivateCalendar().makeReservation(date1);
    exam3.getPrivateCalendar().makeReservation(datebook);

    ArrayList<String> assignedCourses = new ArrayList<>();
    assignedCourses.add("SDJ");
    assignedCourses.add("RWD");
    assignedCourses.add("SEP");

    Person person1 = new Person("Benj", "20192", assignedCourses, false);
    Person person2 = new Person("Benjamin", "20192", assignedCourses, false);
    Person person3 = new Person("Mik", "20232", assignedCourses, false);
    Person teacher = new Person("Michael", "MIVI", assignedCourses, true);


    exam1.addPerson(teacher);
    exam1.addPerson(person2);
    exam2.addPerson(teacher);
    exam2.addPerson(person2);
    exam3.addPerson(teacher);
    exam3.addPerson(person2);

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
    Exam examlist1 = (Exam) calendarschedule.get(0).get(2);
    Exam examlist2 = (Exam) calendarschedule.get(1).get(2);
    Exam examlist3 = (Exam) calendarschedule.get(2).get(2);
    System.out.println(calendarschedule.get(0).get(0) + " : " + examlist1.getPrivateCalendar());
    System.out.println(calendarschedule.get(1).get(0) + " : " + examlist2.getPrivateCalendar());
    System.out.println(calendarschedule.get(2).get(0) + " : " + examlist3.getPrivateCalendar());

  }
}
