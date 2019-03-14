package bearmaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private int size;
    private ArrayList<PriorityNode> heap;
    private HashMap<T, Integer> container;

    public ArrayHeapMinPQ() {
        size = 0;
        heap = new ArrayList<>();
        heap.add(0, null);
        container = new HashMap<>();
    }

    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentExceptionb if item is already present. */
    @Override
    public void add(T item, double priority) {
        /*if (using hashtable to keep track the items) {
            throw new IllegalArgumentException("Already present given item");
        }*/
        if (contains(item)) {
            throw new IllegalArgumentException("Already present given item");
        }
        PriorityNode newNode = new PriorityNode(item, priority);
        size += 1;
        heap.add(size, newNode);
        swimUp(size);
        container.put(item, heap.indexOf(newNode));
    }

    /* Returns true if the PQ contains the given item. */
    @Override
    public boolean contains(T item) {
        return container.containsKey(item);
    }

    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T getSmallest() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }
        return heap.get(1).getItem();
    }

    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }
        T smallest = heap.get(1).getItem();
        swapItem(1, size);
        container.remove(heap.get(size).item, size);
        heap.remove(size);
        size -= 1;
        // need to search down to find right position
        swimDown(1);

        return smallest;

    }

    /* Returns the number of items in the PQ. */
    @Override
    public int size() {
        return size;
    }

    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    @Override
    public void changePriority(T item, double priority) {
        // using contain to find the item, and get the index-> swap with last positioon-> swimup
        int index = container.get(item);
        heap.get(index).priority = priority;
        ///swapItem(index, size);
        if (parent(index) != 0 && priority < heap.get(parent(index)).priority) {
            swimUp(index);
        } else {
            swimDown(index);
        }
    }

    /*Evaluate position of their parent(package private)*/
    int parent(int i) {
        return i / 2;
    }

    /* Evaluate position of left child(package private)*/
    int leftChild(int i) {
        return i * 2;
    }

    /* Evaluate position of right child(package private)*/
    int rightChild(int i) {
        return i * 2 + 1;
    }

    /*return true if leftchild is bigger than rightchild(package private)*/
    int minimumChild(int i) {
        // what if there is no left or right child exist?
        if (rightChild(i) > size && leftChild(i) == size) {
            return leftChild(i);
        }
        if (heap.get(leftChild(i)).getPriority() > heap.get(rightChild(i)).getPriority()) {
            return rightChild(i);
        } else {
            return leftChild(i);
        }
    }

    /*swap the specific postions of the items(package private)*/
    void swapItem(int prio1, int prio2) {
        PriorityNode tempNode1 = heap.get(prio1);
        //PriorityNode tempNode2 = heap.get(prio2);
        //int position1 = heap.indexOf(heap.get(prio1));
        //int position2 = heap.indexOf(heap.get(prio2));
        container.replace(heap.get(prio1).item, prio2);
        container.replace(heap.get(prio2).item, prio1);
        //heap.remove(prio1);
        heap.set(prio1, heap.get(prio2));
        //heap.remove(prio2);
        heap.set(prio2, tempNode1);
        /*usign set method in arraylist*/
    }

    /*swim up to the heap until find right position(package private)*/
    void swimUp(int i) {
        if (i == 1 || heap.get(i).getPriority() >= heap.get(parent(i)).getPriority()) {
            return;
        } else {
            swapItem(i, parent(i));
        }
        swimUp(parent(i));
    }

    /*swim down to the heap until find right position(package private)*/
    void swimDown(int i) {
        if ((leftChild(i) > size && rightChild(i) > size)
                || heap.get(i).getPriority() <= heap.get(minimumChild(i)).getPriority()) {
            return;
        } else {
            swapItem(i, minimumChild(i));
            swimDown(minimumChild(i));
        }
    }

    private class PriorityNode {
        private T item;
        private double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(int priority) {
            this.priority = priority;
        }
    }
}

