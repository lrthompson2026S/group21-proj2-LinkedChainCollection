//Lucas Abiy 10/7/26

package proj2.collectibles;

import java.util.Scanner;

public class ActionFigure extends Collectible {

    private String series;
    private boolean sealed;
    private String character;


    /**
     * Creates an ActionFigure with default values.
     */
    public ActionFigure() {
        super();
        series = "";
        sealed = false;
        character = "";
    }


    /**
     * Creates an ActionFigure with values specified by the user
     *
     * @param name
     *     name of the action figure
     * @param desc
     *     short description
     * @param value
     *     monetary value in dollars
     * @param series
     *     what series is the figure a part of
     * @param sealed
     *     if the figure is sealed or not
     * @param character
     *     what character the figure is depicting
     */
    public ActionFigure(String name,
        String desc,
        double value,
        String series,
        boolean sealed,
        String character) {
        super(name, desc, value);
        this.series = series;
        this.sealed = sealed;
        this.character = character;
    }


    /**
     * Returns the series of the action figure.
     *
     * @return the series
     */
    public String getSeries() {
        return series;
    }


    /**
     * Sets the series of the action figure.
     *
     * @param series
     *     the series
     */
    public void setSeries(String series) {
        if (series == null || series.isEmpty()) {
            System.out.println("Series cannot be empty.");
            return;
        }

        this.series = series;
    }


    /**
     * Returns whether the action figure is sealed.
     *
     * @return true if sealed
     */
    public boolean getSealed() {
        return sealed;
    }


    /**
     * Sets whether the action figure is sealed.
     *
     * @param sealed
     *     whether the action figure is sealed
     */
    public void setSealed(boolean sealed) {
        this.sealed = sealed;
    }


    /**
     * Returns the character represented by the action figure.
     *
     * @return the character
     */
    public String getCharacter() {
        return character;
    }


    /**
     * Sets the character represented by the action figure.
     *
     * @param character
     *     the character
     */
    public void setCharacter(String character) {
        if (character == null || character.isEmpty()) {
            System.out.println("Character cannot be empty.");
            return;
        }

        this.character = character;
    }


    /**
     * Returns a short description of the action figure.
     *
     * @return action figure information
     */
    public String info() {
        return "Action Figure - " + character;
    }


    /**
     * Returns all information about the action figure.
     *
     * @return action figure information as a String
     */
    @Override
    public String toString() {
        return "ActionFigure{" + "series='" + series + '\'' + ", sealed=" + sealed + ", character='" + character + '\'' + '}';
    }


    /**
     * Allows the user to edit the action figure's fields. Validates menu
     * choices and user input.
     */
    public void edit() {
        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while (option < 1 || option > 3) {
            System.out.println("1. Edit series");
            System.out.println("2. Edit sealed status");
            System.out.println("3. Edit character");
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
            String newSeries;

            do {
                System.out.print("Enter series: ");
                newSeries = scanner.nextLine();

                if (newSeries.trim().isEmpty()) {
                    System.out.println("Series cannot be empty.");
                }
            }
            while (newSeries.trim().isEmpty());

            setSeries(newSeries);
        } else
            if (option == 2) {
                String input;

                do {
                    System.out.print(
                        "Is the action figure sealed? (true/false): ");
                    input = scanner.nextLine().trim();

                    if (!input.equalsIgnoreCase(
                        "true") && !input.equalsIgnoreCase("false")) {
                        System.out.println(
                            "Invalid input. Enter true or false.");
                    }
                }
                while (!input.equalsIgnoreCase(
                    "true") && !input.equalsIgnoreCase("false"));

                setSealed(Boolean.parseBoolean(input));
            } else {
                String newCharacter;

                do {
                    System.out.print("Enter character: ");
                    newCharacter = scanner.nextLine();

                    if (newCharacter.trim().isEmpty()) {
                        System.out.println("Character cannot be empty.");
                    }
                }
                while (newCharacter.trim().isEmpty());

                setCharacter(newCharacter);
            }
    }
}
