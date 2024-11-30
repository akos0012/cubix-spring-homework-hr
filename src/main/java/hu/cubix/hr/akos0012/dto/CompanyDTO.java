package hu.cubix.hr.akos0012.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompanyDTO {
    private final long id;
    private String registrationNumber;
    private String name;
    private String address;
    private Set<EmployeeDTO> employees;

    // No-arg constructor (required by Jackson)
    //The employees field is Set<EmployeeDTO>, which Jackson may have trouble deserializing directly.
    public CompanyDTO() {
        this.id = 0;
        this.employees = new HashSet<>();
    }

    public CompanyDTO(long id, String registrationNumber, String name, String address) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.employees = new HashSet<>();
    }


    public CompanyDTO(long id, String registrationNumber, String name, String address, Set<EmployeeDTO> employees) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.employees = employees;
    }

    public CompanyDTO toBasicDTO() {
        return new CompanyDTO(this.id, this.registrationNumber, this.name, this.address);
    }

    public void addEmployee(EmployeeDTO employee) {
        employees.add(employee);
    }

    public void addEmployees(List<EmployeeDTO> employeesList) {
        employees.addAll(employeesList);
    }

    public void removeEmployee(long employeeID) {
        employees.removeIf(e -> e.getId() == employeeID);
    }

    public boolean isEmployeeExist(long employeeID) {
        return employees.stream().anyMatch(e -> e.getId() == employeeID);
    }

    public void replaceEmployeeList(List<EmployeeDTO> employeeList) {
        employees.clear();
        employees.addAll(employeeList);
    }

    public long getId() {
        return id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Set<EmployeeDTO> getEmployees() {
        return Set.copyOf(employees);
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
