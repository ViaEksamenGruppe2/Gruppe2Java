package model;

import java.io.*;
import java.util.ArrayList;

public class SaveLoad
{
  //Start of save
  public void saveToBinary(ArrayList<Object> examCalender) {
    final String filename = "examData.bin";



    ObjectOutputStream out = null;

    try {
      File file = new File(filename);
      FileOutputStream fos = new FileOutputStream(file);
      out = new ObjectOutputStream(fos);

      for (int i = 0; i < examCalender.size(); i++) {

      }

    }

    catch (IOException e) {
      System.out.println("Exception: " + filename);
    }

    finally {
      try {
        out.close();
      }

      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void loadFromBinary() {
    String filename = "examData.bin";
    ObjectInputStream in = null;

    try {
      File file = new File(filename);
      FileInputStream fis = new FileInputStream(file);
      in = new ObjectInputStream(fis);

      int count = in.readInt(); // The first thing we read from the file is
      // of type int

      for (int i = 0; i < count; i++) {
        int intValue = in.readInt(); // The next thing we read from the file
        // is of type int
        double doubleValue = in.readDouble(); // The next thing we read from
        // the file is of type double
        System.out.println(intValue + ", " + doubleValue);
      }
    }

    catch (IOException e) {
      e.printStackTrace();
    }

    finally {
      try {
        in.close();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
