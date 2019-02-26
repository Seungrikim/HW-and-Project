package es.datastructur.synthesizer;
import java.util.Arrays;
import java.util.Iterator;

//TODO: Make sure to that this class and all of its methods are public
//TODO: Make sure to add the override tag for all overridden methods
//TODO: Make sure to make this class implement BoundedQueue<T>

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        rb = (T[]) new Object[capacity];
        this.first = 0;
        this.last = 0;
        this.fillCount = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update
        //       last.
        if (capacity() == fillCount()) {
            throw new RuntimeException("Ring Buffer overflow");
        } else {
            rb[last] = x;
            last = (last + 1) % capacity();
            fillCount += 1;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and
        //       update first.
        if (fillCount() == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        } else {
            T result = rb[first];
            rb[first] = null;
            first = (first + 1) % capacity();
            fillCount -= 1;
            return result;
        }
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should
        //       change.
        return rb[first];
    }

    /**
     * return size of the buffer
     */
    @Override
    public int capacity() {
        return rb.length;
    }

    /**
     * return number of items currently in the buffer
     */
    @Override
    public int fillCount() {
        return fillCount;
    }

    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {

        private int iteratorFirst;
        private int iteratorFillCount;
        private T[] iteratorRb;

        public ArrayRingBufferIterator() {
            iteratorRb = rb;
            iteratorFirst = first;
            iteratorFillCount = fillCount;
        }

        @Override
        public boolean hasNext() {
            return iteratorFillCount > 0;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T itemInposition = iteratorRb[iteratorFirst];
                if (iteratorFillCount > 1) {
                    iteratorFirst = (iteratorFirst + 1) % capacity();
                }
                iteratorFillCount -= 1;
                return itemInposition;
            }
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        ArrayRingBuffer<T> newBuffer = (ArrayRingBuffer) o;
        Iterator object1 = iterator();
        Iterator object2 = newBuffer.iterator();
        if (fillCount() != newBuffer.fillCount()) {
            return false;
        }
        while (object1.hasNext()) {
            if (object1.next() != object2.next()) {
                return false;
            }
        }
        return true;
    }

    // TODO: When you get to part 4, implement the needed code to support
    //       iteration and equals.
}
    // TODO: Remove all comments that say TODO when you're done.
