package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {
    @Test
    public void testAdd() {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ();
        assertEquals(0, test.size());
        test.add(1, 4);
        assertEquals(1, test.size());
        test.add(2, 2);
        test.add(3, 5);
        test.add(4, 1);
        assertEquals(4, test.size());
        assertEquals(4, test.getSmallest());
        test.add(5, 3);
        test.add(6, 1);
        assertEquals(6, test.getSmallest());
    }

    @Test
    public void testRemoveSmallest() {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ();
        test.add(1, 2);
        test.add(2, 3);
        test.add(3, 4);
        test.add(4, 5);
        assertEquals(1, test.getSmallest());
        test.removeSmallest();
        assertEquals(3, test.size());
        assertEquals(2, test.getSmallest());
        test.add(5,1);
        assertEquals(5, test.getSmallest());
        test.removeSmallest();
        assertEquals(2, test.getSmallest());
        test.removeSmallest();
        assertEquals(3, test.getSmallest());
        test.removeSmallest();
        assertEquals(4, test.getSmallest());
        test.removeSmallest();
        test.getSmallest();
    }

    @Test
    public void testHelpers() {
        ArrayHeapMinPQ test =  new ArrayHeapMinPQ();
        test.add(11, 1);
        test.add(12, 2);
        test.add(13, 3);
        test.add(14, 4);
        assertEquals(true, test.contains(12));
    }

}
