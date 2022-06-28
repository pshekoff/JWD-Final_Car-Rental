package com.epam.jwd.kirvepa.bean;

import java.io.Serializable;

public class Employee extends User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String role = "Administrator";
	
	private String department;
	private String position;
	private double salary;
	
	public Employee(String login
					, int passwordHash
					, String email
					, String firstName
					, String lastName
					, String homeAdress
					, String phone
					, String department
					, String position
					, double salary) {
		super(login, passwordHash, email, firstName, lastName, homeAdress, phone, role);
		setDepartment(department);
		setPosition(position);
		setSalary(salary);
		
	}
	
	public void setDepartment(String department) {
		this.department = department;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getDepartment() {
		return department;
	}
	public String getPosition() {
		return position;
	}
	public double getSalary() {
		return salary;
	}

}
