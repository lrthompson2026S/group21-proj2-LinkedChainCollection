package proj2.collectibles;

import proj2.core.Input;

// -------------------------------------------------------------------------
/**
 * This is the trading card class, it extends Collectible. It is what a user
 * should use if they want to create a new trading card class.
 * 
 * @author Elijah O'Donoghue
 * @version Oct 7, 2026
 */
public class TradingCard extends Collectible {
    private String type;
    private int grade;
    private int printYear;

    // ----------------------------------------------------------
    /**
     * Create a new TradingCard object.
     */
    public TradingCard() {
        super();
        this.type = "Unknown";
        this.grade = 0;
        this.printYear = 0;
    }


    // ----------------------------------------------------------
    /**
     * Create a new TradingCard object.
     * 
     * @param name
     *            String name of card
     * @param desc
     *            String desc of card
     * @param value
     *            Int value of card
     * @param type
     *            String type of card
     * @param grade
     *            Int grade of card
     * @param printYear
     *            Int year card was printed
     */
    public TradingCard(
        String name,
        String desc,
        double value,
        String type,
        int grade,
        int printYear) {
        super(name, desc, value);
        setType(type);
        setGrade(grade);
        setPrintYear(printYear);
    }


    // ----------------------------------------------------------
    /**
     * sets the type of trading card
     * 
     * @param type
     *            String type of trading card
     */
    public void setType(String type) {
        this.type = type;
    }


    // ----------------------------------------------------------
    /**
     * sets the grade of the trading card
     * 
     * @param grade
     *            Int grade of trading card
     */
    public void setGrade(int grade) {
        this.grade = grade;
    }


    // ----------------------------------------------------------
    /**
     * sets the year of the trading card print
     * 
     * @param year
     *            Int year
     */
    public void setPrintYear(int year) {
        this.printYear = year;
    }


    // ----------------------------------------------------------
    /**
     * Returns the grade of the card, the grade is -1 if the card is ungraded
     * 
     * @return grade
     *         int grade
     */
    public int getGrade() {
        return this.grade;
    }


    // ----------------------------------------------------------
    /**
     * Returns the type of the card (i.e. Pokemon, Topps Baseball, Magic,
     * YuGiOh)
     * 
     * @return String type of the card
     */
    public String getType() {
        return this.type;
    }


    // ----------------------------------------------------------
    /**
     * Returns the the year the card was printed
     * 
     * @return int year card was printed
     */
    public int getPrintYear() {
        return this.printYear;
    }


    @Override
    public String info() {
        String information = this.getName() + " - $" + String.valueOf(this
            .getValue()) + " - Type: " + this.type;
        return information;
    }


    @Override
    public String toString() {
        return String.format("--- Trading Card ---\n" + "Name: " + getName()
            + "\n" + "Description: " + getDesc() + "\n" + "Value: " + getValue()
            + "\n" + "Type: " + type + "\n" + "Grade: " + String.valueOf(grade)
            + "\n" + "Print Year: " + String.valueOf(printYear) + "\n");
    }


    @Override
    public void edit() {
        System.out.println("edit me when you have input class working");
    }
}

/*
 * + setGrade(int grade): void
 * + getGrade(): int
 * + setType(String type): void
 * + getType(): String
 * + setPrintYear(int year): void
 * + getPrintYear(): int
 * + info(): String
 * + C TradingCard()
 * + toString(): String
 * + edit(): void
 */
