package bearmaps.lab9;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class MyTrieSet implements TrieSet61B{
    private Node root;

    public class Node {
        private Character c;
        HashMap<Character, Node> map;
        boolean isKey;
        /*public Node(char val, boolean isKey) {
            map = new HashMap();
            //this.key = val;
            this.isKey = isKey;
        }*/
        public Node(Character c, boolean isKey) {
            this.c = c;
            this.isKey = isKey;
            map = new HashMap<>();
        }
    }

    public MyTrieSet() {
        clear();
    }

    /** Returns true if the Trie contains KEY, false otherwise */
    @Override
    public boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                return false;
            }
            curr = curr.map.get(c);
        }
        return curr.isKey;
    }

    /** Inserts string KEY into Trie */
    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }

    /** Clears all items out of Trie */
    @Override
    public void clear() {
        root = new Node(null, false);
    }

    /** Returns a list of all words that start with PREFIX */
    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> result = new ArrayList<>();
        Node curr = root;
        for (int i = 0, n = prefix.length(); i < n; i++) {
            char c = prefix.charAt(i);
            if (!curr.map.containsKey(c)) {
                return result;
            }
            curr = curr.map.get(c);
        }
        collect(prefix, result, curr);
        return result;
    }

    private void collect(String prefix, List<String> lst, Node n) {
        if (n.isKey) {
            lst.add(prefix);
        }
        for (Character c : n.map.keySet()) {
            collect(prefix + c, lst, n.map.get(c));
        }
    }

    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }

}
