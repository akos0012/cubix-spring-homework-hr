package hu.cubix.hr.akos0012.repository;


import hu.cubix.hr.akos0012.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findDistinctByEmployeesSalaryGreaterThan(int salary);

    @Query("SELECT c FROM Company c WHERE SIZE(c.employees) > :limit")
    List<Company> findCompaniesWithMoreThanEmployees(@Param("limit") int limit);

    @Query("SELECT e.position.name, AVG(e.salary) FROM Employee e WHERE e.company.id = :id GROUP BY e.position.name ORDER BY AVG(e.salary) DESC")
    List<Object[]> findAverageSalaryByJobTitleAndCompanyId(@Param("id") long id);

    @NonNull
    Page<Company> findAll(@NonNull Pageable pageable);

    //@Query("SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.employees")
    @Query("SELECT c FROM Company c")
    //EntityGraph(attributePaths = {"employees"})
    @EntityGraph("Company.withEmployees")
    List<Company> findAllWithEmployees();

    @Query("SELECT c FROM Company c WHERE c.id = :id")
    @EntityGraph("Company.withEmployees")
    Optional<Company> findByIdWithEmployees(long id);

}
