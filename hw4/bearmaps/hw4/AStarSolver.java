package bearmaps.hw4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;
    private double timeSpent;
    private int numStatesExplored;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        solution = new ArrayList<>();
        Vertex current = start;
        Stopwatch sw = new Stopwatch();
        ArrayHeapMinPQ<Vertex> fringe = new ArrayHeapMinPQ<>();
        //DoubleMapPQ<Vertex> fringe = new DoubleMapPQ<>();
        HashMap<Vertex, Double> distTo = new HashMap();
        HashMap<Vertex, Vertex> edgeTo = new HashMap();
        double heuristic = input.estimatedDistanceToGoal(start, end);
        fringe.add(start, heuristic);
        distTo.put(start, 0.0);
        edgeTo.put(start, null);
        if (start == end) {
            solution.add(start);
            solutionWeight = distTo.get(current);
            outcome = SolverOutcome.SOLVED;
            timeSpent = sw.elapsedTime();
            numStatesExplored = 0;
            return;
        }

        while (sw.elapsedTime() < timeout) {
            //List<WeightedEdge<Vertex>> neighbor = input.neighbors(current);
            for (WeightedEdge<Vertex> e : input.neighbors(current)) {
                if (fringe.size() == 0) {
                    outcome = SolverOutcome.UNSOLVABLE;
                    return;
                }
                heuristic = input.estimatedDistanceToGoal(e.to(), end);
                if (!distTo.containsKey(e.to())) {
                    distTo.put(e.to(), e.weight() + distTo.get(e.from()));
                    edgeTo.put(e.to(), e.from());
                    fringe.add(e.to(), distTo.get(e.to()) + heuristic);
                } else {
                    if (distTo.get(e.from()) + e.weight() < distTo.get(e.to())) {
                        distTo.replace(e.to(), distTo.get(e.from()) + e.weight());
                        edgeTo.replace(e.to(), e.from());
                        if (fringe.contains(e.to())) {
                            fringe.changePriority(e.to(), distTo.get(e.to()) + heuristic);
                        } else {
                            fringe.add(e.to(), distTo.get(e.to()) + heuristic);
                        }
                    }
                }
            }
            current = fringe.getSmallest();
            numStatesExplored += 1;
            if (fringe.getSmallest().equals(end)) {
                solution = solutionHelper(edgeTo, start, end);
                solutionWeight = distTo.get(current);
                outcome = SolverOutcome.SOLVED;
                timeSpent = sw.elapsedTime();
                return;
            }
            fringe.removeSmallest();
        }
        outcome = SolverOutcome.UNSOLVABLE;
        timeSpent = sw.elapsedTime();
    }
    public SolverOutcome outcome() {
        return outcome;
    }
    public List<Vertex> solution() {
        return solution;
    }
    public double solutionWeight() {
        return solutionWeight;
    }
    public int numStatesExplored() {
        return numStatesExplored;
    }
    public double explorationTime() {
        return timeSpent;
    }

    private List<Vertex> solutionHelper(HashMap edgeTo, Vertex start, Vertex end) {
        LinkedList<Vertex> tempSolution = new LinkedList<>();
        tempSolution.addFirst(end);
        Vertex path = end;
        while (!edgeTo.get(path).equals(null) && !edgeTo.get(path).equals(start)) {
            tempSolution.addFirst((Vertex) edgeTo.get(path));
            path = (Vertex) edgeTo.get(path);
        }
        tempSolution.addFirst(start);
        return tempSolution;
    }
}
