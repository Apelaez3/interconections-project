package model.data_structures;

import java.util.Comparator;

public class Country implements Comparable<Country> {
	private String countryName;
	private String capitalName;
	private double latitude;
	private double longitude;
	private String code;
	private String continentName;
	private float population;
	private double users;
	private double distlan;

	public Country(String countryName, String capitalName, double latitude, double longitude,
				   String code, String continentName, float population, double users) {
		this.countryName = countryName;
		this.capitalName = capitalName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.code = code;
		this.continentName = continentName;
		this.population = population;
		this.users = users;
		this.distlan = 0; // Initializing distlan to 0 by default.
	}

	// Getters and setters
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCapitalName() {
		return capitalName;
	}

	public void setCapitalName(String capitalName) {
		this.capitalName = capitalName;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContinentName() {
		return continentName;
	}

	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}

	public float getPopulation() {
		return population;
	}

	public void setPopulation(float population) {
		this.population = population;
	}

	public double getUsers() {
		return users;
	}

	public void setUsers(double users) {
		this.users = users;
	}

	public double getDistlan() {
		return distlan;
	}

	public void setDistlan(double distlan) {
		this.distlan = distlan;
	}

	@Override
	public int compareTo(Country o) {
		// Default comparison by country name (could be adjusted to other criteria)
		return this.countryName.compareTo(o.getCountryName());
	}

	// Comparador por distancia
	public static class ComparadorXKm implements Comparator<Country> {
		@Override
		public int compare(Country pais1, Country pais2) {
			return Double.compare(pais1.getDistlan(), pais2.getDistlan());
		}
	}

	// Comparador por nombre
	public static class ComparadorXNombre implements Comparator<Country> {
		@Override
		public int compare(Country pais1, Country pais2) {
			return pais1.getCountryName().compareTo(pais2.getCountryName());
		}
	}
}

