package model.data_structures;

import model.exceptions.PosException;
import model.exceptions.NullException;
import model.exceptions.VacioException;

/**
 * Interfaz genérica para una lista que admite elementos comparables.
 * @param <T> Tipo de los elementos de la lista.
 */
public interface ILista<T extends Comparable<T>> {

    /**
     * Agrega un elemento al inicio de la lista.
     * @param element Elemento a agregar.
     */
    void addFirst(T element);

    /**
     * Agrega un elemento al final de la lista.
     * @param element Elemento a agregar.
     */
    void addLast(T element);

    /**
     * Inserta un elemento en una posición específica.
     * @param element Elemento a insertar.
     * @param pos Posición donde insertar el elemento (1-based index).
     * @throws PosException Si la posición es inválida.
     * @throws NullException Si el elemento es nulo.
     */
    void insertElement(T element, int pos) throws PosException, NullException;

    /**
     * Elimina y retorna el primer elemento de la lista.
     * @return Primer elemento eliminado.
     * @throws VacioException Si la lista está vacía.
     */
    T removeFirst() throws VacioException;

    /**
     * Elimina y retorna el último elemento de la lista.
     * @return Último elemento eliminado.
     * @throws VacioException Si la lista está vacía.
     */
    T removeLast() throws VacioException;

    /**
     * Elimina y retorna un elemento en una posición específica.
     * @param pos Posición del elemento a eliminar (1-based index).
     * @return Elemento eliminado.
     * @throws PosException Si la posición es inválida.
     * @throws VacioException Si la lista está vacía.
     */
    T deleteElement(int pos) throws PosException, VacioException;

    /**
     * Retorna el primer elemento de la lista sin eliminarlo.
     * @return Primer elemento.
     * @throws VacioException Si la lista está vacía.
     */
    T firstElement() throws VacioException;

    /**
     * Retorna el último elemento de la lista sin eliminarlo.
     * @return Último elemento.
     * @throws VacioException Si la lista está vacía.
     */
    T lastElement() throws VacioException;

    /**
     * Retorna el elemento en una posición específica.
     * @param pos Posición del elemento a retornar (1-based index).
     * @return Elemento en la posición indicada.
     * @throws PosException Si la posición es inválida.
     * @throws VacioException Si la lista está vacía.
     */
    T getElement(int pos) throws PosException, VacioException;

    /**
     * Retorna el número de elementos en la lista.
     * @return Tamaño de la lista.
     */
    int size();

    /**
     * Verifica si la lista está vacía.
     * @return `true` si la lista está vacía, de lo contrario `false`.
     */
    boolean isEmpty();

    /**
     * Verifica si un elemento está presente en la lista.
     * @param element Elemento a buscar.
     * @return Posición del elemento (1-based index) o -1 si no está presente.
     * @throws VacioException Si la lista está vacía.
     * @throws NullException Si el elemento es nulo.
     */
    int isPresent(T element) throws VacioException, NullException;

    /**
     * Intercambia los elementos en dos posiciones específicas.
     * @param pos1 Primera posición (1-based index).
     * @param pos2 Segunda posición (1-based index).
     * @throws PosException Si alguna posición es inválida.
     * @throws VacioException Si la lista está vacía.
     */
    void exchange(int pos1, int pos2) throws PosException, VacioException;

    /**
     * Cambia el contenido de un elemento en una posición específica.
     * @param pos Posición del elemento a cambiar (1-based index).
     * @param element Nuevo contenido del elemento.
     * @throws PosException Si la posición es inválida.
     * @throws VacioException Si la lista está vacía.
     * @throws NullException Si el elemento es nulo.
     */
    void changeInfo(int pos, T element) throws PosException, VacioException, NullException;

    /**
     * Crea una sublista desde la posición inicial y con el número de elementos especificados.
     * @param pos Posición inicial de la sublista (1-based index).
     * @param numElementos Número de elementos a incluir en la sublista.
     * @return Sublista creada con la misma representación de la lista original.
     * @throws PosException Si la posición es inválida o excede los límites de la lista.
     * @throws VacioException Si la lista está vacía.
     */
    ILista<T> sublista(int pos, int numElementos) throws PosException, VacioException;
}



