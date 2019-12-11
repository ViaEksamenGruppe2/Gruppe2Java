package model;

import java.util.ArrayList;

public class test
{
  public static void main(String[] args)
  {
    Date date1 = new Date(2,1,2020);
    Date date2 = new Date(14,1,2020);
    Date datebook = new Date(6,1,2020);

    Room room1 = new Room(10,true,true,true,"FavouriteRoom");
    Room room2 = new Room(30,true,true,true,"SDJROOM");
    Room room3 = new Room(10,true,true,true,"BestRoom");

    Exam exam1 = new Exam("SDJ", 20, true, false);
    Exam exam2 = new Exam("RWD", 800, false, false);
    Exam exam3 = new Exam("SEP", 900,room3, false, false);


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
    Room.saveToBinary(rooms);
    Exam.saveToBinary(exams);
    Person.saveToBinary(persons);

    ExamCalendar examCalendar = new ExamCalendar(date1,date2,persons,rooms,exams);

    ArrayList<ArrayList<Object>> calendarschedule = examCalendar.generateExamSchedule();
    for (int i = 0; i < calendarschedule.size(); i++)
    {
        System.out.println(calendarschedule.get(i).get(0) + " : " + ((Room) calendarschedule.get(i).get(1)).getRoomName() + " : " + ((Exam) calendarschedule.get(i).get(2)).getCourseName() + " : " + ((Exam) calendarschedule.get(i).get(2)).getPrivateCalendar());
    }
  }
}
