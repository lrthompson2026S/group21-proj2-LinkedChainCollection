package proj2.collectibles;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Tests the methods of the ActionFigure class.
 */
public class ActionFigureTest {

    private ActionFigure figure;


    /**
     * Creates a new ActionFigure before each test.
     */
    @Before
    public void setUp() {
        figure = new ActionFigure();
    }


    /**
     * Tests the default constructor.
     */
    @Test
    public void testDefaultConstructor() {
        assertEquals("", figure.getSeries());
        assertFalse(figure.getSealed());
        assertEquals("", figure.getCharacter());
    }


    /**
     * Tests setting and getting the series.
     */
    @Test
    public void testSetAndGetSeries() {
        figure.setSeries("Marvel Legends");

        assertEquals("Marvel Legends", figure.getSeries());
    }


    /**
     * Tests that setting the series to null is rejected.
     */
    @Test
    public void testSetSeriesNull() {
        figure.setSeries("Marvel Legends");
        figure.setSeries(null);

        assertEquals("Marvel Legends", figure.getSeries());
    }


    /**
     * Tests that setting the series to an empty string is rejected.
     */
    @Test
    public void testSetSeriesEmpty() {
        figure.setSeries("Marvel Legends");
        figure.setSeries("");

        assertEquals("Marvel Legends", figure.getSeries());
    }


    /**
     * Tests setting and getting the sealed status.
     */
    @Test
    public void testSetAndGetSealed() {
        figure.setSealed(true);
        assertTrue(figure.getSealed());

        figure.setSealed(false);
        assertFalse(figure.getSealed());
    }


    /**
     * Tests setting and getting the character.
     */
    @Test
    public void testSetAndGetCharacter() {
        figure.setCharacter("Spider-Man");

        assertEquals("Spider-Man", figure.getCharacter());
    }


    /**
     * Tests that setting the character to null is rejected.
     */
    @Test
    public void testSetCharacterNull() {
        figure.setCharacter("Spider-Man");
        figure.setCharacter(null);

        assertEquals("Spider-Man", figure.getCharacter());
    }


    /**
     * Tests that setting the character to an empty string is rejected.
     */
    @Test
    public void testSetCharacterEmpty() {
        figure.setCharacter("Spider-Man");
        figure.setCharacter("");

        assertEquals("Spider-Man", figure.getCharacter());
    }


    /**
     * Tests the info method.
     */
    @Test
    public void testInfo() {
        figure.setCharacter("Spider-Man");

        assertEquals("Action Figure - Spider-Man", figure.info());
    }


    /**
     * Tests toString with populated fields.
     */
    @Test
    public void testToString() {
        figure.setSeries("Marvel Legends");
        figure.setSealed(true);
        figure.setCharacter("Spider-Man");

        assertEquals(
            "ActionFigure{series='Marvel Legends', sealed=true, " + "character='Spider-Man'}",
            figure.toString());
    }


    /**
     * Tests toString with default values.
     */
    @Test
    public void testToStringDefaultValues() {
        assertEquals("ActionFigure{series='', sealed=false, character=''}",
            figure.toString());
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

            figure.edit();

            return output.toString();
        }
        finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }


    /**
     * Tests editing the series through the menu.
     */
    @Test
    public void testEditSeries() {
        runEdit("1\nMarvel Legends\n");

        assertEquals("Marvel Legends", figure.getSeries());
    }


    /**
     * Tests editing the sealed status to true.
     */
    @Test
    public void testEditSealedTrue() {
        runEdit("2\ntrue\n");

        assertTrue(figure.getSealed());
    }


    /**
     * Tests editing the sealed status to false.
     */
    @Test
    public void testEditSealedFalse() {
        figure.setSealed(true);

        runEdit("2\nfalse\n");

        assertFalse(figure.getSealed());
    }


    /**
     * Tests editing the character through the menu.
     */
    @Test
    public void testEditCharacter() {
        runEdit("3\nSpider-Man\n");

        assertEquals("Spider-Man", figure.getCharacter());
    }


    /**
     * Tests that an invalid menu choice is rejected.
     */
    @Test
    public void testEditInvalidMenuChoice() {
        String output = runEdit("4\n1\nMarvel Legends\n");

        assertTrue(output.contains("Invalid choice"));
        assertEquals("Marvel Legends", figure.getSeries());
    }


    /**
     * Tests that nonnumeric menu input is rejected.
     */
    @Test
    public void testEditNonnumericMenuChoice() {
        String output = runEdit("abc\n1\nMarvel Legends\n");

        assertTrue(output.contains("Invalid input"));
        assertEquals("Marvel Legends", figure.getSeries());
    }


    /**
     * Tests that an empty series is rejected and requested again.
     */
    @Test
    public void testEditEmptySeries() {
        String output = runEdit("1\n\nMarvel Legends\n");

        assertTrue(output.contains("Series cannot be empty."));
        assertEquals("Marvel Legends", figure.getSeries());
    }


    /**
     * Tests that an empty character is rejected and requested again.
     */
    @Test
    public void testEditEmptyCharacter() {
        String output = runEdit("3\n\nSpider-Man\n");

        assertTrue(output.contains("Character cannot be empty."));
        assertEquals("Spider-Man", figure.getCharacter());
    }


    /**
     * Tests that invalid sealed input is rejected and requested again.
     */
    @Test
    public void testEditInvalidSealedInput() {
        String output = runEdit("2\nyes\ntrue\n");

        assertTrue(output.contains("Invalid input"));
        assertTrue(figure.getSealed());
    }


    /**
     * Tests that whitespace-only series input is rejected.
     */
    @Test
    public void testEditWhitespaceSeries() {
        String output = runEdit("1\n   \nMarvel Legends\n");

        assertTrue(output.contains("Series cannot be empty."));
        assertEquals("Marvel Legends", figure.getSeries());
    }


    /**
     * Tests that whitespace-only character input is rejected.
     */
    @Test
    public void testEditWhitespaceCharacter() {
        String output = runEdit("3\n   \nSpider-Man\n");

        assertTrue(output.contains("Character cannot be empty."));
        assertEquals("Spider-Man", figure.getCharacter());
    }
}
