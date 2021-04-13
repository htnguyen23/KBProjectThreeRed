// --== CS400 File Header Information ==--
// Name: Akshat Bansal
// Email: akshat.bansal@wisc.edu
// Team: Red
// Group: KB
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

class FrontendTests {
  /**
   * Test method that checks if the welcome screen is printed out as desired.
   */
  @Test
  void testWelcomeScreen() {
    try {
      String firstFile = "national_parks.csv";
      String secondFile = "national_park_info.csv";
      String input = "x";

      PrintStream initialOutput = System.out;
      InputStream initialInput = System.in;

      InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
      System.setIn(inputStreamSimulator);
      ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outputStreamCaptor));

      Frontend frontend = new Frontend();
      Backend backend = new Backend(firstFile, secondFile);
      frontend.run(backend);

      System.setOut(initialOutput);
      System.setIn(initialInput);

      String output = outputStreamCaptor.toString();
      assertEquals(output, "|* * * * * * * * * * OOOOOOOOOOOOOOOOOOOOOOOOO|\n"
          + "| * * * * * * * * *  :::::::::::::::::::::::::|\n"
          + "|* * * * * * * * * * OOOOOOOOOOOOOOOOOOOOOOOOO|\n"
          + "| * * * * * * * * *  :::::::::::::::::::::::::|\n"
          + "|* * * * * * * * * * OOOOOOOOOOOOOOOOOOOOOOOOO|\n"
          + "| * * * * * * * * *  ::::::::::::::::::::;::::|\n"
          + "|* * * * * * * * * * OOOOOOOOOOOOOOOOOOOOOOOOO|\n"
          + "|:::::::::::::::::::::::::::::::::::::::::::::|\n"
          + "|OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO|\n"
          + "|:::::::::::::::::::::::::::::::::::::::::::::|\n"
          + "|OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO|\n"
          + "|:::::::::::::::::::::::::::::::::::::::::::::|\n"
          + "|OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO|\n"
          + "\n       WELCOME TO THE GOLDEN EAGLE PATH!       \n"
          + "\nUse this application to plan your next great " + "\nAmerican adventure!"
          + "\nChoose a national park to visit and we will "
          + "\nshow you the shortest way there, through the "
          + "\nother national parks along the way of course!"
          + "\nCan't decide which park you want to visit? "
          + "\nWe're happy to give you a suggestion!" + "\nWant to save some plans for the future?"
          + "\nYou can save up to 3 itineraries!\n" + "\nEnjoy your park-hopping experience!"
          + "\n___________________________________________" + "\n"
          + "\n            PLAN YOUR ADVENTURE" + "\n___________________________________________"
          + "\nEnter the number of the option you wish to " + "\nchoose.\n"
          + "\n1. I want to view a list of the Natl. Parks"
          + "\n2. Help! I can't decide where to go!" + "\n3. I want to see my saved itineraries"
          + "\nx - I think I'm done\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Test method that checks if the shortest path screen is printed out as desired.
   */
  @Test
  void testShortestPathParkScreen() {
    try {
      String firstFile = "national_parks.csv";
      String secondFile = "national_park_info.csv";
      String input = "1" + System.lineSeparator() + "46" + System.lineSeparator() + "x"
          + System.lineSeparator() + "x";

      PrintStream initialOutput = System.out;
      InputStream initialInput = System.in;

      InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
      System.setIn(inputStreamSimulator);
      ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outputStreamCaptor));

      Frontend frontend = new Frontend();
      Backend backend = new Backend(firstFile, secondFile);
      frontend.run(backend);

      System.setOut(initialOutput);
      System.setIn(initialInput);

      String output = outputStreamCaptor.toString();
      assertTrue(output.contains("Total distance: 1652 miles"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Test method that checks if the saved itineraries screen is printed out as desired.
   */
  @Test
  void testSavedItinerariesScreen() {
    try {
      String firstFile = "national_parks.csv";
      String secondFile = "national_park_info.csv";
      String input = "1" + System.lineSeparator() + "28" + System.lineSeparator() + "y"
          + System.lineSeparator() + "x" + System.lineSeparator() + "3" + System.lineSeparator()
          + "x" + System.lineSeparator() + "x";

      PrintStream initialOutput = System.out;
      InputStream initialInput = System.in;

      InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
      System.setIn(inputStreamSimulator);
      ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outputStreamCaptor));

      Frontend frontend = new Frontend();
      Backend backend = new Backend(firstFile, secondFile);
      frontend.run(backend);

      System.setOut(initialOutput);
      System.setIn(initialInput);

      String output = outputStreamCaptor.toString();
      assertTrue(output.contains("Would you like to remove any itinerary from your list?"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Test method that checks if the national park list screen is printed out as desired.
   */
  @Test
  void testNatlParkListScreen() {
    try {
      String firstFile = "national_parks.csv";
      String secondFile = "national_park_info.csv";
      String input = "1" + System.lineSeparator() + "x" + System.lineSeparator() + "x";

      PrintStream initialOutput = System.out;
      InputStream initialInput = System.in;

      InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
      System.setIn(inputStreamSimulator);
      ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outputStreamCaptor));

      Frontend frontend = new Frontend();
      Backend backend = new Backend(firstFile, secondFile);
      frontend.run(backend);

      System.setOut(initialOutput);
      System.setIn(initialInput);

      String output = outputStreamCaptor.toString();
      assertTrue(output.contains("35. Lassen Volcanic     California"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
