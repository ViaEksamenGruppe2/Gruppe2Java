package model;

import java.util.ArrayList;

public class test
{
  public static void main(String[] args)
  {
    Date date1 = new Date(2,1,2020);
    Date date2 = new Date(5,1,2020);
    Date datebook = new Date(6,1,2020);

    Room room1 = new Room(25,true,true,true,"S.203-RW");
    Room room2 = new Room(15,true,true,true,"E.201a");
    Room room3 = new Room(30,true,true,true,"E.203b");

    Exam exam1 = new Exam("SW-SDJ-A19", 30, true, false, true);
    Exam exam2 = new Exam("SW-RWD-A19", 200, false, true, false);
    Exam exam3 = new Exam("SW-SEP-A19", 25,room3, true, false, false);


    ArrayList<String> assignedCourses = new ArrayList<>();
    assignedCourses.add("SW-SDJ-A19");
    assignedCourses.add("SW-RWD-A19");
    assignedCourses.add("SW-SEP-A19");

    Person person1 = new Person("Benjamin", "201922", assignedCourses, false);
    Person person2 = new Person("Markus", "203192", assignedCourses, false);
    Person person3 = new Person("Oliver", "210232", assignedCourses, false);
    Person teacher = new Person("Michael", "MIVI", assignedCourses, true);


    exam1.addPerson(teacher);
    exam1.addPerson(person1);
    exam1.addPerson(person2);
    exam1.addPerson(person3);
    exam2.addPerson(teacher);
    exam2.addPerson(person1);
    exam2.addPerson(person2);
    exam2.addPerson(person3);
    exam3.addPerson(teacher);
    exam3.addPerson(person1);
    exam3.addPerson(person2);
    exam3.addPerson(person3);

    person1.addExam(exam1);
    person1.addExam(exam2);
    person1.addExam(exam3);
    person2.addExam(exam1);
    person2.addExam(exam2);
    person2.addExam(exam3);
    person3.addExam(exam1);
    person3.addExam(exam2);
    person3.addExam(exam3);
    teacher.addExam(exam1);
    teacher.addExam(exam2);
    teacher.addExam(exam3);

    System.out.println(teacher.getExams());

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
    for (int i = 0; i < calendarschedule.size(); i++)
    {
        System.out.println(calendarschedule.get(i).get(0) + " : " + ((Room) calendarschedule.get(i).get(1)).getRoomName() + " : " + ((Exam) calendarschedule.get(i).get(2)).getCourseName() + " : " + ((Exam) calendarschedule.get(i).get(2)).getPrivateCalendar());
    }

    Room.saveToBinary(rooms);
    Person.saveToBinary(persons);
    Exam.saveToBinary(exams);
  }
}
