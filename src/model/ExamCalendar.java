package model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class ExamCalendar implements Serializable
{
  private Date firstExamDate;
  private Date lastExamDate;
  private ArrayList<Person> persons;
  private ArrayList<Room> rooms;
  private ArrayList<Exam> exams;
  private String success;

  public ExamCalendar(Date firstExamDate, Date lastExamDate, ArrayList<Person> persons, ArrayList<Room> rooms, ArrayList<Exam> exams)
  {
    this.firstExamDate = firstExamDate;
    this.lastExamDate = lastExamDate;
    this.persons = persons;
    this.rooms = rooms;
    this.exams = exams;
    this.success = "The process has not yet been initialised";
  }

  public String getSuccess() {
    return success;
  }

  //
  // Method to generate exam schedule. This method generates a 2D ArrayList.
  //
  //
  public ArrayList<ArrayList<Object>> generateExamSchedule()
  {
    // The structure of the 2D ArrayList:
    // Date - The first date of the specific exam
    // Room - The room associated with the exam
    // Exam - The specific exam/course
    // Teachers - Which teacher(s) associated with the exam

    ArrayList<ArrayList<Object>> examPlanList = new ArrayList<>();

    Date plannerDate, lastDate; // making two Date objects, these are used for the logic in the method.
    // plannerDate is the date the exam is planned at
    // lastDate is the last date that an exam can be held at

    success = "The schedule is processing...";
    //Clears non-personal date reservations
    int examSize = exams.size(), roomSize = rooms.size(), personSize = persons.size();
    for (int i = 0; i < examSize; i++) {
      exams.get(i).getPrivateCalendar().removeAllNonPersonalDates();
    }
    for (int i = 0; i < roomSize; i++) {
      rooms.get(i).getPrivateCalendar().removeAllNonPersonalDates();
    }
    for (int i = 0; i < personSize; i++) {
      persons.get(i).getPrivateCalendar().removeAllNonPersonalDates();
    }

    //Shuffle exams
    Collections.shuffle(exams);
    //Moves exams that are held for seventh semester to the front
    //So they will have priority
    for (int i = 0; i < examSize; i++) {
      if (exams.get(i).isSeventhSemester()){
        Exam exam = exams.get(i);
        exams.remove(i);
        exams.add(0, exam );
      }
    }
    // for loop running for every exam.
    for (int i = 0; i < examSize; i++)
    {
      lastDate = lastExamDate.copy(); //
      lastDate.stepForwardOneDay();   // Here lastDate and plannerDate is assigned
      plannerDate = firstExamDate.copy(); //

      // This while loops runs while the specific exams calendar is booked
      // Or while the teachers calendar is booked
      // Or while the exam causes back to back exams
      // And while the planner date is before the last date the exams can be planned on
      // If these conditions are true then an exam can't be held at that date and
      // then the plannerDate will step forward with one day.
      while (exams.get(i).getPrivateCalendar().isBooked(plannerDate)
          || exams.get(i).getTeacher().get(0).getPrivateCalendar().isBooked(plannerDate)
          || !exams.get(i).willThisCauseBack2BackExams(plannerDate)
          && plannerDate.isBefore(lastDate))
      {
        plannerDate.stepForwardOneDay();
        plannerDate = plannerDate.copy();
      }

      // This if statement checks if the exam has a priority room
      // And if the exams calendar isn't booked
      // And if the room can be used for the exam i.e. only certain rooms can
      // be used for written exams.
      // And if the rooms calendar isn't booked
      // And if the teachers calendar isn't booked
      // If these conditions are all true, then we can start planning the exam
      // on that specific date
      // THIS ONLY RUNS IF THE EXAM HAS A PRIORITY ROOM!
        if (exams.get(i).getPriorityRoom() != null
            && !exams.get(i).getPrivateCalendar().isBooked(plannerDate)
            && exams.get(i).isRoomOkayForExam(exams.get(i).getPriorityRoom())
            && !exams.get(i).getPriorityRoom().getPrivateCalendar().isBooked(plannerDate)
            && !exams.get(i).getTeacher().get(0).getPrivateCalendar().isBooked(plannerDate))
        {

          // Here we check if it's possible to have the exam on the date given
          // if the length of the exam stretches over multiple days it might
          // not be possible to hold the exam before the last date of the exams
          // in that case it will give an warning.
          Date checkdate = plannerDate.copy();
          checkdate.stepForwardManyDays(exams.get(i).getTotalExamDurationInDays() - 1);
          if(!checkdate.isBefore(lastDate))
          {
            success = "Failed! (" + (i + 1) + "/" + examSize + ") The planner failed during planning of: " + exams.get(i).getCourseName() + ", caused by to short date range!";
            break;
          }

          // Here we create an ArrayList of Objects which is used to add to
          // the 2D ArrayList. We also add the date entry.
          ArrayList<Object> addList = new ArrayList<>();
          addList.add(plannerDate.copy());

          // This for loop runs through the duration of days the exam takes
          // which updates the private calendar for the exam, the room,
          // the teacher(s) and the students.
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

          // Finally we add the room, the exam and the teacher to the 2D ArrayList.
          addList.add(exams.get(i).getPriorityRoom());
          addList.add(exams.get(i));
          addList.add(exams.get(i).getTeacher());
          examPlanList.add(addList);
        }

        // This if statement checks if the exams calendar isn't booked
        // And if the teachers calendar isn't booked
        // THIS ONLY RUNS IF THE EXAM DOESN'T HAVE A PRIORITY ROOM!
        else if (!exams.get(i).getPrivateCalendar().isBooked(plannerDate)
            && !exams.get(i).getTeacher().get(0).getPrivateCalendar().isBooked(plannerDate))
        {

          // Here we check if it's possible to have the exam on the date given
          // if the length of the exam stretches over multiple days it might
          // not be possible to hold the exam before the last date of the exams
          // in that case it will give an warning.
          Date checkdate = plannerDate.copy();
          checkdate.stepForwardManyDays(exams.get(i).getTotalExamDurationInDays() - 1);
          if(!checkdate.isBefore(lastDate))
          {
            success = "Failed! (" + (i + 1) + "/" + examSize + ") The planner failed during planning of: " + exams.get(i).getCourseName() + ", caused by to short date range!";
            break;
          }

          // This for loop runs through all the rooms
          for (int j = 0; j < roomSize; j++)
          {

            // This if statement checks if the rooms calendar isn't booked
            // And if the room can be used for the exam i.e. only certain rooms can
            // be used for written exams.
            if (rooms.get(j).isAvailable(plannerDate)
                && exams.get(i).isRoomOkayForExam(rooms.get(j)))
            {

              // Here we create an ArrayList of Objects which is used to add to
              // the 2D ArrayList. We also add the date entry.
              ArrayList<Object> addList = new ArrayList<>();
              addList.add(plannerDate.copy());

              // This for loop runs through the duration of days the exam takes
              // which updates the private calendar for the exam, the room,
              // the teacher(s) and the students.
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

              // Finally we add the room, the exam and the teacher to the 2D ArrayList.
              addList.add(rooms.get(j));
              addList.add(exams.get(i));
              addList.add(exams.get(i).getTeacher());
              examPlanList.add(addList);
              break;
            }
          }
        }
    }

    // Here we print out if the planner was successful or not & return the
    // 2D ArrayList with the exam plan
    if (success.equals("The schedule is processing..."))
      success = "The scheduling was successful";
    return examPlanList;
  }


  //Start of write jsCSV-file
  private static final String filename = "src/savefiles/examText.js";

  public static void saveToJS(ArrayList<ArrayList<Object>> fileList) {
    File file = new File(filename);

    try {
      PrintWriter out = new PrintWriter(file); //Opens the file

      out.println("const classes = [");
      out.flush();

      for (ArrayList<Object> objects : fileList) {
        Room roomToCsv = (Room) objects.get(1);
        Exam examToCsv = (Exam) objects.get(2);
        String output = "";

        //Creates a string with all the given data. Repeats three times
        output += "{classname:'" + examToCsv.getCourseName() + "',examType:'"
            + examToCsv.getType() + "',roomName:'" + roomToCsv.getRoomName()
            + "',hasHDMI:'" + roomToCsv.hasHDMI() + "',hasVGA:'" + roomToCsv
            .hasVGA() + "',hasProjector:'" + roomToCsv.hasProjector()
            + "',dateStart:'" + examToCsv.getPrivateCalendar()
            .getDateFromIndex(0) + "',dateEnd:'" + examToCsv
            .getPrivateCalendar().getLastDate() + "',color:'" + examToCsv
            .getAccentColour() + "',is7thSemester:'" + examToCsv.isSeventhSemester();

        //Multiple teachers can have the same exam
        //Create for loop that iterates over all teachers
        for (int j = 0; j < examToCsv.getTeacher().size(); j++) {
          output += "',teacher:'" + (examToCsv.getTeacher().get(j).getViaID())
              + "'},";
        }

        out.println(output);
        out.flush(); //Forces it to write text
      }
      out.println("];");
      out.flush();
      out.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  //End of Write jsCSV-file
}
