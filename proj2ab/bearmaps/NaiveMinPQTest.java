package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

public class NaiveMinPQTest {
    @Test
    public void runtimeTest() {
        NaiveMinPQ test = new NaiveMinPQ();
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 1000000; i++) {
            int j = StdRandom.uniform(0, 1000000);
            double k = StdRandom.uniform(0, 10);
            if (!test.contains(j)) {
                test.add(j, k);
            }
        }
        System.out.println("Total time elapsed for add with random: "
                + sw.elapsedTime() + " seconds.");

        sw = new Stopwatch();
        int testsize = test.size();
        System.out.println("size before remove:" + test.size());
        for (int i = 0; i < testsize; i++) {
            test.removeSmallest();
        }
        System.out.println("size after remove:" + test.size());
        System.out.println("Total time elapsed for remove: "
                + sw.elapsedTime() + " seconds.");

        sw = new Stopwatch();
        for (int i = 0; i < 1000000; i++) {
            double k = StdRandom.uniform(0, 10);
            test.add(i, k);
        }
        System.out.println("Total time elapsed for add with constant: "
                + sw.elapsedTime() + " seconds.");

        System.out.println("size before changePriorty with constant:"
                + test.size());
        sw = new Stopwatch();
        for (int i = 0; i < 1000000; i++) {
            int j = StdRandom.uniform(0, 1000000);
            double k = StdRandom.uniform(0, 10);
            test.changePriority(j, k);
        }
        System.out.println("size after changepriorty with constant:" + test.size());
        System.out.println("Total time elapsed for changePriorty: "
                + sw.elapsedTime() + " seconds.");

        sw = new Stopwatch();
        testsize = test.size();
        System.out.println("size before remove:" + test.size());
        for (int i = 0; i < testsize; i++) {
            test.removeSmallest();
        }
        System.out.println("size after remove:" + test.size());
        System.out.println("Total time elapsed for remove: " + sw.elapsedTime() + " seconds.");

        sw = new Stopwatch();
        for (int i = 0; i < 1000000; i++) {
            int j = StdRandom.uniform(0, 1000000);
            double k = StdRandom.uniform(0, 10);
            if (!test.contains(j)) {
                test.add(j, k);
            }
        }
        System.out.println("Total time elapsed for add random: " + sw.elapsedTime() + " seconds.");
    }
}
