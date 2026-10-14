/**
 * Rin Hall 9069-13080 10/13/2026
 */

package proj2.test.core;

import proj2.core.Input;
import student.TestCase;

public class InputTest extends TestCase {

    /**
     * Since there is no input console in junit, this is how we get around it
     *
     * @param input
     *     The exact string that the program will read as input
     */
    private void setInput(String... input) {
        setSystemIn(input);
    }


    /**
     * Restores the input stream to the default
     */
    private void restoreInput() {
        resetSystemIO();
    }


    @Override
    public void setUp() {

    }


    @Override
    public void tearDown() {
        restoreInput();
    }


    /**
     * Test
     */
    public void testOptions() {
        setInput("-1\n10\nNot a number\n1");
        String prompt = "Please choose a number";
        String[] display = { "Test fail", "Test pass", "Test fail" };
        int index = (new Input()).options(prompt, display);
        assertEquals(index, 1);
    }


    /**
     * Test
     */
    public void testInputString() {
        setInput("pass", "Too Long of a String", "pass");

        Input in = (new Input());

        String prompt = "Please input a String";
        String output = in.inputString(prompt);
        assertEquals(output, "pass");

        output = in.inputString(prompt, 5);
        assertEquals(output, "pass");
    }


    /**
     * Test
     */
    public void testInputDouble() {
        setInput("50.61", "Not a double", "-99999999999999999", "90.84");

        Input in = (new Input());

        String prompt = "Please input a Double";
        double output = in.inputDouble(prompt);
        assertTrue(output == 50.61);

        output = in.inputDouble(prompt, 999);
        assertTrue(output == 90.84);
    }


    /**
     * Test
     */
    public void testInputInt() {
        setInput("50", "Not an int", "99999999999", "3.4", "90");

        Input in = (new Input());

        String prompt = "Please input an Integer";
        double output = in.inputInt(prompt);
        assertTrue(output == 50);

        output = in.inputInt(prompt, 999);
        assertTrue(output == 90);
    }
}
