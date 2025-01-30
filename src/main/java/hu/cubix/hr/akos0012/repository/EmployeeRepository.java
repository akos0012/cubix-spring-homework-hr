package hu.cubix.hr.akos0012.repository;


import hu.cubix.hr.akos0012.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    Optional<Employee> findByUsername(String username);


    @Query("SELECT DISTINCT e FROM Employee e " +
            "LEFT JOIN e.position p " +
            "WHERE (:salary IS NULL OR e.salary >= :salary)" +
            "AND (:job IS NULL OR p.name = :job)" +
            "AND (:name IS NULL OR e.name LIKE :name%)" +
            "AND ((CAST(:startDate AS timestamp) IS NULL OR CAST(:endDate AS timestamp ) IS NULL) OR e.dateOfStartWork BETWEEN :startDate AND :endDate)")
    List<Employee> findAll(Integer salary, String job, String name, LocalDateTime startDate, LocalDateTime endDate);

    @Modifying
    @Query("UPDATE Employee e SET e.salary = :minSalary WHERE e.company.id = :companyID AND e.position.name = :positionName AND e.salary < :minSalary")
    int updateSalaryForPosition(@Param("companyID") long companyID, @Param("positionName") String positionName, @Param("minSalary") int minSalary);

}
