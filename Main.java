// --== CS400 File Header Information ==--
// Name: Akshat Bansal
// Email: akshat.bansal@wisc.edu
// Team: Red
// Group: KB
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.io.IOException;
import java.util.zip.DataFormatException;

public class Main {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    if (args == null) {
      System.out.println("<<< Please enter a .csv filename >>>");
      return;
    } else if (args.length != 1) {
      System.out.println("<<< Incorrect number of arguments >>>");
    }

    String csvFilepath = args[0];
    Backend backend;
    try {
      backend = new Backend(csvFilepath);
      Frontend frontend = new Frontend();
      frontend.run(backend);
    } catch (IOException ioe) {
      System.out.println("IOException was thrown!");
      ioe.printStackTrace();
    } catch (DataFormatException dfe) {
      System.out.println("DataFormatException was thrown!");
      dfe.printStackTrace();
    }
  }

}
