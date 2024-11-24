package controller;

import java.io.IOException;
import java.util.Scanner;

import model.logic.Modelo;
import view.View;

public class Controller {

	private Modelo modelo;
	private View view;
	private Scanner lector;

	public Controller() {
		view = new View();
		lector = new Scanner(System.in).useDelimiter("\n");
	}

	public void run() {
		boolean fin = false;

		while (!fin) {
			view.printMenu();
			int option = lector.nextInt();
			fin = executeOption(option);
		}
		lector.close();
	}

	private boolean executeOption(int option) {
		switch (option) {
			case 1:
				cargarDatos();
				break;
			case 2:
				realizarConexion();
				break;
			case 3:
				mostrarResultado(modelo.req2String());
				break;
			case 4:
				compararPaises();
				break;
			case 5:
				mostrarResultado(modelo.req4String());
				break;
			case 6:
				realizarBusquedaPuntoConexion();
				break;
			case 7:
				view.printMessage("--------- \n Hasta pronto !! \n---------");
				return true;
			default:
				view.printMessage("--------- \n Opcion Invalida !! \n---------");
				break;
		}
		return false;
	}

	private void cargarDatos() {
		view.printMessage("--------- \nCargar datos");
		modelo = new Modelo(1);
		try {
			modelo.cargar();
			view.printModelo(modelo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void realizarConexion() {
		String punto1 = solicitarNombre("Ingrese el nombre del primer punto de conexión");
		String punto2 = solicitarNombre("Ingrese el nombre del segundo punto de conexión");

		String resultado = modelo.req1String(punto1, punto2);
		view.printMessage(resultado);
	}

	private void compararPaises() {
		String pais1 = solicitarNombre("Ingrese el nombre del primer país");
		String pais2 = solicitarNombre("Ingrese el nombre del segundo país");

		String resultado = modelo.req3String(pais1, pais2);
		view.printMessage(resultado);
	}

	private void realizarBusquedaPuntoConexion() {
		String puntoConexion = solicitarNombre("Ingrese el nombre del punto de conexión");
		String resultado = modelo.req5String(puntoConexion);
		view.printMessage(resultado);
	}

	private String solicitarNombre(String mensaje) {
		view.printMessage("--------- \n" + mensaje);
		String nombre = lector.next();
		lector.nextLine();  // Limpiar el buffer
		return nombre;
	}

	private void mostrarResultado(String resultado) {
		view.printMessage(resultado);
	}
}

