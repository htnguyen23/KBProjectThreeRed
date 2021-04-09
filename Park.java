// --== CS400 File Header Information ==--
// Name: Anna Stephan
// Email: amstephan@wisc.edu
// Team: Red
// Group: kb
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.ArrayList;
import java.util.List;

public class Park implements ParkInterface {

    private String name;                    // the name of the National Park
    private String states;                   // the State the National Park resides in
    private String description;             // description of the park
    private List<String> neighbors;         // the nearest neighbors that this park has a route to
    private List<Integer> distances;        // the distances from this park to the neighbor parks

    /**
     * Creates a Park object.
     * 
     * @param destinations the header list from input csv file
     * @param data the distances corresponding to the destinations in param destinations
     */
    public Park(String[] destinations, String[] data) {
        // if data is null, Park object is initialized with all null fields
        if (destinations == null || data == null || destinations.length == 0 || data.length == 0) {
            return;
        }
        int distance;
        neighbors = new ArrayList<String>();
        distances = new ArrayList<Integer>();
        this.name = data[0];
        for(int i = 1; i < destinations.length; i++) {
            // check if there is a route available (data != -1)
            if((distance = Integer.parseInt(data[i])) != -1) {
                neighbors.add(destinations[i]);
                distances.add(distance);
            }
        }
    }

    /**
     * This method gets the name of the National park instance
     * 
     * @return the National Park name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * This method gets the states that this National Park
     * is located in
     * 
     * @return String list of states
     */
    @Override
    public String getStates() {
        return this.states;
    }

    /**
     * This method is used by ParkDataReader to initialize the
     * states of the National park
     * 
     * @param states - a String list of states 
     *                  (Ex: "New Mexico, Nevada")
     */
    @Override
    public void setStates(String states) {
        this.states = states;
    }

    /**
     * This method gets the description of the National Park
     * 
     * @return a String description of the park
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * This method is used by ParkDataReader to initialize the
     * description of the National Park
     * 
     * @param description - a String description of the park
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method returns a list of the names of the surrounding
     * National Parks that have routes to them from the current park.
     * 
     * @return a list of nearby park names
     */
    @Override
    public List<String> getNeighbors() {
        return this.neighbors;
    }

    /**
     * This method returns a list of distances associated with the
     * surrounding National Parks having routes to them.
     * NOTE: the element at index i in this list corresponds to the
     *      element at index i in the list returned by getNeighbors()
     * 
     * @return a list of distances
     */
    @Override
    public List<Integer> getDistances() {
        return this.distances;
    }

    /**
     * This method compares one National Park to the other lexographically
     * 
     * @ return 0 if both parks have the same name, a positive number
     *          if the otherPark is lexographically later than this park, and 
     *          a negative number if the otherPark is lexographically earlier
     *          than this park
     */
    @Override
    public int compareTo(ParkInterface otherCharacter) {
        if(this.name == null && otherCharacter.getName() == null) {
            return 0;
        } if(this.name == null && otherCharacter.getName() != null) {
            return 1;
        } else if(otherCharacter.getName() == null && this.name != null) {
            return -1;
        } else {
            return otherCharacter.getName().compareTo(this.name);
        }
    }
    
    /**
     * Returns the string representation of the Park object.
     * @return the park name
     */
    @Override
    public String toString() {
      return this.name; 
    }
}
