package utils;

import java.util.Comparator;
import model.data_structures.ILista;
import model.exceptions.PosException;
import model.exceptions.VacioException;

public final class Ordenamiento<T extends Comparable<T>> {

	/**
	 * Método auxiliar para realizar la comparación según el criterio
	 * y si debe ser ascendente o descendente.
	 */
	private int comparar(Comparator<T> criterio, boolean ascendente, T a, T b) {
		int resultado = criterio.compare(a, b);
		return ascendente ? resultado : -resultado;
	}

	/**
	 * Método auxiliar para intercambiar dos elementos en la lista.
	 */
	private void intercambiar(ILista<T> lista, int i, int j) throws PosException {
		T temp = lista.getElement(i);
		lista.changeInfo(i, lista.getElement(j));
		lista.changeInfo(j, temp);
	}

	/**
	 * Algoritmo de ordenación por selección.
	 */
	public void ordenarSeleccion(ILista<T> lista, Comparator<T> criterio, boolean ascendente) throws PosException, VacioException {
		int size = lista.size();
		for (int i = 1; i < size; i++) {
			int posMayorMenor = i;
			for (int j = i + 1; j <= size; j++) {
				if (comparar(criterio, ascendente, lista.getElement(posMayorMenor), lista.getElement(j)) > 0) {
					posMayorMenor = j;
				}
			}
			intercambiar(lista, posMayorMenor, i);
		}
	}

	/**
	 * Algoritmo de ordenación por inserción.
	 */
	public void ordenarInsercion(ILista<T> lista, Comparator<T> criterio, boolean ascendente) throws PosException, VacioException {
		int size = lista.size();
		for (int i = 2; i <= size; i++) {
			for (int j = i; j > 1 && comparar(criterio, ascendente, lista.getElement(j), lista.getElement(j - 1)) < 0; j--) {
				intercambiar(lista, j, j - 1);
			}
		}
	}

	/**
	 * Algoritmo de ordenación Shell.
	 */
	public void ordenarShell(ILista<T> lista, Comparator<T> criterio, boolean ascendente) throws PosException, VacioException {
		int n = lista.size();
		int h = 1;

		// Calcular el valor de h inicial
		while (h < (n / 3)) {
			h = 3 * h + 1;
		}

		// Aplicar el algoritmo Shell
		while (h >= 1) {
			for (int i = h + 1; i <= n; i++) {
				for (int j = i; j > h && comparar(criterio, ascendente, lista.getElement(j), lista.getElement(j - h)) < 0; j -= h) {
					intercambiar(lista, j, j - h);
				}
			}
			h /= 3;
		}
	}

	/**
	 * Algoritmo de ordenación QuickSort.
	 */
	public void ordenarQuickSort(ILista<T> lista, Comparator<T> criterio, boolean ascendente) throws PosException, VacioException {
		sort(lista, criterio, ascendente, 1, lista.size());
	}

	/**
	 * Método recursivo para QuickSort.
	 */
	private void sort(ILista<T> lista, Comparator<T> criterio, boolean ascendente, int lo, int hi) throws PosException, VacioException {
		if (lo >= hi) return;
		int pivot = partition(lista, criterio, ascendente, lo, hi);
		sort(lista, criterio, ascendente, lo, pivot - 1);
		sort(lista, criterio, ascendente, pivot + 1, hi);
	}

	/**
	 * Función de partición utilizada en QuickSort.
	 */
	private int partition(ILista<T> lista, Comparator<T> criterio, boolean ascendente, int lo, int hi) throws PosException, VacioException {
		int follower = lo;
		for (int leader = lo; leader < hi; leader++) {
			if (comparar(criterio, ascendente, lista.getElement(leader), lista.getElement(hi)) < 0) {
				intercambiar(lista, follower, leader);
				follower++;
			}
		}
		intercambiar(lista, follower, hi);
		return follower;
	}

	/**
	 * Algoritmo de ordenación MergeSort.
	 */
	public void ordenarMergeSort(ILista<T> lista, Comparator<T> criterio, boolean ascendente) throws PosException, VacioException {
		int size = lista.size();
		if (size > 1) {
			int mid = size / 2;
			ILista<T> leftList = lista.sublista(1, mid);
			ILista<T> rightList = lista.sublista(mid + 1, size - mid);

			ordenarMergeSort(leftList, criterio, ascendente);
			ordenarMergeSort(rightList, criterio, ascendente);

			merge(lista, leftList, rightList, criterio, ascendente);
		}
	}

	/**
	 * Función de mezcla utilizada en MergeSort.
	 */
	private void merge(ILista<T> lista, ILista<T> leftList, ILista<T> rightList, Comparator<T> criterio, boolean ascendente) throws PosException, VacioException {
		int i = 1, j = 1, k = 1;
		int leftSize = leftList.size();
		int rightSize = rightList.size();

		while (i <= leftSize && j <= rightSize) {
			T leftElem = leftList.getElement(i);
			T rightElem = rightList.getElement(j);

			if (comparar(criterio, ascendente, leftElem, rightElem) <= 0) {
				lista.changeInfo(k, leftElem);
				i++;
			} else {
				lista.changeInfo(k, rightElem);
				j++;
			}
			k++;
		}

		while (i <= leftSize) {
			lista.changeInfo(k, leftList.getElement(i));
			i++;
			k++;
		}

		while (j <= rightSize) {
			lista.changeInfo(k, rightList.getElement(j));
			j++;
			k++;
		}
	}
}


