package proj2.core;


public class Driver {
    private static int hover = -1;
    private Collection<Collectible> currentCollection = null;

    public static void run(String[] args)
    {
        
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
                    selectCollectible();
                    //remove that collectible
                    break;
                case 3:
                    selectCollectible();
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
    }

    public static int selectCollectible(){
        //use toArray, getValue, and info to create options
        String prompt = "Select a collectible";
        String[] options = currentCollection.toArray();
        int choice = -1;
        choice = Input.options(prompt, options);
        hover = choice;
    }

    public static void editCollectible(){

    }

    public static void displayCollection(){

    }
}
