package hu.cubix.hr.akos0012.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Employee {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    //private String jobTitle;
    private int salary;
    private LocalDateTime dateOfStartWork;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    @ManyToOne
    private Employee manager;

    @ManyToOne
    private Position position;

    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "employee")
    private List<TimeOffRequest> timeOffRequests;

    public Employee() {
    }

    public Employee(long id, String name, int salary, LocalDateTime dateOfStartWork, String username, String password) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.dateOfStartWork = dateOfStartWork;
        this.username = username;
        this.password = password;
        this.roles = Set.of("user");
        this.timeOffRequests = new ArrayList<>();
    }

    public Employee(String name, int salary, LocalDateTime dateOfStartWork, String username, String password) {
        this.name = name;
        this.salary = salary;
        this.dateOfStartWork = dateOfStartWork;
        this.username = username;
        this.password = password;
        this.roles = Set.of("user");
        this.timeOffRequests = new ArrayList<>();
    }

    public void addTimeOfRequest(TimeOffRequest timeOffRequest) {
        this.timeOffRequests.add(timeOffRequest);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDateTime getDateOfStartWork() {
        return dateOfStartWork;
    }

    public void setDateOfStartWork(LocalDateTime dateOfStartWork) {
        this.dateOfStartWork = dateOfStartWork;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public List<TimeOffRequest> getTimeOffRequests() {
        return timeOffRequests;
    }

    public void setTimeOffRequests(List<TimeOffRequest> timeOffRequests) {
        this.timeOffRequests = timeOffRequests;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", dateOfStartWork=" + dateOfStartWork +
                ", company=" + company +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
