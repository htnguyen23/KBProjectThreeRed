// --== CS400 File Header Information ==--
// Name: Elliott Weinshenker
// Email: eweinshenker@wisc.edu
// Team: Red
// Group: kb
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>


import static org.junit.jupiter.api.Assertions.*;
import java.util.zip.DataFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.io.IOException;

class BackendTests {
  private Backend backend;

  /**
   * Instantiate backend and load graph data.
   */
  @BeforeEach
  void setUpClass() {
    // TODO: Change strings to final file names
    String parkGraph = "./national_parks.csv";
    String parkInfo = "./national_park_info.csv";
    try {
      backend = new Backend(parkGraph, parkInfo);
    } catch (IOException | DataFormatException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * This test checks that pathSequence returns a list of Objects of type ParkInterface and the data
   * in the final vertex matches the user input destination.
   */
  @Test
  void testGetPathSequence() {
    List<ParkInterface> path = backend.getPathSequence("Redwood");

    assertTrue(path.get(path.size() - 1) instanceof ParkInterface);

    ParkInterface end = path.get(path.size() - 1);
    assertTrue(end.toString().equals("Redwood"));
  }

  /**
   * This test checks that the constructor reads and stores the correct number of ParkInterface
   * objects
   */
  @Test
  void testGetAllParks() {

    assertTrue(backend.getAllParks().size() == 51);

  }

  /**
   * This test checks the shortest path is returned for a sample of destinations
   */
  @Test
  void testGetShortestPath() {
    assertEquals(701, backend.shortestPath("Badlands"));
    assertEquals(800, backend.shortestPath("Shenandoah"));
    assertEquals(2898, backend.shortestPath("Redwood"));
  }



  /**
   * This test checks that the itinerary capacity does exceed three stored routes. The test will
   * fails if the user is able to add a fourth route.
   */
  @Test
  void testItinerary() {
    backend.getPathSequence("Redwood");
    assertTrue(backend.addToItinerary());
    backend.getPathSequence("Shenandoah");
    assertTrue(backend.addToItinerary());
    backend.getPathSequence("Biscayne");
    assertTrue(backend.addToItinerary());

    backend.getPathSequence("Hot Springs");
    assertFalse(backend.addToItinerary());
    assertEquals(3, backend.getItinerary().size());

  }

  /**
   * Try removing a route from a non-empty itinerary
   */
  @Test
  void testRemoveFromItinerary() {
    backend.getPathSequence("Redwood");
    backend.addToItinerary();
    backend.getPathSequence("Shenandoah");
    backend.addToItinerary();

    assertEquals(2, backend.getItinerary().size());
    assertTrue(backend.removeFromItinerary("Shenandoah"));
    assertEquals(1, backend.getItinerary().size());

  }


  /**
   * This test checks that the list of ParkInterfaces returned by getItinerary() are accessible and
   * an object returns the expected values for getDescription() and getStates().
   */
  @Test
  void testParkObject() {
    backend.getPathSequence("Redwood");
    backend.addToItinerary();
    List<List<ParkInterface>> itin = backend.getItinerary();
    ParkInterface onRoute = itin.get(0).get(3);
    assertTrue(onRoute instanceof ParkInterface);
    assertTrue(onRoute.getDescription().contains(
        "Rising above a scene rich with extraordinary wildlife, pristine lakes, and alpine terrain"
            + ", the Teton Range"));
    assertTrue(onRoute.getStates().contains("Wyoming"));

  }
}
