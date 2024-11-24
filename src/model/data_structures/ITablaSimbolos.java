package model.data_structures;

public interface ITablaSimbolos<K extends Comparable<K>, V extends Comparable<V>> {

	/**
	 * Inserta un valor en la tabla de símbolos.
	 * Si la clave ya existe, reemplaza el valor asociado.
	 *
	 * @param key La clave que se inserta
	 * @param value El valor asociado a la clave
	 */
	void put(K key, V value);

	/**
	 * Obtiene el valor asociado a la clave.
	 *
	 * @param key La clave cuyo valor se quiere obtener
	 * @return El valor asociado a la clave, o null si la clave no existe
	 */
	V get(K key);

	/**
	 * Elimina el valor asociado a la clave y la clave misma.
	 *
	 * @param key La clave que se desea eliminar
	 * @return El valor previamente asociado a la clave, o null si la clave no existe
	 */
	V remove(K key);

	/**
	 * Verifica si una clave está presente en la tabla de símbolos.
	 *
	 * @param key La clave que se quiere verificar
	 * @return true si la clave está presente, false si no
	 */
	boolean contains(K key);

	/**
	 * Verifica si la tabla de símbolos está vacía.
	 *
	 * @return true si la tabla está vacía, false si tiene elementos
	 */
	boolean isEmpty();

	/**
	 * Obtiene el número de elementos en la tabla de símbolos.
	 *
	 * @return El tamaño de la tabla de símbolos
	 */
	int size();

	/**
	 * Obtiene una lista de todas las claves en la tabla.
	 *
	 * @return Una lista con todas las claves
	 */
	ILista<K> keySet();

	/**
	 * Obtiene una lista de todos los valores en la tabla.
	 *
	 * @return Una lista con todos los valores
	 */
	ILista<V> valueSet();
}

