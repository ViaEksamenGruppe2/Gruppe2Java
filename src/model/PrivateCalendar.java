package model;

import java.util.ArrayList;

public class PrivateCalendar
{
  // Field variables
  private ArrayList<Date> dates;
  // Field variables

  // Constructor for PrivateCalendar object
  public PrivateCalendar()
  {
    dates = new ArrayList<>();
  }
  public void makeReservation(Date date){
    dates.add(date);
  }
  public void removeReservation(Date date){
    dates.remove(date);
  }
  public boolean isBooked(Date date){
    return dates.contains(date);
  }
  public ArrayList<Date> getBookedDates(){
    return dates;
  }
  public Date getNextAvailableDate(Date date){
    Date control = date.copy();
    Boolean look = true;
    while (look)
    {
      if (isBooked(control)){
        control.stepForwardOneDay();
        control = control.copy();
      }
      else
        look = false;
    }
    return control;
  }
  public void removeAllNonPersonalDates(){
    for (int i = 0; i < dates.size(); i++) {
      if (!dates.get(i).isPersonalDate())
        dates.remove(i);
    }
  }

  @Override
  public String toString() {
    return "PrivateCalendar{" +
            "dates=" + dates +
            '}';
  }
}
