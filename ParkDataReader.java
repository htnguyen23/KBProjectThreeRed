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
import java.io.IOException;
import java.io.Reader;
import java.util.zip.DataFormatException;
import java.io.BufferedReader;

public class ParkDataReader {

  List<Park> parkList;

  /**
   * This method reads the data from the file and instantiates Park objects accordingly.
   * 
   * @param inputFileReader
   * @return
   * @throws IOException
   * @throws DataFormatException
   */
  public List<Park> readData(Reader inputFileReader) throws IOException, DataFormatException {
    if (inputFileReader == null)
      throw new NullPointerException("Reader is null");
    BufferedReader buffReader = new BufferedReader(inputFileReader);
    String dataLine = "";
    String[] data = null;
    String[] destinations = null;
    Park tempPark = null;
    parkList = new ArrayList<Park>();
    boolean headerFlag = true;
    while ((dataLine = buffReader.readLine()) != null) {
      if (headerFlag) {
        destinations = dataLine.split(","); // get String array for header 
        headerFlag = false;
      } else {
        data = dataLine.split(",");
        tempPark = new Park(destinations, data);
        parkList.add(tempPark);
      }
    }
    buffReader.close();
    return parkList;
  }

    /**
   * This method reads the data from the descriptions file and updates the Park objects accordingly.
   * 
   * @param inputFileReader
   * @return
   * @throws IOException
   * @throws DataFormatException
   */
  public List<Park> readDataDescriptions(Reader inputFileReader, List<Park> list) throws IOException, DataFormatException {
    if (inputFileReader == null)
      throw new NullPointerException("Reader is null");
    if (list == null) 
      throw new NullPointerException("Park list not initialized and is null");
    BufferedReader buffReader = new BufferedReader(inputFileReader);
    String dataLine = "";
    String[] data = null;
    String[] header = null;
    boolean headerFlag = true;
    String name = null;
    String description = null;
    String states = null;
    while ((dataLine = buffReader.readLine()) != null) {
      if (headerFlag) {
        header = dataLine.split(","); // get String array for header 
        headerFlag = false;
      } else {
        data = dataLine.split(",\"");
        name = data[0];
        if(data.length != 3) {
          data = data[1].split("\",");
          description = data[0].replaceAll("\"", "");
          states = data[1].replaceAll("\"","");
        } else {
          description = data[1].replaceAll("\"","");
          states = data[2].replaceAll("\"","");
        }
        
        for(int i = 0; i < parkList.size(); i++) {
          if(list.get(i).getName().equals(name)) {
            list.get(i).setStates(states);
            list.get(i).setDescription(description);
            break;
          }
        }
      }
    }
    buffReader.close();
    return parkList;
  }
}
