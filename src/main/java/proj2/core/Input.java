package proj2.core;

import java.util.Scanner;

public class Input {
    private Scanner scan = new Scanner(System.in);;

    public int options(String prompt, String[] options) {
        System.out.println(prompt);
        for(int i = 0; i < options.length; i++){
            System.out.println("[" + i + "] " + options[i]);
        }
        int optionIndex = -1;

        while((optionIndex < 0) || (optionIndex > options.length - 1)) {
            try {
                String input = scan.nextLine();
                optionIndex = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("Invalid input. Please input a number from 0 - " + (options.length - 1) + ".");
                throw new RuntimeException(e);
            }
        }

        return optionIndex;
    }

    public String inputString(String prompt, int charLimit = 1000){
        System.out.println(prompt);
        boolean valid = false;
        String output = "";
        while(!valid) {
            valid = true;
            output = scan.nextLine();
            if(output.length() > charLimit){
                System.out.println("That String is too large! Please enter a String of less than " + charLimit + " characters.");
                valid = false;
            }
        }

        return output;
    }

    public double inputDouble(String prompt, int limit){
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
            } catch (Exception e) {
                System.out.println("Invalid input. Please input a number from -" + limit + " to " + limit + ".");
                throw new RuntimeException(e);
            }
        }

        return output;
    }
}
