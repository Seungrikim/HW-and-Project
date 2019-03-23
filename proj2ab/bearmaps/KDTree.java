package bearmaps;

import java.util.List;

//@source from professor Hug's Video
public class KDTree implements PointSet {
    private static final boolean HORIZONTAL = false;
    private Node root;

    private class Node {
        private Point point;
        private boolean orientation;
        private Node leftChild;
        private Node rightChild;

        private Node(Point p, boolean o) {
            point = p;
            orientation = o;
        }
    }

    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = add(p, root, HORIZONTAL);
        }
    }

    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Node best  = root;
        return nearestHelper(root, goal, best).point;
    }

    private Node nearestHelper(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }
        Node goodSide;
        Node badSide;
        if (n.point.distance(n.point, goal) < best.point.distance(best.point, goal)) {
            best = n;
        }
        if (comparePoints(goal, n.point, n.orientation) < 0) {
            goodSide = n.leftChild;
            badSide = n.rightChild;
        } else {
            goodSide = n.rightChild;
            badSide = n.leftChild;
        }
        best = nearestHelper(goodSide, goal, best);
        if (usefulBad(n, goal, best)) {
            best = nearestHelper(badSide, goal, best);
        }
        /*} else if (comparePoints(goal, n.point, n.orientation) >= 0 || usefulBad(n, goal, best)){
            best = nearestHelper(n.rightChild, goal, best);
        }*/
        return best;
    }

    private boolean usefulBad(Node n, Point goal, Node best) {
        if (goalToPoint(n, goal) <= best.point.distance(best.point, goal)) {
            return true;
        }
        return false;
    }

    private double goalToPoint(Node n, Point goal) {
        if (n.orientation == HORIZONTAL) {
            return Math.pow(n.point.getX() - goal.getX(), 2);
        } else {
            return Math.pow(n.point.getY() - goal.getY(), 2);
        }
    }

    private Node add(Point p, Node r, boolean o) {
        if (r == null) {
            return new Node(p, o);
        }
        if (p.equals(r.point)) {
            return r;
        }
        int cmpare = comparePoints(p, r.point, o);
        if (cmpare < 0) {
            r.leftChild = add(p, r.leftChild, !o);
        } else if (cmpare >= 0) {
            r.rightChild = add(p, r.rightChild, !o);
        }
        return r;
    }

    private int comparePoints(Point p1, Point p2, boolean o) {
        if (o == HORIZONTAL) {
            return Double.compare(p1.getX(), p2.getX());
        } else {
            return Double.compare(p1.getY(), p2.getY());
        }
    }
}
