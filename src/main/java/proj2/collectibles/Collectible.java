package proj2.collectibles;

// -------------------------------------------------------------------------
/**
 * This is the collectible class for our project. Each item within the linked
 * list is a collectible object. Items like Trading card, Stamp, or action
 * figure extend this.
 * 
 * @author Elijah O'Donoghue
 * @version Oct 7, 2026
 */
public abstract class Collectible {
    private String name;
    private String desc;
    private double value;

    // ----------------------------------------------------------
    /**
     * This creates the Collectible object and uses default values
     */
    public Collectible() {
        this.name = "Unknown Collectible";
        this.desc = "None";
        this.value = 0.0;
    }


    // ----------------------------------------------------------
    /**
     * Create a new Collectible object.
     * 
     * @param name
     *            String name of the collectible item, between 1 and 1000 chars
     * @param desc
     *            String desc of the collectible item
     * @param value
     *            Double value of the collectible item
     */
    public Collectible(String name, String desc, double value) {
        setName(name);
        setDesc(desc);
        setValue(value);
    }


    // ----------------------------------------------------------
    /**
     * returns name
     * 
     * @return name
     */
    public String getName() {
        return this.name;
    }


    // ----------------------------------------------------------
    /**
     * sets the name of the collectible to the inputed string
     * 
     * @param name
     *            string name of the collectible
     */
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty() && name.length() <= 1000) {
            this.name = name;
        }
        else {
            System.out.println(
                "Error: Name must be between 1 and 1000 characters.");
        }
    }


    // ----------------------------------------------------------
    /**
     * Sets the description of the collectible to the given string
     * 
     * @param desc
     *            String description of the collectible
     */
    public void setDesc(String desc) {
        if (desc != null && desc.length() <= 1000) {
            this.desc = desc;
        }
        else {
            System.out.println(
                "Error: Description must be 1000 characters or fewer.");
        }
    }


    // ----------------------------------------------------------
    /**
     * returns the description of the collectible
     * 
     * @return string description
     */
    public String getDesc() {
        return this.desc;
    }


    // ----------------------------------------------------------
    /**
     * returns the value of the given collectible
     * 
     * @return double value of collectible
     */
    public double getValue() {
        return this.value;

    }


    // ----------------------------------------------------------
    /**
     * Sets the value of the collectible to the given double value
     * 
     * @param value
     *            double value of the collectible
     */
    public void setValue(double value) {
        if (value >= 0.0 && value <= 9999999.0) {
            this.value = value;
        }
        else {
            System.out.println(
                "Error: Value must be non-negative and capped at $9,999,999.");
        }
    }


    // ----------------------------------------------------------
    /**
     * This method returns a one line string of information. This one line of
     * information contains the name and the value in the format [name] -
     * $[value]
     * 
     * @return string information
     */
    public String info() {
        String information = this.name + " - $" + String.valueOf(this.value);
        return information;
    }


    // ----------------------------------------------------------
    /**
     * leaves the output to the implementations of toString
     * 
     * @return String
     */
    public String view() {
        return toString();
    }


    public abstract void edit();

    @Override
    public abstract String toString();
}

/*
 * - value: double
 * - name: String
 * - desc: String
 * + setName(string): void
 * + getName(): String
 * + setDesc(String): void
 * + getDesc(): String
 * + setValue(double): void
 * + getValue(): double
 * + info(): String
 * + view(): String -> toString()
 * + edit(): void
 */
