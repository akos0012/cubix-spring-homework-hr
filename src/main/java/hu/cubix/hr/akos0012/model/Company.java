package hu.cubix.hr.akos0012.model;

import jakarta.persistence.*;

import java.util.*;

@NamedEntityGraph(
        name = "Company.withEmployees",
        attributeNodes = {
                @NamedAttributeNode("companyForm"),
                @NamedAttributeNode("employees"),
                @NamedAttributeNode(value = "employees", subgraph = "employeePosition")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "employeePosition",
                        attributeNodes = {
                                @NamedAttributeNode("position")
                        }
                )
        }
)

@Entity
public class Company {
    @Id
    @GeneratedValue
    private long id;
    private String registrationNumber;
    private String name;
    private String address;

    @OneToMany(mappedBy = "company")
    private Set<Employee> employees;

    @ManyToOne
    private CompanyForm companyForm;

    public Company() {
    }

    public Company(String registrationNumber, String name, CompanyForm companyForm, String address, Set<Employee> employees) {
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.companyForm = companyForm;
        this.address = address;
        this.employees = employees;
    }

    public Company(String registrationNumber, String name, CompanyForm companyForm, String address) {
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.companyForm = companyForm;
        this.address = address;
        this.employees = new HashSet<>();
    }

    public void addEmployee(Employee employee) {
        employee.setCompany(this);
        employees.add(employee);
    }

    public void addEmployees(List<Employee> employeesList) {
        employeesList.forEach(e -> e.setCompany(this));
        employees.addAll(employeesList);
    }

    public void removeEmployee(long employeeID) {
        employees.removeIf(e -> e.getId() == employeeID);
    }

    public boolean isEmployeeExist(long employeeID) {
        return employees.stream().anyMatch(e -> e.getId() == employeeID);
    }

    public void replaceEmployeeList(List<Employee> employeeList) {
        employees.forEach(e -> e.setCompany(null));
        employees.clear();
        addEmployees(employeeList);
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

    public CompanyForm getCompanyForm() {
        return companyForm;
    }

    public void setCompanyForm(CompanyForm companyForm) {
        this.companyForm = companyForm;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", employees=" + employees +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company company)) return false;
        return id == company.id || Objects.equals(registrationNumber, company.registrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registrationNumber);
    }
}
