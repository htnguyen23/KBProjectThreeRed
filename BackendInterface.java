// --== CS400 File Header Information ==--
// Name: Elliott Weinshenker
// Email: eweinshenker@wisc.edu
// Team: Red
// Group: kb
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.List;
import java.util.NoSuchElementException;

/**
 * This ADT represents the backend structure for storing and returning objects from the graph
 * of vertexes representing United States National Parks. 
 *
 * @param <T> the data type of the object corresponding to each graph vertex
 */
public interface BackendInterface<T>{
 
  /**
   * Returns a list of all vertices in the map of national parks
   * 
   * @return list of all objects of data type representing the vertices of the graph
   */
  public List<T> getAllParks(); 
  
  /**
   * Returns the sequence of vertices representing the shortest path between start and end. 
   * Uses Dijkstra's shortest path algorithm to find the shortest path.
   * 
   * @param end   the name of the park in the end vertex for the path
   * @throws NoSuchElementException when no path from start to end can be found including when no
   *                                vertex containing start or end can be found
   */
  public List<T> getPathSequence(String end);

  /**
   * Returns the cost of the path (sum over edge weights) between start and end. Uses Dijkstra's
   * shortest path algorithm to find the shortest path.
   * 
   * @param end   the name of the park in the end vertex for the path
   * @return the cost of the shortest path between vertex starting at city Madison and the vertex 
   *         with data item end, including all edges between start and end
   * @throws NoSuchElementException when no path from start to end can be found including when no
   *                                vertex containing start or end can be found
   */
  public int shortestPath(String end); 
  
  /**
   * Returns the shortest path between Madison and a randomly generated destination vertex

   * @return the cost of the shortest path between vertex starting at city Madison and the randomly 
   *         generated destination vertex, including all vertices between start and end
   */
  public List<T> getRandPath(); // return a random path to a destination 
  
  /**
   * Adds the path returned in getPathSequence() to list of strips store in the Object instance 
   * of itinerary.  
   * 
   * @return true if the path could be added, false if the itinerary is at full capacity (three 
   *         paths) 
   */
  public boolean addToItinerary(); 
  
  /**
   * Remove a path from the itinerary with an end vertex that corresponds to the destination
   * National Park.
   * 
   * @param end   the name of the park in the end vertex for the path
   * @return true if the path could be removed, false if it was not in the graph
   * @throws NoSuchElementException if the itinerary is empty
   */
  public boolean removeFromItinerary(String end);
  
  /**
   * Returns the list of paths (a collection of vertices) presently in itinerary that stores
   * the saved shortestPath returned in getPathSequence(). 
   * 
   * @return a collection of lists. Each list contains a path, a sequence of vertices in 
   */
  public List<List<T>> getItinerary();
  
}
