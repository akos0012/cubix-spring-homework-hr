package hu.cubix.hr.akos0012.model;

import hu.cubix.hr.akos0012.dto.EmployeeDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Company {
    private long id;
    private String registrationNumber;
    private String name;
    private String address;
    private Set<Employee> employees;

    public Company() {
        this.employees = new HashSet<>();
    }

    public Company(long id, String registrationNumber, String name, String address, Set<Employee> employees) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.employees = employees;
    }

    public Company(long id, String registrationNumber, String name, String address) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.employees = new HashSet<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void addEmployees(List<Employee> employeesList) {
        employees.addAll(employeesList);
    }

    public void removeEmployee(long employeeID) {
        employees.removeIf(e -> e.getId() == employeeID);
    }

    public boolean isEmployeeExist(long employeeID) {
        return employees.stream().anyMatch(e -> e.getId() == employeeID);
    }

    public void replaceEmployeeList(List<Employee> employeeList) {
        employees.clear();
        employees.addAll(employeeList);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Employee> getEmployees() {
        return new ArrayList<>(employees);
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
