package hu.cubix.hr.akos0012.repository;


import hu.cubix.hr.akos0012.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT e.position.name, AVG(e.salary) FROM Employee e WHERE e.company.id = :id GROUP BY e.position.name ORDER BY AVG(e.salary) DESC")
    List<Object[]> findAverageSalaryByJobTitleAndCompanyId(@Param("id") long id);

    //@Query("SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.employees")
    @Query("SELECT DISTINCT c FROM Company c " +
            "LEFT JOIN c.employees e " +
            "WHERE (:salary IS NULL OR e.salary >= :salary) " +
            "AND (:limit IS NULL OR (SELECT COUNT(emp) FROM Employee emp WHERE emp.company = c) >= :limit)")
    //EntityGraph(attributePaths = {"employees"})
    @EntityGraph("Company.withEmployees")
    Page<Company> findAllWithEmployees(Integer salary, Integer limit, Pageable pageable);

    @Query("SELECT DISTINCT c FROM Company c " +
            "LEFT JOIN c.employees e " +
            "WHERE (:salary IS NULL OR e.salary >= :salary) " +
            "AND (:limit IS NULL OR (SELECT COUNT(emp) FROM Employee emp WHERE emp.company = c) >= :limit)")
    Page<Company> findAllWithoutEmployees(Integer salary, Integer limit, Pageable pageable);

    @Query("SELECT c FROM Company c WHERE c.id = :id")
    @EntityGraph("Company.withEmployees")
    Optional<Company> findByIdWithEmployees(long id);

}
