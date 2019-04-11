import edu.princeton.cs.algs4.Queue;
import org.junit.Assert;
import org.junit.Assert.*;

import org.junit.Test;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<String> q = new Queue<>();
        q.enqueue("61b");
        q.enqueue("Josh Hug");
        q.enqueue("apple");
        q.enqueue("Chancellor Christ");

        Queue<String> sorted = QuickSort.quickSort(q);

        Assert.assertTrue(isSorted(sorted));
    }

    @Test
    public void testMergeSort() {
        Queue<String> q = new Queue<>();
        q.enqueue("61b");
        q.enqueue("Josh Hug");
        q.enqueue("apple");
        q.enqueue("Chancellor Christ");

        Queue<String> sorted = MergeSort.mergeSort(q);

        Assert.assertTrue(isSorted(sorted));

    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
