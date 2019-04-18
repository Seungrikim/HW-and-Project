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
        HashMap map = new HashMap<>();
        List trieList = new LinkedList();
        for(Node node : nodes) {
            if (node.name() != null) {
               String cleaned = cleanString(node.name());
               map.put(cleaned, node.name());
               trie.add(cleaned);
               /*if (map.containsKey(cleaned)) {
                   trie.add((String) map.get(cleaned));
                } else {
                   map.put(cleaned, node.name());
                   trie.add((String) map.get(cleaned));
               }*/
            }
        }
        /*trieList = trie.keysWithPrefix(prefix);
        for (int i!trieList.isEmpty()) {
            trieList.remove
        }*/

        return trie.keysWithPrefix(cleanString(prefix));

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
