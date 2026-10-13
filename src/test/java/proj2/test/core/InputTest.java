package proj2.test.core;

import proj2.core.Input;
import student.TestCase;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class InputTest extends TestCase{
    final InputStream original = System.in;

    private void setInput(String input)
    {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

    private void restoreInput()
    {
        System.setIn(original);
    }

    @Override
    public void setUp(){
    }

    @Override
    public void tearDown()
    {
        restoreInput();
    }

    public void testOptions(){
        setInput("-1\n10\nNot a number\n1");
        String prompt = "Please choose a number";
        String[] display = {"Test fail", "Test pass", "Test fail"};
        int index = Input.options(prompt, display);
        assertEquals(index, 1);
    }

    public void testInputString(){
        setInput("pass");
        String prompt = "Please input a String";
        String output = Input.inputString(prompt);
        assertEquals(output, "pass");

        setInput("Too Long of a String\npass");
        output = Input.inputString(prompt, 5);
        assertEquals(output, "pass");
    }

    public void testInputDouble(){
        setInput("50.61");
        String prompt = "Please input a Double";
        double output = Input.inputDouble(prompt);
        assertTrue(output == 50.61);

        setInput("Not a double\n-99999999999999999\n90.84");
        output = Input.inputDouble(prompt, 999);
        assertTrue(output == 90.84);
    }
}
