package es.datastructur.synthesizer;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer<>(10);
        ///Object arb2 = new ArrayRingBuffer<>(10);

        /**ArrayRingBuffer Basic Test*/
        assertEquals(10, arb.capacity());
        assertEquals(0, arb.fillCount());

        arb.enqueue(0);
        arb.enqueue(1);

        assertEquals(10, arb.capacity());
        assertEquals(2, arb.fillCount());
        assertEquals(0, arb.peek());
        assertEquals(0, arb.dequeue());
        assertEquals(1, arb.peek());
        assertEquals(1,arb.fillCount());
        arb.dequeue();

        for (int i = 0; i < 4; i++) {
            arb.enqueue(i);
        }

        assertEquals(4, arb.fillCount());
        assertEquals(0, arb.peek());
        assertEquals(0,arb.dequeue());
        assertEquals(1,arb.dequeue());
        assertEquals(2, arb.dequeue());
        assertEquals(1,arb.fillCount());
        assertEquals(3, arb.dequeue());
        assertEquals(0, arb.fillCount());
        assertSame("Ring Buffer underflow", arb.peek());

        /*for (int i= 0 ; i < 4; i++) {
            arb.dequeue();
        }

        assertEquals(3, arb.fillCount());
        assertEquals(6,arb.peek());
        assertEquals(6,arb.dequeue());
        assertEquals(7,arb.dequeue());
        assertEquals(8,arb.dequeue());
        assertEquals(0, arb.fillCount());
        assertEquals(null,arb.dequeue());*/
    }

    @Test
    public void testARBIterator() {
        ArrayRingBuffer<String> arb = new ArrayRingBuffer<>(4);
        arb.enqueue("one");
        arb.enqueue("two");
        arb.enqueue("three");
        arb.enqueue("four");

        Iterator<String> arbi = arb.iterator();
        assertTrue(arbi.hasNext());
        assertEquals("one", arbi.next());
        assertTrue(arbi.hasNext());
        assertEquals("two", arbi.next());
        assertTrue(arbi.hasNext());
        assertEquals("three", arbi.next());
        assertTrue(arbi.hasNext());
        assertEquals("four", arbi.next());
        assertFalse(arbi.hasNext());
    }
    @Test
    public void testEquals() {
        ArrayRingBuffer<String> arb1 = new ArrayRingBuffer<>(3);
        //assertFalse(arb1.equals(null)); //null check
        //assertFalse(arb1.equals("hi")); //class check

        ArrayRingBuffer<String> arb2 = new ArrayRingBuffer<>(3);
        ArrayRingBuffer<String> arb3 = new ArrayRingBuffer<>(2);
        //assertTrue(arb1.equals(arb2)); //two empty queues equal
        //assertFalse(arb1.equals(arb3)); //different capacities check

        ArrayRingBuffer<String> orig1 = arb1;
        ArrayRingBuffer<String> orig2 = arb2;

        arb1.enqueue("one");
        arb2.enqueue("two");
        assertFalse(arb1.equals(arb2)); //general not equals check

        arb2.dequeue();
        arb2.enqueue("one");
        assertTrue(arb1.equals(arb2)); //general equals check

        arb1.enqueue("two");
        arb2.enqueue("two");
        assertTrue(arb1.equals(arb2)); //general equals check (length > 1)

        arb2.enqueue("three");
        assertFalse(arb1.equals(arb2)); //different lengths check

        arb1.enqueue("two");
        assertFalse(arb1.equals(arb2)); //general not equals check (length > 1)

        assertSame(orig1, arb1); //test non-destructive
        assertSame(orig2, arb2); //test non-destructive
    }
}
