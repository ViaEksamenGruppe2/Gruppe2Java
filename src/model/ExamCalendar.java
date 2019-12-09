package model;

import java.util.ArrayList;

public class ExamCalendar
{
  private Date firstExamDate;
  private Date lastExamDate;
  private ArrayList<Person> persons;
  private ArrayList<Room> rooms;
  private ArrayList<Exam> exams;

  public ExamCalendar(Date firstExamDate, Date lastExamDate, ArrayList<Person> persons, ArrayList<Room> rooms, ArrayList<Exam> exams)
  {
    this.firstExamDate = firstExamDate;
    this.lastExamDate = lastExamDate;
    this.persons = persons;
    this.rooms = rooms;
    this.exams = exams;
  }

  public ArrayList<ArrayList<Object>> generateExamSchedule()
  {
    // STRUCTURE FOR 2D ARRAYLIST:
    // DATE
    // ROOM
    // EXAM
    // TEACHERS
    ArrayList<ArrayList<Object>> examPlanList = new ArrayList<>();
    Date plannerDate;
    for (int i = 0; i < exams.size(); i++)
    {
      plannerDate = firstExamDate.copy();
      while (exams.get(i).getPrivateCalendar().isBooked(plannerDate) || exams.get(i).getTeacher().get(0).getPrivateCalendar().isBooked(plannerDate))
      {
        plannerDate.stepForwardOneDay();
        plannerDate = plannerDate.copy();
      }
        if (exams.get(i).getPriorityRoom() != null && !exams.get(i).getPriorityRoom().getPrivateCalendar().isBooked(plannerDate) && !exams.get(i).getTeacher().get(0).getPrivateCalendar().isBooked(plannerDate))
        {
          exams.get(i).getPrivateCalendar().makeReservation(plannerDate);
          exams.get(i).getPriorityRoom().getPrivateCalendar().makeReservation(plannerDate);
          exams.get(i).getTeacher().get(0).getPrivateCalendar().makeReservation(plannerDate);
          // NEEDS AN AUTO UPDATE ON CALENDAR FOR TEACHER AND STUDENT. AN UPDATE ALL CALENDAR FUNCTION BASED ON DATE
          ArrayList<Object> addList = new ArrayList<>();
          addList.add(plannerDate);
          addList.add(exams.get(i).getPriorityRoom());
          addList.add(exams.get(i));
          addList.add(exams.get(i).getTeacher());
          examPlanList.add(addList);
        }
        else if (!exams.get(i).getPrivateCalendar().isBooked(plannerDate) && !exams.get(i).getTeacher().get(0).getPrivateCalendar().isBooked(plannerDate))
        {
          for (int j = 0; j < rooms.size(); j++)
          {
            if (rooms.get(j).isAvailable(plannerDate))
            {
              exams.get(i).getPrivateCalendar().makeReservation(plannerDate);
              rooms.get(j).getPrivateCalendar().makeReservation(plannerDate);
              exams.get(i).getTeacher().get(0).getPrivateCalendar().makeReservation(plannerDate);
              ArrayList<Object> addList = new ArrayList<>();
              addList.add(plannerDate);
              addList.add(rooms.get(j));
              addList.add(exams.get(i));
              addList.add(exams.get(i).getTeacher());
              examPlanList.add(addList);
              break;
            }
          }
        }
    }
    return examPlanList;
  }
}
