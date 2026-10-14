/**
 * Rin Hall 9069-13080 10/13/2026
 */

package proj2.core;

import proj2.collectibles.ActionFigure;
import proj2.collectibles.Collectible;
import proj2.collectibles.Stamp;
import proj2.collectibles.TradingCard;
import proj2.collections.Collection;

import java.io.*;

import java.util.NoSuchElementException;

/**
 * This class is what controls the collection and interfaces with the user to edit it
 */
public class Driver implements Serializable{
    private static final String[] collectibleTypes =
        { "Action Figure", "Stamp", "Trading Card" };
    private static Collectible hover = null;
    private static Collection currentCollection = null;

    /**
     * Run is practically main, but so as not to store logic in main, we made a new method for it
     * @param args default param
     */
    public static void run(String[] args) {
        startMenu();
    }

    /**
     * Start menu allows the user to create a new collection
     */
    public static void startMenu() {
        String prompt = "Would you like to start a new collection?";
        String[] options = { "Exit", "Start a new collection.", "Load a preexisting collection" };
        int choice;
        choice = (new Input()).options(prompt, options);

        switch (choice) {
            case 0:
                break;
            case 1:
                currentCollection = new Collection<>();
                mainMenu();
                break;
            case 2:
                loadCollection();
                mainMenu();
                break;
        }
    }


    /**
     * Main menu is the primary control loop of the program. Once called, it enters a loop that allows the user to
     * add, remove, edit, or display the collection. It only allows users to remove from, edit, or display the
     * collection if the collection is not empty.
     */
    public static void mainMenu() {
        boolean endLoop = false;
        String[] defaultOptions =
            { "Exit", "Add a collectible", "Remove a collectible",
                "Edit a collectible", "Display collection", "Save collection" };
        String[] emptyCollectionOptions = { "Exit", "Add a collectible" };
        while (!endLoop) {
            String prompt = "What would you like to do?";
            String[] options = defaultOptions;
            if (currentCollection.isEmpty()) {
                options = emptyCollectionOptions;
                System.out.println("Current collection is empty");
            }
            int choice;
            choice = (new Input()).options(prompt, options);
            switch (choice) {
                case 0:
                    endLoop = true;
                    break;
                case 1:
                    addCollectible();
                    break;
                case 2:
                    hover = selectCollectible();
                    removeCollectible();
                    break;
                case 3:
                    hover = selectCollectible();
                    editCollectible();
                    break;
                case 4:
                    displayCollection();
                    break;
                case 5:
                    saveCollection();
            }
        }
    }


    /**
     * Prompts a user for a type of collectible, creates that kind of collectible.
     * Add that created collectible to the current collection.
     */
    public static void addCollectible() {
        //Create a collectible
        //Add that to the front of the collection
        String prompt = "What kind of collectible would you like to add?";
        int choice;
        choice = (new Input()).options(prompt, collectibleTypes);
        String name =
            (new Input()).inputString("What is the name of your collectible?");
        String desc =
            (new Input()).inputString("Please add a short description.");
        double value =
            (new Input()).inputDouble("What is the value of your collectible?");
        Collectible entry = null;
        //Yes this switch is hard coded based on collectible types don't judge me
        //Starts at 0 and goes alphabetically
        switch (choice) {
            case 0:
                String series = (new Input()).inputString(
                    "What series is your action figure?");
                String[] options = { "Sealed", "Opened" };
                int sealedInt =
                    (new Input()).options("Is your figure still sealed?",
                        options);
                boolean sealed = sealedInt == 0;
                String character = (new Input()).inputString(
                    "What character is the action figure of?");
                entry = new ActionFigure(name, desc, value, series, sealed,
                    character);

                break;
            case 1:
                int age = (new Input()).inputInt("How old is the stamp?");
                String printLocation =
                    (new Input()).inputString("Where was the stamp printed?");
                String[] misprintOptions = { "Normal", "Misprint" };
                int misprintInt =
                    (new Input()).options("Was your stamp misprinted?",
                        misprintOptions);
                boolean misprint = misprintInt == 1;
                entry =
                    new Stamp(name, desc, value, age, printLocation, misprint);

                break;
            case 2:
                String type = (new Input()).inputString(
                    "What type of trading card is it?");
                //grade should be a double
                int grade = (new Input()).inputInt(
                    "What is the grade of the trading card?");
                int printYear =
                    (new Input()).inputInt("What year was it printed?");
                entry =
                    new TradingCard(name, desc, value, type, grade, printYear);

                break;
        }
        currentCollection.add(entry);
    }


    /**
     * Choose a collectible from the current collection
     *
     * @return the collectible the user selected
     */
    public static Collectible selectCollectible() {
        if (currentCollection.isEmpty()) {
            throw new NoSuchElementException();
        }
        //use toArray, getValue, and info to create options
        String prompt = "Select a collectible";
        Object[] tempOptions = currentCollection.toArray();
        Collectible[] collectibles = new Collectible[tempOptions.length];
        for (int i = 0; i < collectibles.length; i++) {
            collectibles[i] = (Collectible)tempOptions[i];
        }
        String[] options = new String[collectibles.length];
        for (int i = 0; i < collectibles.length; i++) {
            options[i] = collectibles[i].getName();
        }

        int choice;
        choice = (new Input()).options(prompt, options);
        return collectibles[choice];
    }


    /**
     * Removes the currently selected collectible from the collection
     */
    public static Collectible removeCollectible() {
        if (currentCollection.isEmpty()) {
            throw new NoSuchElementException();
        }
        Object removed = currentCollection.remove(hover);
        return (Collectible)removed;
    }


    /**
     * Calls the edit() function of the currently selected collectible
     */
    public static void editCollectible() {
        if (hover != null) {
            hover.edit();
        }
    }


    /**
     * Displays the toString() of every collectible in the current collection
     */
    public static void displayCollection() {
        if (currentCollection.isEmpty()) {
            throw new NoSuchElementException();
        }

        Object[] tempOptions = currentCollection.toArray();
        Collectible[] collectibles = new Collectible[tempOptions.length];
        for (int i = 0; i < collectibles.length; i++) {
            collectibles[i] = (Collectible)tempOptions[i];
            System.out.println(collectibles[i]);
        }
        System.out.println();
    }

    public static void loadCollection(){
        System.out.println("Loading collection");
        String filename = "serialTest.ser";
        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);
            currentCollection = (Collection) in.readObject();
            in.close();
            file.close();
            System.out.println("Object has been deserialized");

        } catch (IOException ex) {
            System.out.println("IOException is caught");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }
    }

    public static void saveCollection(){
        String filename = "serialTest.ser";
        Collection serialize = currentCollection;
        try {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(serialize);
            out.close();
            file.close();
            System.out.println("Collection has been serialized");

        } catch (IOException ioe) {
            System.out.println("An error occurred during serialization");
            System.out.println(ioe.getMessage());
        }
    }
}
