package hu.cubix.hr.akos0012.repository;


import hu.cubix.hr.akos0012.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByJobTitle(String jobTitle);

    List<Employee> findBySalaryIsGreaterThan(int salary);

    List<Employee> findByNameStartingWithIgnoreCase(String name);

    List<Employee> findByDateOfStartWorkBetween(LocalDateTime startDate, LocalDateTime endDate);
}
