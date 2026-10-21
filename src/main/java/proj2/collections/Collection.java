package proj2.collections;

import proj2.collectibles.ActionFigure;
import proj2.collectibles.Collectible;
import proj2.collectibles.Stamp;
import proj2.collectibles.TradingCard;
import proj2.core.Input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * A mutable, singly linked collection of collectibles.
 * <p>
 * Collections may contain both individual collectibles and other collections.
 * Items are inserted at the head.
 *
 * @param <T>
 *     the original collectible type parameter, retained for compatibility with
 *     existing constructors
 */
public class Collection<T extends Collectible> extends Collectible
    implements LinkedChain<Collectible> {

    private Node<Collectible> head;
    private int count;


    /**
     * Constructs an empty collection.
     */
    public Collection() {
        this("Unnamed Collection", "None");
    }


    /**
     * Constructs a named collection.
     *
     * @param name
     *     the collection name
     * @param desc
     *     the collection description
     */
    public Collection(String name, String desc) {
        super(name, desc);
        head = null;
        count = 0;
    }


    /**
     * Constructs a named collection containing items.
     *
     * @param name
     *     the collection name
     * @param desc
     *     the collection description
     * @param items
     *     the initial items
     */
    public Collection(String name,
        String desc,
        java.util.Collection<? extends Collectible> items) {
        this(name, desc);
        addAll(items);
    }


    /**
     * Constructs a collection from a Java collection.
     *
     * @param items
     *     the items to add
     */
    public Collection(java.util.Collection<? extends Collectible> items) {
        this();
        addAll(items);
    }


    /**
     * Copies another collection's metadata and items.
     *
     * @param items
     *     the collection to copy
     */
    public Collection(Collection<?> items) {
        this(items.getName(), items.getDesc());
        addAll(items);
    }


    /**
     * Constructs a collection from an array.
     *
     * @param items
     *     the items to add
     */
    public Collection(T[] items) {
        this(Arrays.asList(items));
    }


    /**
     * Adds a collectible to the head of the chain.
     *
     * @param <U>
     *     the collectible subtype
     * @param item
     *     the item to add
     */
    @Override
    public <U extends Collectible> void add(U item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item");
        }

        if (item instanceof Collection<?> child && child.containsCollectionReference(
            this)) {
            throw new IllegalArgumentException(
                "Circular subcollections are not allowed");
        }

        head = new Node<>(item, head);
        count++;
    }


    /**
     * Adds a subcollection as a single item.
     *
     * @param subcollection
     *     the collection to add
     */
    public void add(Collection<?> subcollection) {
        add((Collectible)subcollection);
    }


    /**
     * Checks whether a collection is contained by reference at any depth,
     * including itself.
     *
     * @param target
     *     the collection reference to find
     * @return true if the reference is present
     */
    private boolean containsCollectionReference(Collection<?> target) {
        if (this == target) {
            return true;
        }

        for (Collectible item : this) {
            if (item instanceof Collection<?> nested && nested.containsCollectionReference(
                target)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Adds items from a Java collection.
     *
     * @param <U>
     *     the collectible subtype
     * @param items
     *     the items to add
     */
    @Override
    public <U extends Collectible> void addAll(java.util.Collection<U> items) {

        for (U item : items) {
            add(item);
        }
    }


    /**
     * Adds items from another linked chain.
     *
     * @param <U>
     *     the collectible subtype
     * @param items
     *     the items to add
     */
    @Override
    public <U extends Collectible> void addAll(LinkedChain<U> items) {
        addAll(items.toJavaCollection());
    }


    /**
     * Checks for an equal collectible.
     *
     * @param item
     *     the item to find
     * @return true if the item is present
     */
    @Override
    public boolean contains(Collectible item) {
        if (item == null) {
            return false;
        }

        for (Collectible contained : this) {
            if (item.equals(contained)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Checks for an equal subcollection.
     *
     * @param subcollection
     *     the collection to find
     * @return true if an equal collection is present
     */
    public boolean contains(Collection<?> subcollection) {
        return contains((Collectible)subcollection);
    }


    /**
     * Checks whether all queried items are present.
     */
    @Override
    public boolean containsAll(java.util.Collection<Collectible> items) {

        for (Collectible item : items) {
            if (!contains(item)) {
                return false;
            }
        }

        return true;
    }


    /**
     * Checks items supplied by another linked chain.
     */
    @Override
    public boolean containsAll(LinkedChain<Collectible> items) {
        return containsAll(items.toJavaCollection());
    }


    /**
     * Removes the head item.
     *
     * @return the removed item, or null if empty
     */
    @Override
    public Collectible remove() {
        if (head == null) {
            return null;
        }

        Collectible oldValue = head.value;
        head = head.next;
        count--;

        return oldValue;
    }


    /**
     * Removes the first equal item from the chain.
     *
     * @param item
     *     the item to remove
     * @return the removed item, or null
     */
    @Override
    public Collectible remove(Collectible item) {
        if (item == null) {
            return null;
        }

        Node<Collectible> previous = null;
        Node<Collectible> current = head;

        while (current != null) {
            if (current.value.equals(item)) {
                if (previous == null) {
                    head = current.next;
                } else {
                    previous.next = current.next;
                }

                count--;
                return current.value;
            }

            previous = current;
            current = current.next;
        }

        return null;
    }


    /**
     * Removes one equal subcollection.
     *
     * @param subcollection
     *     the subcollection to remove
     * @return the removed subcollection, or null
     */
    public Collection<?> remove(Collection<?> subcollection) {
        return (Collection<?>)remove((Collectible)subcollection);
    }


    /**
     * Removes at most one matching item per requested item.
     */
    @Override
    public java.util.Collection<Collectible> removeAll(java.util.Collection<Collectible> items) {

        java.util.Collection<Collectible> removed = new ArrayList<>();

        if (items == null) {
            return removed;
        }

        for (Collectible item : items) {
            Collectible found = remove(item);

            if (found != null) {
                removed.add(found);
            }
        }

        return removed;
    }


    /**
     * Removes items requested by another linked chain.
     */
    @Override
    public java.util.Collection<Collectible> removeAll(LinkedChain<Collectible> items) {

        return removeAll(items.toJavaCollection());
    }


    /**
     * Removes all items.
     */
    @Override
    public void clear() {
        head = null;
        count = 0;
    }


    /**
     * Compares unordered contents, including duplicates and nested
     * collections.
     *
     * @param other
     *     the object to compare
     * @return true if the contents match
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other == null || other.getClass() != getClass()) {
            return false;
        }

        Collection<?> source = (Collection<?>)other;
        Collection<Collectible> remaining = new Collection<>(source);

        if (remaining.size() != size()) {
            return false;
        }

        java.util.Collection<Collectible> removed = remaining.removeAll(this);

        return removed.size() == size() && remaining.isEmpty();
    }


    /**
     * Returns an order-independent hash code.
     */
    @Override
    public int hashCode() {
        int hash = 0;

        for (Collectible item : this) {
            hash += item.hashCode();
        }

        return hash;
    }


    @Override
    public boolean isEmpty() {
        return count == 0;
    }


    @Override
    public int size() {
        return count;
    }


    /**
     * Creates an array of all direct items.
     *
     * @return the items in iteration order
     */
    @Override
    public Collectible[] toArray() {
        Collectible[] result = new Collectible[count];

        int i = 0;
        for (Collectible item : this) {
            result[i++] = item;
        }

        return result;
    }


    @Override
    public java.util.Collection<Collectible> toJavaCollection() {
        return Arrays.stream(toArray()).toList();
    }


    @Override
    public java.util.Iterator<Collectible> iterator() {
        return new Iterator<>(head);
    }


    /**
     * Calculates the combined value of direct items. Nested collections
     * calculate their own values.
     *
     * @return the collection's total value
     */
    @Override
    public double getValue() {
        double total = 0.0;

        for (Collectible item : this) {
            total += item.getValue();
        }

        return total;
    }


    @Override
    public void setValue(double value) {
        throw new UnsupportedOperationException(
            "A collection's value is calculated from its contents");
    }


    @Override
    public String info() {
        return super.info() + " - Items: " + size();
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("--- Collection ---\n");
        result.append("Name: ").append(getName()).append('\n');
        result.append("Description: ").append(getDesc()).append('\n');
        result.append("Value: $").append(getValue()).append('\n');
        result.append("Items: ").append(size()).append('\n');

        for (Collectible item : this) {
            result.append("- ").append(item.info()).append('\n');
        }

        return result.toString();
    }


    /**
     * Allows editing the collection and its contents.
     */
    @Override
    public void edit() {
        Input input = new Input();
        boolean editing = true;

        while (editing) {
            int choice = input.options("What would you like to do?",
                new String[] { "Finish editing", "Edit collection name",
                    "Edit collection description", "Add a collectible",
                    "Create subcollection", "Remove a collectible",
                    "Edit a collectible", "Display collection" });

            switch (choice) {
                case 0:
                    editing = false;
                    break;

                case 1:
                case 2:
                    editCommon(choice, input);
                    break;

                case 3:
                    add(createCollectible(input));
                    break;

                case 4: {
                    String name = input.inputString("Subcollection name:");

                    String desc =
                        input.inputString("Subcollection description:");

                    Collection<Collectible> child =
                        new Collection<>(name, desc);

                    add(child);

                    // Immediately enter the new subcollection
                    child.edit();
                    break;
                }

                case 5:
                case 6: {
                    if (isEmpty()) {
                        System.out.println("Collection is empty.");
                        break;
                    }

                    Collectible[] items = toArray();
                    String[] names = new String[items.length];

                    for (int i = 0; i < items.length; i++) {
                        names[i] = items[i].info();
                    }

                    int index = input.options("Select a collectible", names);

                    Collectible selected = items[index];

                    if (choice == 5) {
                        Collectible removed = remove(selected);

                        System.out.println("Removed: " + removed.info());
                    } else {
                        selected.edit();
                    }

                    break;
                }

                case 7:
                    System.out.println(this);
                    break;

                default:
                    break;
            }
        }
    }


    /**
     * Creates a collectible through user input.
     *
     * @param input
     *     the input helper
     * @return the newly created collectible
     */
    private Collectible createCollectible(Input input) {
        int type =
            input.options("What kind of collectible would you like to add?",
                new String[] { "Action Figure", "Stamp", "Trading Card" });

        String name =
            input.inputString("What is the name of your collectible?");

        String desc = input.inputString("Please add a short description.");

        double value;
        do {
            value = input.inputDouble("What is the value?", 9999999);
        }
        while (!Double.isFinite(value) || value < 0.0);

        switch (type) {
            case 0: {
                String series = input.inputString("What series is the figure?");

                boolean sealed = input.options("Is it sealed?",
                    new String[] { "Sealed", "Opened" }) == 0;

                String character = input.inputString("What character is it?");

                return new ActionFigure(name, desc, value, series, sealed,
                    character);
            }

            case 1: {
                int age;
                do {
                    age = input.inputInt("How old is the stamp?");
                }
                while (age < 0);

                String location = input.inputString("Where was it printed?");

                boolean misprint = input.options("Was it misprinted?",
                    new String[] { "Normal", "Misprint" }) == 1;

                return new Stamp(name, desc, value, age, location, misprint);
            }

            case 2: {
                String cardType = input.inputString("What type of card is it?");

                double grade = input.inputDouble("Card grade:", 10);

                int year = input.inputInt("Print year:");

                return new TradingCard(name, desc, value, cardType, grade,
                    year);
            }

            default:
                throw new IllegalStateException("Unexpected collectible type");
        }
    }


    /**
     * Stores a value and a reference to the next node.
     *
     * @param <E>
     *     the stored value type
     */
    private static class Node<E> implements java.io.Serializable {
        private final E value;
        private Node<E> next;


        private Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }




    /**
     * Iterates over the linked nodes.
     *
     * @param <E>
     *     the stored value type
     */
    private static class Iterator<E> implements java.util.Iterator<E> {

        private Node<E> current;


        private Iterator(Node<E> start) {
            current = start;
        }


        @Override
        public boolean hasNext() {
            return current != null;
        }


        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            E value = current.value;
            current = current.next;
            return value;
        }
    }
}
