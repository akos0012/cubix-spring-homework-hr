package hu.cubix.hr.akos0012.service;

import hu.cubix.hr.akos0012.model.Employee;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    int getPayRaisePercent(Employee employee);

    public List<Employee> findAll();

    public Employee findById(long id);

    public Employee save(Employee employee);

    public Employee create(Employee employee);

    public Employee update(Employee employee);

    public void delete(long id);

    public List<Employee> findByJobTitle(String jobTitle);

    public List<Employee> findBySalaryIsGreaterThan(int salary);

    public List<Employee> findByNameStartingWithIgnoreCase(String name);

    public List<Employee> findByDateOfStartWorkBetween(LocalDateTime startDate, LocalDateTime endDate);
}
