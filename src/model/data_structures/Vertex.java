package model.data_structures;

import java.util.Comparator;

public class Vertex<K extends Comparable<K>, V extends Comparable<V>> implements Comparable<Vertex<K, V>> {
    private K key;
    private V value;
    private ILista<Edge<K, V>> edges;
    private boolean marked;

    // Constructor
    public Vertex(K id, V value) {
        this.key = id;
        this.value = value;
        this.edges = new ArregloDinamico<>(1);
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

    public ILista<Edge<K, V>> getEdges() {
        return edges;
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
        try {
            edges.insertElement(edge, edges.size() + 1);
        } catch (PosException | NullException e) {
            e.printStackTrace();
        }
    }

    // Get the edge connecting to a specific vertex
    public Edge<K, V> getEdge(K vertex) {
        for (int i = 1; i <= edges.size(); i++) {
            try {
                Edge<K, V> edge = edges.getElement(i);
                if (edge.getDestination().getId().compareTo(vertex) == 0) {
                    return edge;
                }
            } catch (PosException | VacioException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Return all connected vertices
    public ILista<Vertex<K, V>> getConnectedVertices() {
        ILista<Vertex<K, V>> connectedVertices = new ArregloDinamico<>(1);
        for (int i = 1; i <= edges.size(); i++) {
            try {
                connectedVertices.insertElement(edges.getElement(i).getDestination(), connectedVertices.size() + 1);
            } catch (PosException | NullException | VacioException e) {
                e.printStackTrace();
            }
        }
        return connectedVertices;
    }

    // BFS Traversal
    public void bfs() {
        ColaEncadenada<Vertex<K, V>> queue = new ColaEncadenada<>();
        this.mark();
        queue.enqueue(this);

        while (queue.peek() != null) {
            Vertex<K, V> current = queue.dequeue();
            for (int i = 1; i <= current.edges.size(); i++) {
                try {
                    Vertex<K, V> neighbor = current.edges.getElement(i).getDestination();
                    if (!neighbor.isMarked()) {
                        neighbor.mark();
                        queue.enqueue(neighbor);
                    }
                } catch (PosException | VacioException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // DFS Traversal
    public void dfs(Edge<K, V> incomingEdge) {
        this.mark();
        for (int i = 1; i <= edges.size(); i++) {
            try {
                Edge<K, V> edge = edges.getElement(i);
                Vertex<K, V> destination = edge.getDestination();
                if (!destination.isMarked()) {
                    destination.dfs(edge);
                }
            } catch (PosException | VacioException e) {
                e.printStackTrace();
            }
        }
    }

    // Topological Order
    public void topologicalOrder(ColaEncadenada<Vertex<K, V>> pre, ColaEncadenada<Vertex<K, V>> post, PilaEncadenada<Vertex<K, V>> reversePost) {
        this.mark();
        pre.enqueue(this);

        for (int i = 1; i <= edges.size(); i++) {
            try {
                Vertex<K, V> destination = edges.getElement(i).getDestination();
                if (!destination.isMarked()) {
                    destination.topologicalOrder(pre, post, reversePost);
                }
            } catch (PosException | VacioException e) {
                e.printStackTrace();
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
                try {
                    mst.insertElement(edge, mst.size() + 1);
                } catch (PosException | NullException e) {
                    e.printStackTrace();
                }
                addEdgesToMinPQ(minPQ, destination);
            }
        }
        return mst;
    }

    private void addEdgesToMinPQ(MinPQ<Float, Edge<K, V>> minPQ, Vertex<K, V> vertex) {
        vertex.mark();

        for (int i = 1; i <= vertex.edges.size(); i++) {
            try {
                Edge<K, V> edge = vertex.edges.getElement(i);
                minPQ.insert(edge.getWeight(), edge);
            } catch (PosException | VacioException e) {
                e.printStackTrace();
            }
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
