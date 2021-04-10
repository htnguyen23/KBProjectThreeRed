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
  
  public ParkDataReader() {
     
  }

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
  public List<Park> readDataDescriptions(Reader inputFileReader) throws IOException, DataFormatException {
      if (inputFileReader == null)
        throw new NullPointerException("Reader is null");

      try (BufferedReader br = new BufferedReader(inputFileReader)) {
        br.readLine(); 
        return br.lines()
          .map(line -> Arrays.asList(line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")))
          .collect(Collectors.toList());
      
//      .map(line -> line.replaceAll("\"", ""))
      }
  }
}
