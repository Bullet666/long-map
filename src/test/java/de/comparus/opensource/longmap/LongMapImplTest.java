package de.comparus.opensource.longmap;

import de.comparus.opensource.longmap.entity.Foo;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LongMapImplTest {

    LongMap<Foo> longMap;

    @Before
    public void fillMap() {
        longMap = new LongMapImpl<>();
        longMap.put(1, new Foo(1, "foo1", true));
    }

    @Test
    public void shouldFindValue() {
        /// when
        Foo foo = longMap.get(1);

        /// then
        assertNotNull(foo);
    }

    @Test
    public void shouldNotFindValue() {
        /// when
        Foo foo = longMap.get(3);

        /// then
        assertNull(foo);
    }

    @Test
    public void shouldReplaceValueAndLeaveSameSize() {
        /// when
        longMap.put(1, new Foo(2, "foo2", false));
        Foo foo = longMap.get(1);

        /// then
        assertEquals(2, foo.getL());
        assertEquals(1, longMap.size());
        assertEquals(1, longMap.keys().length);
        assertEquals(1, longMap.values().length);
    }

    @Test
    public void shouldRemoveAndRetrieveValueAndDecreaseSizeKeysAndValues() {
        /// when
        Foo foo = longMap.remove(1);

        /// then
        assertEquals(1, foo.getL());
        assertEquals(0, longMap.size());
        assertEquals(0, longMap.keys().length);
        assertEquals(0, longMap.values().length);
    }

    @Test
    public void shouldNotRemoveAndRetrieveNullAndLeaveSameSizeKeysAndValues() {
        /// when
        Foo foo = longMap.remove(2);

        /// then
        assertNull(foo);
        assertEquals(1, longMap.size());
        assertEquals(1, longMap.keys().length);
        assertEquals(1, longMap.values().length);
    }

    @Test
    public void shouldBeNotEmpty() {
        /// when
        boolean empty = longMap.isEmpty();

        /// then
        assertFalse(empty);
    }

    @Test
    public void shouldContainsKey() {
        /// when
        boolean containsKey = longMap.containsKey(1);

        /// then
        assertTrue(containsKey);
    }

    @Test
    public void shouldNotContainsKey() {
        /// when
        boolean containsKey = longMap.containsKey(2);

        /// then
        assertFalse(containsKey);
    }

    @Test
    public void shouldContainsValue() {
        /// when
        boolean containsValue = longMap.containsValue(new Foo(1, "foo1", true));

        /// then
        assertTrue(containsValue);
    }

    @Test
    public void shouldNotContainsValue() {
        /// when
        boolean containsValue = longMap.containsValue(new Foo(2, "foo2", false));

        /// then
        assertFalse(containsValue);
    }

    @Test
    public void shouldClearMap() {
        /// when
        longMap.clear();

        /// then
        assertEquals(0, longMap.size());
        assertEquals(0, longMap.keys().length);
        assertEquals(0, longMap.values().length);
    }
}
