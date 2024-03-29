package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

     /* that shows the flaw in the hashCode function.
     */
    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();
        ArrayList<Integer> params = new ArrayList<>(4);
        ArrayList<Integer> params2 = new ArrayList<>(4);
        int test = 0;
        for (int i = 0; i < 4; i++) {
            params.add(i);
        }
        for (int i = 4; i < 8; i++) {
            params2.add(test);
            test = test + 10;
        }
        for (int i = 0; i < 5; i++) {
            deadlyList.add(new ComplexOomage(params));
        }
        for (int i = 5; i < 10; i++) {
            deadlyList.add(new ComplexOomage(params2));
        }
        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));

        /*List<Oomage> deadlyList = new ArrayList<>();
        int T = 255;
        //List<Integer> params = new ArrayList<>(N);
        for (int i = 0; i < T; i++) {
            param.add(i);
            deadlyList.add(new ComplexOomage(param));
        }
        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));*/
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
