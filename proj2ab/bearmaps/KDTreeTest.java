package bearmaps;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import java.util.Random;
import edu.princeton.cs.algs4.Stopwatch;


//@source from professor Hug's Video
public class KDTreeTest {
    private static Random r = new Random(500);

    @Test
    public void lectureTest() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        Point ret = kd.nearest(0, 7); // returns p2
        System.out.println(ret.getX()); // evaluates to 3.3
        System.out.println(ret.getY()); // evaluates to 4.4
    }

    @Test
    public void verySimpleTest() {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(4.0, 4.0);
        Point p4 = new Point(-2.5, 4.0);
        Point p5 = new Point(-2.9, -4.0);
        Point p6 = new Point(3.5, 4.5);
        Point p7 = new Point(2.9, 4.1);
        Point p8 = new Point(2.0, 4.0);

        KDTree kd = new KDTree(List.of(p1, p2, p3, p7, p8));
        Point ret = kd.nearest(3.0, 4.0); // returns p2
        System.out.println(ret.getX()); // evaluates to 3.3
        System.out.println(ret.getY()); // evaluates to 4.4
    }

    @Test
    public void edgecase() {
        Point p1 = new Point(2, 4);
        Point p2 = new Point(2, 4);
        Point p3 = new Point(2, 4);
        Point p4 = new Point(2, 0);
        Point p5 = new Point(2, 0);
        Point p6 = new Point(0, 0);

        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6));
    }


    private Point randomPoint() {
        double x = r.nextDouble();
        double y = r.nextDouble();
        return new Point(x, y);
    }

    private List<Point> randomPoints(int N) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i <  N; i += 1) {
            points.add(randomPoint());
        }
        return points;
    }

    private void testWithPointsAndQUereis(int pointCount, int queryCount) {
        List<Point> points = randomPoints(pointCount);
        NaivePointSet np = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        List<Point> queries = randomPoints(queryCount);
        for (Point p : queries) {
            Point expected = np.nearest(p.getX(), p.getY());
            Point actual = kd.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
    }

    private double testRuntimeForNaive(int pointCount, int queryCount) {
        List<Point> points = randomPoints(pointCount);
        NaivePointSet np = new NaivePointSet(points);
        Stopwatch sw = new Stopwatch();
        List<Point> queries = randomPoints(queryCount);
        for (Point p : queries) {
            np.nearest(p.getX(), p.getY());
        }
        /*System.out.println("Total time elapsed for Naive with random: "
                + sw.elapsedTime() +  " seconds.");*/
        return sw.elapsedTime();
    }

    private double testRuntimeForKdtree(int pointCount, int queryCount) {
        List<Point> points = randomPoints(pointCount);
        KDTree kd = new KDTree(points);
        Stopwatch sw = new Stopwatch();
        List<Point> queries = randomPoints(queryCount);
        for (Point p : queries) {
            kd.nearest(p.getX(), p.getY());
        }
        /*System.out.println("Total time elapsed for Kdtree with random: "
                + sw.elapsedTime() +  " seconds.");*/
        return sw.elapsedTime();
    }

    private void ratioTest(int queryCouny) {
        int size = 4;
        double[] ratios = new double[size];
        int pointCount = 100;
        for (int i = 0; i < size; i++) {
            double naivetime = testRuntimeForNaive(pointCount, queryCouny);
            double kdtime = testRuntimeForKdtree(pointCount, queryCouny);
            ratios[i] = naivetime / kdtime;
            pointCount = pointCount * 10;
        }
        for (double i : ratios) {
            System.out.print("Ration: " + i + " ");
        }
    }

    @Test
    public void testWithsize() {
        int pointCount = 100000;
        int queryCount = 10000;
        double naiveTime = testRuntimeForNaive(pointCount, queryCount);
        double kdTime = testRuntimeForKdtree(pointCount, queryCount);
        System.out.println(naiveTime / kdTime);
        assertTrue(kdTime / naiveTime < 0.1);
        ratioTest(queryCount);
        testWithPointsAndQUereis(pointCount, queryCount);
    }


}
