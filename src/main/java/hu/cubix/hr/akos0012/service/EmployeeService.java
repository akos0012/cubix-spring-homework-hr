package hu.cubix.hr.akos0012.service;

import hu.cubix.hr.akos0012.model.Employee;
import hu.cubix.hr.akos0012.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        //return new ArrayList<>(employees.values());
        return employeeRepository.findAll();
    }

    public Employee findById(long id) {
        //return employees.get(id);
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> findByJobTitle(String jobTitle) {
        return employeeRepository.findByJobTitle(jobTitle);
    }

    public List<Employee> findBySalaryIsGreaterThan(int salary) {
        return employeeRepository.findBySalaryIsGreaterThan(salary);
    }

    public List<Employee> findByNameStartingWithIgnoreCase(String name) {
        return employeeRepository.findByNameStartingWithIgnoreCase(name);
    }

    public List<Employee> findByDateOfStartWorkBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return employeeRepository.findByDateOfStartWorkBetween(startDate, endDate);
    }

    private Employee save(Employee employee) {
        //employees.put(employee.getId(), employee);
        return employeeRepository.save(employee);
    }

    public Employee create(Employee employee) {
        if (findById(employee.getId()) != null) return null;
        return save(employee);
    }

    public Employee update(Employee employee) {
        if (findById(employee.getId()) == null) return null;
        return save(employee);
    }

    public void delete(long id) {
        employeeRepository.deleteById(id);
    }

}
