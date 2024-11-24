package model.data_structures;

import model.exceptions.PosException;
import model.exceptions.NullException;
import model.exceptions.VacioException;

/**
 * Interfaz genérica para una lista que admite elementos comparables.
 * @param <T> Tipo de los elementos de la lista.
 */
public interface ILista<T extends Comparable<T>> {

    void addFirst(T element);

    void addLast(T element);

    void insertElement(T element, int pos) throws PosException, NullException;

    T removeFirst() throws VacioException;

    T removeLast() throws VacioException;

    T deleteElement(int pos) throws PosException, VacioException;

    T firstElement() throws VacioException;

    T lastElement() throws VacioException;

    T getElement(int pos) throws PosException, VacioException;

    int size();

    boolean isEmpty();

    int isPresent(T element) throws VacioException, NullException;

    void exchange(int pos1, int pos2) throws PosException, VacioException;

    void changeInfo(int pos, T element) throws PosException, VacioException, NullException;

    /**
     * Crear una sublista de la lista original.
     * @param pos Posición inicial desde donde crear la sublista.
     * @param numElementos Número de elementos que contendrá la sublista.
     * @return Sublista creada con la misma representación de la lista original.
     * @throws PosException Si la posición es inválida.
     * @throws VacioException Si la lista está vacía.
     * @throws NullException Si algún argumento es nulo.
     */
    ILista<T> sublista(int pos, int numElementos) throws PosException, VacioException, NullException;

}


