import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private static final int DEFAULT_SIZE = 16;
    private static final double DEFAULT_LF = 0.75;
    private int initialSize;
    private double loadFactor;
    private int size = 0;
    private int threshold = (int) (DEFAULT_SIZE * DEFAULT_LF);
    private HashSet set = new HashSet();
    private ArrayList[] bucket = new ArrayList[DEFAULT_SIZE];


    public MyHashMap() {
        this(DEFAULT_SIZE, DEFAULT_LF);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_LF);
        bucket = new ArrayList[initialSize];
        threshold = (int) (initialSize * DEFAULT_LF);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        bucket = new ArrayList[initialSize];
        threshold = (int) (initialSize * loadFactor);
    }

    @Override
    public void clear() {
        bucket = new ArrayList[initialSize];
        size = 0;
    }

    @Override
    /** Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        return set.contains(key);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */

    @Override
    public V get(K key) {
        if (containsKey(key)) {
            int hash = key.hashCode();
            int index = hash % initialSize;
            for (int i = 0; i < bucket[index].size(); i++) {
                Entry temp = (Entry) bucket[index].get(i);
                if (temp.key.equals(key)) {
                    return temp.val;
                }
            }
        }
        return null;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return this.size();
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */

    @Override
    public void put(K key, V value) {
        if (size > threshold) {
            resize();
        }
        int hash = key.hashCode();
        int index = hash % initialSize;
        if (bucket[index] == null) {
            bucket[index] = new ArrayList();
            bucket[index].add(new Entry(key, value));
            size++;
        } else {
            if (containsKey(key)) {
                for (int i = 0; i < bucket[index].size(); i++) {
                    Entry temp = (Entry) bucket[index].get(i);
                    if (temp.key.equals(key)) {
                        temp.val = value;
                    }
                }
            } else {
                bucket[index].add(new Entry(key, value));
                size++;
            }
        }
    }

    @Override
    /** Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        return set;
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHashMapIter();
    }

    private void resize() {
        initialSize = initialSize * 2;
        threshold = initialSize * threshold;
        int hash;
        int index;
        ArrayList[] temp = new ArrayList[initialSize];
        for (int i = 0; i < initialSize / 2; i++) {
            for (int j = 0; j < bucket[i].size(); j++) {
                Entry newEntry = (Entry) bucket[i].get(j);
                hash = newEntry.key.hashCode();
                index = hash % initialSize;
                temp[index].add(newEntry);
            }
        }
    }

    private class Entry {
        private K key;
        private V val;
        Entry(K k, V v) {
            this.key = k;
            this.val = v;
        }
    }
    private class MyHashMapIter implements Iterator<K> {

        /**
         * Create a new ULLMapIter by setting cur to the first node in the
         * linked list that stores the key-value pairs.
         */

        private int bin;
        private ArrayList cur;
        private MyHashMapIter() {
        }
        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public K next() {
            return null;
        }
    }
    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }
}
