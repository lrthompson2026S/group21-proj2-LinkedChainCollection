package proj2.test.collectibles;

import java.time.Year;

import proj2.collectibles.VinylRecord;

/**
 * Tests the VinylRecord class.
 *
 * @author Lucas Abiy
 * @version 2026-10-20
 */
public class VinylRecordTest extends student.TestCase {

    private VinylRecord record;

    /**
     * Sets up the test fixture.
     */
    @Override
    public void setUp() {
        record = new VinylRecord(
            "Thriller Record",
            "A classic pop album",
            25.0,
            "Michael Jackson",
            "Thriller",
            1982);
    }

    /**
     * Resets system input and output after each test.
     */
    @Override
    public void tearDown() {
        resetSystemIO();
    }

    /**
     * Tests the default constructor.
     */
    public void testDefaultConstructor() {
        VinylRecord emptyRecord = new VinylRecord();

        assertEquals("", emptyRecord.getArtist());
        assertEquals("", emptyRecord.getAlbum());
        assertEquals(0, emptyRecord.getReleaseYear());
    }

    /**
     * Tests the full constructor.
     */
    public void testFullConstructor() {
        assertEquals("Michael Jackson", record.getArtist());
        assertEquals("Thriller", record.getAlbum());
        assertEquals(1982, record.getReleaseYear());
        assertEquals("Thriller Record", record.getName());
        assertEquals("A classic pop album", record.getDesc());
        assertEquals(25.0, record.getValue(), 0.001);
    }

    /**
     * Tests setting and getting the artist.
     */
    public void testArtist() {
        record.setArtist("Prince");
        assertEquals("Prince", record.getArtist());

        record.setArtist("Taylor Swift");
        assertEquals("Taylor Swift", record.getArtist());
    }

    /**
     * Tests invalid artist values.
     */
    public void testInvalidArtist() {
        record.setArtist(null);
        assertEquals("Michael Jackson", record.getArtist());

        record.setArtist("");
        assertEquals("Michael Jackson", record.getArtist());

        record.setArtist("   ");
        assertEquals("Michael Jackson", record.getArtist());

        record.setArtist(repeatCharacter('a', 1001));
        assertEquals("Michael Jackson", record.getArtist());
    }

    /**
     * Tests setting and getting the album title.
     */
    public void testAlbum() {
        record.setAlbum("Purple Rain");
        assertEquals("Purple Rain", record.getAlbum());
    }

    /**
     * Tests invalid album titles.
     */
    public void testInvalidAlbum() {
        record.setAlbum(null);
        assertEquals("Thriller", record.getAlbum());

        record.setAlbum("");
        assertEquals("Thriller", record.getAlbum());

        record.setAlbum("   ");
        assertEquals("Thriller", record.getAlbum());

        record.setAlbum(repeatCharacter('b', 1001));
        assertEquals("Thriller", record.getAlbum());
    }

    /**
     * Tests setting and getting the release year.
     */
    public void testReleaseYear() {
        record.setReleaseYear(1999);
        assertEquals(1999, record.getReleaseYear());

        record.setReleaseYear(0);
        assertEquals(0, record.getReleaseYear());

        record.setReleaseYear(-1);
        assertEquals(0, record.getReleaseYear());

        record.setReleaseYear(Year.now().getValue() + 1);
        assertEquals(0, record.getReleaseYear());
    }

    /**
     * Tests inherited name, description, and value setters.
     */
    public void testInheritedSetters() {
        record.setName("New Record");
        assertEquals("New Record", record.getName());

        record.setDesc("New description");
        assertEquals("New description", record.getDesc());

        record.setValue(50.0);
        assertEquals(50.0, record.getValue(), 0.001);
    }

    /**
     * Tests the info method.
     */
    public void testInfo() {
        String result = record.info();

        assertTrue(result.contains("Thriller Record"));
        assertTrue(result.contains("$25.0"));
        assertTrue(result.contains("Michael Jackson"));
        assertTrue(result.contains("Thriller"));
    }

    /**
     * Tests the toString method.
     */
    public void testToString() {
        String result = record.toString();

        assertTrue(result.contains("VinylRecord"));
        assertTrue(result.contains("Thriller Record"));
        assertTrue(result.contains("Michael Jackson"));
        assertTrue(result.contains("Thriller"));
        assertTrue(result.contains("1982"));
    }

    /**
     * Tests editing the artist.
     */
    public void testEditArtist() {
        setSystemIn("4\nPrince\n7\n");

        record.edit();

        assertEquals("Prince", record.getArtist());
    }

    /**
     * Tests editing the album.
     */
    public void testEditAlbum() {
        setSystemIn("5\nPurple Rain\n7\n");

        record.edit();

        assertEquals("Purple Rain", record.getAlbum());
    }

    /**
     * Tests editing the release year.
     */
    public void testEditReleaseYear() {
        setSystemIn("6\n1999\n7\n");

        record.edit();

        assertEquals(1999, record.getReleaseYear());
    }

    /**
     * Tests editing the name.
     */
    public void testEditName() {
        setSystemIn("1\nPurple Rain Record\n7\n");

        record.edit();

        assertEquals("Purple Rain Record", record.getName());
    }

    /**
     * Tests editing the description.
     */
    public void testEditDescription() {
        setSystemIn("2\nA rock album\n7\n");

        record.edit();

        assertEquals("A rock album", record.getDesc());
    }

    /**
     * Tests editing the value.
     */
    public void testEditValue() {
        setSystemIn("3\n40.5\n7\n");

        record.edit();

        assertEquals(40.5, record.getValue(), 0.001);
    }

    /**
     * Tests an invalid menu choice.
     */
    public void testEditInvalidChoice() {
        setSystemIn("99\n7\n");

        record.edit();

        assertEquals("Michael Jackson", record.getArtist());
    }

    /**
     * Tests a nonnumeric menu choice.
     */
    public void testEditNonnumericChoice() {
        setSystemIn("abc\n7\n");

        record.edit();

        assertEquals("Michael Jackson", record.getArtist());
    }

    /**
     * Tests invalid artist input during editing.
     */
    public void testEditInvalidArtist() {
        setSystemIn("4\n\n7\n");

        record.edit();

        assertEquals("Michael Jackson", record.getArtist());
    }

    /**
     * Tests invalid album input during editing.
     */
    public void testEditInvalidAlbum() {
        setSystemIn("5\n\n7\n");

        record.edit();

        assertEquals("Thriller", record.getAlbum());
    }

    /**
     * Tests invalid release-year input during editing.
     */
    public void testEditInvalidYear() {
        setSystemIn("6\n-10\n7\n");

        record.edit();

        assertEquals(1982, record.getReleaseYear());
    }

    /**
     * Tests nonnumeric release-year input during editing.
     */
    public void testEditNonnumericYear() {
        setSystemIn("6\nabc\n7\n");

        record.edit();

        assertEquals(1982, record.getReleaseYear());
    }

    /**
     * Tests invalid numeric value input during editing.
     */
    public void testEditNonnumericValue() {
        setSystemIn("3\nabc\n7\n");

        record.edit();

        assertEquals(25.0, record.getValue(), 0.001);
    }

    /**
     * Creates a string containing a repeated character.
     *
     * @param character
     *            the character to repeat
     * @param count
     *            the number of repetitions
     * @return the resulting string
     */
    private String repeatCharacter(char character, int count) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < count; i++) {
            result.append(character);
        }

        return result.toString();
    }
}