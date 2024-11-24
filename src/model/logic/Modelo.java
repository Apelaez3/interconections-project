package model.logic;

import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import model.data_structures.*;
import model.exceptions.*;

/**
 * Modelo del mundo refactorizado con mejor cohesión y menor complejidad
 */
public class Modelo {
	// Constantes
	private static final String COUNTRIES_FILE = "./data/countries.csv";
	private static final String LANDING_POINTS_FILE = "./data/landing_points.csv";
	private static final String CONNECTIONS_FILE = "./data/connections.csv";
	private static final double EARTH_RADIUS_KM = 6371.0;

	// Estructuras de datos principales
	private final GrafoListaAdyacencia grafo;
	private final ITablaSimbolos<String, Country> paises;
	private final ITablaSimbolos<String, Landing> points;
	private final ITablaSimbolos<String, ILista<Vertex>> landingIdTabla;
	private final ITablaSimbolos<String, String> nombreCodigo;

	/**
	 * Constructor del modelo
	 */
	public Modelo() {
		this.grafo = new GrafoListaAdyacencia(2);
		this.paises = new TablaHashLinearProbing<>(2);
		this.points = new TablaHashLinearProbing<>(2);
		this.landingIdTabla = new TablaHashSeparteChaining<>(2);
		this.nombreCodigo = new TablaHashSeparteChaining<>(2);
	}

	/**
	 * Carga los datos desde los archivos CSV
	 */
	public void cargarDatos() throws IOException {
		cargarPaises();
		cargarPuntosAterrizaje();
		cargarConexiones();
		construirGrafoConexiones();
	}

	/**
	 * Carga los países desde el archivo CSV
	 */
	private void cargarPaises() throws IOException {
		try (FileReader reader = new FileReader(COUNTRIES_FILE)) {
			for (CSVRecord record : CSVFormat.RFC4180.withHeader().parse(reader)) {
				if (!record.get(0).isEmpty()) {
					Country pais = crearPaisDesdeCSV(record);
					grafo.insertVertex(pais.getCapitalName(), pais);
					paises.put(pais.getCountryName(), pais);
				}
			}
		}
	}

	/**
	 * Crea un objeto Country desde un registro CSV
	 */
	private Country crearPaisDesdeCSV(CSVRecord record) {
		return new Country(
				record.get(0), // countryName
				record.get(1), // capitalName
				Double.parseDouble(record.get(2)), // latitude
				Double.parseDouble(record.get(3)), // longitude
				record.get(4), // code
				record.get(5), // continentName
				Float.parseFloat(record.get(6).replace(".", "")), // population
				Double.parseDouble(record.get(7).replace(".", "")) // users
		);
	}

	/**
	 * Carga los puntos de aterrizaje desde el archivo CSV
	 */
	private void cargarPuntosAterrizaje() throws IOException {
		try (FileReader reader = new FileReader(LANDING_POINTS_FILE)) {
			for (CSVRecord record : CSVFormat.RFC4180.withHeader().parse(reader)) {
				Landing landing = crearLandingDesdeCSV(record);
				points.put(landing.getLandingId(), landing);
			}
		}
	}

	/**
	 * Crea un objeto Landing desde un registro CSV
	 */
	private Landing crearLandingDesdeCSV(CSVRecord record) {
		String[] nombreInfo = record.get(2).split(", ");
		return new Landing(
				record.get(0), // landingId
				record.get(1), // id
				nombreInfo[0], // name
				nombreInfo[nombreInfo.length - 1], // paisnombre
				Double.parseDouble(record.get(3)), // latitude
				Double.parseDouble(record.get(4)) // longitude
		);
	}

	/**
	 * Carga las conexiones desde el archivo CSV
	 */
	private void cargarConexiones() throws IOException {
		try (FileReader reader = new FileReader(CONNECTIONS_FILE)) {
			for (CSVRecord record : CSVFormat.RFC4180.withHeader().parse(reader)) {
				procesarConexion(
						record.get(0), // origin
						record.get(1), // destination
						record.get(3)  // cableId
				);
			}
		}
	}

	/**
	 * Procesa una conexión entre dos puntos
	 */
	private void procesarConexion(String origin, String destination, String cableId) {
		Landing landing1 = points.get(origin);
		Landing landing2 = points.get(destination);

		if (landing1 != null && landing2 != null) {
			String vertexId1 = landing1.getLandingId() + cableId;
			String vertexId2 = landing2.getLandingId() + cableId;

			agregarVerticesYConexiones(landing1, landing2, vertexId1, vertexId2);
			actualizarTablasIndice(landing1, landing2, vertexId1, vertexId2);
		}
	}

