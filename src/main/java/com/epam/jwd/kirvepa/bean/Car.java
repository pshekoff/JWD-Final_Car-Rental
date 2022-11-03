package com.epam.jwd.kirvepa.bean;

import java.io.Serializable;

import com.epam.jwd.kirvepa.controller.ResourceManager;

public class Car implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final ResourceManager manager = ResourceManager.getInstance();
	
	private int id;
	private String manufacturer;
	private String model;
	private String licensePlate;
	private String vin;
	private String bodyType;
	private int issueYear;
	private String engine;
	private String transmission;
	private String driveType;
	private String color;
	private int weight;
	private boolean available;
	
	public Car() {}
	
	public Car(int id
			, String manufacturer
			, String model
			, String licensePlate
			, String vin
			, String bodyType
			, int issueYear
			, String engine
			, String transmission
			, String driveType
			, String color
			, int weight
			, boolean available) {
		setId(id);
		setManufacturer(manufacturer);
		setModel(model);
		setLicensePlate(licensePlate);
		setVin(vin);
		setBodyType(bodyType);
		setIssueYear(issueYear);
		setEngine(engine);
		setTransmission(transmission);
		setDriveType(driveType);
		setColor(color);
		setWeight(weight);
		setAvailable(available);
	}
	
	public Car(String manufacturer
			, String model
			, String licensePlate
			, String vin
			, String bodyType
			, int issueYear
			, String engine
			, String transmission
			, String driveType
			, String color
			, int weight
			, boolean available) {
		setManufacturer(manufacturer);
		setModel(model);
		setLicensePlate(licensePlate);
		setVin(vin);
		setBodyType(bodyType);
		setIssueYear(issueYear);
		setEngine(engine);
		setTransmission(transmission);
		setDriveType(driveType);
		setColor(color);
		setWeight(weight);
		setAvailable(available);
	}
	
	public Car(String manufacturer
			, String model
			, String bodyType
			, String engine
			, String transmission
			, String driveType) {
		setManufacturer(manufacturer);
		setModel(model);
		setBodyType(bodyType);
		setEngine(engine);
		setTransmission(transmission);
		setDriveType(driveType);
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}

	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getBodyType() {
		return bodyType;
	}
	public void setBodyType(String bodyType) {
		this.bodyType = bodyType;
	}

	public int getIssueYear() {
		return issueYear;
	}
	public void setIssueYear(int issueYear) {
		this.issueYear = issueYear;
	}

	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}

	public String getTransmission() {
		return transmission;
	}
	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}

	public String getDriveType() {
		return driveType;
	}
	public void setDriveType(String driveType) {
		this.driveType = driveType;
	}

	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}

	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public boolean getAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bodyType == null) ? 0 : bodyType.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((driveType == null) ? 0 : driveType.hashCode());
		result = prime * result + ((engine == null) ? 0 : engine.hashCode());
		result = prime * result + id;
		result = prime * result + issueYear;
		result = prime * result + ((licensePlate == null) ? 0 : licensePlate.hashCode());
		result = prime * result + ((manufacturer == null) ? 0 : manufacturer.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((transmission == null) ? 0 : transmission.hashCode());
		result = prime * result + ((vin == null) ? 0 : vin.hashCode());
		result = prime * result + weight;
		result = prime * result + (available ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Car other = (Car) obj;
		return this.bodyType.equals(other.bodyType)
				&& this.color.equals(other.color)
				&& this.driveType.equals(other.driveType)
				&& this.engine.equals(other.engine)
				&& this.id == other.id
				&& this.issueYear == other.issueYear
				&& this.licensePlate.equals(other.licensePlate)
				&& this.manufacturer.equals(other.manufacturer)
				&& this.model.equals(other.model)
				&& this.transmission.equals(other.transmission)
				&& this.vin.equals(other.vin)
				&& this.weight == other.weight
				&& this.available == other.available;
	}

	public String toShortString(String language) {
		return String.format("%s %s %s, " + manager.getValue("string.format.engine", language) + " %s, %s, %s"
				, manufacturer, model, bodyType, engine, transmission, driveType);
	}
	
	public String toLongString(String language) {
		return String.format("%s %s %d, %s, %s, " + manager.getValue("string.format.engine", language)
				+ " %s, %s, %s, " + manager.getValue("string.format.weight", language) + " %s"
				, manufacturer, model, issueYear, color, bodyType, engine, transmission, driveType, weight);
	}
	
	public String getStatus() {
		if (available) {
			return "Available";
		} else {
			return "Not available";
		}
	}
	
}
