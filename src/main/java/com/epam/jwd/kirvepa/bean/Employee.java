package com.epam.jwd.kirvepa.bean;

import java.util.Objects;

public class Employee extends User {
	private static final long serialVersionUID = 1L;
	
	private String department;
	private String position;
	private double salary;
	
	public Employee() {}
	
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
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((department == null) ? 0 : department.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		long temp;
		temp = Double.doubleToLongBits(salary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Employee other = (Employee) obj;
		return this.department.equals(other.department)
				&& this.position.equals(other.position)
				&& Double.doubleToLongBits(salary) == Double.doubleToLongBits(other.salary)
				&& this.isAdmin() == other.isAdmin()
				&& this.getEmail().equals(other.getEmail())
				&& this.getLogin().equals(other.getLogin())
				&& Objects.equals(this.getPersonalData(), other.getPersonalData());
	}
	
	@Override
	public String toString() {
		return this.getClass().getName()
				+ "{"
				+ "name=" + this.getPersonalData().getFirstName()
				+ " " + this.getPersonalData().getLastName()
				+ ", dayOfBirth=" + this.getPersonalData().getDayOfBirth()
				+ ", login=" + this.getLogin()
				+ ", email=" + this.getEmail()
				+ ", position=" + this.position
				+ ", department=" + this.department
				+ "}";
	}
	
}
