import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.DataFormatException;

public class Backend implements BackendInterface<ParkInterface> {
  private final String start = "Madison (City)";
  private static ParkDataReader reader; 
  private static Random randGen; 
  
  private HashMap<String, ParkInterface> dataStore; 
  private List<List<ParkInterface>> itinerary; 
  private CS400Graph<String> map; 
  private List<ParkInterface> path = null; 

  public Backend(String parkData, String parkInfo) throws IOException, DataFormatException {
    reader = new ParkDataReader();  
    List<Park> listOfParks = reader.readData(new FileReader(parkData));
    map = new CS400Graph<String>(); 
    dataStore = new HashMap<String, ParkInterface>();
    itinerary = new ArrayList<List<ParkInterface>>();
    randGen = new Random(); 
    
    // Create prefix hashmap
    for(ParkInterface park: listOfParks) { // line is not EOF
      dataStore.put(park.getName(), park);
      map.insertVertex(park.getName());
    }
    
    for(ParkInterface park: listOfParks) {
      this.insertMapEdge(park);
    }
    
    List<List<String>> output = reader.readDataDescriptions(new FileReader(parkInfo));
    for(List<String> list: output) {
      ParkInterface park = dataStore.get(list.get(0));
      park.setDescription(list.get(1));
      park.setStates(list.get(2));  
    }
    
    assert(map.containsEdge("Madison (City)", "Indiana Dunes" ));

  }
  
  
  private void insertMapEdge(ParkInterface vertex) {
    // a. Iterate through distances filter only distances that are not INFINITY
    List<String> neighbors = vertex.getNeighbors();
    List<Integer> distances = vertex.getDistances();
    // b. If valid distance, retrieve the index
    for(int i = 0; i < distances.size(); i++) {
      int weight = 0; 
      if((weight = distances.get(i)) > 0) {
        // Create edge for the neighbor and the corresponding distance
        map.insertEdge(vertex.getName(), neighbors.get(i), weight);
      }
    }    
  }
  
  public Backend(StringReader reader) {}

  @Override
  public List<ParkInterface> getPathSequence(String end) {

    List<String> parkList = map.shortestPath(start, end);
    path = parkList.stream()
        .map(name -> dataStore.get(name))
        .collect(Collectors.toList());
    return path; 
  }


  @Override
  public List<ParkInterface> getAllParks() {

    List<ParkInterface> allParks = new ArrayList<ParkInterface>(); 
    
    for(String key: dataStore.keySet()) {
      allParks.add(dataStore.get(key)); 
    }
    return allParks;
  }


  @Override
  public int shortestPath(String end) {

    return map.getPathCost(start, end);
  }


  @Override
  public List<ParkInterface> getRandPath() {

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
  public boolean addToItinerary() {
    if(itinerary.size() == 3)
      return false; 
    
    return itinerary.add(path);

  }


  @Override
  public boolean removeFromItinerary(String end) {
    if(this.itinerary.isEmpty())
      throw new NoSuchElementException();     
    for(List<ParkInterface>innerList: itinerary) {
      int size = innerList.size(); 
      if(innerList.get(size - 1).toString().equals(end)) {
        return itinerary.remove(innerList);
      }
    }
    return false; 
   
  }


  @Override
  public List<List<ParkInterface>> getItinerary() {
    return itinerary;
  }
  
//  public static void main(String[] args) {
//    // TODO Auto-generated method stub
//    String csvFilepath = args[0];
//    String csvFilepath2 = args[1];
//    try {
//      Backend backend = new Backend(csvFilepath, csvFilepath2);
//      System.out.println(backend.getPathSequence("Redwood"));
//      System.out.println(backend.shortestPath("Redwood"));
//      System.out.println(backend.getRandPath().toString());
//    } catch(Exception ex) {
//      ex.printStackTrace(); 
//    }
//
//  }
}




