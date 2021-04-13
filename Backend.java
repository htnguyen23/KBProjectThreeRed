// --== CS400 File Header Information ==--
// Name: Elliott Weinshenker
// Email: eweinshenker@wisc.edu
// Team: Red
// Group: kb
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;


/**
 * This class represents the data access layer of an application that provides users with trip
 * recommendations to US National Parks.
 * 
 * @author ellio
 *
 */
public class Backend implements BackendInterface<ParkInterface> {
  private final String start = "Madison (City)";
  private static ParkDataReader reader;
  private static Random randGen;

  private HashMap<String, ParkInterface> dataStore;
  private List<List<ParkInterface>> itinerary;
  private CS400Graph<String> map;
  private List<ParkInterface> path = null;

  /**
   * Creates a directed acyclic graph of edges representing paths from Madison, WI to US National 
   * Parks.
   * 
   * @param parkData csv file that contains list of neighbors and weights for constructing instance
   *                 of CS400Graph
   * @param parkInfo csv file with descriptive information about each park in the graph
   * @throws IOException           if the named file does not exist, is a directory rather than a
   *                               regular file , or for some other reason cannot be opened for
   *                               reading.
   * @throws DataFormatException   runtime exception for malformed input.
   * @throws FileNotFoundException if the file does not exist, is a directory rather than a regular
   *                               file, or for some other reason cannot be opened for reading.
   */
  public Backend(String parkData, String parkInfo)
      throws IOException, FileNotFoundException, DataFormatException {
    reader = new ParkDataReader();

    dataStore = new HashMap<String, ParkInterface>();
    map = new CS400Graph<String>();
    itinerary = new ArrayList<List<ParkInterface>>();
    randGen = new Random();

    List<Park> listOfParks = reader.readData(new FileReader(parkData));
    for (ParkInterface park : listOfParks) {
      dataStore.put(park.getName(), park);
      map.insertVertex(park.getName());
    }

    for (ParkInterface park : listOfParks) {
      this.insertMapEdge(park);
    }

    List<List<String>> input = reader.readDataDescriptions(new FileReader(parkInfo));
    for (List<String> innerList : input) {
      ParkInterface park = dataStore.get(innerList.get(0));
      park.setDescription(innerList.get(1));
      park.setStates(innerList.get(2));
    }

  }
  
  /**
   * Constructor for the backend class. Creates a directed acyclic graph of edges representing paths
   * from Madison, WI to US National Parks.
   * 
   * @param parkData csv file that contains list of neighbors and weights for constructing instance
   *                 of CS400Graph
   * @param parkInfo csv file with descriptive information about each park in the graph
   * @throws IOException           if the named file does not exist, is a directory rather than a
   *                               regular file , or for some other reason cannot be opened for
   *                               reading.
   * @throws DataFormatException   runtime exception for malformed input.
   * @throws FileNotFoundException if the file does not exist, is a directory rather than a regular
   *                               file, or for some other reason cannot be opened for reading.
   */

  public Backend(StringReader sr) throws IOException, FileNotFoundException, DataFormatException {
    reader = new ParkDataReader();

    dataStore = new HashMap<String, ParkInterface>();
    map = new CS400Graph<String>();
    itinerary = new ArrayList<List<ParkInterface>>();
    randGen = new Random();

    List<Park> listOfParks = reader.readData(sr);
    for (ParkInterface park : listOfParks) {
      dataStore.put(park.getName(), park);
      map.insertVertex(park.getName());
    }

    for (ParkInterface park : listOfParks) {
      this.insertMapEdge(park);
    }

    List<List<String>> input = reader.readDataDescriptions(sr);
    for (List<String> innerList : input) {
      ParkInterface park = dataStore.get(innerList.get(0));
      park.setDescription(innerList.get(1));
      park.setStates(innerList.get(2));
    }

  }


