package hu.cubix.hr.akos0012.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Employee {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String jobTitle;
    private int salary;
    private LocalDateTime dateOfStartWork;

    public Employee() {
    }

    public Employee(String name, String jobTitle, int salary, LocalDateTime dateOfStartWork) {
        this.name = name;
        this.jobTitle = jobTitle;
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

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
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
}
