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
    private MyTrieSet labTrie;
    private HashMap<String, LinkedList<Node>> prefixMap;
    private List<Node> str;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        prefixMap = new HashMap<>();
        nodes = this.getNodes();
        labTrie = new MyTrieSet();
        str = new LinkedList<>();
        /*for(Node node : nodes) {
            if (node.name() != null) {
                String cleaned = cleanString(node.name());
                if (prefixMap.containsKey(cleaned)) {
                    prefixMap.get(cleaned).addLast(node);
                    labTrie.add(cleaned);
                } else {
                    LinkedList<Node> fullName = new LinkedList();
                    fullName.addLast(node);
                    prefixMap.put(cleaned, fullName);
                    labTrie.add(cleaned);
                }
            }
        }*/
        prefixConstuctor();
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
        List list = new LinkedList();
        List<String> trieList = labTrie.keysWithPrefix(prefix);
        for (int i = 0; i < trieList.size(); i++) {
            LinkedList<Node> duplicate = prefixMap.get(trieList.get(i));
            for (int j = 0; j < duplicate.size(); j++) {
                ((LinkedList) list).addLast(duplicate.get(j).name());
            }
        }
        return list;
    }

    private void prefixConstuctor() {
        for(Node node : nodes) {
            if (node.name() != null) {
                String cleaned = cleanString(node.name());
                if (cleaned == "") {
                    str.add(node);
                } else if (prefixMap.containsKey(cleaned)) {
                    prefixMap.get(cleaned).addLast(node);
                    labTrie.add(cleaned);
                } else {
                    LinkedList<Node> fullName = new LinkedList();
                    fullName.addLast(node);
                    prefixMap.put(cleaned, fullName);
                    labTrie.add(cleaned);
                }
            }
        }
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
        String cleanedLocation = cleanString(locationName);
        List listOfplace = new LinkedList();
        List<String> trieList = labTrie.keysWithPrefix(cleanedLocation);
        if (cleanedLocation == "") {
            for (int k = 0; k < str.size(); k++) {
                ((LinkedList) listOfplace).addLast(formatter(str.get(k)));
            }
            return listOfplace;
        }
        for (int i = 0; i < trieList.size(); i++) {
            if (trieList.get(i).equals(cleanedLocation)) {
                LinkedList<Node> duplicate = prefixMap.get(trieList.get(i));
                for (int j = 0; j < duplicate.size(); j++) {
                    ((LinkedList) listOfplace).addLast(formatter(duplicate.get(j)));
                }
            }
        }
        return listOfplace;
    }

    private Map<String, Object> formatter (Node n) {
        Map<String, Object> newFormat = new HashMap<>();
        newFormat.put("name",n.name());
        newFormat.put("lon", n.lon());
        newFormat.put("id", n.id());
        newFormat.put("lat", n.lat());
        return newFormat;
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
