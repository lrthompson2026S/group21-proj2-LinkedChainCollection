package proj2.core;

import proj2.collections.Collection;

import java.io.*;
import java.io.File;

/**
 * This class handles saving and loading files
 */
public class Save {

    private static final String folderName = "Saves";

    /**
     * Selects a file from the saves folder
     *
     * @return The chosen file
     */
    public static String selectSave(){
        String prompt = "Please select a save file.";
        File folder = new File(folderName);
        if(!folder.exists()){
            System.out.println("Save folder could not be found!");
            return null;
        }
        File[] fileList = folder.listFiles();
        if(fileList == null){
            throw new NullPointerException();
        } else if(fileList.length == 0){
            return null;
        }

        String[] options = new String[fileList.length];
        for(int i = 0; i < fileList.length; i++){
            options[i] = fileList[i].getName();
        }
        int selection = (new Input()).options(prompt, options);

        return options[selection];
    }

    /**
     * Save an object to the saves folder as a .ser file
     *
     * @param target   The object to be saved
     * @param filename The name for the file to have, not including ".ser"
     */
    //TODO: add validation for filename
    public static void save(Object target, String filename){
        System.out.println("Saving object in file " + filename + "...");
        String filepath = folderName + File.separator + filename + ".ser";

        //validate the folder exists
        File folder = new File(folderName);
        if (!folder.exists()) {
            System.out.println("Saves folder cannot be located, creating new saves folder...");
            folder.mkdirs(); // Creates the folder (and parent dirs if needed)
        }

        try {
            FileOutputStream file = new FileOutputStream(filepath);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(target);
            out.close();
            file.close();
            System.out.println("Saved!");
        } catch (IOException ioe) {
            System.out.println("An error occurred during serialization");
            System.out.println(ioe.getMessage());
        }
    }

    /**
     * Returns an object from a specified filename
     * Only reads the "Saves" folder
     *
     * @param filename The name of the file that the object was saved in
     * @return The object that was saved
     */
    public static Object load(String filename){
        if(filename == null){
            return null;
        }
        String filepath = folderName + File.separator + filename;

        System.out.println("Loading " + filename + "...");
        Object loadedObject = null;
        try {
            FileInputStream file = new FileInputStream(filepath);
            ObjectInputStream in = new ObjectInputStream(file);
            loadedObject = in.readObject();
            in.close();
            file.close();
            System.out.println("Load successful");

        } catch (IOException ioe) {
            System.out.println("IOException is caught");
            System.out.println(ioe.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }
        if(loadedObject == null){
            System.out.println("Warning: No save detected, returning null.");
        }

        return loadedObject;
    }
}
