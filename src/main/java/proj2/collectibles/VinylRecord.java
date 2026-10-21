package proj2.collectibles;

import java.time.Year;
import java.util.Scanner;

/**
 * Represents a vinyl record in a collectibles collection.
 *
 * @author Lucas Abiy
 * @version 2026-10-20
 */
public class VinylRecord extends Collectible {

    private String artist;
    private String album;
    private int releaseYear;

    /**
     * Creates a vinyl record with default values.
     */
    public VinylRecord() {
        super();
        artist = "";
        album = "";
        releaseYear = 0;
    }

    /**
     * Creates a vinyl record with the specified values.
     *
     * @param name
     *            the record name
     * @param desc
     *            the record description
     * @param value
     *            the record's monetary value
     * @param artist
     *            the performing artist
     * @param album
     *            the album title
     * @param releaseYear
     *            the album's release year
     */
    public VinylRecord(
        String name,
        String desc,
        double value,
        String artist,
        String album,
        int releaseYear) {

        super(name, desc, value);

        this.artist = "";
        this.album = "";
        this.releaseYear = 0;

        setArtist(artist);
        setAlbum(album);
        setReleaseYear(releaseYear);
    }

    /**
     * Gets the artist.
     *
     * @return the artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Sets the artist.
     *
     * @param artist
     *            the artist to set
     */
    public void setArtist(String artist) {
        if (artist != null && !artist.trim().isEmpty()
            && artist.length() <= 1000) {
            this.artist = artist;
        }
        else {
            System.out.println("Invalid artist.");
        }
    }

    /**
     * Gets the album title.
     *
     * @return the album title
     */
    public String getAlbum() {
        return album;
    }

    /**
     * Sets the album title.
     *
     * @param album
     *            the album title to set
     */
    public void setAlbum(String album) {
        if (album != null && !album.trim().isEmpty()
            && album.length() <= 1000) {
            this.album = album;
        }
        else {
            System.out.println("Invalid album title.");
        }
    }

    /**
     * Gets the release year.
     *
     * @return the release year
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * Sets the release year.
     *
     * @param releaseYear
     *            the release year to set
     */
    public void setReleaseYear(int releaseYear) {
        if (releaseYear >= 0
            && releaseYear <= Year.now().getValue()) {
            this.releaseYear = releaseYear;
        }
        else {
            System.out.println("Invalid release year.");
        }
    }

    /**
     * Returns a brief description of the record.
     *
     * @return the record information
     */
    @Override
    public String info() {
        return getName() + " - $" + getValue()
            + " - Artist: " + artist
            + " - Album: " + album;
    }

    /**
     * Allows the user to edit the record.
     */
    @Override
    public void edit() {
        Scanner input = new Scanner(System.in);
        boolean editing = true;

        while (editing) {
            System.out.println("What would you like to edit?");
            System.out.println("1. Name");
            System.out.println("2. Description");
            System.out.println("3. Value");
            System.out.println("4. Artist");
            System.out.println("5. Album");
            System.out.println("6. Release Year");
            System.out.println("7. Finish editing");

            String choice = input.nextLine();

            try {
                switch (Integer.parseInt(choice)) {
                    case 1:
                        System.out.println("Enter the new name:");
                        setName(input.nextLine());
                        break;

                    case 2:
                        System.out.println("Enter the new description:");
                        setDesc(input.nextLine());
                        break;

                    case 3:
                        System.out.println("Enter the new value:");
                        setValue(Double.parseDouble(input.nextLine()));
                        break;

                    case 4:
                        System.out.println("Enter the artist:");
                        setArtist(input.nextLine());
                        break;

                    case 5:
                        System.out.println("Enter the album title:");
                        setAlbum(input.nextLine());
                        break;

                    case 6:
                        System.out.println("Enter the release year:");
                        setReleaseYear(Integer.parseInt(input.nextLine()));
                        break;

                    case 7:
                        editing = false;
                        break;

                    default:
                        System.out.println("Invalid menu choice.");
                        break;
                }
            }
            catch (NumberFormatException exception) {
                System.out.println(
                    "Invalid number. Please enter a valid number.");
            }
        }
    }

    /**
     * Returns a string representation of the record.
     *
     * @return the record as a string
     */
    @Override
    public String toString() {
        return String.format("--- Vinyl Record ---\n" + "Name: " + getName()
                + "\n" + "Description: " + getDesc() + "\n" + "Value: " + getValue()
                + "\n" + "Artist: " + artist + "\n" + "Album: " + album
                + "\n" + "Release Year: " + String.valueOf(releaseYear) + "\n");
    }
}