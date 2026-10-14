package proj2.test.collectibles;

// -------------------------------------------------------------------------

import proj2.collectibles.TradingCard;

/**
 * Just a test case class for trading card
 *
 * @author Elijah O'Donoghue
 * @version Oct 7, 2026
 */
public class TradingCardTest extends student.TestCase {
    private TradingCard pikachu;

    // ----------------------------------------------------------


    /**
     * Sets up the test file, simply creates a cone
     */
    @Override
    public void setUp() {
        pikachu = new TradingCard();
    }


    @Override
    public void tearDown() {
        resetSystemIO();
    }


    public void testType() {
        assertEquals("Unknown", pikachu.getType());
        pikachu.setType("Pokemon");
        assertEquals("Pokemon", pikachu.getType());
    }


    public void testName() {
        assertEquals("Unknown Collectible", pikachu.getName());
        pikachu.setName("");
        assertEquals("Unknown Collectible", pikachu.getName());
        pikachu.setName("Pikachu");
        assertEquals("Pikachu", pikachu.getName());
    }


    public void testDesc() {
        assertEquals("None", pikachu.getDesc());
        pikachu.setDesc(
            "After the adoption of the Type 74, the Japanese High Command was already looking for a superior, completely indigenous tank designed to defeat the Soviet T-72. As a result, development of a prototype Joint development was performed by Mitsubishi Heavy Industries and TRDI (Japan Defense Agency's Technology Research and Development Institute). Major subcontractors included Japan Steel Works, Daikin Industries, Mitsubishi Electric, Fujitsu and NEC.[citation needed]\n" + "\n" + "A first series of two prototypes of the Type 90 were developed, both armed with a Japanese 120 mm smoothbore gun (produced by Japan Steel Works Limited) firing Japanese ammunition (produced by Daikin Industries Limited).[1]\n" + "The second prototype of the Type 90 on display at the JGSDF Public Information Center. It was the last prototype to feature the Japan Steel Works 120mm main gun, with future prototypes (later official production) using the Rheinmetall 120mm main gun instead.\n" + "\n" + "A second series of four prototypes was built between 1986 and 1988, incorporating changes resulting from trials with the first two prototypes. These were armed with the Rheinmetall 120 mm smoothbore gun also fitted to the German Leopard 2 and, in a modified version, the US M1A1/M1A2 Abrams main battle tanks. These second prototypes were used for development and then user trials, all of which were completed by December 1989, before Japan formally introduced the Type 90 in August 1990. Mass production began in 1990, and 30 vehicles were produced by the end of the year.[2]\n" + "\n" + "With the exception of the 120 mm smoothbore gun, which is made under license from Rheinmetall of Germany, the Type 90 and its subsystems are all designed and built in Japan, leading to higher per unit costs than comparable models from NATO countries such as the M1 Abrams and the Challenger 2. Development of upgrades to the Type 90 suffered both as a result of limited budget, which caused procurement delays,[3] and funding prioritization in favor of the Type 10 main battle tank. Due to a perception that Type 90s are unsuited to operations in the tight confines of Japan's urban areas, they are preferentially assigned to the JGSDF Fuji School Brigade and the 7th Armored Division based in Hokkaido, where there is sufficient room for maneuver.[4]\n" + "\n" + "With the exception of training exercises such as the annual Combined Live Fire Exercise hosted by the United States Army at Yakima Training Center in Washington state,[5] the Type 90 has never been deployed overseas, and has never been tested in combat.");
        assertEquals("None", pikachu.getDesc());
        pikachu.setDesc("Pikachu card from 2011 BW Promo 3-pack blister");
        assertEquals("Pikachu card from 2011 BW Promo 3-pack blister",
            pikachu.getDesc());
    }


    public void testOneLine() {
        pikachu.setName("Pikachu");
        pikachu.setGrade(10);
        pikachu.setPrintYear(2010);
        pikachu.setDesc("Cool Pikachu");
        pikachu.setValue(145.32);
        pikachu.setType("Pokemon");
        System.out.println(pikachu.toString());
        assertEquals("Pikachu - $145.32 - Type: Pokemon", pikachu.info());
        // It works I don't wanna spam the assert equals on print statements
    }


    public void testEditName() {
        setSystemIn("1\nCharizard\n");
        pikachu.edit();
        assertEquals("Charizard", pikachu.getName());
    }


    public void testEditDesc() {
        setSystemIn("2\nUpdated description\n");
        pikachu.edit();
        assertEquals("Updated description", pikachu.getDesc());
    }


    public void testEditValue() {
        setSystemIn("3\n250.50\n");
        pikachu.edit();
        assertEquals(250.50, pikachu.getValue(), 0.001);
    }


    public void testEditType() {
        setSystemIn("4\nMagic\n");
        pikachu.edit();
        assertEquals("Magic", pikachu.getType());
    }


    public void testEditGrade() {
        setSystemIn("5\n9\n");
        pikachu.edit();
        assertEquals(9, pikachu.getGrade());
    }


    public void testEditPrintYear() {
        setSystemIn("6\n2012\n");
        pikachu.edit();
        assertEquals(2012, pikachu.getPrintYear());
    }


    public void testEditInvalidOption() {
        setSystemIn("99\n");
        pikachu.edit();
        // Verifies it handles invalid input without crashing
        assertEquals("Unknown Collectible", pikachu.getName());
    }
    // This so far is basically everything BESIDES the edit stuff but i need the
    // input stuff to finalize that anyway

}
