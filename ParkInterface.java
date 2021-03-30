// --== CS400 File Header Information ==--
// Name: Anna Stephan
// Email: amstephan@wisc.edu
// Team: Red
// Group: kb
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.List;

public interface ParkInterface extends Comparable<ParkInterface> {
    
    public String getName();
    public String getStates();
    public void setStates(String states);
    public String getDescription();
    public void setDescription(String description);
    public List<String> getNeighbors();
    public List<Integer> getDistances();

    // from super interface Comparable
    public int compareTo(ParkInterface otherCharacter);
}