  /**
   * Helper method called by constructor to insert a new directed edge with a positive edge weight
   * into the graph.
   * 
   * @param vertex
   */
  private void insertMapEdge(ParkInterface vertex) {
    // a. Iterate through distances filter only distances that are not INFINITY
    List<String> neighbors = vertex.getNeighbors();
    List<Integer> distances = vertex.getDistances();
    // b. If valid distance, retrieve the index
    for (int i = 0; i < distances.size(); i++) {
      int weight = 0;
      if ((weight = distances.get(i)) > 0) {
        // Create edge for the neighbor and the corresponding distance
        map.insertEdge(vertex.getName(), neighbors.get(i), weight);
      }
    }
  }


  /**
   * Returns the sequence of target vertices of the edges of the shortest path between start and
   * the entered destination. Uses Dijkstra's shortest path algorithm to find the shortest path.
   * 
   * @param end the name of the park in the end vertex for the path
   * @throws NoSuchElementException when no path from start to end can be found including when no
   *                                vertex containing start or end can be found
   */
  @Override
  public List<ParkInterface> getPathSequence(String end) {

    List<String> parkList = map.shortestPath(start, end);
    path = parkList.stream().map(name -> dataStore.get(name)).collect(Collectors.toList());
    return path;
  }

  /**
   * Returns a list of all vertices in the map of national parks
   * 
   * @return list of all objects of data type ParkInterface
   */
  @Override
  public List<ParkInterface> getAllParks() {

    List<ParkInterface> allParks = new ArrayList<ParkInterface>();

    for (String key : dataStore.keySet()) {
      allParks.add(dataStore.get(key));
    }
    return allParks;
  }

  /**
   * Returns the cost of the path (sum over edge weights) between start and end. Uses Dijkstra's
   * shortest path algorithm to find the shortest path.
   * 
   * @param end the name of the park in the end vertex for the path
   * @return the cost of the shortest path between vertex starting at city Madison and the vertex
   *         with data item end, including all edges between start and end
   * @throws NoSuchElementException when no path from start to end can be found including when no
   *                                vertex containing start or end can be found
   */
  @Override
  public int shortestPath(String end) {

    return map.getPathCost(start, end);
  }


  /**
   * Returns the shortest path between Madison and a randomly generated destination each time
   * an instance of the backend is created.
   * 
   * @return the cost of the shortest path between vertex starting at city Madison and the randomly
   *         generated destination vertex, including all vertices between start and end
   */
  @Override
  public List<ParkInterface> getRandPath() {
    int rand = randGen.nextInt(dataStore.size());
    int count = 0;

    String destination = null;
    for (String key : dataStore.keySet()) {
      if (rand == count) {
        destination = key;
        break;
      }
      count++;
    }

    return getPathSequence(destination);
  }

  /**
   * Adds the path returned in getPathSequence() to list of trips stored in the Object instance of
   * itinerary.
   * 
   * @return true if the path could be added, false if the itinerary is at full capacity (three
   *         paths)
   */
  @Override
  public boolean addToItinerary() {
    if (itinerary.size() == 3)
      return false;

    return itinerary.add(path);

  }

  /**
   * Remove a path from the itinerary with an end vertex that corresponds to the destination
   * National Park.
   * 
   * @param end the key of the end vertex for the path
   * @return true if the path was removed, false if the itinerary does not contain a list with the
   *         provided end vertex
   * @throws NoSuchElementException if the itinerary is empty
   */

  @Override
  public boolean removeFromItinerary(String end) {
    if (this.itinerary.isEmpty())
      throw new NoSuchElementException("ERROR: Itinerary is empty");
    for (List<ParkInterface> innerList : itinerary) {
      int size = innerList.size();
      if (innerList.get(size - 1).toString().equals(end)) {
        return itinerary.remove(innerList);
      }
    }
    return false;

  }


  /**
   * Returns the list of paths (a collection of vertices) presently in itinerary that stores the
   * saved shortestPath returned in getPathSequence().
   * 
   * @return a collection of lists. Each list contains a path, a sequence of vertices in the graph
   */
  @Override
  public List<List<ParkInterface>> getItinerary() {
    return itinerary;
  }
}


