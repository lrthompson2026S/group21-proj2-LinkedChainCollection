package proj2.collections;

import proj2.collectibles.Collectible;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * A mutable, singly linked collection of collectible items.
 *
 * <p>New items are inserted at the head of the chain, so iteration visits
 * the most recently inserted item first. Duplicates are permitted. Equality
 * compares the stored items without considering their iteration order.
 *
 * @param <T> the type of collectible stored in the collection
 */
public class Collection<T extends Collectible> implements LinkedChain<T>, Serializable {
    private Node<T> head;
    private int count;


    /**
     * Constructs an empty collection.
     */
    public Collection() {
        head = null;
        count = 0;
    }


    /**
     * Constructs a collection from a Java collection.
     * Items are inserted one by one at the head of the chain.
     *
     * @param items the items to add
     * @throws NullPointerException if {@code items} is null
     * @throws IllegalArgumentException if {@code items} contains null
     */
    public Collection(java.util.Collection<T> items) {
        this(); // default
        add(items);
    }


    /**
     * Constructs a collection containing the items of another linked
     * collection. Insertion at the head reverses the source iteration order.
     *
     * @param items the source linked collection
     * @throws NullPointerException if {@code items} is null
     */
    public Collection(Collection<T> items) {
        this(); // default
        add(items);
    }


    /**
     * Constructs a collection from an array.
     * Items are inserted one by one at the head of the chain.
     *
     * @param items the array of items to add
     * @throws NullPointerException if {@code items} is null
     * @throws IllegalArgumentException if {@code items} contains null
     */
    public Collection(T[] items) {
        this(Arrays.stream(items).toList());
    }


    /**
     * Inserts an item at the head of the collection.
     *
     * @param <U> a subtype of the collectible type
     * @param item the item to insert
     * @throws IllegalArgumentException if {@code item} is null
     */
    @Override
    public <U extends T> void add(U item) {
        if (item == null) {
            throw new IllegalArgumentException(
                "Cannot add null item to collection");
        }

        Node<T> temp = head;
        head = new Node<>(item, temp);
        count++;
    }


    /**
     * Inserts all items from a Java collection at the head, one by one.
     * The final iteration order is the reverse of the input order.
     *
     * @param <U> a subtype of the collectible type
     * @param items the items to insert
     * @throws NullPointerException if {@code items} is null
     * @throws IllegalArgumentException if an item is null
     */
    @Override
    public <U extends T> void add(java.util.Collection<U> items) {
        for (T item : items) {
            this.add(item);
        }
    }


    /**
     * Inserts all items from another linked chain at the head, one by one.
     *
     * @param <U> a subtype of the collectible type
     * @param items the items to insert
     * @throws NullPointerException if {@code items} is null
     * @throws IllegalArgumentException if an item is null
     */
    @Override
    public <U extends T> void add(LinkedChain<U> items) {
        for (T item : items) {
            this.add(item);
        }
    }


