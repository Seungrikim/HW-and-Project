package bearmaps;

import java.util.ArrayList;
import java.util.List;

public class NaivePointSet implements PointSet {
    private List<Point> myPoints;

    public NaivePointSet(List<Point> points) {
        myPoints = new ArrayList<>();
        for (Point a: points) {
            myPoints.add(a);
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Point best = myPoints.get(0);
        Point goal = new Point(x, y);
        for (Point a : myPoints) {
            double currDistance = Point.distance(a, goal);
            if (currDistance < Point.distance(best, goal)) {
                best = a;
            }
        }
        return best;
    }

    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(4.0, 4.0);
        Point p4 = new Point(-2.5, 4.0);
        Point p5 = new Point(-2.9, -4.0);
        Point p6 = new Point(3.5, 4.5);
        Point p7 = new Point(2.9, 4.1);
        Point p8 = new Point(2.0, 4.0);

        NaivePointSet nv = new NaivePointSet(List.of(p1, p2, p3, p7, p8));
        Point ret = nv.nearest(3.0, 4.0); // returns p2
        System.out.println(ret.getX()); // evaluates to 3.3
        System.out.println(ret.getY()); // evaluates to 4.4); // evaluates to 4.4
    }
}
