import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private static final int DEFAULT_INIT_SIZE = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    private ArrayList<Entry>[] map;
    private int size;
    private double loadFactor;
    private HashSet<K> keys;



    public MyHashMap() {
        this(DEFAULT_INIT_SIZE, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        this.map = (ArrayList<Entry>[]) new ArrayList[initialSize];
        this.size = 0;
        this.loadFactor = loadFactor;
        this.keys = new HashSet<K>();
    }
    //sk
    @Override
    public void clear() {
        this.map = (ArrayList<Entry>[]) new ArrayList[DEFAULT_INIT_SIZE];
        this.size = 0;
        this.keys = new HashSet<K>();
    }

    ///sk
    @Override
    /** Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        return getEntry(key) != null;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */

    //sk
    @Override
    public V get(K key) {
        Entry e = getEntry(key);
        if (e == null) {
            return null;
        }
        return e.value;
    }

    /** Returns the number of key-value mappings in this map. */
    //sk
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */

    @Override
    public void put(K key, V value) {
        Entry e = getEntry(key);
        if (e != null) {
            e.value = value;
            return;
        }

        if (((double) this.size) / this.map.length > this.loadFactor) {
            this.rehash(this.map.length * 2);
        }
        this.size++;
        this.keys.add(key);
        int idx = hash(key);
        ArrayList<Entry> bucket = this.map[idx];
        if( bucket == null) {
            bucket = new ArrayList<Entry>();
            map[idx] = bucket;
        }
        bucket.add(new Entry(key, value));
    }

    @Override
    /** Returns a Set view of the keys contained in this map. */
    //sk
    public Set<K> keySet() {
        return this.keys;
    }

    @Override
    //sk
    public Iterator<K> iterator() {
        return this.keys.iterator();
    }

    ///sk
    private int hash(K key) {
        return hash(key, this.map.length);
    }

    //sk
    private int hash(K key, int mapSize) {
        return Math.floorMod(key.hashCode(), mapSize);
    }

    //sk
    private class Entry {
        K key;
        V value;
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    //sr
    private void rehash(int targetSize) {
        ArrayList<Entry>[] newMap = (ArrayList<Entry>[]) new ArrayList[targetSize];
        for (K key : this.keys) {
            int idx = hash(key, newMap.length);
            ArrayList<Entry> bucket = newMap[idx];
            if (bucket == null) {
                bucket = new ArrayList<Entry>();
                newMap[idx] = bucket;
            }
            bucket.add(getEntry(key));
        }
        this.map = newMap;
    }

    private Entry getEntry(K key) {
        int idx = hash(key);
        ArrayList<Entry> bucket = this.map[idx];
        if(bucket != null) {
            for (Entry entry : bucket) {
                if(entry.key.equals(key)) {
                    return entry;
                }
            }
        }
        return null;
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
