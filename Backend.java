import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

public class Backend implements BackendInterface<ParkInterface> {
  private final String start = "Madison (City)";
  private static ParkDataReader reader; 
  private static Random randGen; 
  
  private HashMap<String, ParkInterface> dataStore; // prefix hashmap for storing park information  
  private CS400Graph<String> map; 
  private Set<ParkInterface> itinerary; 

  public Backend(String filePath) throws IOException, DataFormatException {
    reader = new ParkDataReader();  
    List<Park> listOfParks = reader.readData(new FileReader(filePath));
    map = new CS400Graph<String>(); 
    dataStore = new HashMap<String, ParkInterface>();
    randGen = new Random(); 
    
    // Create prefix hashmap
    for(ParkInterface park: listOfParks) { // line is not EOF
      dataStore.put(park.getName(), park);
      map.insertVertex(park.getName());
    }
    
    for(ParkInterface park: listOfParks) {
      this.insertEdge(park);
    }
//    assert(map.containsEdge(start, "Indiana Dunes" ));
  }
  
  private static interface Predicate<T>{
    boolean test (T t); 
  }
  
  private void insertEdge(ParkInterface vertex) {
    // a. Iterate through distances filter only distances that are not INFINITY
    List<String> neighbors = vertex.getNeighbors();
    List<Integer> distances = vertex.getDistances();
    // b. If valid distance, retrieve the index
    for(int i = 0; i < distances.size(); i++) {
      int weight = 0; 
      if((weight = distances.get(i)) > 0) {
        map.insertEdge(vertex.getName(), neighbors.get(i), weight);
      }
    }
    
    // c. Obtain the corresponding neighbor from getNeighbors
    
    // d. Insert and edge for the neighbor and the distance in c
  }
  
  public Backend(StringReader reader) {}

  @Override
  public List<ParkInterface> getPathSequence(String end) {
    // TODO Auto-generated method stub
    List<String> paths= map.shortestPath(start, end);
    List<ParkInterface> parkList = paths.stream()
        .map(i -> dataStore.get(i))
        .collect(Collectors.toList());
    return parkList; 
  }


  @Override
  public List<ParkInterface> getThreeParks() {
    // TODO Auto-generated method stub
    List<ParkInterface> threeParks = new ArrayList<ParkInterface>(); 
    
    for(String key: dataStore.keySet()) {
      threeParks.add(dataStore.get(key)); 
    }
    return null;
  }


  @Override
  public int shortestPath(String end) {
    // TODO Auto-generated method stub
    return map.getPathCost(start, end);
  }


  @Override
  public List<ParkInterface> getRandPath() {
    // TODO Auto-generated method stub
    int rand = randGen.nextInt(dataStore.size());
    int count = 0;
    
    String destination = null;  
    for(String key: dataStore.keySet()) {
      if(rand == count) {
        destination = key; 
        break; 
      }
      count++; 
    }
    
    return getPathSequence(destination); 
  }


  @Override
  public boolean addToItinerary(String end) {
    // TODO Auto-generated method stub
    if(itinerary.size() == 3)
      return false; 
    
    ParkInterface park = dataStore.get(end);

    return itinerary.add(park);
  }


  @Override
  public boolean removeFromItinerary(String end) {
    // TODO Auto-generated method stub
    ParkInterface park = dataStore.get(end);
    return itinerary.remove(park);
   
  }


  @Override
  public Set<ParkInterface> getItinerary() {
    // TODO Auto-generated method stub
    return itinerary;
  }
  
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    String csvFilepath = args[0];
    try {
      Backend backend = new Backend(csvFilepath);
    } catch(Exception ex) {
      ex.printStackTrace(); 
    }
  }
}




