package hu.cubix.hr.akos0012.repository;

import hu.cubix.hr.akos0012.model.TimeOffRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

public interface TimeOffRequestRepository extends JpaRepository<TimeOffRequest, Long>, JpaSpecificationExecutor<TimeOffRequest> {

    @NonNull
    Page<TimeOffRequest> findAll(Specification<TimeOffRequest> specification, @NonNull Pageable pageable);
}
