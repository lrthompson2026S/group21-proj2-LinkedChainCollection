package proj2.collections;

/**
 * Defines a mutable collection backed by a sequence of linked items.
 * Implementations support individual and bulk insertion, membership
 * checks, removal, and iteration.
 *
 * @param <T> the type of item stored in the chain
 */
public interface LinkedChain<T> extends Iterable<T> {
    /**
     * Adds an item to the chain.
     *
     * @param <U> a subtype of the chain's item type
     * @param item the item to add
     */
    <U extends T> void add(U item);

    /**
     * Adds items from a Java collection.
     *
     * @param <U> a subtype of the chain's item type
     * @param items the items to add
     */
    <U extends T> void add(java.util.Collection<U> items);

    /**
     * Adds items from another linked chain.
     *
     * @param <U> a subtype of the chain's item type
     * @param items the items to add
     */
    <U extends T> void add(LinkedChain<U> items);

    /**
     * Checks whether an equal item is present.
     *
     * @param item the item to check
     * @return {@code true} if a matching item is present
     */
    boolean contains(Object item);

    /**
     * Checks whether all items from a Java collection are present.
     *
     * @param items the items to check
     * @return {@code true} if all queried items are present
     */
    boolean contains(java.util.Collection<Object> items);

    /**
     * Checks whether all items from another linked chain are present.
     *
     * @param items the items to check
     * @return {@code true} if all queried items are present
     */
    boolean contains(LinkedChain<Object> items);

    /**
     * Removes an item from the chain according to implementation order.
     *
     * @return the removed item, or {@code null} if empty
     */
    Object remove();

    /**
     * Removes one matching item from the chain.
     *
     * @param item the item to remove
     * @return the removed item, or {@code null} if no item matches
     */
    Object remove(Object item);

    /**
     * Removes matching items requested by a Java collection.
     *
     * @param items the values to remove
     * @return the successfully removed items
     */
    java.util.Collection<Object> remove(java.util.Collection<Object> items);

    /**
     * Removes matching items requested by another linked chain.
     *
     * @param items the values to remove
     * @return the successfully removed items
     */
    java.util.Collection<Object> remove(LinkedChain<Object> items);

    /**
     * Removes all items from the chain.
     */
    void clear();

    /**
     * Compares this chain with another object.
     *
     * @param other the object to compare
     * @return {@code true} if both objects are considered equal
     */
    boolean equals(Object other);

    /**
     * Checks whether the chain contains no items.
     *
     * @return {@code true} if empty
     */
    boolean isEmpty();

    /**
     * Returns the number of items in the chain.
     *
     * @return the current item count
     */
    int size();

    /**
     * Copies the chain's items to an array.
     *
     * @return an array containing the items in iteration order
     */
    Object[] toArray();

    /**
     * Copies the chain's items to a Java collection.
     *
     * @return a Java collection containing the items
     */
    java.util.Collection<Object> toJavaCollection();
}
