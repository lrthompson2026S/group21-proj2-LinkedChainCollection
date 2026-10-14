package proj2.test.collections;

import proj2.collectibles.ActionFigure;
import proj2.collectibles.Collectible;
import proj2.collectibles.Stamp;
import proj2.collectibles.TradingCard;
import proj2.collections.Collection;
import proj2.collections.LinkedChain;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Tests the linked collection with the project's actual collectible types.
 *
 * <p>The collectible classes do not override {@code equals(Object)}, so
 * matching items are the same object references, not separate objects with
 * identical field values. A collection can hold different collectible types.
 *
 * @version 10.13.2026
 */
public class CollectionTest extends student.TestCase {
    private Collection<Collectible> collection;
    private TradingCard card;
    private Stamp stamp;
    private ActionFigure figure;


    /**
     * Creates a fresh collection and one of each collectible before each test.
     */
    @Override
    public void setUp() {
        collection = new Collection<>();
        card = new TradingCard("Pikachu", "Pokemon card", 25.0, "Pokemon", 9,
            2020);
        stamp =
            new Stamp("Blue stamp", "Postal stamp", 12.0, 45, "United States",
                false);
        figure =
            new ActionFigure("Mario", "Nintendo figure", 40.0, "Nintendo", true,
                "Mario");
    }


    /**
     * Tests the state of a newly constructed empty collection.
     */
    public void testEmptyCollection() {
        assertTrue(collection.isEmpty());
        assertEquals(0, collection.size());
        assertEquals(0, collection.toArray().length);
        assertTrue(collection.toJavaCollection().isEmpty());
        assertEquals("", collection.toString());
    }


    /**
     * Tests the array constructor and reverse-insertion order.
     */
    public void testArrayConstructor() {
        Collectible[] source = { card, stamp, figure };
        collection = new Collection<>(source);

        assertEquals(3, collection.size());
        assertEquals(figure, collection.toArray()[0]);
        assertEquals(stamp, collection.toArray()[1]);
        assertEquals(card, collection.toArray()[2]);
    }


    /**
     * Tests construction from a standard Java collection.
     */
    public void testJavaCollectionConstructor() {
        List<Collectible> source = Arrays.asList(card, stamp, figure);
        collection = new Collection<>(source);

        assertEquals(3, collection.size());
        assertEquals(figure, collection.toArray()[0]);
        assertEquals(stamp, collection.toArray()[1]);
        assertEquals(card, collection.toArray()[2]);
    }


    /**
     * Tests copying another collection without sharing its nodes.
     */
    public void testCollectionCopyConstructor() {
        Collection<Collectible> original = new Collection<>();
        original.add(card);
        original.add(stamp);
        collection = new Collection<>(original);

        assertEquals(2, collection.size());
        assertEquals(card, collection.toArray()[0]);
        assertEquals(stamp, collection.toArray()[1]);
        original.clear();
        assertEquals(2, collection.size());
    }


    /**
     * Tests that the array constructor rejects a null element.
     */
    public void testArrayConstructorWithNullItem() {
        try {
            new Collection<>(new Collectible[] { card, null });
            fail("A null element should be rejected");
        }
        catch (IllegalArgumentException expected) {
            assertTrue(collection.isEmpty());
        }
    }


    /**
     * Tests adding three different subclasses to the same collection.
     */
    public void testAddDifferentCollectibleTypes() {
        collection.add(card);
        collection.add(stamp);
        collection.add(figure);

        assertEquals(3, collection.size());
        assertEquals(figure, collection.toArray()[0]);
        assertEquals(stamp, collection.toArray()[1]);
        assertEquals(card, collection.toArray()[2]);
    }


    /**
     * Tests allowing duplicate references as separate entries.
     */
    public void testAddDuplicateItem() {
        collection.add(card);
        collection.add(card);

        assertEquals(2, collection.size());
        assertEquals(card, collection.toArray()[0]);
        assertEquals(card, collection.toArray()[1]);
    }


    /**
     * Tests that a null item cannot be inserted.
     */
    public void testAddNullItem() {
        try {
            collection.add((Collectible)null);
            fail("Inserting null should throw IllegalArgumentException");
        }
        catch (IllegalArgumentException expected) {
            assertEquals(0, collection.size());
        }
    }


    /**
     * Tests insertion from a standard Java collection.
     */
    public void testAddJavaCollection() {
        collection.add(Arrays.asList(card, stamp));

        assertEquals(2, collection.size());
        assertEquals(stamp, collection.toArray()[0]);
        assertEquals(card, collection.toArray()[1]);
    }


    /**
     * Tests that a Java collection containing null cannot be added.
     */
    public void testAddJavaCollectionWithNull() {
        try {
            collection.add(Arrays.<Collectible> asList(card, null));
            fail("A null element should throw IllegalArgumentException");
        }
        catch (IllegalArgumentException expected) {
            assertEquals(1, collection.size());
            assertEquals(card, collection.toArray()[0]);
        }
    }


