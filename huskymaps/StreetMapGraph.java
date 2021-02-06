package huskymaps;

import astar.AStarGraph;
import astar.WeightedEdge;
import huskymaps.params.Location;
import pointset.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StreetMapGraph implements AStarGraph<Long> {
    private Map<Long, Node> nodes = new HashMap<>();
    private Map<Long, Set<WeightedEdge<Long>>> neighbors = new HashMap<>();
    // TODO: add fields as necessary

    public StreetMapGraph(String filename) {
        OSMGraphHandler.initializeFromXML(this, filename);
        // TODO: replace this with your code
    }

    /**
     * Returns the vertex closest to the given location.
     * @param target The target location.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(Location target) {
        Point point = target.toPoint();
        // TODO: replace this with your code
        return 0;
    }

    /**
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of full names of locations matching the <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        // TODO: replace this with your code
        return List.of();
    }

    /**
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose name matches the <code>locationName</code>.
     */
    public List<Location> getLocations(String locationName) {
        // TODO: replace this with your code
        return List.of();
    }

    /** Returns a list of outgoing edges for V. Assumes V exists in this graph. */
    @Override
    public List<WeightedEdge<Long>> neighbors(Long v) {
        return new ArrayList<>(neighbors.get(v));
    }

    /**
     * Returns the great-circle distance between S and GOAL. Assumes
     * S and GOAL exist in this graph.
     */
    @Override
    public double estimatedDistanceToGoal(Long s, Long goal) {
        return location(s).greatCircleDistance(location(goal));
    }

    /** Returns a set of my vertices. Altering this set does not alter this graph. */
    public Set<Long> vertices() {
        return new HashSet<>(nodes.keySet());
    }

    /** Adds an edge to this graph if it doesn't already exist, using distance as the weight. */
    public void addWeightedEdge(long from, long to, String name) {
        if (nodes.containsKey(from) && nodes.containsKey(to)) {
            double weight = location(from).greatCircleDistance(location(to));
            neighbors.get(from).add(new WeightedEdge<>(from, to, weight, name));
        }
    }

    /** Adds an edge to this graph if it doesn't already exist. */
    public void addWeightedEdge(long from, long to, double weight, String name) {
        if (nodes.containsKey(from) && nodes.containsKey(to)) {
            neighbors.get(from).add(new WeightedEdge<>(from, to, weight, name));
        }
    }

    /** Adds an edge to this graph if it doesn't already exist. */
    public void addWeightedEdge(WeightedEdge<Long> edge) {
        if (nodes.containsKey(edge.from()) && nodes.containsKey(edge.to())) {
            neighbors.get(edge.from()).add(edge);
        }
    }

    /** Checks if a vertex has 0 out-degree from graph. */
    private boolean isNavigable(Node node) {
        return !neighbors.get(node.id()).isEmpty();
    }

    /**
     * Returns the location for the given id.
     * @param id The id of the location.
     * @return The location instance.
     */
    public Location location(long id) {
        Location location = nodes.get(id);
        if (location == null) {
            throw new IllegalArgumentException("Location not found for id: " + id);
        }
        return location;
    }

    /** Adds a node to this graph, if it doesn't yet exist. */
    void addNode(Node node) {
        if (!nodes.containsKey(node.id())) {
            nodes.put(node.id(), node);
            neighbors.put(node.id(), new HashSet<>());
        }
    }

    Node.Builder nodeBuilder() {
        return new Node.Builder();
    }
}
