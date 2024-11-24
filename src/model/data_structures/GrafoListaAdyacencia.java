package model.data_structures;

public class GrafoListaAdyacencia<K extends Comparable<K>, V extends Comparable<V>> {

	private final ITablaSimbolos<K, Vertex<K, V>> vertices;
	private final ILista<Edge<K, V>> arcos;
	private int numEdges;

	public GrafoListaAdyacencia(int numVertices) {
		this.vertices = new TablaHashLinearProbing<>(numVertices);
		this.arcos = new ArregloDinamico<>(1);
		this.numEdges = 0;
	}

	public boolean containsVertex(K id) {
		return vertices.contains(id);
	}

	public int numVertices() {
		return vertices.size();
	}

	public int numEdges() {
		return numEdges;
	}

	public void insertVertex(K id, V value) {
		if (!containsVertex(id)) {
			Vertex<K, V> nuevoVertice = new Vertex<>(id, value);
			vertices.put(id, nuevoVertice);
		}
	}

	public void addEdge(K source, K dest, float weight) {
		Vertex<K, V> origen = getVertex(source);
		Vertex<K, V> destino = getVertex(dest);

		if (origen != null && destino != null && getEdge(source, dest) == null) {
			Edge<K, V> arco = new Edge<>(origen, destino, weight);
			origen.addEdge(arco);
			arcos.insertElement(arco, arcos.size() + 1);
			numEdges++;
		}
	}

	public Vertex<K, V> getVertex(K id) {
		return vertices.get(id);
	}

	public Edge<K, V> getEdge(K source, K dest) {
		Vertex<K, V> origen = getVertex(source);
		return origen != null ? origen.getEdge(dest) : null;
	}

	public ILista<Edge<K, V>> adjacentEdges(K id) {
		Vertex<K, V> origen = getVertex(id);
		return origen != null ? origen.edges() : new ArregloDinamico<>(0);
	}

	public ILista<Vertex<K, V>> adjacentVertex(K id) {
		Vertex<K, V> origen = getVertex(id);
		return origen != null ? origen.vertices() : new ArregloDinamico<>(0);
	}

	public void unmarkVertices() {
		for (Vertex<K, V> vertex : vertices.values()) {
			vertex.unmark();
		}
	}

	public void dfs(K id) {
		Vertex<K, V> inicio = getVertex(id);
		if (inicio != null) {
			inicio.dfs();
			unmarkVertices();
		}
	}

	public void bfs(K id) {
		Vertex<K, V> inicio = getVertex(id);
		if (inicio != null) {
			inicio.bfs();
			unmarkVertices();
		}
	}

	public Edge<K, V> minEdge() {
		return findEdgeByWeight(true);
	}

	public Edge<K, V> maxEdge() {
		return findEdgeByWeight(false);
	}

	private Edge<K, V> findEdgeByWeight(boolean findMin) {
		if (arcos.isEmpty()) return null;
		Edge<K, V> resultado = arcos.getElement(1);
		float pesoComparado = resultado.getWeight();

		for (Edge<K, V> edge : arcos) {
			float pesoActual = edge.getWeight();
			if ((findMin && pesoActual < pesoComparado) || (!findMin && pesoActual > pesoComparado)) {
				resultado = edge;
				pesoComparado = pesoActual;
			}
		}
		return resultado;
	}

	public GrafoListaAdyacencia<K, V> reverse() {
		GrafoListaAdyacencia<K, V> grafoReverso = new GrafoListaAdyacencia<>(numVertices());

		for (Vertex<K, V> vertex : vertices.values()) {
			grafoReverso.insertVertex(vertex.getId(), vertex.getInfo());
		}

		for (Edge<K, V> edge : arcos) {
			grafoReverso.addEdge(edge.getDestination().getId(), edge.getSource().getId(), edge.getWeight());
		}

		return grafoReverso;
	}

	public ITablaSimbolos<K, Integer> stronglyConnectedComponents() {
		PilaEncadenada<Vertex<K, V>> reverseOrder = reverse().topologicalOrder();
		ITablaSimbolos<K, Integer> components = new TablaHashLinearProbing<>(numVertices());
		int componentId = 1;

		while (!reverseOrder.isEmpty()) {
			Vertex<K, V> vertex = reverseOrder.pop();
			if (!vertex.isMarked()) {
				vertex.dfsForComponent(components, componentId);
				componentId++;
			}
		}
		unmarkVertices();
		return components;
	}

	public PilaEncadenada<Vertex<K, V>> topologicalOrder() {
		PilaEncadenada<Vertex<K, V>> reversePostOrder = new PilaEncadenada<>();

		for (Vertex<K, V> vertex : vertices.values()) {
			if (!vertex.isMarked()) {
				vertex.dfsForTopologicalOrder(reversePostOrder);
			}
		}

		unmarkVertices();
		return reversePostOrder;
	}

	public ILista<Edge<K, V>> mstPrimLazy(K idOrigen) {
		Vertex<K, V> origen = getVertex(idOrigen);
		if (origen == null) return new ArregloDinamico<>(0);

		ILista<Edge<K, V>> mst = origen.mstPrimLazy();
		unmarkVertices();
		return mst;
	}

	public PilaEncadenada<Edge<K, V>> shortestPath(K idOrigen, K idDestino) {
		ITablaSimbolos<K, NodoTS<Float, Edge<K, V>>> tree = getVertex(idOrigen).dijkstraTree();
		PilaEncadenada<Edge<K, V>> path = new PilaEncadenada<>();

		K current = idDestino;
		while (current != null) {
			NodoTS<Float, Edge<K, V>> node = tree.get(current);
			if (node == null || node.getValue() == null) break;
			path.push(node.getValue());
			current = node.getValue().getSource().getId();
		}
		unmarkVertices();
		return path;
	}
}

