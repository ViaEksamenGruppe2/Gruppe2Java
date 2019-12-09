package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class PrivateCalendar implements Serializable
{
  // Field variables
  private ArrayList<Date> dates;
  // Field variables

  // Constructor for PrivateCalendar object
  public PrivateCalendar()
  {
    dates = new ArrayList<>();
  }


  //Logic
  public void makeReservation(Date date){
    if(!dates.contains(date))
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

  public Date getDateFromIndex(int i){
    return dates.get(i);
  }

  public Date getNextAvailableDate(Date date) {
    Date control = date.copy();
    Boolean look = true;
    while (look) {
      if (isBooked(control)) {
        control.stepForwardOneDay();
        control = control.copy();
      }
      else
        look = false;
    }
    return control;
  }

  public void removeAllNonPersonalDates() {
    Iterator<Date> i = dates.iterator();
    while (i.hasNext()) {
      Date s = i.next(); // must be called before you can call i.remove()
      if (!s.isPersonalDate())
        i.remove();
    }
  }

  @Override
  public String toString() {
    return "PrivateCalendar{" +
            "dates=" + dates +
            '}';
  }

  public boolean equals(Object obj){
    if (!(obj instanceof PrivateCalendar))
      return false;
    PrivateCalendar other = (PrivateCalendar) obj;
    return dates.equals(other.getBookedDates());
  }
}
