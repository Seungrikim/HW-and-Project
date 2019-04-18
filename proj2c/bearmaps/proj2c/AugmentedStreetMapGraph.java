package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.lab9.MyTrieSet;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.WeirdPointSet;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    private List<Node> nodes;
    private WeirdPointSet wps;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        nodes = this.getNodes();
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        ArrayList points = new ArrayList();
        HashMap<Point, Node> map = new HashMap<>();
        for(Node node : nodes) {
            if (node.name() == null) {
                Point point = new Point(node.lon(), node.lat());
                map.put(point, node);
                points.add(point);
            }
        }
        wps = new WeirdPointSet(points);
        return map.get(wps.nearest(lon, lat)).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        MyTrieSet trie = new MyTrieSet();
        List list = new LinkedList();
        HashMap<String, LinkedList<Node>> map = new HashMap<>();
        List trieList = new LinkedList();
        LinkedList<Node> duplicate = new LinkedList();
        for(Node node : nodes) {
            if (node.name() != null) {
                String cleaned = cleanString(node.name());
                /*if (cleaned == "") {
                    ((LinkedList) empty).addLast(node.name());
                }*/
                if (map.containsKey(cleaned)) {
                    duplicate = map.get(cleaned);
                    duplicate.addLast(node);
                    map.replace(cleaned, duplicate);
                    trie.add(cleaned);
                } else {
                    duplicate.addLast(node);
                    map.put(cleaned, duplicate);
                    trie.add(cleaned);
                }
               /*if (map.containsKey(cleaned)) {
                   trie.add((String) map.get(cleaned));
                } else {
                   map.put(cleaned, node.name());
                   trie.add((String) map.get(cleaned));
               }*/
            }
        }
        /*if (prefix == " ") {
            return empty;
        }*/
        trieList = trie.keysWithPrefix(prefix);
        for (int i = 0; i < trieList.size(); i++) {
            if (map.get(trieList.get(i)).size() > 1) {
                while (!map.get(trieList.get(i)).isEmpty()) {
                    ((LinkedList) list).addLast(map.get(trieList.get(i)).removeFirst());
                }
            } else {
                ((LinkedList) list).addLast(map.get(trieList.get(i)));
            }

        }
        return list;

    /*    if (name != null) {
            cleanname =;
            if map.contain(cleanname);
            map.get.(cleanaem).add(N);

        } else map.put(cleanname) and tried*/
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        return new LinkedList<>();
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
