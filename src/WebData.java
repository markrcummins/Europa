import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class WebData {

  private String time, urlStream;
  private int red, purple, colorData;
  private double data;

  public WebData() {

    time = "0000";
    data = 1;
    red = 1;
    purple = 1;
    colorData = 1;
  }

  public String getTime() {return time;}
  public void setTime(String time) {this.time = time;}
  public double getData() {return data;}
  public void setData(int data) {this.data = data;}
  public int getRed() {return red;}
  public void setRed(int red) {this.red = red;}
  public int getPurple() {return purple;}
  public void setPurple(int purple) {this.purple = purple;}
  public int getColorData() {return colorData;}
  public void setColorData(int colorData) {this.colorData = colorData;}

  public void getKingRiverData() {
      try {
        final Document doc = Jsoup.connect("http://www.spk-wc.usace.army.mil/fcgi-bin/hourly.py?report=pnf&textonly=true").get();
        String str = doc.select("body").text();
        String strArray[] = str.split("\\s+");
        double inflowTotal = averageInflow(strArray);
        double outflowTotal = averageOutflow(strArray);
        System.out.println("inflowTotal: " + inflowTotal);
        System.out.println("outflowTotal: " + outflowTotal);
        System.out.println("???: " + (inflowTotal/outflowTotal)*100);
        double flowPercent = inflowTotal/outflowTotal * 100;

        this.data = flowPercent;
        System.out.println("Flow Percent value: " + flowPercent);
        System.out.println("this.data value: " + this.data);
      }
      catch(IOException e) {
        System.out.println("Website inaccessible");
      }
    }

  private int averageInflow(String array[]) {
    int average;
    int total = 0;
    int counter = 0;
    int i = 56;
    while (i <= 620) {
      //System.out.println("Value at index " + i + " : " + array[i]);
      if(isInt(array[i])) {
        int validInput = Integer.parseInt(array[i]);
        total += validInput;
        i+=12;
        counter++;

      }
      else {
        i+=12;
      }
    }
    average = total/counter;
    System.out.println("Average Inflow: " + average);
    return average;
  }

    private int averageOutflow(String array[]) {
      int average;
      int total = 0;
      int counter = 0;
      int k = 55;
      while (k <=619) {
        if(isInt(array[k])) {
          int validInput = Integer.parseInt(array[k]);
          total += validInput;
          k+=12;
          counter++;

        }
        else {
          System.out.println(array[k]);
          k+=12;
        }
      }

      average = total/counter;
      System.out.println("Average Outflow: " + average);
      return average;
    }

    private boolean isInt(String message) {
      try {
        Integer.parseInt(message);
        return true;

      }
      catch(NumberFormatException e) {
        return false;
      }
    }
}