	/**
	 * Agrega vértices y conexiones al grafo
	 */
	private void agregarVerticesYConexiones(Landing landing1, Landing landing2,
											String vertexId1, String vertexId2) {
		grafo.insertVertex(vertexId1, landing1);
		grafo.insertVertex(vertexId2, landing2);

		conectarPaisConLanding(landing1, vertexId1);
		conectarPaisConLanding(landing2, vertexId2);

		// Conexión entre landings
		grafo.addEdge(vertexId1, vertexId2, calcularDistancia(
				landing1.getLongitude(), landing1.getLatitude(),
				landing2.getLongitude(), landing2.getLatitude()
		));
	}

	/**
	 * Conecta un país con su punto de aterrizaje
	 */
	private void conectarPaisConLanding(Landing landing, String vertexId) {
		Country pais = obtenerPaisPorNombre(landing.getPais());
		if (pais != null) {
			float distancia = calcularDistancia(
					pais.getLongitude(), pais.getLatitude(),
					landing.getLongitude(), landing.getLatitude()
			);
			grafo.addEdge(pais.getCapitalName(), vertexId, distancia);
		}
	}

	/**
	 * Actualiza las tablas de índice con la nueva conexión
	 */
	private void actualizarTablasIndice(Landing landing1, Landing landing2,
										String vertexId1, String vertexId2) {
		Vertex vertex1 = grafo.getVertex(vertexId1);
		Vertex vertex2 = grafo.getVertex(vertexId2);

		actualizarTablaLandingId(landing1.getLandingId(), vertex1);
		actualizarTablaLandingId(landing2.getLandingId(), vertex2);

		nombreCodigo.put(landing1.getName(), landing1.getLandingId());
		nombreCodigo.put(landing2.getName(), landing2.getLandingId());
	}

	/**
	 * Actualiza la tabla de ID de landing points
	 */
	private void actualizarTablaLandingId(String landingId, Vertex vertex) {
		ILista<Vertex> vertices = (ILista<Vertex>) landingIdTabla.get(landingId);
		if (vertices == null) {
			vertices = new ArregloDinamico<>(1);
			landingIdTabla.put(landingId, vertices);
		}
		try {
			vertices.insertElement(vertex, vertices.size() + 1);
		} catch (PosException | NullException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Construye las conexiones adicionales en el grafo
	 */
	private void construirGrafoConexiones() {
		try {
			ILista<ILista<Vertex>> valores = landingIdTabla.valueSet();
			for (int i = 1; i <= valores.size(); i++) {
				ILista<Vertex> listaVertices = valores.getElement(i);
				if (listaVertices != null && listaVertices.size() > 1) {
					conectarVerticesEntreSi(listaVertices);
				}
			}
		} catch (PosException | VacioException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Conecta todos los vértices entre sí en una lista
	 */
	private void conectarVerticesEntreSi(ILista<Vertex> vertices) {
		try {
			for (int i = 1; i < vertices.size(); i++) {
				for (int j = i + 1; j <= vertices.size(); j++) {
					Vertex v1 = vertices.getElement(i);
					Vertex v2 = vertices.getElement(j);
					grafo.addEdge(v1.getId(), v2.getId(), 100);
				}
			}
		} catch (PosException | VacioException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene un país por su nombre, manejando casos especiales
	 */
	private Country obtenerPaisPorNombre(String nombrePais) {
		if ("Côte d'Ivoire".equals(nombrePais)) {
			return paises.get("Cote d'Ivoire");
		}
		return paises.get(nombrePais);
	}

	/**
	 * Calcula la distancia entre dos puntos usando la fórmula de Haversine
	 */
	private static float calcularDistancia(double lon1, double lat1, double lon2, double lat2) {
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);

		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(lat1) * Math.cos(lat2) *
						Math.sin(dLon/2) * Math.sin(dLon/2);

		double c = 2 * Math.asin(Math.min(1.0, Math.sqrt(a)));

		return (float) (EARTH_RADIUS_KM * c);
	}

	// Métodos de acceso y consulta

	public ITablaSimbolos<String, Country> getPaises() {
		return paises;
	}

	public GrafoListaAdyacencia getGrafo() {
		return grafo;
	}

	public ITablaSimbolos<String, Landing> getPoints() {
		return points;
	}
}
