import java.util.List;
import java.util.Set;

public interface BackendInterface<T>{
 
  public List<T> getPathSequence(String end);
  public List<T> getThreeParks(); //number of returned elements subject to change
  public int shortestPath(String end); 
  
  public List<T> getRandPath(); // return a random path to a destination 
  
  public boolean addToItinerary(String end); 
  public boolean removeFromItinerary(String end);
  public Set<T> getItinerary();
  
}
