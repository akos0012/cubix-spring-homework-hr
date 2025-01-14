package hu.cubix.hr.akos0012.repository;

import hu.cubix.hr.akos0012.model.PositionDetailsByCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionDetailsByCompanyRepository extends JpaRepository<PositionDetailsByCompany, Long> {

    List<PositionDetailsByCompany> findByPositionNameAndCompanyId(String positionName, long companyId);
}
