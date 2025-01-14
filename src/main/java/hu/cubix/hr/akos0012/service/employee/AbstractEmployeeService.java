package hu.cubix.hr.akos0012.service.employee;

import hu.cubix.hr.akos0012.model.Employee;
import hu.cubix.hr.akos0012.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public abstract class AbstractEmployeeService implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAll() {
        //return new ArrayList<>(employees.values());
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(long id) {
        //return employees.get(id);
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> findByJobTitle(String jobTitle) {
        return employeeRepository.findByPositionName(jobTitle);
    }

    @Override
    public List<Employee> findBySalaryIsGreaterThan(int salary) {
        return employeeRepository.findBySalaryIsGreaterThan(salary);
    }

    @Override
    public List<Employee> findByNameStartingWithIgnoreCase(String name) {
        return employeeRepository.findByNameStartingWithIgnoreCase(name);
    }

    @Override
    public List<Employee> findByDateOfStartWorkBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return employeeRepository.findByDateOfStartWorkBetween(startDate, endDate);
    }

    @Override
    public Page<Employee> findEmployeesWithPaging(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Employee save(Employee employee) {
        //employees.put(employee.getId(), employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee create(Employee employee) {
        if (employeeRepository.existsById(employee.getId())) return null;
        return save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        if (!employeeRepository.existsById(employee.getId())) return null;
        return save(employee);
    }

    @Override
    public void delete(long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public int updateSalaryForPosition(long companyID, String positionName, int minSalary) {
        return employeeRepository.updateSalaryForPosition(companyID, positionName, minSalary);
    }

}
