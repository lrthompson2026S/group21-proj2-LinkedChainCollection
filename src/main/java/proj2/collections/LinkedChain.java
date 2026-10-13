package proj2.collections;

public interface LinkedChain<T> extends Iterable<T> {
    <U extends T> void add(U item);

    <U extends T> void add(java.util.Collection<U> items);

    <U extends T> void add(LinkedChain<U> items);

    boolean contains(Object item);

    boolean contains(java.util.Collection<Object> items);

    boolean contains(LinkedChain<Object> items);

    Object remove();

    Object remove(Object item);

    java.util.Collection<Object> remove(java.util.Collection<Object> items);

    java.util.Collection<Object> remove(LinkedChain<Object> items);

    void clear();

    boolean equals(Object other);

    boolean isEmpty();

    int size();

    Object[] toArray();

    java.util.Collection<Object> toJavaCollection();
}
