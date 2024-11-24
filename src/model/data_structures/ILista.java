package model.data_structures;


/**
 * Interfaz genérica para una lista. Define operaciones básicas
 * como adición, eliminación, acceso y manipulación de elementos.
 * 
 * @param <T> Tipo de elementos en la lista, debe ser comparable.
 */
public interface ILista<T extends Comparable<T>> extends Comparable<ILista<T>> {

    /**
     * Agrega un elemento al inicio de la lista.
     * 
     * @param element Elemento a agregar.
     */
    void addFirst(T element);

    /**
     * Agrega un elemento al final de la lista.
     * 
     * @param element Elemento a agregar.
     */
    void addLast(T element);

    /**
     * Inserta un elemento en una posición específica de la lista.
     * 
     * @param element Elemento a insertar.
     * @param pos     Posición donde insertar el elemento (1-indexado).
     * @throws PosException  Si la posición está fuera de rango.
     * @throws NullException Si el elemento es nulo.
     */
    void insertElement(T element, int pos) throws PosException, NullException;

    /**
     * Elimina y devuelve el primer elemento de la lista.
     * 
     * @return Primer elemento eliminado.
     * @throws VacioException Si la lista está vacía.
     */
    T removeFirst() throws VacioException;

    /**
     * Elimina y devuelve el último elemento de la lista.
     * 
     * @return Último elemento eliminado.
     */
    T removeLast();

    /**
     * Elimina un elemento en una posición específica.
     * 
     * @param pos Posición del elemento a eliminar (1-indexado).
     * @return Elemento eliminado.
     * @throws PosException  Si la posición está fuera de rango.
     * @throws VacioException Si la lista está vacía.
     */
    T deleteElement(int pos) throws PosException, VacioException;

    /**
     * Devuelve el primer elemento de la lista sin eliminarlo.
     * 
     * @return Primer elemento.
     * @throws VacioException Si la lista está vacía.
     */
    T firstElement() throws VacioException;

    /**
     * Devuelve el último elemento de la lista sin eliminarlo.
     * 
     * @return Último elemento.
     * @throws VacioException Si la lista está vacía.
     */
    T lastElement() throws VacioException;

    /**
     * Obtiene un elemento en una posición específica de la lista.
     * 
     * @param pos Posición del elemento a obtener (1-indexado).
     * @return Elemento en la posición dada.
     * @throws PosException  Si la posición está fuera de rango.
     * @throws VacioException Si la lista está vacía.
     */
    T getElement(int pos) throws PosException, VacioException;

    /**
     * Devuelve el tamaño de la lista.
     * 
     * @return Número de elementos en la lista.
     */
    int size();

    /**
     * Indica si la lista está vacía.
     * 
     * @return true si la lista está vacía, false de lo contrario.
     */
    boolean isEmpty();

    /**
     * Busca un elemento en la lista y devuelve su posición.
     * 
     * @param element Elemento a buscar.
     * @return Posición del elemento (1-indexado), o -1 si no está presente.
     * @throws VacioException Si la lista está vacía.
     * @throws NullException  Si el elemento es nulo.
     */
    int isPresent(T element) throws VacioException, NullException;

    /**
     * Intercambia los elementos en dos posiciones específicas.
     * 
     * @param pos1 Primera posición (1-indexado).
     * @param pos2 Segunda posición (1-indexado).
     * @throws PosException  Si alguna posición está fuera de rango.
     * @throws VacioException Si la lista está vacía.
     */
    void exchange(int pos1, int pos2) throws PosException, VacioException;

    /**
     * Cambia la información de un elemento en una posición específica.
     * 
     * @param pos     Posición del elemento a modificar (1-indexado).
     * @param element Nuevo valor del elemento.
     * @throws PosException  Si la posición está fuera de rango.
     * @throws VacioException Si la lista está vacía.
     * @throws NullException  Si el elemento es nulo.
     */
    void changeInfo(int pos, T element) throws PosException, VacioException, NullException;

    /**
     * Crea una sublista basada en la lista original.
     * 
     * @param pos          Posición inicial de la sublista (1-indexado).
     * @param numElementos Número de elementos que contendrá la sublista.
     *                     Si excede el tamaño de la lista original, se devuelve hasta el final.
     * @return Nueva sublista con la misma representación.
     * @throws PosException  Si la posición está fuera de rango.
     * @throws VacioException Si la lista está vacía.
     * @throws NullException  Si el número de elementos es negativo o nulo.
     */
    ILista<T> sublista(int pos, int numElementos) throws PosException, VacioException, NullException;
}

