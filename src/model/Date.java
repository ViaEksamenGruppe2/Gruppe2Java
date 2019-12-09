package model;

import java.io.Serializable;

public class Date implements Serializable
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
  //Is year of date leapyear
  public boolean isLeapYear()
  {
    return (year % 4 == 0 && ((year % 100 != 0) || (year % 400 == 0)));
  }
  //Returns number of days in month
  public int numberOfDaysInMonth()
  {
    switch (month)
    {
      case 4:
      case 6:
      case 9:
      case 11:
        return 30;
      case 2:
        if (isLeapYear())
        {
          return 29;
        }
        else
        {
          return 28;
        }
      default:
        return 31;
    }
  }
  //If other day is before current date
  public boolean isBefore(Date other)
  {
    if(other.year > this.year)
      return false;
    else if(other.year < this.year)
      return true;
    else
    {
      if(this.month > other.month)
        return false;
      else if(other.month > this.month)
        return true;
      else
      {
        if(this.day > other.day)
          return false;
        else if(other.day > this.day)
          return true;
        else
          return false; //if the dates are equal, isBefore returns false.
      }
    }
  }
  public void stepForwardOneDay()
  {
    do {
      day++;
      if (day > numberOfDaysInMonth()) {
        day = 1;
        month++;
        if (month > 12) {
          month = 1;
          year++;
        }
      }
    }
    while(new Date(day, month, year).isWeekDay() == false);
  }
  public void stepForwardManyDays(int x){
    for (int i = 0; i < x; i++) {
      stepForwardOneDay();;
    }
  }
  public void stepBackOneDay()
  {
    do {
      day--;
      if (day < 1) {
        month--;
        day = numberOfDaysInMonth();
        if (month < 1) {
          month = 12;
          year--;
        }
      }
    }
    while(new Date(day, month, year).isWeekDay() == false);
  }
  public boolean isWeekDay(){
    int yy = year % 100, monthCode = 0, y = year - (year % 1000), centuryCode = 0;
    int yearCode = (int)(yy + Math.floor(yy / 4)) % 7;
    switch (month){
      case 1: case 10: monthCode = 0; break;
      case 5: monthCode = 1; break;
      case 8: monthCode = 2; break;
      case 2: case 3: case 11: monthCode = 3; break;
      case 6: monthCode = 4; break;
      case 9: case 12: monthCode = 5; break;
      case 4: case 7: monthCode = 6; break;
    }
    switch (y){
      case 1700: case 2100: centuryCode = 4; break;
      case 1800: case 2200: centuryCode = 2; break;
      case 1900: case 2300: centuryCode = 0; break;
      case 2000: centuryCode = 6; break;
    }
    int leapYear = (isLeapYear()) ? 1 : 0;
    int dayCode = (yearCode + monthCode + centuryCode + day - leapYear) % 7;
    if (dayCode == 6 || dayCode == 0)
      return false;
    return true;
  }
  //Counts the start and end date of the interval
  public int weekdaysBetween(Date other){
    int days = 0;
    Date counterDate, endDate;

    if (this.isBefore(other)) {
      counterDate = this.copy();
      endDate = other;
    }
    else {
      counterDate = other.copy();
      endDate = this;
    }

    while (!counterDate.equals(endDate)) {
      if (counterDate.isWeekDay())
        days++;
      counterDate.stepForwardOneDay();
    }
    if (counterDate.isWeekDay())
      days++;
    return days;
  }

  //Counts the start and end of the interval
  public int daysBetween(Date other) {
    int days = 0;
    Date counterDate, endDate;

    if (this.isBefore(other)) {
      counterDate = this.copy();
      endDate = other;
    }
    else {
      counterDate = other.copy();
      endDate = this;
    }

    while (!counterDate.equals(endDate)) {
      days++;
      counterDate.stepForwardOneDay();
    }
    if (counterDate.isWeekDay() || !counterDate.isWeekDay())
      days++;
    return days;
  }

  // Copy method
  public Date copy() {
    Date copy = new Date(day, month, year);
    return copy;
  }
  public boolean equals(Object obj) {
    if (!(obj instanceof Date))
      return false;

    Date other = (Date) obj;
    return day == other.day && month == other.month && year == other.year;
  }

  public String toString()
  {
    String s = "";
    if (day < 10)
    {
      s += "0";
    }
    s += day + "/";
    if (month < 10)
    {
      s += "0";
    }
    s += month + "/" + year;

    return s;
  }
}
