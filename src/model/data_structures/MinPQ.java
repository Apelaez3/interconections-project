package model.data_structures;

public class MinPQ<K extends Comparable<K>, V extends Comparable<V>> {
	protected ILista<NodoTS<K, V>> arbol;
	protected int tamano;

	public MinPQ(int inicial) {
		arbol = new ArregloDinamico<NodoTS<K, V>>(inicial);
		tamano = 0;
	}

	// Método para mantener la propiedad de la cola de prioridad (sube el nodo en el árbol)
	private void swim(int pos) {
		while (pos > 1 && compareKeys(pos, pos / 2) < 0) {
			exchange(pos, pos / 2);
			pos = pos / 2;
		}
	}

	// Método para mantener la propiedad de la cola de prioridad (baja el nodo en el árbol)
	private void sink(int pos) {
		int size = arbol.size();
		while (2 * pos <= size) {
			int left = 2 * pos;
			int right = left + 1;
			int smallest = pos;

			if (left <= size && compareKeys(left, smallest) < 0) {
				smallest = left;
			}
			if (right <= size && compareKeys(right, smallest) < 0) {
				smallest = right;
			}
			if (smallest == pos) {
				break;
			}
			exchange(pos, smallest);
			pos = smallest;
		}
	}

	// Método para comparar las claves de dos nodos
	private int compareKeys(int pos1, int pos2) {
		try {
			return arbol.getElement(pos1).getKey().compareTo(arbol.getElement(pos2).getKey());
		} catch (PosException | VacioException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// Método para intercambiar elementos en la lista
	private void exchange(int pos1, int pos2) {
		try {
			arbol.exchange(pos1, pos2);
		} catch (PosException | VacioException e) {
			e.printStackTrace();
		}
	}

	public void insert(K key, V value) {
		try {
			arbol.insertElement(new NodoTS<>(key, value), arbol.size() + 1);
			tamano++;
			swim(tamano); // Asegura que el nuevo elemento suba a la posición correcta
		} catch (PosException | NullException e) {
			e.printStackTrace();
		}
	}

	public int size() {
		return tamano;
	}

	public NodoTS<K, V> min() {
		if (tamano > 0) {
			try {
				return arbol.getElement(1); // El mínimo siempre estará en la raíz
			} catch (PosException | VacioException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public NodoTS<K, V> delMin() {
		if (tamano == 0) return null;

		NodoTS<K, V> min = null;
		try {
			if (tamano > 1) {
				exchange(1, tamano); // Intercambiar el primer y el último
				min = arbol.removeLast(); // Eliminar el último elemento (el mínimo)
			} else {
				min = arbol.removeLast(); // Solo queda un elemento
			}
			tamano--;
			if (tamano > 0) {
				sink(1); // Asegurar que el árbol siga siendo un heap después de la eliminación
			}
		} catch (PosException | VacioException e) {
			e.printStackTrace();
		}
		return min;
	}

	public boolean isEmpty() {
		return tamano == 0;
	}
}

