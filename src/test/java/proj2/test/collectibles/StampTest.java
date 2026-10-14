package proj2.collectibles;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Tests the methods of the Stamp class.
 */
public class StampTest {

    private Stamp stamp;


    /**
     * Creates a new Stamp before each test.
     */
    @Before
    public void setUp() {
        stamp = new Stamp();
    }


    /**
     * Tests the default constructor.
     */
    @Test
    public void testDefaultConstructor() {
        assertEquals(0, stamp.getAge());
        assertEquals("", stamp.getPrintLocation());
        assertFalse(stamp.getMisprint());
    }


    /**
     * Tests setting and getting the age.
     */
    @Test
    public void testSetAndGetAge() {
        stamp.setAge(25);

        assertEquals(25, stamp.getAge());
    }


    /**
     * Tests that a negative age is rejected.
     */
    @Test
    public void testSetNegativeAge() {
        stamp.setAge(25);
        stamp.setAge(-1);

        assertEquals(25, stamp.getAge());
    }


    /**
     * Tests that zero is a valid age.
     */
    @Test
    public void testSetZeroAge() {
        stamp.setAge(10);
        stamp.setAge(0);

        assertEquals(0, stamp.getAge());
    }


    /**
     * Tests setting and getting the print location.
     */
    @Test
    public void testSetAndGetPrintLocation() {
        stamp.setPrintLocation("United States");

        assertEquals("United States", stamp.getPrintLocation());
    }


    /**
     * Tests that a null print location is rejected.
     */
    @Test
    public void testSetNullPrintLocation() {
        stamp.setPrintLocation("United States");
        stamp.setPrintLocation(null);

        assertEquals("United States", stamp.getPrintLocation());
    }


    /**
     * Tests that an empty print location is rejected.
     */
    @Test
    public void testSetEmptyPrintLocation() {
        stamp.setPrintLocation("United States");
        stamp.setPrintLocation("");

        assertEquals("United States", stamp.getPrintLocation());
    }


    /**
     * Tests setting and getting the misprint status.
     */
    @Test
    public void testSetAndGetMisprint() {
        stamp.setMisprint(true);
        assertTrue(stamp.getMisprint());

        stamp.setMisprint(false);
        assertFalse(stamp.getMisprint());
    }


    /**
     * Tests the info method.
     */
    @Test
    public void testInfo() {
        stamp.setPrintLocation("United States");

        assertEquals("Stamp - United States", stamp.info());
    }


    /**
     * Tests toString with populated fields.
     */
    @Test
    public void testToString() {
        stamp.setAge(25);
        stamp.setPrintLocation("United States");
        stamp.setMisprint(true);

        assertEquals(
            "Stamp{age=25, printLocation='United States', misprint=true}",
            stamp.toString());
    }


    /**
     * Tests toString with default values.
     */
    @Test
    public void testToStringDefaultValues() {
        assertEquals("Stamp{age=0, printLocation='', misprint=false}",
            stamp.toString());
    }


    /**
     * Runs edit with simulated input and captures its output.
     *
     * @param input
     *     the input to provide to edit
     * @return the text printed by edit
     */
    private String runEdit(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            System.setIn(new ByteArrayInputStream(
                input.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output));

            stamp.edit();

            return output.toString();
        }
        finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }


    /**
     * Tests editing the age through the menu.
     */
    @Test
    public void testEditAge() {
        runEdit("1\n25\n");

        assertEquals(25, stamp.getAge());
    }


    /**
     * Tests editing the print location through the menu.
     */
    @Test
    public void testEditPrintLocation() {
        runEdit("2\nUnited States\n");

        assertEquals("United States", stamp.getPrintLocation());
    }


    /**
     * Tests editing the misprint status to true.
     */
    @Test
    public void testEditMisprintTrue() {
        runEdit("3\ntrue\n");

        assertTrue(stamp.getMisprint());
    }


    /**
     * Tests editing the misprint status to false.
     */
    @Test
    public void testEditMisprintFalse() {
        stamp.setMisprint(true);

        runEdit("3\nfalse\n");

        assertFalse(stamp.getMisprint());
    }


    /**
     * Tests that an invalid menu choice is rejected.
     */
    @Test
    public void testEditInvalidMenuChoice() {
        String output = runEdit("4\n1\n25\n");

        assertTrue(output.contains("Invalid choice"));
        assertEquals(25, stamp.getAge());
    }


    /**
     * Tests that nonnumeric menu input is rejected.
     */
    @Test
    public void testEditNonnumericMenuChoice() {
        String output = runEdit("abc\n1\n25\n");

        assertTrue(output.contains("Invalid input"));
        assertEquals(25, stamp.getAge());
    }


    /**
     * Tests that nonnumeric age input is rejected.
     */
    @Test
    public void testEditNonnumericAge() {
        String output = runEdit("1\nabc\n25\n");

        assertTrue(output.contains("Invalid input"));
        assertEquals(25, stamp.getAge());
    }


    /**
     * Tests that negative age input is rejected.
     */
    @Test
    public void testEditNegativeAge() {
        String output = runEdit("1\n-5\n25\n");

        assertTrue(output.contains("Age cannot be negative."));
        assertEquals(25, stamp.getAge());
    }


    /**
     * Tests that an empty print location is rejected.
     */
    @Test
    public void testEditEmptyPrintLocation() {
        String output = runEdit("2\n\nUnited States\n");

        assertTrue(output.contains("Print location cannot be empty."));
        assertEquals("United States", stamp.getPrintLocation());
    }


    /**
     * Tests that whitespace-only print location input is rejected.
     */
    @Test
    public void testEditWhitespacePrintLocation() {
        String output = runEdit("2\n   \nUnited States\n");

        assertTrue(output.contains("Print location cannot be empty."));
        assertEquals("United States", stamp.getPrintLocation());
    }


    /**
     * Tests that invalid misprint input is rejected.
     */
    @Test
    public void testEditInvalidMisprintInput() {
        String output = runEdit("3\nyes\ntrue\n");

        assertTrue(output.contains("Invalid input"));
        assertTrue(stamp.getMisprint());
    }
}
