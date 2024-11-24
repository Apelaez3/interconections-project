package view;

import model.logic.Modelo;

public class View {

	// Constantes para las opciones del menú
	public static final int OPCION_CARGAR_DATOS = 1;
	public static final int OPCION_COMPONENTES_CONECTADOS = 2;
	public static final int OPCION_ENCONTRAR_LANDING_INTERCONEXION = 3;
	public static final int OPCION_RUTA_MINIMA = 4;
	public static final int OPCION_RED_EXPANSION_MINIMA = 5;
	public static final int OPCION_FALLAS_CONEXION = 6;
	public static final int OPCION_EXIT = 7;

	/**
	 * Método constructor
	 */
	public View() {
	}

	/**
	 * Muestra el menú con las opciones disponibles.
	 */
	public void printMenu() {
		System.out.println("Seleccione una opción:");
		System.out.println(OPCION_CARGAR_DATOS + ". Cargar datos");
		System.out.println(OPCION_COMPONENTES_CONECTADOS + ". Componentes conectados");
		System.out.println(OPCION_ENCONTRAR_LANDING_INTERCONEXION + ". Encontrar landings interconexión");
		System.out.println(OPCION_RUTA_MINIMA + ". Ruta mínima");
		System.out.println(OPCION_RED_EXPANSION_MINIMA + ". Red de expansión mínima");
		System.out.println(OPCION_FALLAS_CONEXION + ". Fallas en conexión");
		System.out.println(OPCION_EXIT + ". Exit");
		System.out.println("Ingrese el número de opción y presione Enter (e.g., 1):");
	}

	/**
	 * Imprime un mensaje genérico.
	 *
	 * @param mensaje El mensaje a imprimir.
	 */
	public void printMessage(String mensaje) {
		System.out.println(mensaje);
	}

	/**
	 * Imprime el estado del modelo.
	 *
	 * @param modelo El objeto modelo que contiene los datos y estado actual.
	 */
	public void printModelo(Modelo modelo) {
		System.out.println(modelo);
	}

	/**
	 * Imprime un mensaje de error.
	 *
	 * @param error El mensaje de error.
	 */
	public void printError(String error) {
		System.err.println("Error: " + error);
	}

	/**
	 * Solicita la entrada del usuario para una opción del menú.
	 *
	 * @return La opción seleccionada por el usuario.
	 */
	public int solicitarOpcion() {
		try {
			// Simulamos la captura de entrada del usuario
			// En la implementación real, usarías Scanner u otro método
			int opcion = new java.util.Scanner(System.in).nextInt();
			return opcion;
		} catch (java.util.InputMismatchException e) {
			System.err.println("Entrada inválida. Por favor, ingrese un número de opción válido.");
			return -1; // O algún valor que indique error
		}
	}
}

