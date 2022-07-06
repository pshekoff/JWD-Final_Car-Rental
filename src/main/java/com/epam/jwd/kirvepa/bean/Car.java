package com.epam.jwd.kirvepa.bean;

import java.io.Serializable;

public class Car implements Serializable {
	private static final long serialVersionUID = 1L;
	
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
			, int weight) {
		setId(id);
		setManufacturer(manufacturer);
		setModel(model);
		setLicensePlate(licensePlate);
		setVIN(vin);
		setBodyType(bodyType);
		setIssueYear(issueYear);
		setEngine(engine);
		setTransmission(transmission);
		setDriveType(driveType);
		setColor(color);
		setWeight(weight);
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

	public String getVIN() {
		return vin;
	}
	public void setVIN(String vin) {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s, двигатель %s, %s, %s",
				manufacturer, model, bodyType, engine, transmission, driveType);
	}
	
	

}
