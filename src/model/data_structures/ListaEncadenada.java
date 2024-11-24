package model.data_structures;

public class ListaEncadenada<T extends Comparable<T>> implements ILista<T> {

	private Nodo<T> first;
	private int size;
	private Nodo<T> last;

	public ListaEncadenada() {
		first = null;
		last = null;
		size = 0;
	}

	public ListaEncadenada(T element) {
		first = new Nodo<T>(element);
		last = first;
		size = 1;
	}

	// Método auxiliar para verificar si la posición es válida
	private void validatePos(int pos) throws PosException {
		if (pos < 1 || pos > size) {
			throw new PosException("La posición no es válida");
		}
	}

	// Método auxiliar para manejar nodos al obtener un nodo en una posición específica
	private Nodo<T> getNodeAt(int pos) throws VacioException {
		if (isEmpty()) {
			throw new VacioException("La lista está vacía");
		}
		Nodo<T> current = first;
		for (int i = 0; i < pos - 1; i++) {
			current = current.getNext();
		}
		return current;
	}

	// Método para insertar al inicio
	public void addFirst(T element) {
		if (element == null) throw new NullException("No es válido el elemento ingresado");
		Nodo<T> newNode = new Nodo<T>(element);
		newNode.setNext(first);
		first = newNode;
		if (last == null) {
			last = first;
		}
		size++;
	}

	// Método para insertar al final
	public void addLast(T element) {
		if (element == null) throw new NullException("No es válido el elemento ingresado");
		Nodo<T> newNode = new Nodo<T>(element);
		if (isEmpty()) {
			first = newNode;
			last = newNode;
		} else {
			last.setNext(newNode);
			last = newNode;
		}
		size++;
	}

	// Método para insertar un elemento en una posición específica
	public void insertElement(T element, int pos) throws PosException, NullException {
		if (element == null) throw new NullException("No es válido el elemento ingresado");
		validatePos(pos);

		if (pos == 1) {
			addFirst(element);
		} else if (pos == size + 1) {
			addLast(element);
		} else {
			Nodo<T> newNode = new Nodo<T>(element);
			Nodo<T> prevNode = getNodeAt(pos - 1);
			newNode.setNext(prevNode.getNext());
			prevNode.setNext(newNode);
			size++;
		}
	}

	// Eliminar el primer elemento
	public T removeFirst() throws VacioException {
		if (isEmpty()) throw new VacioException("La lista está vacía");
		T firstElement = first.getInfo();
		first = first.getNext();
		if (first == null) {
			last = null;
		}
		size--;
		return firstElement;
	}

	// Eliminar el último elemento
	public T removeLast() throws VacioException {
		if (isEmpty()) throw new VacioException("La lista está vacía");
		if (size == 1) {
			T lastElement = first.getInfo();
			first = null;
			last = null;
			size--;
			return lastElement;
		}

		Nodo<T> penultimate = first;
		while (penultimate.getNext() != last) {
			penultimate = penultimate.getNext();
		}
		T lastElement = last.getInfo();
		last = penultimate;
		last.setNext(null);
		size--;
		return lastElement;
	}

	// Eliminar un elemento en una posición específica
	public T deleteElement(int pos) throws PosException, VacioException {
		validatePos(pos);
		T element = null;

		if (pos == 1) {
			element = removeFirst();
		} else if (pos == size) {
			element = removeLast();
		} else {
			Nodo<T> prevNode = getNodeAt(pos - 1);
			element = prevNode.getNext().getInfo();
			prevNode.setNext(prevNode.getNext().getNext());
			size--;
		}
		return element;
	}

	// Obtener el primer elemento
	public T firstElement() throws VacioException {
		if (isEmpty()) throw new VacioException("La lista está vacía");
		return first.getInfo();
	}

	// Obtener el último elemento
	public T lastElement() {
		if (isEmpty()) return null;
		return last.getInfo();
	}

	// Obtener un elemento en una posición específica
	public T getElement(int pos) throws PosException, VacioException {
		validatePos(pos);
		return getNodeAt(pos).getInfo();
	}

	// Tamaño de la lista
	public int size() {
		return size;
	}

	// Verificar si la lista está vacía
	public boolean isEmpty() {
		return first == null;
	}

	// Cambiar el valor de un elemento en una posición específica
	public void changeInfo(int pos, T element) throws PosException, VacioException, NullException {
		validatePos(pos);
		if (element == null) throw new NullException("No es válido el elemento ingresado");

		Nodo<T> node = getNodeAt(pos);
		node.change(element);
	}

	// Sublista de elementos
	public ILista<T> sublista(int pos, int numElementos) throws PosException, VacioException, NullException {
		if (numElementos < 0) throw new PosException("La cantidad de elementos no es válida");
		if (numElementos >= size) return this;

		ILista<T> sublist = new ListaEncadenada<>();
		for (int i = 0; i < numElementos; i++) {
			sublist.insertElement(getElement(pos + i), i + 1);
		}
		return sublist;
	}

	@Override
	public int compareTo(ILista o) {
		// Implementación de la comparación (si es necesario)
		return 0;
	}
}

