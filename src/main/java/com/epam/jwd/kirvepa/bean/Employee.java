package com.epam.jwd.kirvepa.bean;

public class Employee extends User {
	private static final long serialVersionUID = 1L;
	
	private String department;
	private String position;
	private double salary;
	
	public Employee(String login
			, String email
			, boolean admin
			, PersonalData personalData
			, String department
			, String position
			, double salary) {
		super(login, email, admin);
		super.setPersonalData(personalData);
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
