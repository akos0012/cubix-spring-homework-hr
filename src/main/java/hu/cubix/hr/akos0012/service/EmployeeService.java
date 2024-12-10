package hu.cubix.hr.akos0012.service;

import hu.cubix.hr.akos0012.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {
    private final Map<Long, Employee> employees = new HashMap<>();

    public List<Employee> findAll() {
        return new ArrayList<>(employees.values());
    }

    public Employee findById(long id) {
        return employees.get(id);
    }

    private Employee save(Employee employee) {
        employees.put(employee.getId(), employee);
        return employee;
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
        employees.remove(id);
    }

}
