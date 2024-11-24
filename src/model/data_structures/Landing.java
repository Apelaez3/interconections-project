package model.data_structures;

public class Landing implements Comparable<Landing> {
	private String landingId;
	private String id;
	private String name;
	private String pais;
	private double latitude;
	private double longitude;
	private String codigo;

	public Landing(String landingId, String id, String name, String pais, double latitude, double longitude) {
		this.landingId = landingId;
		this.id = id;
		this.name = name;
		this.pais = pais;
		this.latitude = latitude;
		this.longitude = longitude;
		this.codigo = ""; // Set default empty value for codigo
	}

	// Getters and Setters
	public String getLandingId() {
		return landingId;
	}

	public void setLandingId(String landingId) {
		this.landingId = landingId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	// Implementación de compareTo por nombre
	@Override
	public int compareTo(Landing o) {
		// Se compara por el nombre (puedes cambiar este criterio según sea necesario)
		return this.name.compareTo(o.getName());
	}
}

