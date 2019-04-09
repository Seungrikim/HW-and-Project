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
        //solution.add(end);
        Stopwatch sw = new Stopwatch();
        ArrayHeapMinPQ<Vertex> fringe = new ArrayHeapMinPQ<>();
        //DoubleMapPQ<Vertex> fringe = new DoubleMapPQ<>();
        HashMap<Vertex, Double> distTo = new HashMap();
        HashMap<Vertex, Vertex> edgeTo = new HashMap();
        double heuristic = input.estimatedDistanceToGoal(start, end);
        fringe.add(start, heuristic);
        distTo.put(start, 0.0);

        while (sw.elapsedTime() < timeout) {
            //List<WeightedEdge<Vertex>> neighbor = input.neighbors(current);
            for (WeightedEdge<Vertex> e : input.neighbors(current)) {
                if (fringe.equals(null)) {
                    outcome = SolverOutcome.UNSOLVABLE;
                    return;
                }
                heuristic = input.estimatedDistanceToGoal(e.to(), end);// has problem?
                //System.out.println("vertex is " + e.to());
                //System.out.println("heuristic is " + heuristic);
                if (!distTo.containsKey(e.to())) {
                    distTo.put(e.to(), e.weight() + distTo.get(e.from()));
                    edgeTo.put(e.to(), e.from());
                    fringe.add(e.to(), distTo.get(e.to()) + heuristic);
                } else {
                    if (distTo.get(e.from()) + e.weight() < distTo.get(e.to())) {
                        distTo.put(e.to(), distTo.get(e.from()) + e.weight());
                        edgeTo.put(e.to(), e.from());
                        if (fringe.contains(e.to())) {
                            fringe.changePriority(e.to(), distTo.get(e.to()) + heuristic);
                        } else {
                            fringe.add(e.to(), distTo.get(e.to()) + heuristic);
                        }
                    }
                }
            }
            //solution.add(fringe.getSmallest());
            //System.out.println("smallest is " + fringe.getSmallest());
            //System.out.println("heuristic is " + input.estimatedDistanceToGoal(fringe.getSmallest(), end));
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
        while (!edgeTo.get(end).equals(start)) {
            tempSolution.addFirst((Vertex) edgeTo.get(end));
            end = (Vertex) edgeTo.get(end);
        }
        tempSolution.addFirst(start);
        return tempSolution;
    }
}

