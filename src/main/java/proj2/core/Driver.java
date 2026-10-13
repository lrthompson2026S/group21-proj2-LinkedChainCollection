package proj2.core;

import proj2.collectibles.ActionFigure;
import proj2.collectibles.Collectible;
import proj2.collectibles.Stamp;
import proj2.collectibles.TradingCard;
import proj2.collections.Collection;

import java.util.NoSuchElementException;

public class Driver {
    private static Collectible hover = null;
    private static Collection<Collectible> currentCollection = null;
    private static final String[] collectibleTypes = {"Action Figure", "Stamp", "Trading Card"};


    public static void run(String[] args)
    {
        startMenu();
    }

    public static void startMenu(){
        String prompt = "Would you like to start a new collection?";
        String[] options = {"Start a new collection.", "Exit"};
        int choice = -1;
        choice = Input.options(prompt, options);

        switch(choice) {
            case 0:
                //create a new collection and call main menu
                currentCollection = new Collection<Collectible>();
                mainMenu();
                break;
            case 1:
                break;
        }
    }

    public static void mainMenu(){
        boolean endLoop = false;
        String[] defaultOptions = {"Exit", "Add a collectible", "Remove a collectible", "Edit a collectible", "Display collection"};
        String[] emptyCollectionOptions = {"Exit", "Add a collectible"};
        while(!endLoop) {
            String prompt = "What would you like to do?";
            String[] options = defaultOptions;
            if(currentCollection.isEmpty()){
                options = emptyCollectionOptions;
                System.out.println("Current collection is empty");
            }
            int choice = -1;
            choice = Input.options(prompt, options);
            switch(choice){
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
            }
        }
    }

    public static void addCollectible(){
        //Create a collectible
        //Add that to the front of the collection
        String prompt = "What kind of collectible would you like to add?";
        int choice = -1;
        choice = Input.options(prompt, collectibleTypes);
        String name = Input.inputString("What is the name of your collectible?");
        String desc = Input.inputString("Please add a short description.");
        double value = Input.inputDouble("What is the value of your collectible?");
        Collectible entry = null;
        //Yes this switch is hard coded based on collectible types don't judge me
        //Starts at 0 and goes alphabetically
        switch(choice){
            case 0:
                String series = Input.inputString("What series is your action figure?");
                String[] options = {"Sealed", "Opened"};
                int sealedInt = Input.options("Is your figure still sealed?", options);
                boolean sealed = sealedInt == 0;
                String character = Input.inputString("What character is the action figure of?");
                entry = new ActionFigure(name, desc, value, series, sealed, character);

                break;
            case 1:
                int age = Input.inputInt("How old is the stamp?");
                String printLocation = Input.inputString("Where was the stamp printed?");
                String[] misprintOptions = {"Normal", "Misprint"};
                int misprintInt = Input.options("Was your stamp misprinted?", misprintOptions);
                boolean misprint = misprintInt == 1;
                entry = new Stamp(name, desc, value, age, printLocation, misprint);

                break;
            case 2:
                String type = Input.inputString("What type of trading card is it?");
                //grade should be a double
                int grade = Input.inputInt("What is the grade of the trading card?");
                int printYear = Input.inputInt("What year was it printed?");
                entry = new TradingCard(name, desc, value, type, grade, printYear);

                break;
        }
        currentCollection.add(entry);
    }

    public static Collectible selectCollectible(){
        if(currentCollection.isEmpty()){
            throw new NoSuchElementException();
        }
        //use toArray, getValue, and info to create options
        String prompt = "Select a collectible";
        Object[] tempOptions = currentCollection.toArray();
        Collectible[] collectibles = new Collectible[tempOptions.length];
        for(int i = 0; i < collectibles.length; i++){
            collectibles[i] = (Collectible) tempOptions[i];
        }
        String[] options = new String[collectibles.length];
        for(int i = 0; i < collectibles.length; i++){
            options[i] = collectibles[i].getName();
        }

        int choice = -1;
        choice = Input.options(prompt, options);
        return collectibles[choice];
    }

    public static void removeCollectible(){
        if(currentCollection.isEmpty()){
            throw new NoSuchElementException();
        }
        currentCollection.remove(hover);
    }

    public static void editCollectible(){
        if(hover != null){
            hover.edit();
        }
    }

    public static void displayCollection(){
        if(currentCollection.isEmpty()){
            throw new NoSuchElementException();
        }

        Object[] tempOptions = currentCollection.toArray();
        Collectible[] collectibles = new Collectible[tempOptions.length];
        for(int i = 0; i < collectibles.length; i++) {
            collectibles[i] = (Collectible) tempOptions[i];
            System.out.println(collectibles[i]);
        }
    }
}