    /**
     * Tests inserting items from a linked chain.
     */
    public void testAddLinkedChain() {
        Collection<Collectible> source = new Collection<>();
        source.add(card);
        source.add(stamp);
        collection.add(source);

        assertEquals(2, collection.size());
        assertEquals(card, collection.toArray()[0]);
        assertEquals(stamp, collection.toArray()[1]);
        assertEquals(2, source.size());
    }


    /**
     * Tests that the generic add overload accepts subtype collections.
     */
    public void testAddSubtypeCollections() {
        collection.add(List.of(card));
        Collection<Stamp> stamps = new Collection<>();
        stamps.add(stamp);
        collection.add(stamps);

        assertEquals(2, collection.size());
        assertEquals(stamp, collection.toArray()[0]);
        assertEquals(card, collection.toArray()[1]);
    }


    /**
     * Tests membership checks for existing and absent objects.
     */
    public void testContainsItem() {
        assertFalse(collection.contains(card));
        collection.add(card);
        assertTrue(collection.contains(card));
        assertFalse(collection.contains(stamp));
    }


    /**
     * Tests that distinct collectible objects with identical data are not
     * considered equal by the current collectible implementations.
     */
    public void testContainsUsesObjectIdentity() {
        TradingCard copy =
            new TradingCard("Pikachu", "Pokemon card", 25.0, "Pokemon", 9,
                2020);
        collection.add(card);
        assertFalse(collection.contains(copy));
    }


    /**
     * Tests checking for all elements of a Java collection.
     */
    public void testContainsJavaCollection() {
        collection.add(card);
        collection.add(stamp);

        assertTrue(collection.contains(List.of()));
        assertTrue(collection.contains(List.of(card, stamp)));
        assertTrue(collection.contains(List.of(card, card)));
        assertFalse(collection.contains(List.of(card, figure)));
    }


    /**
     * Tests checking for all elements of another linked chain.
     */
    public void testContainsLinkedChain() {
        collection.add(card);
        collection.add(stamp);
        Collection<Collectible> query = new Collection<>();
        query.add(card);

        assertTrue(collection.contains(asObjectChain(query)));
        query.add(figure);
        assertFalse(collection.contains(asObjectChain(query)));
    }


    /**
     * Tests removing an item from an empty collection.
     */
    public void testRemoveHeadEmpty() {
        assertNull(collection.remove());
        assertTrue(collection.isEmpty());
    }


    /**
     * Tests removing successive heads in last-in-first-out order.
     */
    public void testRemoveHead() {
        collection.add(card);
        collection.add(stamp);
        collection.add(figure);

        assertEquals(figure, collection.remove());
        assertEquals(stamp, collection.remove());
        assertEquals(card, collection.remove());
        assertNull(collection.remove());
        assertEquals(0, collection.size());
    }


    /**
     * Tests removal by value when the target is the head.
     */
    public void testRemoveItemAtHead() {
        collection.add(card);
        collection.add(stamp);

        assertEquals(stamp, collection.remove(stamp));
        assertEquals(1, collection.size());
        assertEquals(card, collection.toArray()[0]);
    }


    /**
     * Tests removal by value from the middle and tail.
     */
    public void testRemoveItemFromMiddleAndTail() {
        collection.add(card);
        collection.add(stamp);
        collection.add(figure);

        assertEquals(stamp, collection.remove(stamp));
        assertEquals(2, collection.size());
        assertEquals(figure, collection.toArray()[0]);
        assertEquals(card, collection.toArray()[1]);

        assertEquals(card, collection.remove(card));
        assertEquals(1, collection.size());
        assertEquals(figure, collection.toArray()[0]);
    }


    /**
     * Tests missing items and null in the single-item removal overload.
     */
    public void testRemoveMissingOrNullItem() {
        assertNull(collection.remove(card));
        collection.add(card);
        assertNull(collection.remove(stamp));
        assertNull(collection.remove((Object)null));
        assertEquals(1, collection.size());
    }


    /**
     * Tests removing one matching entry per request from a Java collection.
     */
    public void testRemoveJavaCollection() {
        collection.add(card);
        collection.add(stamp);
        collection.add(card);

        java.util.Collection<Object> removed =
            collection.remove(List.of(card, card, figure, stamp));

        assertEquals(3, removed.size());
        assertEquals(Arrays.<Object> asList(card, card, stamp), removed);
        assertTrue(collection.isEmpty());
    }


    /**
     * Tests passing null for bulk removal with a Java collection.
     */
    public void testRemoveNullJavaCollection() {
        collection.add(card);
        assertTrue(
            collection.remove((java.util.Collection<Object>)null).isEmpty());
        assertEquals(1, collection.size());
    }


    /**
     * Tests removing entries requested by another linked chain.
     */
    public void testRemoveLinkedChain() {
        collection.add(card);
        collection.add(stamp);
        Collection<Collectible> query = new Collection<>();
        query.add(card);
        query.add(figure);

        java.util.Collection<Object> removed =
            collection.remove(asObjectChain(query));
        assertEquals(1, removed.size());
        assertEquals(card, removed.iterator().next());
        assertEquals(1, collection.size());
        assertTrue(collection.contains(stamp));
    }


