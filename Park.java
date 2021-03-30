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

    
    public String getName() {
        return this.name;
    }

    public String getStates() {
        return this.states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getNeighbors() {
        return this.neighbors;
    }

    public List<Integer> getDistances() {
        return this.distances;
    }

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
}