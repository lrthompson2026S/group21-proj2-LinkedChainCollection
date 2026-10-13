package proj2.core;

import proj2.collectibles.Collectible;
import proj2.collectibles.TradingCard;
import proj2.collections.Collection;

public class Driver {
    private static int hover = -1;
    private static Collection<Collectible> currentCollection = null;
    private static final String[] collectibleTypes = {"Action Figure", "Stamp", "Trading Card"};


    public static void run(String[] args)
    {
        startMenu();
    }

    public static void startMenu(){
        String prompt = "Would you like to start a new collection?";
        String[] options = {"Start a new collction.", "Exit"};
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
        while(!endLoop) {
            String prompt = "What would you like to do?";
            String[] defaultOptions = {"Exit", "Add a collectible", "Remove a collectible", "Edit a collectible", "Display collection"};
            String[] emptyCollectionOptions = {"Exit", "Add a collectible"};
            String[] options = defaultOptions;
            if(currentCollection.isEmpty()){
                options = emptyCollectionOptions;
            }
            int choice = -1;
            choice = Input.options(prompt, options);
            switch(choice){
                case 0:
                    endLoop = true;
                    break;
                case 1:
                    //
                    break;
                case 2:
                    hover = selectCollectible();
                    //remove that collectible
                    break;
                case 3:
                    hover = selectCollectible();
                    //edit that collectible;
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

        //Yes this switch is hard coded based on collectible types don't judge me
        //Starts at 0 and goes alphabetically
        switch(choice){
            case 0:
                //ActionFigure entry = new ActionFigure(PUT THE FIELDS IN HERE);
                break;
            case 1:
                //yeah, fill this one out too
                break;
            case 2:
                String type = Input.inputString("What type of trading card is it?");
                int grade = Input.inputInt("What is the grade of the trading card?");
                int printYear = Input.inputInt("What year was it printed?");
                TradingCard entry = new TradingCard(name, desc, value, type, grade, printYear);
                break;
        }
    }

    public static int selectCollectible(){
        //use toArray, getValue, and info to create options
        String prompt = "Select a collectible";
        Object[] tempOptions = currentCollection.toArray();
        Collectible[] collectibles = (Collectible[]) tempOptions;
        String[] options = new String[collectibles.length];
        for(int i = 0; i < collectibles.length; i++){
            options[i] = collectibles[i].getName();
        }

        int choice = -1;
        choice = Input.options(prompt, options);
        return choice;
    }

    public static void editCollectible(){

    }

    public static void displayCollection(){

    }
}
