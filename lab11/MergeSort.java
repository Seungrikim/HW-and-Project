import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /**
     * Returns a queue of queues that each contain one item from items.
     *
     * This method should take linear time.
     *
     * @param   items  A Queue of items.
     * @return         A Queue of queues, each containing an item from items.
     *
     */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {

        Queue<Queue<Item>> qOfqs = new Queue<>();
        for (Item item : items) {
            Queue<Item> q = new Queue<>();
            q.enqueue(item);
            qOfqs.enqueue(q);
        }
        return qOfqs;

        /*Queue<Queue<Item>> qOfq = new Queue<>();
        if (items.isEmpty()) {
            return null;
        } else {
            Queue<Item> item = new Queue<>();
            item.enqueue(items.dequeue());
            qOfq.enqueue(item);
        }
        return qOfq;*/
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> merged = new Queue<>();
        while (!q1.isEmpty() && q2.isEmpty()) {
            merged.enqueue(getMin(q1, q2));
        }
        return merged;

        /*Queue<Item> result = new Queue<>();
        if (q2.isEmpty() && q1.isEmpty()) {
            return null;
        } else {
            if (getMin(q1,q2).equals(q1.peek())) {
                result.enqueue(q1.dequeue());
            } else {
                result.enqueue(q2.dequeue());
            }
            /*q1.enqueue(q2.dequeue());
            mergeSortedQueues(q1, q2);
        }
        return result;*/
    }

    /**
     * Returns a Queue that contains the given items sorted from least to greatest.
     *
     * This method should take roughly nlogn time where n is the size of "items"
     * this method should be non-destructive and not empty "items".
     *
     * @param   items  A Queue to be sorted.
     * @return         A Queue containing every item in "items".
     *
     */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        if (items == null || items.isEmpty()) {
            return items;
        }

        Queue<Queue<Item>> qOfqs = makeSingleItemQueues(items);

        while (qOfqs.size() > 1) {
            Queue<Item> q1 = qOfqs.dequeue();
            Queue<Item> q2 = qOfqs.dequeue();
            Queue<Item> merged = mergeSortedQueues(q1, q2);
            qOfqs.enqueue(merged);
        }

        return qOfqs.dequeue();
    }
}
