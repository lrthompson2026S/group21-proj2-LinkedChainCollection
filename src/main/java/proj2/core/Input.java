package proj2.core;

import java.util.Scanner;

public class Input {

    private static final Scanner scan = new Scanner(System.in);

    public static int options(String prompt, String[] options) {
        System.out.println(prompt);
        for(int i = 0; i < options.length; i++){
            System.out.println("[" + i + "] " + options[i]);
        }

        boolean valid = false;
        int optionIndex = -1;
        while(!valid) {
            try {
                valid = true;
                String input = scan.nextLine();
                optionIndex = Integer.parseInt(input);
                if((optionIndex < 0) || (optionIndex > options.length - 1)){
                    valid = false;
                    System.out.println("Invalid input. Please input a number between 0 and " + (options.length - 1) + ".");
                }
            } catch (Exception e) {
                valid = false;
                System.out.println("Invalid input. Please input a number between 0 and " + (options.length - 1) + ".");
                //throw new RuntimeException(e);
            }
        }

        return optionIndex;
    }

    public static String inputString(String prompt, int charLimit){
        Scanner scan = new Scanner(System.in);
        System.out.println(prompt);
        boolean valid = false;
        String output = "";
        while(!valid) {
            valid = true;
            output = scan.nextLine();
            if(output.length() > charLimit){
                valid = false;
                System.out.println("That String is too large! Please enter a String of less than " + charLimit + " characters.");
            }
        }

        return output;
    }

    public static String inputString(String prompt){   //Overloaded method for a default character limit of 1000
        return inputString(prompt, 1000);
    }


    public static double inputDouble(String prompt, int limit){
        Scanner scan = new Scanner(System.in);
        String input = "";
        double output = 0;
        boolean valid = false;

        while(!valid) {
            try {
                valid = true;
                input = scan.nextLine();
                output = Double.parseDouble(input);
                if(Math.abs(output) > limit) {
                    System.out.println("Invalid input. Please input a number from -" + limit + " to " + limit + ".");
                    valid = false;
                }
            } catch (NumberFormatException e) {
                valid = false;
                System.out.println("Not a double. Please input a number from -" + limit + " to " + limit + ".");
            }
        }

        return output;
    }

    public static double inputDouble(String prompt){
        return inputDouble(prompt, 999999999);
    }
}

