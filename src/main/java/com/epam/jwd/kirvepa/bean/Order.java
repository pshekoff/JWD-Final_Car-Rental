package com.epam.jwd.kirvepa.bean;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Car car;
	private User user;
	private Employee employee;
	private Date dateFrom;
	private Date dateTo;
	private double amount;
	private OrderStatus status;
	
	public Order(int id
			, Car car
			, User user
			, Employee employee
			, Date dateFrom
			, Date dateTo
			, double amount
			, OrderStatus status) {
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
			, OrderStatus status) {
		setId(id);
		setCar(car);
		setDateFrom(dateFrom);
		setDateTo(dateTo);
		setAmount(amount);
		setStatus(status);
	}
	
	public enum OrderStatus {
		PREPARED,
		CREATED,
		PAID,
		APPROVED,
		CANCELLED,
		REJECTED,
		COMPLETED,
		IN_PROGRESS
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

	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((car == null) ? 0 : car.hashCode());
		result = prime * result + ((dateFrom == null) ? 0 : dateFrom.hashCode());
		result = prime * result + ((dateTo == null) ? 0 : dateTo.hashCode());
		result = prime * result + ((employee == null) ? 0 : employee.hashCode());
		result = prime * result + id;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Order other = (Order) obj;
		return Double.doubleToLongBits(this.amount) == Double.doubleToLongBits(other.amount)
				&& Objects.equals(this.car, other.car)
				&& Objects.equals(this.dateFrom, other.dateFrom)
				&& Objects.equals(this.dateTo, other.dateTo)
				&& Objects.equals(this.employee, other.employee)
				&& this.id == other.id
				&& this.status == other.status
				&& Objects.equals(this.user, other.user);
	}
	
	@Override
	public String toString() {
		return this.getClass()
				+ "{"
				+ "id=" + id
				+ ", car=" + car.toString()
				+ ", user=" + user.getLogin()
				+ ", employee=" + employee.getLogin()
				+ ", dateFrom=" + dateFrom
				+ ", dateTo=" + dateTo
				+ ", amount=" + amount
				+ ", status=" + status
				+ "}";
	}

}
