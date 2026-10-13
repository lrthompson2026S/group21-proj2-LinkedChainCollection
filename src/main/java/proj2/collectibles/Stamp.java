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
     * @param name name of the stamp
     * @param desc short description
     * @param value monetary value in dollars
     * @param age how old is the stamp
     * @param printLocation where was the stamp made
     * @param misprint whether or not the stamp was misprinted
     */
    public Stamp(String name, String desc, double value, int age, String printLocation, boolean misprint) {
        super(name, desc, value);
        this.age = age;
        this.printLocation = printLocation;
        this.misprint = misprint;
    }

    /**
     * Sets the age of the stamp.
     *
     * @param age the age of the stamp
     */
    public void setAge(int age) {
        if (age < 0) {
            System.out.println("Age cannot be negative.");
            return;
        }

        this.age = age;
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
     * Sets the print location of the stamp.
     *
     * @param location the location where the stamp was printed
     */
    public void setPrintLocation(String location) {
        if (location == null || location.isEmpty()) {
            System.out.println("Print location cannot be empty.");
            return;
        }

        printLocation = location;
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
     * Sets whether the stamp is a misprint.
     *
     * @param misprint whether the stamp is a misprint
     */
    public void setMisprint(boolean misprint) {
        this.misprint = misprint;
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
        return "Stamp{"
                + "age=" + age
                + ", printLocation='" + printLocation + '\''
                + ", misprint=" + misprint
                + '}';
    }

    /**
     * Allows the user to edit the stamp's fields.
     */
    public void edit() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Edit age");
        System.out.println("2. Edit print location");
        System.out.println("3. Edit misprint");

        System.out.print("Choose an option: ");

        int option = scanner.nextInt();
        scanner.nextLine();

        if (option == 1) {
            System.out.print("Enter age: ");
            int newAge = scanner.nextInt();
            scanner.nextLine();
            setAge(newAge);
        }
        else if (option == 2) {
            System.out.print("Enter print location: ");
            String newLocation = scanner.nextLine();
            setPrintLocation(newLocation);
        }
        else if (option == 3) {
            System.out.print("Is this stamp a misprint? (true/false): ");
            boolean newMisprint = scanner.nextBoolean();
            setMisprint(newMisprint);
        }
        else {
            System.out.println("Invalid menu choice.");
        }
    }
}