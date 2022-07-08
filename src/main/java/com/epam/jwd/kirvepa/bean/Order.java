package com.epam.jwd.kirvepa.bean;

import java.io.Serializable;
import java.sql.Date;

public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Car car;
	private User user;
	private Employee employee;
	private Date dateFrom;
	private Date dateTo;
	private double amount;
	private String status;
	
	public Order(int id
			, Car car
			, User user
			, Employee employee
			, Date dateFrom
			, Date dateTo
			, double amount
			, String status) {
		setId(id);
		setCar(car);
		setUser(user);
		setEmployee(employee);
		setDateFrom(dateFrom);
		setDateTo(dateTo);
		setAmount(amount);
		setStatus(status);
	}
	
	public Order(int id
			, Car car
			, Date dateFrom
			, Date dateTo
			, double amount
			, String status) {
		setId(id);
		setCar(car);
		setDateFrom(dateFrom);
		setDateTo(dateTo);
		setAmount(amount);
		setStatus(status);
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
