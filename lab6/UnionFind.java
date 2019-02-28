import org.junit.Test;
import java.util.Arrays;
import java.util.ArrayList;

public class UnionFind {

    // TODO - Add instance variables?
     private int[] parent;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        parent = new int[n];
        Arrays.fill(parent, -1);
        /*for (int i = 0; i < n ; i++) {
            parent[i] = i;
        }*/
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex < 0 || vertex >= parent.length) {
            throw new IllegalArgumentException("not a valid vertex");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        /* int root = find(v1);
        return -1 * parent[root]
         */
        while (parent(v1) > 0) {
            v1 = parent(v1);
        }
        return -1 * parent[v1];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        return parent[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2) ;
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        /*
        int rootv1 = find(v1);
        int rootv2 = find(v2);
        if( rootV1 != rootV2) {
            if(paretm[rootV1] < parent[rootV2] {
                parent[rootV1] += parent[rootV2]
                parent[rootV2] = rootV2;
                } else 반대
         */
        if(!connected(v1, v2)) {
            if (sizeOf(v1) > sizeOf(v2)) {
                parent[find(v1)] += parent[find(v2)];
                parent[find(v2)] = find(v1);
            } else if (sizeOf(v1) <= sizeOf(v2)) {
                parent[find(v2)] += parent[find(v1)];
                parent[find(v1)] = find(v2);
            }
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        while (parent(vertex) > 0) {
            int temp = vertex;
            vertex = parent(vertex);
            parent[temp] = parent(vertex);
        }
        return vertex;
    }

        /* validate(vertex);
           if(parent[vertex] < 0) {
                reutn vertex;
           } else {
                    ArrayList<Integer> to Compress = new ArrayList<~>();
                    int root = vertex;
                    while(parenet[root] >= 0) {
                        toCompress.add(root);
                        root = parent[root];
                    }
                    for (int i :toCompress) {
                        data[i] = root;
                        */

}
