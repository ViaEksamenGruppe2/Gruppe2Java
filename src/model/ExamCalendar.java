package model;

import java.io.Serializable;
import java.util.ArrayList;

public class ExamCalendar implements Serializable
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
    Date plannerDate, lastDate;
    String success = "The planner was successful";
    for (int i = 0; i < exams.size(); i++)
    {
      lastDate = lastExamDate.copy();
      lastDate.stepForwardOneDay();
      plannerDate = firstExamDate.copy();
      while (exams.get(i).getPrivateCalendar().isBooked(plannerDate)
          || exams.get(i).getTeacher().get(0).getPrivateCalendar().isBooked(plannerDate)
          || !exams.get(i).willThisCauseBack2BackExams(plannerDate)
          && plannerDate.isBefore(lastDate))
      {
        plannerDate.stepForwardOneDay();
        plannerDate = plannerDate.copy();
      }
        if (exams.get(i).getPriorityRoom() != null
            && exams.get(i).isRoomOkayForExam(exams.get(i).getPriorityRoom())
            && !exams.get(i).getPriorityRoom().getPrivateCalendar().isBooked(plannerDate)
            && !exams.get(i).getTeacher().get(0).getPrivateCalendar().isBooked(plannerDate))
        {
          Date checkdate = plannerDate.copy();
          checkdate.stepForwardManyDays(exams.get(i).getTotalExamDurationInDays() - 1);
          if(!checkdate.isBefore(lastDate))
          {
            success = "The planner failed to add: " + exams.get(i).getCourseName() + " exam, because of too little date range";
            break;
          }
          ArrayList<Object> addList = new ArrayList<>();
          addList.add(plannerDate.copy());
          for (int j = 0; j < exams.get(i).getTotalExamDurationInDays(); j++)
          {
            exams.get(i).getPrivateCalendar().makeReservation(plannerDate.copy());
            exams.get(i).getPriorityRoom().getPrivateCalendar().makeReservation(plannerDate.copy());
            exams.get(i).getTeacher().get(0).getPrivateCalendar().makeReservation(plannerDate.copy());
            exams.get(i).makeReservationForAllStudents(plannerDate.copy());
            if (exams.get(i).getTotalExamDurationInDays() > 1)
            {
              plannerDate.stepForwardOneDay();
            }
          }
          addList.add(exams.get(i).getPriorityRoom());
          addList.add(exams.get(i));
          addList.add(exams.get(i).getTeacher());
          examPlanList.add(addList);
        }
        else if (!exams.get(i).getPrivateCalendar().isBooked(plannerDate)
            && !exams.get(i).getTeacher().get(0).getPrivateCalendar().isBooked(plannerDate))
        {
          Date checkdate = plannerDate.copy();
          checkdate.stepForwardManyDays(exams.get(i).getTotalExamDurationInDays() - 1);
          if(!checkdate.isBefore(lastDate))
          {
            success = "The planner failed to add: " + exams.get(i).getCourseName() + " exam, because of too little date range";
            break;
          }
          for (int j = 0; j < rooms.size(); j++)
          {
            if (rooms.get(j).isAvailable(plannerDate)
                && exams.get(i).isRoomOkayForExam(rooms.get(j)))
            {
              ArrayList<Object> addList = new ArrayList<>();
              addList.add(plannerDate.copy());
              for (int k = 0; k < exams.get(i).getTotalExamDurationInDays(); k++)
              {
                exams.get(i).getPrivateCalendar().makeReservation(plannerDate.copy());
                rooms.get(j).getPrivateCalendar().makeReservation(plannerDate.copy());
                exams.get(i).getTeacher().get(0).getPrivateCalendar().makeReservation(plannerDate.copy());
                exams.get(i).makeReservationForAllStudents(plannerDate.copy());
                if (exams.get(i).getTotalExamDurationInDays() > 1)
                {
                  plannerDate.stepForwardOneDay();
                }
              }
              addList.add(rooms.get(j));
              addList.add(exams.get(i));
              addList.add(exams.get(i).getTeacher());
              examPlanList.add(addList);
              break;
            }
          }
        }
    }
    System.out.println(success);
    return examPlanList;
  }

}
