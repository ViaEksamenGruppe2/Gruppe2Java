package model;

public class Date
{
  // Field variables
  private int day, month, year;
  private boolean isPersonal;
  // Field variables

  // Constructor for Date object
  public Date(int day, int month, int year)
  {
    setDate(day, month, year);
    isPersonal = false;
  }

  // Start of getters for Date object
  public Date getDate()
  {
    return new Date(day, month, year);
  }

  public int getDay()
  {
    return day;
  }

  public int getMonth()
  {
    return month;
  }

  public int getYear()
  {
    return year;
  }

  public boolean isPersonalDate()
  {
    return isPersonal;
  }
  // End of getters for Date object

  // Start of setters for Date object
  public void setDate(int day, int month, int year)
  {
    this.day = day;
    this.month = month;
    this.year = year;
  }

  public void setDate(Date date)
  {
    setDate(date.getDay(), date.getMonth(), date.getYear());
  }
  // End of setters for Date object

  // Method to change the state of the isPersonal boolean
  public void changePersonalDate(boolean state)
  {
    this.isPersonal = state;
  }

  // Copy method
  public Date copy()
  {
    Date copy = new Date(day, month, year);
    return copy;
  }
}
