import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K,V> {

    private int sizeOfTree;
    private Node root;
    private class Node {
        private K key;
        private V val;
        private Node left, right;

        public Node(K key, V val, Node left, Node right) {
            this.key = key;
            this.val = val;
            this.left = left;
            this.right = right;
        }

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    public BSTMap() {
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        sizeOfTree = 0;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        return getHelper(root, key) != null;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        if (getHelper(root, key) != null) {
            return getHelper(root, key).val;
        }
        return null;
    }

    private Node getHelper(Node r, K key) {
        if (r == null || key == null) {
            return null;
        }
        int compare = r.key.compareTo(key);
        if (compare < 0) {
            return getHelper(r.right, key);
        } else if (compare > 0) {
            return getHelper(r.left, key);
        } else {
            return r;
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return sizeOfTree;
    }

    /*public int sizeHelper(Node r) {
        if (r == null ) {
            return 0;
        } else {
            return r.size;
        }
    }*/

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            return;
        }
        root = putHelper(key, value, root);
    }

    public Node putHelper(K key, V value, Node r) {
        if (r == null) {
            sizeOfTree++;
            return new Node(key, value);
        }
        int compare = r.key.compareTo(key);
        if (compare > 0) {
            putHelper(key, value, r.left);
        } else if (compare < 0) {
            putHelper(key, value, r.right);
        } else {
            r.val = value;
        }
        return r;
    }

    /*prints out your BSTMap in order of increasing Key.*/
    public void printInOrder() {
        StringBuilder toPrint = new StringBuilder();
        if (root != null) {
            printHelper(root, toPrint);
            toPrint.deleteCharAt(toPrint.length() - 1);
        }
        System.out.println(toPrint);
    }

    public void printHelper(Node r, StringBuilder s) {
        if (r != null) {
            printHelper(r.left, s);
            s.append(r.key).append(" ");
            printHelper(r.right, s);
        }
    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("No method implemented");
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("No method implemented");
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("No method implemented");
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("No method implemented");
    }
}