    /**
     * Tests that clearing drops every stored item.
     */
    public void testClear() {
        collection.add(card);
        collection.add(stamp);
        collection.clear();

        assertTrue(collection.isEmpty());
        assertEquals(0, collection.size());
        assertEquals(0, collection.toArray().length);
        assertNull(collection.remove());
    }


    /**
     * Tests reflexivity and comparisons with null and another class.
     */
    public void testEqualsBasicCases() {
        assertTrue(collection.equals(collection));
        assertFalse(collection.equals(null));
        assertFalse(collection.equals("not a collection"));
    }


    /**
     * Tests equality of two empty collections.
     */
    public void testEqualsEmptyCollections() {
        assertTrue(collection.equals(new Collection<Collectible>()));
    }


    /**
     * Tests equality independent of item iteration order.
     */
    public void testEqualsIgnoresOrder() {
        collection.add(card);
        collection.add(stamp);
        Collection<Collectible> other = new Collection<>();
        other.add(stamp);
        other.add(card);

        assertTrue(collection.equals(other));
        assertTrue(other.equals(collection));
        assertEquals(2, collection.size());
        assertEquals(2, other.size());
    }


    /**
     * Tests that matching field values do not imply equality of objects.
     */
    public void testEqualsDifferentReferences() {
        collection.add(card);
        TradingCard copy =
            new TradingCard("Pikachu", "Pokemon card", 25.0, "Pokemon", 9,
                2020);
        Collection<Collectible> other = new Collection<>();
        other.add(copy);

        assertFalse(collection.equals(other));
    }


    /**
     * Tests unequal item counts and different items.
     */
    public void testEqualsUnequalCollections() {
        collection.add(card);
        Collection<Collectible> other = new Collection<>();
        assertFalse(collection.equals(other));
        other.add(stamp);
        assertFalse(collection.equals(other));
    }


    /**
     * Tests that equality respects the number of duplicate references.
     */
    public void testEqualsDuplicates() {
        collection.add(card);
        collection.add(card);
        Collection<Collectible> other = new Collection<>();
        other.add(card);
        other.add(stamp);
        assertFalse(collection.equals(other));
        other.remove(stamp);
        other.add(card);
        assertTrue(collection.equals(other));
    }


    /**
     * Tests array order and the array's independence from the chain.
     */
    public void testToArray() {
        collection.add(card);
        collection.add(stamp);
        Object[] array = collection.toArray();

        assertEquals(2, array.length);
        assertEquals(stamp, array[0]);
        assertEquals(card, array[1]);
        array[0] = figure;
        assertEquals(stamp, collection.toArray()[0]);
    }


    /**
     * Tests that conversion to a Java collection returns a snapshot.
     */
    public void testToJavaCollection() {
        collection.add(card);
        java.util.Collection<Object> snapshot = collection.toJavaCollection();
        collection.add(stamp);

        assertEquals(1, snapshot.size());
        assertEquals(card, snapshot.iterator().next());
        assertEquals(2, collection.size());
    }


    /**
     * Tests iterator order and the hasNext state between items.
     */
    public void testIterator() {
        collection.add(card);
        collection.add(stamp);
        Iterator<Collectible> iterator = collection.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(stamp, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(card, iterator.next());
        assertFalse(iterator.hasNext());
    }


    /**
     * Tests that an empty iterator throws when advanced.
     */
    public void testEmptyIterator() {
        Iterator<Collectible> iterator = collection.iterator();
        assertFalse(iterator.hasNext());
        try {
            iterator.next();
            fail("An empty iterator should throw NoSuchElementException");
        }
        catch (NoSuchElementException expected) {
            assertFalse(iterator.hasNext());
        }
    }


    /**
     * Tests advancing beyond the last item of a nonempty iterator.
     */
    public void testExhaustedIterator() {
        collection.add(card);
        Iterator<Collectible> iterator = collection.iterator();
        assertEquals(card, iterator.next());
        try {
            iterator.next();
            fail("An exhausted iterator should throw NoSuchElementException");
        }
        catch (NoSuchElementException expected) {
            assertFalse(iterator.hasNext());
        }
    }


    /**
     * Tests concatenating the actual collectible info strings in list order.
     */
    public void testToString() {
        collection.add(card);
        collection.add(stamp);
        collection.add(figure);

        assertEquals("""
            Action Figure - Mario
            Stamp - United States
            Pikachu - $25.0 - Type: Pokemon
            """, collection.toString());
    }


    /**
     * Adapts a collectible chain to the interface's {@code Object}-typed
     * bulk-operation methods. The test only reads through the adapted view.
     *
     * @param source
     *     the chain to view
     * @return the chain as required by the current interface signature
     */
    @SuppressWarnings("unchecked")
    private LinkedChain<Object> asObjectChain(Collection<Collectible> source) {
        return (LinkedChain<Object>)(LinkedChain<?>)source;
    }
}
