//Lucas Abiy 10/7/26

package proj2.collectibles;

import java.util.Scanner;

public class Stamp extends Collectible {

    private int age;
    private String printLocation;
    private boolean misprint;


    /**
     * Creates a Stamp with default values.
     */
    public Stamp() {
        super();
        age = 0;
        printLocation = "";
        misprint = false;
    }


    /**
     * Creates a Stamp with values specified by the user
     *
     * @param name
     *     name of the stamp
     * @param desc
     *     short description
     * @param value
     *     monetary value in dollars
     * @param age
     *     how old is the stamp
     * @param printLocation
     *     where was the stamp made
     * @param misprint
     *     whether or not the stamp was misprinted
     */
    public Stamp(String name,
        String desc,
        double value,
        int age,
        String printLocation,
        boolean misprint) {
        super(name, desc, value);
        this.age = age;
        this.printLocation = printLocation;
        this.misprint = misprint;
    }


    /**
     * Returns the age of the stamp.
     *
     * @return the age
     */
    public int getAge() {
        return age;
    }


    /**
     * Sets the age of the stamp.
     *
     * @param age
     *     the age of the stamp
     */
    public void setAge(int age) {
        if (age < 0) {
            System.out.println("Age cannot be negative.");
            return;
        }

        this.age = age;
    }


    /**
     * Returns the print location of the stamp.
     *
     * @return the print location
     */
    public String getPrintLocation() {
        return printLocation;
    }


    /**
     * Sets the print location of the stamp.
     *
     * @param location
     *     the location where the stamp was printed
     */
    public void setPrintLocation(String location) {
        if (location == null || location.isEmpty()) {
            System.out.println("Print location cannot be empty.");
            return;
        }

        printLocation = location;
    }


    /**
     * Returns whether the stamp is a misprint.
     *
     * @return true if the stamp is a misprint
     */
    public boolean getMisprint() {
        return misprint;
    }


    /**
     * Sets whether the stamp is a misprint.
     *
     * @param misprint
     *     whether the stamp is a misprint
     */
    public void setMisprint(boolean misprint) {
        this.misprint = misprint;
    }


    /**
     * Returns a short description of the stamp.
     *
     * @return stamp information
     */
    public String info() {
        return "Stamp - " + printLocation;
    }


    /**
     * Returns all information about the stamp.
     *
     * @return stamp information as a String
     */
    @Override
    public String toString() {
        return String.format("--- Stamp ---\n" + "Name: " + getName()
                + "\n" + "Description: " + getDesc() + "\n" + "Value: " + getValue()
                + "\n" + "Age: " + age + "\n" + "Pirnt Location: " + printLocation
                + "\n" + "Misprint status: " + String.valueOf(misprint) + "\n");
    }


    /**
     * Allows the user to edit the stamp's fields. Validates menu choices and
     * user input.
     */
    public void edit() {
        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while (option < 1 || option > 3) {
            System.out.println("1. Edit age");
            System.out.println("2. Edit print location");
            System.out.println("3. Edit misprint");
            System.out.print("Choose an option (1-3): ");

            String input = scanner.nextLine().trim();

            try {
                option = Integer.parseInt(input);

                if (option < 1 || option > 3) {
                    System.out.println("Invalid choice. Enter 1, 2, or 3.");
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number.");
            }
        }

        if (option == 1) {
            int newAge = -1;

            while (newAge < 0) {
                System.out.print("Enter age: ");
                String input = scanner.nextLine().trim();

                try {
                    newAge = Integer.parseInt(input);

                    if (newAge < 0) {
                        System.out.println("Age cannot be negative.");
                    }
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid input. Enter a whole number.");
                }
            }

            setAge(newAge);
        } else
            if (option == 2) {
                String newLocation;

                do {
                    System.out.print("Enter print location: ");
                    newLocation = scanner.nextLine();

                    if (newLocation.trim().isEmpty()) {
                        System.out.println("Print location cannot be empty.");
                    }
                }
                while (newLocation.trim().isEmpty());

                setPrintLocation(newLocation);
            } else {
                String input;

                do {
                    System.out.print(
                        "Is this stamp a misprint? (true/false): ");
                    input = scanner.nextLine().trim();

                    if (!input.equalsIgnoreCase(
                        "true") && !input.equalsIgnoreCase("false")) {
                        System.out.println(
                            "Invalid input. Enter true or false.");
                    }
                }
                while (!input.equalsIgnoreCase(
                    "true") && !input.equalsIgnoreCase("false"));

                setMisprint(Boolean.parseBoolean(input));
            }
    }
}
