import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start cannot be null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        if (!(graph.getVertices().contains(start))) {
            throw new IllegalArgumentException("Start node is not in the graph");
        }
        Queue<Vertex<T>> queue = new LinkedList<>();
        List<Vertex<T>> array = new ArrayList<>();
        Map<Vertex<T>, List<VertexDistance<T>>> map = graph.getAdjList();
        array.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            Vertex<T> vert = queue.remove();
            List<VertexDistance<T>> adList = map.get(vert);
            for (VertexDistance<T> vertexDistance : adList) {
                Vertex<T> vertex = vertexDistance.getVertex();
                if (!(array.contains(vertex))) {
                    array.add(vertex);
                    queue.add(vertex);
                }
            }
        }
        return array;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start cannot be null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        if (!(graph.getVertices().contains(start))) {
            throw new IllegalArgumentException("Start node is not in the graph");
        }
        Set<Vertex<T>> set = new HashSet<>();
        List<Vertex<T>> list = new ArrayList<>();
        Map<Vertex<T>, List<VertexDistance<T>>> map = graph.getAdjList();
        dfsHelper(start, list, set, map);

        return list;
    }

    /**
     * Helper method for DFS
     * @param vertex current vertex
     * @param list list to check visited
     * @param set result list
     * @param map adjacency list
     * @param <T> Generic type
     */
    private static <T> void dfsHelper(Vertex<T> vertex, List<Vertex<T>> list,
                                      Set<Vertex<T>> set, Map<Vertex<T>,
                                      List<VertexDistance<T>>> map) {
        set.add(vertex);
        list.add(vertex);
        for (VertexDistance<T> vertexDistance : map.get(vertex)) {
            if (!(set.contains(vertexDistance.getVertex()))) {
                dfsHelper(vertexDistance.getVertex(), list, set, map);
            }
        }
    }


    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start cannot be null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        if (!(graph.getVertices().contains(start))) {
            throw new IllegalArgumentException("Start node is not in the graph");
        }
        List<Vertex<T>> visit = new ArrayList<>();
        Map<Vertex<T>, Integer> map = new HashMap<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adList = graph.getAdjList();
        PriorityQueue<VertexDistance<T>> queue = new PriorityQueue<>();

        for (Vertex<T> v : adList.keySet()) {
            if (v.equals(start)) {
                map.put(v, 0);
            } else {
                map.put(v, Integer.MAX_VALUE);
            }
        }
        queue.add(new VertexDistance<>(start, 0));
        while (!(queue.isEmpty()) && visit.size() < adList.size()) {
            VertexDistance<T> vertexDistance = queue.remove();
            if (!(visit.contains(vertexDistance.getVertex()))) {
                visit.add(vertexDistance.getVertex());
            }
            for (VertexDistance<T> vd : adList.get(vertexDistance.getVertex())) {
                int distance = vertexDistance.getDistance() + vd.getDistance();
                if (!(visit.contains(vd.getVertex())) && map.get(vd.getVertex()).compareTo(distance) > 0) {
                    map.put(vd.getVertex(), distance);
                    queue.add(new VertexDistance<>(vd.getVertex(), distance));
                }
            }
        }

        return map;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * An MST should NOT have self-loops or parallel edges.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        DisjointSet<Vertex<T>> disSet = new DisjointSet<>();
        PriorityQueue<Edge<T>> queue = new PriorityQueue<>(graph.getEdges());
        Set<Edge<T>> result = new HashSet<>();

        while (result.size() < (graph.getVertices().size() - 1) * 2) {
            Edge<T> cur = queue.poll();
            if (cur == null) {
                return null;
            }
            Vertex<T> v = cur.getV();
            Vertex<T> u = cur.getU();

            if (!disSet.find(v).equals(disSet.find(u))) {
                result.add(cur);
                result.add(new Edge<>(v, u, cur.getWeight()));
                disSet.union(v, u);
            }
        }

        return result;
    }
}
