package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.StdRandom;
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
        assertEquals(4, test.getSmallest());
    }

    @Test
    public void testSamePriority() {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ();
        test.add(7,1);
        test.add(6,1);
        test.add(5,1);
        test.add(4,1);
        test.add(3,1);
        test.add(2,1);
    }

    /*@Test
    public void testHelper() {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ();
        assertEquals(6, test.leftChild(3));
        assertEquals(7, test.rightChild(3));
        assertEquals(2, test.parent(4));
        assertEquals(1, test.parent(2));
    }*/

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
        test.add(5, 1);
        assertEquals(5, test.getSmallest());
        test.removeSmallest();
        assertEquals(2, test.getSmallest());
        test.removeSmallest();
        assertEquals(3, test.getSmallest());
        test.removeSmallest();
        assertEquals(4, test.getSmallest());
        test.removeSmallest();
    }

    @Test
    public void testContains() {
        ArrayHeapMinPQ test =  new ArrayHeapMinPQ();
        test.add(11, 1);
        test.add(12, 2);
        test.add(13, 3);
        test.add(14, 4);
        assertEquals(true, test.contains(12));
        assertEquals(true, test.contains(14));
        test.removeSmallest();
        test.removeSmallest();
        assertEquals(false, test.contains(11));
        assertEquals(false, test.contains(12));
    }

    /*@Test
    public void testException() {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ();
        test.add(1, 2);
        test.add(2, 2);
        test.add(3, 2);
        test.add(4, 1);
        test.add(5, 1);
        //test.getSmallest();
    }*/

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ();
        test.add(1, 3);
        test.add(2, 4);
        test.add(3, 5);
        test.add(4, 6);
        assertEquals(1, test.getSmallest());
        test.changePriority(2, 2);
        assertEquals(2, test.getSmallest());
        test.changePriority(2, 7);
        assertEquals(1, test.getSmallest());
    }

    @Test
    public void testEdgecase() {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ();
        test.add(1, 1);
        test.add(2, 2);
        test.add(3, 3);
        test.removeSmallest();
    }

    @Test
    public void runtimeTest() {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ();
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 1000000; i++) {
            int j = StdRandom.uniform(0, 1000000);
            double k = StdRandom.uniform(0, 10);
            if (!test.contains(j)) {
                test.add(j, k);
            }
        }
        System.out.println("Total time elapsed for add with random: " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        int testsize = test.size();
        System.out.println("size before remove:" + test.size());
        for (int i = 0; i < testsize; i++) {
            test.removeSmallest();
        }
        System.out.println("size after remove:" + test.size());
        System.out.println("Total time elapsed for remove: " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        for (int i = 0; i < 1000000; i++) {
            double k = StdRandom.uniform(0, 10);
            test.add(i,k);
        }
        System.out.println("Total time elapsed for add with constant: " + sw.elapsedTime() +  " seconds.");

        System.out.println("size before changePriorty with constant:" + test.size());
        sw = new Stopwatch();
        for (int i = 0; i < 1000000; i++) {
            int j = StdRandom.uniform(0, 1000000);
            double k = StdRandom.uniform(0, 10);
            test.changePriority(j, k);
        }
        System.out.println("size after changepriorty with constant:" + test.size());
        System.out.println("Total time elapsed for changePriorty: " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        testsize = test.size();
        System.out.println("size before remove:" + test.size());
        for (int i = 0; i < testsize; i++) {
            test.removeSmallest();
        }
        System.out.println("size after remove:" + test.size());
        System.out.println("Total time elapsed for remove: " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        for (int i = 0; i < 1000000; i++) {
            int j = StdRandom.uniform(0, 1000000);
            double k = StdRandom.uniform(0, 10);
            if (!test.contains(j)) {
                test.add(j, k);
            }
        }
        System.out.println("Total time elapsed for add again with random: " + sw.elapsedTime() +  " seconds.");

        System.out.println("size before changePriorty:" + test.size());
        /*this test should catch the NosuchElemetException, But it also may passed
        testsize = test.size();
        sw = new Stopwatch();
        // this test should catch the NosuchElemetException, But it also may passed
        for (int i = 0; i < testsize; i++) {
            int j = StdRandom.uniform(0, 1000000);
            double k = StdRandom.uniform(0, 10);
            test.changePriority(j, k);
        }
        System.out.println("size after changepriorty with randome:" + test.size());
        System.out.println("Total time elapsed for random: " + sw.elapsedTime() +  " seconds.");*/
    }

    @Test
    public void testInsertAndRemoveAllButLast() {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ();
        test.add("c", 3);
        test.add("d", 3);
        test.add("e", 4);
        test.add("f", 4);
        test.add("g", 5);
        int i = 0;
        String[] expected = {"c", "d", "f", "e", "g"};
        while (test.size() > 1) {
            assertEquals(expected[i], test.removeSmallest());
            i += 1;
        }
    }
}
