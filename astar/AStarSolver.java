package astar;

import edu.princeton.cs.algs4.Stopwatch;
import pq.ArrayHeapMinPQ;
import pq.ExtrinsicMinPQ;

import java.util.*;

/**
 * @see ShortestPathsSolver for more method documentation
 */
public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight = 0.0;
    private List<Vertex> solution = new ArrayList<>();
    private double timeSpent = 0.0;
    private int count = 0;
    private Map<Vertex, Vertex> edgeTo = new HashMap<>();
    private Map<Vertex, Double> distTo = new HashMap<>();
    private List<Vertex> visited = new ArrayList<>();

    /**
     * Immediately solves and stores the result of running memory optimized A*
     * search, computing everything necessary for all other methods to return
     * their results in constant time. The timeout is given in seconds.
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch w = new Stopwatch();
        ExtrinsicMinPQ<Vertex> fringe = new ArrayHeapMinPQ<>();
        fringe.add(start, input.estimatedDistanceToGoal(start, end));
        distTo.put(start, 0.0);
        count = 0;
        if (start.equals(end) && (timeout > timeSpent)) {
            outcome = SolverOutcome.SOLVED;
            solutionWeight = 0;
            timeSpent = w.elapsedTime();
            solution = List.of(start);
            count = 0;
            return;
        }

        while (!fringe.isEmpty()) {
            Vertex v = fringe.removeSmallest();
            List<WeightedEdge<Vertex>> neighbor = input.neighbors(v);
            for (WeightedEdge<Vertex> edge : neighbor) {
                Vertex from = edge.from();
                Vertex to = edge.to();
                double dist = distTo.get(from) + edge.weight();
                double h = input.estimatedDistanceToGoal(to, end);

                if (!distTo.containsKey(to)) {
                    distTo.put(to, dist);
                    edgeTo.put(to, from);
                    fringe.add(to, dist + h);
                }
                if (distTo.get(to) > dist) {
                    distTo.replace(to, dist);
                    edgeTo.replace(to, from);
                    if (fringe.contains(to)) {
                        fringe.changePriority(to, dist + h);
                    } else {
                        fringe.add(to, dist + h);
                    }
                }
            }
            timeSpent = w.elapsedTime();

            // TIMEOUT
            if (timeSpent >= timeout) {
                outcome = SolverOutcome.TIMEOUT;
                solutionWeight = 0;
                return;
            } else if (v.equals(end) && (timeout > timeSpent)) {
                // SOLVED
                outcome = SolverOutcome.SOLVED;
                solutionWeight = distTo.get(end);
                while (v != null) {
                    solution.add(v);
                    v = edgeTo.get(v);
                }
                Collections.reverse(solution);
                return;
            } else if (fringe.size() == 0) {
                // UNSOLVABLE
                outcome = SolverOutcome.UNSOLVABLE;
                solutionWeight = 0;
                return;
            }
        }
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    /** The total number of priority queue removeSmallest operations. */
    @Override
    public int numStatesExplored() {
        return count;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }
}
