package model.data_structures;

import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;

public class Vertex<K extends Comparable<K>, V extends Comparable<V>> implements Comparable<Vertex<K, V>> {
    private K key;
    private V value;
    private Map<K, Edge<K, V>> edgeMap; // Optimización para búsquedas rápidas de aristas
    private boolean marked;

    // Constructor
    public Vertex(K id, V value) {
        if (id == null || value == null) {
            throw new IllegalArgumentException("Key and value cannot be null");
        }
        this.key = id;
        this.value = value;
        this.edgeMap = new HashMap<>();
        this.marked = false;
    }

    // Getters
    public K getId() {
        return key;
    }

    public V getInfo() {
        return value;
    }

    public boolean isMarked() {
        return marked;
    }

    // Methods to mark/unmark vertex
    public void mark() {
        this.marked = true;
    }

    public void unmark() {
        this.marked = false;
    }

    // Add edge to the vertex
    public void addEdge(Edge<K, V> edge) {
        if (edge == null) {
            throw new IllegalArgumentException("Edge cannot be null");
        }
        K destinationId = edge.getDestination().getId();
        if (!edgeMap.containsKey(destinationId)) {
            edgeMap.put(destinationId, edge);
        } else {
            System.out.println("Edge already exists between " + this.key + " and " + destinationId);
        }
    }

    // Get the edge connecting to a specific vertex
    public Edge<K, V> getEdge(K vertex) {
        return edgeMap.get(vertex);
    }

    // Return all connected vertices
    public Map<K, Edge<K, V>> getEdges() {
        return edgeMap;
    }

    // BFS Traversal
    public void bfs() {
        ColaEncadenada<Vertex<K, V>> queue = new ColaEncadenada<>();
        this.mark();
        queue.enqueue(this);

        while (queue.peek() != null) {
            Vertex<K, V> current = queue.dequeue();
            for (Edge<K, V> edge : current.edgeMap.values()) {
                Vertex<K, V> neighbor = edge.getDestination();
                if (!neighbor.isMarked()) {
                    neighbor.mark();
                    queue.enqueue(neighbor);
                }
            }
        }
    }

    // DFS Traversal
    public void dfs() {
        this.mark();
        for (Edge<K, V> edge : edgeMap.values()) {
            Vertex<K, V> destination = edge.getDestination();
            if (!destination.isMarked()) {
                destination.dfs();
            }
        }
    }

    // Topological Order
    public void topologicalOrder(ColaEncadenada<Vertex<K, V>> pre, ColaEncadenada<Vertex<K, V>> post, PilaEncadenada<Vertex<K, V>> reversePost) {
        this.mark();
        pre.enqueue(this);

        for (Edge<K, V> edge : edgeMap.values()) {
            Vertex<K, V> destination = edge.getDestination();
            if (!destination.isMarked()) {
                destination.topologicalOrder(pre, post, reversePost);
            }
        }

        post.enqueue(this);
        reversePost.push(this);
    }

    // Calculate Minimum Spanning Tree using Prim's Lazy Algorithm
    public ILista<Edge<K, V>> mstPrimLazy() {
        ILista<Edge<K, V>> mst = new ArregloDinamico<>(1);
        MinPQ<Float, Edge<K, V>> minPQ = new MinPQ<>(1);

        addEdgesToMinPQ(minPQ, this);

        while (!minPQ.isEmpty()) {
            Edge<K, V> edge = minPQ.delMin().getValue();
            Vertex<K, V> destination = edge.getDestination();

            if (!destination.isMarked()) {
                mst.insertElement(edge, mst.size() + 1);
                addEdgesToMinPQ(minPQ, destination);
            }
        }
        return mst;
    }

    private void addEdgesToMinPQ(MinPQ<Float, Edge<K, V>> minPQ, Vertex<K, V> vertex) {
        vertex.mark();

        for (Edge<K, V> edge : vertex.edgeMap.values()) {
            minPQ.insert(edge.getWeight(), edge);
        }
    }

    // Comparator class for custom comparison
    public static class ComparadorXKey implements Comparator<Vertex<String, Landing>> {
        @Override
        public int compare(Vertex<String, Landing> v1, Vertex<String, Landing> v2) {
            return v1.getId().compareToIgnoreCase(v2.getId());
        }
    }

    @Override
    public int compareTo(Vertex<K, V> other) {
        return this.key.compareTo(other.getId());
    }
}

