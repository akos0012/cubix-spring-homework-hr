package hu.cubix.hr.akos0012.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Employee {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    //private String jobTitle;
    private int salary;
    private LocalDateTime dateOfStartWork;

    @ManyToOne
    private Position position;

    @ManyToOne
    private Company company;

    public Employee() {
    }

    public Employee(long id, String name, int salary, LocalDateTime dateOfStartWork) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.dateOfStartWork = dateOfStartWork;
    }

    public Employee(String name, int salary, LocalDateTime dateOfStartWork) {
        this.name = name;
        this.salary = salary;
        this.dateOfStartWork = dateOfStartWork;
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
