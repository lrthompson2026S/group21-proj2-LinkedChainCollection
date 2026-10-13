package proj2.collections;

import proj2.collectibles.Collectible;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class Collection<T extends Collectible> implements LinkedChain<T> {
    private Node<T> head;
    private int count;


    public Collection() {
        head = null;
        count = 0;
    }


    public Collection(java.util.Collection<T> items) {
        this(); // default
        add(items);
    }


    public Collection(Collection<T> items) {
        this(); // default
        add(items);
    }


    public Collection(T[] items) {
        this(Arrays.stream(items).toList());
    }


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


    @Override
    public <U extends T> void add(java.util.Collection<U> items) {
        for (T item : items) {
            this.add(item);
        }
    }


    @Override
    public <U extends T> void add(LinkedChain<U> items) {
        for (T item : items) {
            this.add(item);
        }
    }


    @Override
    public boolean contains(Object item) {
        for (T contained : this) {
            if (item.equals(contained)) {
                return true;
            }
        }

        return false;
    }


    @Override
    public boolean contains(java.util.Collection<Object> items) {
        for (Object item : items) {
            if (!this.contains(item)) {
                return false;
            }
        }

        return true;
    }


    @Override
    public boolean contains(LinkedChain<Object> items) {
        return contains(items.toJavaCollection());
    }


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


    @Override
    public java.util.Collection<Object> remove(LinkedChain<Object> items) {
        return remove(items.toJavaCollection());
    }


    @Override
    public void clear() {
        this.head = null;
        this.count = 0;
    }


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


    @Override
    public boolean isEmpty() {
        return size() == 0;
    }


    @Override
    public int size() {
        return count;
    }


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


    private static class Node<T> {
        private T value;
        private Node<T> next;


        public Node() {
            this(null, null);
        }


        public Node(T value) {
            this(value, null);
        }


        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }


        public Node<T> getNext() {
            return next;
        }


        public void setNext(Node<T> next) {
            this.next = next;
        }


        public T getValue() {
            return value;
        }


        public void setValue(T value) {
            this.value = value;
        }
    }




    private static class Iterator<T> implements java.util.Iterator<T> {
        private Node<T> current;


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
