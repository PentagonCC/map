import org.example.CustomHashMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HashMapTest {
    private CustomHashMap<String, Integer> map = new CustomHashMap<>();

    //PUT метод
    @Test
    public void classicPut() {
        Integer result = map.put("key1", 100);
        assertNull(result);
        assertEquals(100, map.get("key1"));
        assertEquals(1, map.getSize());
    }

    @Test
    public void putExistingKey() {
        map.put("key1", 100);
        Integer oldValue = map.put("key1", 200);
        assertEquals(100, oldValue);
        assertEquals(200, map.get("key1"));
        assertEquals(1, map.getSize()); // Размер не меняется
    }

    @Test
    public void putNullKey() {
        Integer result = map.put(null, 42);
        assertNull(result);
        assertEquals(42, map.get(null));
        assertEquals(1, map.getSize());
    }

    @Test
    public void sameHashDifferentKeys() {
        map.put("KING", 100);
        map.put("BLAKE", 10);
        assertEquals(2, map.getSize());
        assertEquals(100, map.get("KING"));
        assertEquals(10, map.get("BLAKE"));
    }

    @Test
    public void testResize() {
        for (int i = 0; i < 20; i++) {
            map.put("sfdf" + i, i);
        }

        assertEquals(20, map.getSize());
    }

    @Test
    public void keyWithSameReference() {
        String key = "gfrege";
        map.put(key, 100);
        Integer value = map.put(key, 200);
        assertEquals(100, value);
        assertEquals(200, map.get(key));
    }

    //GET метод
    @Test
    public void existingKey() {
        map.put("fff", 123);
        Integer result = map.get("fff");
        assertEquals(123, result);
    }

    @Test
    public void notExistingKey() {
        Integer result = map.get("chtoto");
        assertNull(result);
    }

    @Test
    public void getAfterCollision() {
        map.put("KING", 100);
        map.put("BLAKE", 10);
        Integer value1 = map.get("KING");
        Integer value2 = map.get("BLAKE");
        assertEquals(100, value1);
        assertEquals(10, value2);
    }

    @Test
    public void getNullKey() {
        map.put(null, 345);
        Integer result = map.get(null);
        assertEquals(345, result);
    }

    //
    @Test
    public void testGet_FromEmptyMap_ReturnsNull() {
        Integer result = map.get("any");
        assertNull(result);
    }

    @Test
    public void updateValue() {
        map.put("key", 100);
        Integer result = map.get("key");
        assertEquals(100, result);
        map.put("key", 200);
        result = map.get("key");
        assertEquals(200, result);
    }

    //REMOVE метод
    @Test
    public void removeExistingKey() {
        map.put("iii", 555);
        Integer removeResult = map.remove("iii");
        assertEquals(555, removeResult);
        assertNull(map.get("iii"));
        assertEquals(0, map.getSize());
    }

    @Test
    public void removeFirstInChain() {
        map.put("first", 1);
        map.put("second", 2);
        map.put("third", 3);
        Integer removed = map.remove("first");
        assertEquals(1, removed);
        assertNull(map.get("first"));
        assertEquals(2, map.get("second"));
        assertEquals(3, map.get("third"));
    }

    @Test
    public void removeMiddleInChain() {
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        Integer removed = map.remove("b");
        assertEquals(2, removed);
        assertNull(map.get("b"));
        assertEquals(1, map.get("a"));
        assertEquals(3, map.get("c"));
    }

    @Test
    public void removeLastInChain() {
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        Integer removed = map.remove("c");
        assertEquals(3, removed);
        assertNull(map.get("c"));
        assertEquals(1, map.get("a"));
        assertEquals(2, map.get("b"));
    }

    @Test
    public void removeNotExistingKey() {
        Integer result = map.remove("nonexistent");
        assertNull(result);
        assertEquals(0, map.getSize());
    }

    @Test
    public void removeNullKey() {
        map.put(null, 123);
        Integer removedValue = map.remove(null);
        assertEquals(123, removedValue);
        assertNull(map.get(null));

    }

    @Test
    public void removeSingleElement() {
        map.put("odin", 999);
        Integer removed = map.remove("odin");
        assertEquals(999, removed);
        assertNull(map.get("single"));
        assertEquals(0, map.getSize());
    }

    @Test
    public void removeAllElements() {
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        map.remove("a");
        map.remove("b");
        map.remove("c");

        assertEquals(0, map.getSize());
    }

}
