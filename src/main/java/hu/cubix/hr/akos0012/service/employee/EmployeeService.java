package hu.cubix.hr.akos0012.service.employee;

import hu.cubix.hr.akos0012.model.Employee;
import hu.cubix.hr.akos0012.dto.EmployeeFilterDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeService {
    int getPayRaisePercent(Employee employee);

    List<Employee> findAll();

    Employee findById(long id);

    Employee save(Employee employee);

    Employee create(Employee employee);

    Employee update(Employee employee);

    void delete(long id);

    List<Employee> findByJobTitle(String jobTitle);

    List<Employee> findBySalaryIsGreaterThan(int salary);

    List<Employee> findByNameStartingWithIgnoreCase(String name);

    List<Employee> findByDateOfStartWorkBetween(LocalDateTime startDate, LocalDateTime endDate);

    Page<Employee> findEmployeesWithPaging(int page, int size);

    int updateSalaryForPosition(long companyID, String positionName, int minSalary);

    List<Employee> findEmployeesByExample(EmployeeFilterDTO employeeFilterDTO);

    List<Employee> findFilteredEmployees(Integer salary, String job, String name, LocalDateTime startDate, LocalDateTime endDate);
}
