// --== CS400 File Header Information ==--
// Name: Anna Stephan
// Email: amstephan@wisc.edu
// Team: Red
// Group: kb
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import org.junit.Test;

public class DataWranglerTests {

    /**
     * This test checks that the Park object can instantiate with null arguments.
     */
    @Test
    public void testParkObjNull() {
        Park p = new Park(null, null);
        assertNull(p.getName());
        assertNull(p.getNeighbors());
        assertNull(p.getDistances());
    }

    /**
     * This test checks that the Park object correctly instantiates and stores data in its
     * variables, and that the Park object’s accessor methods return the expected values
     */
    @Test
    public void testParkObj() {
        Park p = new Park(
            new String[] {"PARK","Rocky Mountain","Smoky Mountain","Yosemite"},
            new String[] {"Rocky Mountain","0","-1","600"} );

        assertEquals("Rocky Mountain",p.getName());
        assertFalse(p.getNeighbors().contains("Smoky Mountain"));
        assertTrue(p.getNeighbors().contains("Yosemite"));
        assertFalse(p.getDistances().contains(-1));
        assertTrue(p.getDistances().contains(600));
    }

    /**
     * This test checks that ParkDataReader returns a List of Park objects correctly
     * corresponding to a given CSV file.
     */
    @Test
    public void testParkDataReader() {
        FileReader inputFileReader = null;
        ParkDataReader reader = null;

        try {
            reader = new ParkDataReader();
            inputFileReader = new FileReader("national_parks.csv");
            List<Park> result = reader.readData(inputFileReader);
            assertEquals(51, result.size());

            inputFileReader.close();
        } catch (IOException e) {
            fail("IOException");
        } catch (DataFormatException e) {
            fail("DataFormatException");
        }
    }

    /**
     * This test checks that the second reader method works to update the
     * park list when reading from the second csv file
     */
    @Test
    public void testParkDescriptionReader() {
        ParkDataReader reader = null;

        FileReader inputFileReader = null;
        FileReader fileReader2 = null;
        try {
            inputFileReader = new FileReader("test_parkData.csv");
            reader = new ParkDataReader();
            List<Park> result = reader.readData(inputFileReader);

            fileReader2 = new FileReader("test_parkDescriptions.csv");
            List<Park> updatedRes = reader.readDataDescriptions(fileReader2,result);

            assertEquals("p1",updatedRes.get(0).getName());
            assertEquals("Maine",updatedRes.get(0).getStates());
            assertEquals("p1 description, contains commas",updatedRes.get(0).getDescription());

            assertEquals("p2",updatedRes.get(1).getName());
            assertEquals("Utah",updatedRes.get(1).getStates());
            assertEquals("p2 description, also contains commas",updatedRes.get(1).getDescription());

            assertEquals("p3",updatedRes.get(2).getName());
            assertEquals("California, Nevada",updatedRes.get(2).getStates());
            assertEquals("p3 has multiple states, and also has a description that has commas",updatedRes.get(2).getDescription());

            inputFileReader.close();
            fileReader2.close();
        } catch (IOException e) {
            fail("IOException");
        } catch (DataFormatException e) {
            fail("DataFormatException");
        }
    }

    /**
     * This test checks that Park compareTo() method is correctly comparing whether or not a
     * Park is greater or less than another Park based on total power.
     */
    @Test
    public void testParkCompareTo() {
        Park char1 = new Park(
        new String[] {""}, new String[] {});
         Park char2 =
        new Park(
            new String[] {"PARK","Rocky Mountain","Smoky Mountain","Yosemite"},
            new String[] {"Rocky Mountain","0","-1","600"});
        Park char3 = new Park(
            new String[] {"PARK","Rocky Mountain","Smoky Mountain","Yosemite"},
            new String[] {"Yosemite","600","100","0"});
        Park char4 = new Park(
            new String[] {"PARK","Rocky Mountain","Smoky Mountain","Yosemite"},
            new String[] {"Rocky Mountain","0","-1","600"});
        assertTrue(char1.compareTo(char2) > 0);
        assertTrue(char2.compareTo(char1) < 0);
        assertTrue(char3.compareTo(char2) < 0);
        assertTrue(char2.compareTo(char4) == 0);
    }

    /**
     * This test checks if the Park list returned from the readData() method is correct when
     * reading from the full national_parks.csv file.
     */
    @Test
    public void testParkDataReaderFull() {
        ParkDataReader reader = new ParkDataReader();
        try {
        FileReader inputFileReader = new FileReader("national_parks.csv");
        List<Park> result = reader.readData(inputFileReader);
        inputFileReader.close();
        inputFileReader = new FileReader("National Park Descriptions and States.csv");
        reader.readDataDescriptions(inputFileReader, result);
        assertEquals(51, result.size()); // total # of parks in .csv = 51

        inputFileReader.close();
        } catch (IOException e) {
            fail();
        } catch (DataFormatException e2) {
            fail();
        }
    }

}
