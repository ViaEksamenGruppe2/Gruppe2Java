package model;

public interface ExamCalendarController
{
  public Room getRoom();
  public Exam getExam();
  public Person getPerson();
  public Date getDate();
  public PrivateCalendar getPrivateCalendar();
  public ExamCalendar getExamCalendar();
  public boolean roomHasHDMI(Room room);
  public boolean roomHasVGA(Room room);
  public boolean roomHasProjector(Room room);
  
}
