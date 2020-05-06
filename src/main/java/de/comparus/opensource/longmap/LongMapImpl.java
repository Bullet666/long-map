package de.comparus.opensource.longmap;


import java.util.Arrays;

public class LongMapImpl<V> implements LongMap<V> {

    private static final int CAPACITY = 16;
    private final Node<V>[] cells;
    private int size = 0;
    private long[] keys;
    private Object[] values;

    public LongMapImpl() {
        this(CAPACITY);
    }

    public LongMapImpl(int capacity) {
        capacity = Math.max(CAPACITY, capacity);
        this.cells = new Node[CAPACITY];
        this.keys = new long[capacity];
        this.values = new Object[capacity];
    }

    public V put(long key, V value) {
        Node<V> node = new Node<>(key, value, null);

        int cell = getHash(key);

        Node<V> existingNode = cells[cell];
        if (existingNode == null) {
            cells[cell] = node;
            addKeyAndValue(key, value);
            size++;
        } else {
            while (existingNode.next != null) {
                if (existingNode.key.equals(key)) {
                    replaceValue(existingNode.value, value);
                    existingNode.value = value;
                    return value;
                }
                existingNode = existingNode.next;
            }
            if (existingNode.key.equals(key)) {
                replaceValue(existingNode.value, value);
                existingNode.value = value;
            } else {
                existingNode.next = node;
                addKeyAndValue(key, value);
                size++;
            }
        }

        return value;
    }

    public V get(long key) {
        int cell = getHash(key);
        Node<V> existingNode = cells[cell];
        V value = null;
        while (existingNode != null) {
            if (existingNode.key == key) {
                value = existingNode.value;
                break;
            }
            existingNode = existingNode.next;
        }

        return value;
    }

    public V remove(long key) {
        int cell = getHash(key);
        Node<V> existingNode = cells[cell];
        V value = null;
        Node<V> previousNode = null;
        while (existingNode != null) {
            if (existingNode.key == key) {
                if (previousNode == null) {
                    cells[cell] = existingNode.next;
                } else {
                    previousNode.next = existingNode.next;
                }
                value = existingNode.value;
                break;
            }
            previousNode = existingNode;
            existingNode = existingNode.next;
        }

        if (value != null) {
            removeKeyAndValue(key);
            size--;
        }

        return value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(long key) {
        return Arrays.stream(keys).anyMatch(k -> k == key);
    }

    public boolean containsValue(V value) {
        return Arrays.asList(values).contains(value);
    }

    public long[] keys() {
        return Arrays.copyOf(keys, size);
    }

    @SuppressWarnings("unchecked")
    public <V> V[] values() {
        return (V[]) Arrays.copyOf(values, size);
    }

    public long size() {
        return size;
    }

    public void clear() {
        size = 0;
        Arrays.fill(cells, null);
    }

    private void removeKeyAndValue(long key) {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == key) {
                if (i < keys.length - 1) {
                    System.arraycopy(keys, i + 1, keys, i, keys.length - i - 1);
                    System.arraycopy(values, i + 1, values, i, values.length - i - 1);
                }
                break;
            }
        }
    }

    private void addKeyAndValue(long key, V value) {
        if (keys.length == size) {
            int newCapacity = size + (size >> 1);
            keys = Arrays.copyOf(keys, newCapacity);
            values = Arrays.copyOf(values, newCapacity);
        }
        keys[size] = key;
        values[size] = value;
    }

    private void replaceValue(V existValue, V newValue) {
        for (int i = 0; i < values.length; i++) {
            if (existValue.equals(values[i])) {
                values[i] = newValue;
            }
        }
    }

    private static int getHash(long key) {
        return Long.hashCode(key) & (CAPACITY - 1);
    }

    private static class Node<V> {
        private final Long key;

        private V value;
        private Node<V> next;

        public Node(Long key, V value, Node<V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
