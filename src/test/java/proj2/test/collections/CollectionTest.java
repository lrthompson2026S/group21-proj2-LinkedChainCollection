package proj2.test.collections;

import proj2.collectibles.ActionFigure;
import proj2.collectibles.Collectible;
import proj2.collectibles.Stamp;
import proj2.collectibles.TradingCard;
import proj2.collections.Collection;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Tests the linked collection, which is itself a {@code Collectible} and may
 * therefore hold other collections.
 *
 * <p>The collectible classes do not override {@code equals(Object)}, so
 * matching items are the same object references, not separate objects with
 * identical field values. Collections compare by contents (unordered).
 *
 * <p>Note: items are inserted at the head, so any bulk add or copy reverses
 * the source's iteration order.
 *
 * @version 10.21.2026
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

    // ---------------------------------------------------------------
    // Construction and Collectible behavior
    // ---------------------------------------------------------------


    /**
     * Tests the state of a newly constructed empty collection.
     */
    public void testEmptyCollection() {
        assertTrue(collection.isEmpty());
        assertEquals(0, collection.size());
        assertEquals(0, collection.toArray().length);
        assertTrue(collection.toJavaCollection().isEmpty());
        assertEquals(0.0, collection.getValue(), 0.0001);
    }


    /**
     * Tests the default name and description.
     */
    public void testDefaultNameAndDescription() {
        assertEquals("Unnamed Collection", collection.getName());
        assertEquals("None", collection.getDesc());
    }


    /**
     * Tests the named constructor.
     */
    public void testNamedConstructor() {
        Collection<Collectible> named =
            new Collection<>("Favorites", "My best items");
        assertEquals("Favorites", named.getName());
        assertEquals("My best items", named.getDesc());
        assertTrue(named.isEmpty());
    }


    /**
     * Tests the named constructor that takes initial items.
     */
    public void testNamedConstructorWithItems() {
        Collection<Collectible> named =
            new Collection<>("Favorites", "Desc", List.of(card, stamp));

        assertEquals("Favorites", named.getName());
        assertEquals("Desc", named.getDesc());
        assertEquals(2, named.size());
        assertEquals(stamp, named.toArray()[0]);
        assertEquals(card, named.toArray()[1]);
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
     * Tests copying another collection's metadata and items without sharing its
     * nodes.
     */
    public void testCollectionCopyConstructor() {
        Collection<Collectible> original =
            new Collection<>("Original", "Original desc");
        original.add(card);
        original.add(stamp);
        collection = new Collection<>(original);

        assertEquals("Original", collection.getName());
        assertEquals("Original desc", collection.getDesc());
        assertEquals(2, collection.size());
        // The copy re-inserts at the head, so order is reversed.
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
     * Tests that a collection can be used wherever a Collectible is expected.
     */
    public void testCollectionIsCollectible() {
        Collectible asCollectible = collection;
        assertEquals("Unnamed Collection", asCollectible.getName());
    }

    // ---------------------------------------------------------------
    // Add
    // ---------------------------------------------------------------


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
     * Tests that a null subcollection cannot be inserted.
     */
    public void testAddNullSubcollection() {
        try {
            collection.add(null);
            fail("Inserting null should throw IllegalArgumentException");
        }
        catch (IllegalArgumentException expected) {
            assertEquals(0, collection.size());
        }
    }


    /**
     * Tests that a subcollection is added as a single item.
     */
    public void testAddSubcollection() {
        Collection<Collectible> sub = new Collection<>("Sub", "Sub desc");
        sub.add(card);
        sub.add(stamp);
        collection.add(figure);
        collection.add(sub);

        assertEquals(2, collection.size());
        assertSame(sub, collection.toArray()[0]);
        assertSame(figure, collection.toArray()[1]);
        assertEquals(2, sub.size());
    }


    /**
     * Tests that a collection cannot be added to itself.
     */
    public void testAddSelfRejected() {
        try {
            collection.add(collection);
            fail("A collection must not contain itself");
        }
        catch (IllegalArgumentException expected) {
            assertTrue(collection.isEmpty());
        }
    }


    /**
     * Tests that an indirect cycle between two collections is rejected.
     */
    public void testAddCircularTwoLevels() {
        Collection<Collectible> child = new Collection<>("Child", "c");
        collection.add(child);

        try {
            child.add(collection);
            fail("A circular subcollection must be rejected");
        }
        catch (IllegalArgumentException expected) {
            assertTrue(child.isEmpty());
            assertEquals(1, collection.size());
        }
    }


    /**
     * Tests that a cycle through a deeper nesting is rejected.
     */
    public void testAddCircularThreeLevels() {
        Collection<Collectible> middle = new Collection<>("Middle", "m");
        Collection<Collectible> bottom = new Collection<>("Bottom", "b");
        collection.add(middle);
        middle.add(bottom);

        try {
            bottom.add(collection);
            fail("A deep circular subcollection must be rejected");
        }
        catch (IllegalArgumentException expected) {
            assertTrue(bottom.isEmpty());
        }
    }


    /**
     * Tests that nesting without a cycle is allowed, including sharing one
     * child between two unrelated parents.
     */
    public void testAddSharedChildAllowed() {
        Collection<Collectible> shared = new Collection<>("Shared", "s");
        shared.add(card);
        Collection<Collectible> other = new Collection<>("Other", "o");

        collection.add(shared);
        other.add(shared);

        assertEquals(1, collection.size());
        assertEquals(1, other.size());
    }

    // ---------------------------------------------------------------
    // addAll
    // ---------------------------------------------------------------


    /**
     * Tests insertion from a standard Java collection.
     */
    public void testAddAllJavaCollection() {
        collection.addAll(Arrays.asList(card, stamp));

        assertEquals(2, collection.size());
        assertEquals(stamp, collection.toArray()[0]);
        assertEquals(card, collection.toArray()[1]);
    }


    /**
     * Tests that a Java collection containing null cannot be fully added.
     */
    public void testAddAllJavaCollectionWithNull() {
        try {
            collection.addAll(Arrays.<Collectible> asList(card, null));
            fail("A null element should throw IllegalArgumentException");
        }
        catch (IllegalArgumentException expected) {
            assertEquals(1, collection.size());
            assertEquals(card, collection.toArray()[0]);
        }
    }


    /**
     * Tests inserting the items of a linked chain individually.
     */
    public void testAddAllLinkedChain() {
        Collection<Collectible> source = new Collection<>();
        source.add(card);
        source.add(stamp);
        collection.addAll(source);

        assertEquals(2, collection.size());
        // Re-inserted at the head, so order is reversed.
        assertEquals(card, collection.toArray()[0]);
        assertEquals(stamp, collection.toArray()[1]);
        assertEquals(2, source.size());
    }


    /**
     * Tests that addAll accepts lists of a collectible subtype.
     */
    public void testAddAllSubtypes() {
        collection.addAll(List.of(stamp));
        collection.addAll(List.of(card));

        assertEquals(2, collection.size());
        assertEquals(card, collection.toArray()[0]);
        assertEquals(stamp, collection.toArray()[1]);
    }


    /**
     * Tests that addAll with an empty source changes nothing.
     */
    public void testAddAllEmpty() {
        collection.addAll(List.of());
        assertTrue(collection.isEmpty());
    }

    // ---------------------------------------------------------------
    // contains / containsAll
    // ---------------------------------------------------------------


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
     * Tests that a null query is never contained.
     */
    public void testContainsNull() {
        collection.add(card);
        assertFalse(collection.contains((Collectible)null));
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
     * Tests contains for a direct subcollection.
     */
    public void testContainsSubcollection() {
        Collection<Collectible> sub = new Collection<>("Sub", "s");
        sub.add(card);

        assertFalse(collection.contains(sub));
        collection.add(sub);
        assertTrue(collection.contains(sub));
    }


    /**
     * Tests that contains matches an equal (not identical) subcollection.
     */
    public void testContainsEqualSubcollection() {
        Collection<Collectible> sub = new Collection<>("Sub", "s");
        sub.add(card);
        Collection<Collectible> twin = new Collection<>("Twin", "t");
        twin.add(card);
        collection.add(sub);

        assertTrue(collection.contains(twin));
    }


    /**
     * Tests that contains does not look inside nested collections.
     */
    public void testContainsIsNotRecursive() {
        Collection<Collectible> sub = new Collection<>("Sub", "s");
        sub.add(card);
        collection.add(sub);

        assertFalse(collection.contains(card));
    }


    /**
     * Tests checking for all elements of a Java collection.
     */
    public void testContainsAllJavaCollection() {
        collection.add(card);
        collection.add(stamp);

        assertTrue(collection.containsAll(List.of()));
        assertTrue(collection.containsAll(List.of(card, stamp)));
        assertTrue(collection.containsAll(List.of(card, card)));
        assertFalse(collection.containsAll(List.of(card, figure)));
    }


    /**
     * Tests checking for all elements of another linked chain.
     */
    public void testContainsAllLinkedChain() {
        collection.add(card);
        collection.add(stamp);
        Collection<Collectible> query = new Collection<>();
        query.add(card);

        assertTrue(collection.containsAll(query));
        query.add(figure);
        assertFalse(collection.containsAll(query));
    }

    // ---------------------------------------------------------------
    // remove / removeAll / clear
    // ---------------------------------------------------------------


    /**
     * Tests removing the head of an empty collection.
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
     * Tests that removing one duplicate leaves the other in place.
     */
    public void testRemoveOnlyFirstDuplicate() {
        collection.add(card);
        collection.add(card);

        assertEquals(card, collection.remove(card));
        assertEquals(1, collection.size());
        assertTrue(collection.contains(card));
    }


    /**
     * Tests missing items and null in the single-item removal overload.
     */
    public void testRemoveMissingOrNullItem() {
        assertNull(collection.remove(card));
        collection.add(card);
        assertNull(collection.remove(stamp));
        assertNull(collection.remove((Collectible)null));
        assertEquals(1, collection.size());
    }


    /**
     * Tests removing a subcollection.
     */
    public void testRemoveSubcollection() {
        Collection<Collectible> sub = new Collection<>("Sub", "s");
        sub.add(card);
        collection.add(figure);
        collection.add(sub);

        assertSame(sub, collection.remove(sub));
        assertEquals(1, collection.size());
        assertEquals(figure, collection.toArray()[0]);
    }


    /**
     * Tests removing a subcollection that is absent or null.
     */
    public void testRemoveSubcollectionMissingOrNull() {
        Collection<Collectible> sub = new Collection<>("Sub", "s");
        sub.add(card);
        collection.add(figure);

        assertNull(collection.remove(sub));
        assertNull(collection.remove(null));
        assertEquals(1, collection.size());
    }


    /**
     * Tests removing one matching entry per request from a Java collection.
     */
    public void testRemoveAllJavaCollection() {
        collection.add(card);
        collection.add(stamp);
        collection.add(card);

        java.util.Collection<Collectible> removed =
            collection.removeAll(List.of(card, card, figure, stamp));

        assertEquals(3, removed.size());
        assertEquals(Arrays.asList(card, card, stamp), removed);
        assertTrue(collection.isEmpty());
    }


    /**
     * Tests passing null for bulk removal with a Java collection.
     */
    public void testRemoveAllNullJavaCollection() {
        collection.add(card);
        assertTrue(collection.removeAll((java.util.Collection<Collectible>)null)
            .isEmpty());
        assertEquals(1, collection.size());
    }


    /**
     * Tests removing entries requested by another linked chain.
     */
    public void testRemoveAllLinkedChain() {
        collection.add(card);
        collection.add(stamp);
        Collection<Collectible> query = new Collection<>();
        query.add(card);
        query.add(figure);

        java.util.Collection<Collectible> removed = collection.removeAll(query);
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

    // ---------------------------------------------------------------
    // equals / hashCode
    // ---------------------------------------------------------------


    /**
     * Tests reflexivity and comparisons with null and another class.
     */
    public void testEqualsBasicCases() {
        assertTrue(collection.equals(collection));
        assertFalse(collection.equals(null));
        assertFalse(collection.equals("not a collection"));
        assertFalse(collection.equals(card));
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
     * Tests that equality does not depend on name or description.
     */
    public void testEqualsIgnoresNameAndDescription() {
        Collection<Collectible> first = new Collection<>("A", "first");
        Collection<Collectible> second = new Collection<>("B", "second");
        first.add(card);
        second.add(card);

        assertTrue(first.equals(second));
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
     * Tests that equality compares nested collections by contents.
     */
    public void testEqualsNestedCollections() {
        Collection<Collectible> subOne = new Collection<>("S1", "s");
        subOne.add(card);
        Collection<Collectible> subTwo = new Collection<>("S2", "s");
        subTwo.add(card);

        collection.add(subOne);
        Collection<Collectible> other = new Collection<>();
        other.add(subTwo);
        assertTrue(collection.equals(other));

        subTwo.add(stamp);
        assertFalse(collection.equals(other));
    }


    /**
     * Tests that equal collections share a hash code and that it ignores
     * order.
     */
    public void testHashCode() {
        assertEquals(0, collection.hashCode());

        collection.add(card);
        collection.add(stamp);
        Collection<Collectible> other = new Collection<>();
        other.add(stamp);
        other.add(card);

        assertEquals(collection.hashCode(), other.hashCode());
    }

    // ---------------------------------------------------------------
    // Conversion and iteration
    // ---------------------------------------------------------------


    /**
     * Tests array order and the array's independence from the chain.
     */
    public void testToArray() {
        collection.add(card);
        collection.add(stamp);
        Collectible[] array = collection.toArray();

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
        java.util.Collection<Collectible> snapshot =
            collection.toJavaCollection();
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
     * Tests the for-each loop over the collection.
     */
    public void testForEach() {
        collection.add(card);
        collection.add(stamp);

        int count = 0;
        for (Collectible item : collection) {
            assertNotNull(item);
            count++;
        }
        assertEquals(2, count);
    }

    // ---------------------------------------------------------------
    // Value, info, toString
    // ---------------------------------------------------------------


    /**
     * Tests that the value is the sum of the direct items.
     */
    public void testGetValue() {
        collection.add(card);
        collection.add(stamp);
        assertEquals(37.0, collection.getValue(), 0.0001);

        collection.add(figure);
        assertEquals(77.0, collection.getValue(), 0.0001);

        collection.remove(card);
        assertEquals(52.0, collection.getValue(), 0.0001);
    }


    /**
     * Tests that nested collections contribute their own calculated value.
     */
    public void testGetValueNested() {
        Collection<Collectible> sub = new Collection<>("Sub", "s");
        sub.add(card);
        sub.add(stamp);
        collection.add(figure);
        collection.add(sub);

        assertEquals(37.0, sub.getValue(), 0.0001);
        assertEquals(77.0, collection.getValue(), 0.0001);

        sub.add(card);
        assertEquals(102.0, collection.getValue(), 0.0001);
    }


    /**
     * Tests that a collection's value cannot be set directly.
     */
    public void testSetValueUnsupported() {
        try {
            collection.setValue(5.0);
            fail("setValue should throw UnsupportedOperationException");
        }
        catch (UnsupportedOperationException expected) {
            assertEquals(0.0, collection.getValue(), 0.0001);
        }
    }


    /**
     * Tests that info ends with the item count.
     */
    public void testInfo() {
        assertTrue(collection.info().endsWith(" - Items: 0"));
        collection.add(card);
        collection.add(stamp);
        assertTrue(collection.info().endsWith(" - Items: 2"));
    }


    /**
     * Tests the string form of an empty collection.
     */
    public void testToStringEmpty() {
        assertEquals(
            "--- Collection ---\n" + "Name: Unnamed Collection\n" + "Description: None\n" + "Value: $0.0\n" + "Items: 0\n",
            collection.toString());
    }


    /**
     * Tests the string form with items in list order.
     */
    public void testToString() {
        Collection<Collectible> named = new Collection<>("Mine", "Desc");
        named.add(card);
        named.add(stamp);
        named.add(figure);

        String expected =
            "--- Collection ---\n" + "Name: Mine\n" + "Description: Desc\n" + "Value: $77.0\n" + "Items: 3\n" + "- " + figure.info() + "\n" + "- " + stamp.info() + "\n" + "- " + card.info() + "\n";

        assertEquals(expected, named.toString());
    }


    /**
     * Tests that a nested collection is listed by its own info line.
     */
    public void testToStringWithSubcollection() {
        Collection<Collectible> sub = new Collection<>("Sub", "s");
        sub.add(card);
        collection.add(sub);

        String text = collection.toString();
        assertTrue(text.contains("Items: 1\n"));
        assertTrue(text.contains("- " + sub.info() + "\n"));
    }
}
