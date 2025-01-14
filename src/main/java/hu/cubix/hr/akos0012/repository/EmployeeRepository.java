package hu.cubix.hr.akos0012.repository;


import hu.cubix.hr.akos0012.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByPositionName(String positionName);

    List<Employee> findBySalaryIsGreaterThan(int salary);

    List<Employee> findByNameStartingWithIgnoreCase(String name);

    List<Employee> findByDateOfStartWorkBetween(LocalDateTime startDate, LocalDateTime endDate);

    @NonNull
    Page<Employee> findAll(@NonNull Pageable pageable);

    @Modifying
    @Query("UPDATE Employee e SET e.salary = :minSalary WHERE e.company.id = :companyID AND e.position.name = :positionName AND e.salary < :minSalary")
    int updateSalaryForPosition(@Param("companyID") long companyID, @Param("positionName") String positionName, @Param("minSalary") int minSalary);
}
