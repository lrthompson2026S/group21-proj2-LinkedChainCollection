/**
 * Rin Hall 9069-13080 10/13/2026
 */

package proj2.core;

import java.util.Scanner;

/**
 * The class for input. Validates inputs based on type.
 */
public class Input {

    private final Scanner scan = new Scanner(System.in);


    /**
     * Has the user select from a list of options
     *
     * @param prompt
     *     The prompt that the function will output
     * @param options
     *     An array of options for the user to select from
     * @return An integer representing the option chosen.
     */
    public int options(String prompt, String[] options) {
        System.out.println(prompt);
        for (int i = 0; i < options.length; i++) {
            System.out.println("[" + i + "] " + options[i]);
        }

        boolean valid = false;
        int optionIndex = -1;
        while (!valid) {
            try {
                valid = true;
                String input = scan.nextLine();
                optionIndex = Integer.parseInt(input);
                if ((optionIndex < 0) || (optionIndex > options.length - 1)) {
                    valid = false;
                    System.out.println(
                        "Invalid input. Please input a number between 0 and " + (options.length - 1) + ".");
                }
            }
            catch (Exception e) {
                valid = false;
                System.out.println(
                    "Invalid input. Please input a number between 0 and " + (options.length - 1) + ".");
                //throw new RuntimeException(e);
            }
        }

        return optionIndex;
    }


    /**
     * Has the user input a string
     *
     * @param prompt
     *     The prompt that the function will output
     * @param charLimit
     *     The maximum allowed character limit
     * @return The string that the user inputted
     */
    public String inputString(String prompt, int charLimit) {
        System.out.println(prompt);
        boolean valid = false;
        String output = "";
        while (!valid) {
            valid = true;
            output = scan.nextLine();
            if (output.length() > charLimit) {
                valid = false;
                System.out.println(
                    "That String is too large! Please enter a String of less than " + charLimit + " characters.");
            } else if (output.isEmpty()) {
                valid = false;
                System.out.println(
                        "Empty input. Please input a String.");
            }
        }

        return output;
    }


    /**
     * Has the user input a string with a default character limit of 1000
     *
     * @param prompt
     *     The prompt that the function will output
     * @return The string that the user inputted
     */
    public String inputString(String prompt) {   //Overloaded method for a default character limit of 1000
        return inputString(prompt, 1000);
    }


    /**
     * Has the user input a double
     *
     * @param prompt
     *     The prompt that the function will output
     * @param limit
     *     The maximum value that can be inputted This works for negative
     *     numbers too, i.e. a limit of 10 will make -15 invalid
     * @return The double that the user inputted
     */
    public double inputDouble(String prompt, int limit) {
        System.out.println(prompt);
        String input = "";
        double output = 0;
        boolean valid = false;

        while (!valid) {
            try {
                valid = true;
                input = scan.nextLine();
                output = Double.parseDouble(input);
                if (Math.abs(output) > limit) {
                    System.out.println(
                        "Invalid input. Please input a number from -" + limit + " to " + limit + ".");
                    valid = false;
                }
            }
            catch (NumberFormatException e) {
                valid = false;
                System.out.println(
                    "Not a double. Please input a number from -" + limit + " to " + limit + ".");
            }
        }

        return output;
    }


    /**
     * Has the user input a double with default limit of 999,999,999
     *
     * @param prompt
     *     The prompt that the function will output
     * @return The double that the user inputted
     */
    public double inputDouble(String prompt) {
        return inputDouble(prompt, 999999999);
    }


    /**
     * Has the user input an integer
     *
     * @param prompt
     *     The prompt that the function will output
     * @param limit
     *     The maximum value that can be inputted This works for negative
     *     numbers too, i.e. a limit of 10 will make -15 invalid
     * @return The integer that the user inputted
     */
    public int inputInt(String prompt, int limit) {
        System.out.println(prompt);
        String input = "";
        int output = 0;
        boolean valid = false;

        while (!valid) {
            try {
                valid = true;
                input = scan.nextLine();
                output = Integer.parseInt(input);
                if (Math.abs(output) > limit) {
                    System.out.println(
                        "Invalid input. Please input a number from -" + limit + " to " + limit + ".");
                    valid = false;
                }
            }
            catch (NumberFormatException e) {
                valid = false;
                System.out.println(
                    "Not an integer. Please input a number from -" + limit + " to " + limit + ".");
            }
        }

        return output;
    }


    /**
     * Has the user input an integer with a default limit of 999,999,999
     *
     * @param prompt
     *     The prompt that the function will output
     * @return The integer that the user inputted
     */
    public int inputInt(String prompt) {
        return inputInt(prompt, 999999999);
    }
}