    /**
     * Determines whether an item equals any stored item.
     *
     * @param item the item to search for
     * @return {@code true} if a matching item is present
     * @throws NullPointerException if {@code item} is null and the chain
     *     is nonempty
     */
    @Override
    public boolean contains(Object item) {
        for (T contained : this) {
            if (item.equals(contained)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Checks whether every item in a Java collection is present.
     * Each item is checked independently, so repeated query values need
     * not correspond to distinct stored items.
     *
     * @param items the items to search for
     * @return {@code true} if every queried item is present; also
     *     {@code true} for an empty query collection
     * @throws NullPointerException if {@code items} is null
     */
    @Override
    public boolean contains(java.util.Collection<Object> items) {
        for (Object item : items) {
            if (!this.contains(item)) {
                return false;
            }
        }

        return true;
    }


    /**
     * Checks whether every item in another linked chain is present.
     *
     * @param items the linked chain of items to search for
     * @return {@code true} if all queried items are present
     * @throws NullPointerException if {@code items} is null
     */
    @Override
    public boolean contains(LinkedChain<Object> items) {
        return contains(items.toJavaCollection());
    }


    /**
     * Removes the item at the head of the chain.
     *
     * @return the removed item, or {@code null} if the chain is empty
     */
    @Override
    public Object remove() {
        if (isEmpty()) {
            return null; // nothing to return if emtpy
        }

        // remove and replace head
        Node<T> old = head;
        head = old.getNext();

        count--;

        return old.getValue();
    }


    /**
     * Removes the first item equal to the supplied value, searching from
     * the head of the chain.
     *
     * @param item the value to remove
     * @return the removed item, or {@code null} if there is no match
     */
    @Override
    public Object remove(Object item) {
        if (item == null) {
            return null;
        }

        Node<T> previous = null;
        Node<T> current = head;
        while (current != null) {
            if (!current.getValue().equals(item)) {
                previous = current;
                current = current.getNext();
                continue;
            }

            // head equal
            if (previous == null) {
                return remove();
            }

            // have previous now skip current
            previous.setNext(current.getNext());
            count--;

            return current.getValue();
        }

        return null;
    }


    /**
     * Removes at most one matching item for each value in a Java collection.
     * The returned list contains successfully removed items in query order.
     *
     * @param items the items to remove; {@code null} removes nothing
     * @return the items actually removed, possibly empty
     */
    @Override
    public java.util.Collection<Object> remove(java.util.Collection<Object> items) {
        java.util.Collection<Object> result = new ArrayList<>();

        if (items == null) {
            return result;
        }

        for (Object item : items) {
            Object removed = this.remove(item);
            if (removed != null) {
                result.add(removed);
            }
        }

        return result;
    }


    /**
     * Removes at most one matching item for each value in another chain.
     *
     * @param items the chain of items to remove
     * @return the items actually removed, in query iteration order
     * @throws NullPointerException if {@code items} is null
     */
    @Override
    public java.util.Collection<Object> remove(LinkedChain<Object> items) {
        return remove(items.toJavaCollection());
    }


    /**
     * Removes every item and resets the size to zero.
     */
    @Override
    public void clear() {
        this.head = null;
        this.count = 0;
    }


    /**
     * Compares two collections as unordered collections with duplicates.
     * Two collections are equal if they have the same concrete class,
     * size, and matching occurrences of each item.
     *
     * @param other the object to compare with this collection
     * @return {@code true} if both collections contain the same items
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }

        // make new collections from our current collections
        Collection<?> collection = (Collection<?>)(other);
        collection = new Collection<>(collection);

        if (collection.size() != this.size()) {
            return false;
        }

        java.util.Collection<Object> removed =
            collection.remove(this.toJavaCollection());

        return removed.size() == this.size() && collection.isEmpty();
    }


    /**
     * Checks whether this collection contains no items.
     *
     * @return {@code true} if the collection is empty
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }


    /**
     * Returns the number of stored items, including duplicates.
     *
     * @return the number of stored items
     */
    @Override
    public int size() {
        return count;
    }


    /**
     * Creates an array of the stored items in iteration order.
     *
     * @return a new array, ordered from head to tail
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size()];

        Node<T> current = head;
        for (int i = 0; i < size(); ++i) {
            array[i] = current.getValue();
            current = current.getNext();
        }

        return array;
    }


    /**
     * Creates an unmodifiable Java-list snapshot of this collection.
     * The items appear in iteration order.
     *
     * @return a new Java collection containing the items
     */
    @Override
    public java.util.Collection<Object> toJavaCollection() {
        return Arrays.stream(toArray()).toList();
    }


    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public java.util.Iterator<T> iterator() {
        return new Iterator<>(head);
    }


    /**
     * Joins the {@code info()} text of all items, one per line.
     * Each item, including the final item, is followed by a newline.
     *
     * @return the item descriptions, or an empty string when empty
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (T in : this) {
            result.append(in.info()).append("\n");
        }
        return result.toString();
    }


    /**
     * Stores a value and a reference to the next node in the chain.
     *
     * @param <T> the value type
     */
    private static class Node<T> implements Serializable{
        private T value;
        private Node<T> next;


        /**
         * Constructs a node with no value or successor.
         */
        public Node() {
            this(null, null);
        }


        /**
         * Constructs a node containing a value and no successor.
         *
         * @param value the stored value
         */
        public Node(T value) {
            this(value, null);
        }


        /**
         * Constructs a node with the supplied value and successor.
         *
         * @param value the stored value
         * @param next the next node, or {@code null}
         */
        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }


        /**
         * Returns the next node.
         *
         * @return the successor, or {@code null}
         */
        public Node<T> getNext() {
            return next;
        }


        /**
         * Replaces the next-node reference.
         *
         * @param next the new successor
         */
        public void setNext(Node<T> next) {
            this.next = next;
        }


        /**
         * Returns the stored value.
         *
         * @return the stored value
         */
        public T getValue() {
            return value;
        }


        /**
         * Replaces the stored value.
         *
         * @param value the new value
         */
        public void setValue(T value) {
            this.value = value;
        }
    }




    /**
     * Iterates over nodes from the supplied starting node to the tail.
     *
     * @param <T> the value type
     */
    private static class Iterator<T> implements java.util.Iterator<T> {
        private Node<T> current;


        /**
         * Creates an iterator at a particular node.
         *
         * @param start the first node, or {@code null} for an empty iterator
         */
        public Iterator(Node<T> start) {
            this.current = start;
        }


        /**
         * Returns {@code true} if the iteration has more elements. (In other
         * words, returns {@code true} if {@link #next} would return an element
         * rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }


        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException
         *     if the iteration has no more elements
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T value = current.getValue();
            current = current.getNext();
            return value;
        }
    }
}
