package hu.cubix.hr.akos0012.service.timeOffRequest.specification;

import hu.cubix.hr.akos0012.model.Employee_;
import hu.cubix.hr.akos0012.model.RequestStatus;
import hu.cubix.hr.akos0012.model.TimeOffRequest;
import hu.cubix.hr.akos0012.model.TimeOffRequest_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TimeOffRequestSpec {
    public static Specification<TimeOffRequest> hasStatus(RequestStatus status) {
        return (root, cq, cb) -> cb.equal(root.get(TimeOffRequest_.requestStatus), status);
    }

    public static Specification<TimeOffRequest> employeeNameStartWith(String prefix) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get(TimeOffRequest_.employee).get(Employee_.name)), prefix.toLowerCase() + '%');
    }

    public static Specification<TimeOffRequest> managerNameStartWith(String prefix) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get(TimeOffRequest_.manager).get(Employee_.name)), prefix.toLowerCase() + '%');
    }

    public static Specification<TimeOffRequest> createdBetween(LocalDateTime from, LocalDateTime until) {
        return (root, cq, cb) -> cb.between(root.get(TimeOffRequest_.createdAt), from, until);
    }

    public static Specification<TimeOffRequest> isStartDateLessThan(LocalDateTime date) {
        return (root, cq, cb) -> cb.lessThan(root.get(TimeOffRequest_.startDate), date);
    }

    public static Specification<TimeOffRequest> isEndDateGreaterThan(LocalDateTime date) {
        return (root, cq, cb) -> cb.greaterThan(root.get(TimeOffRequest_.endDate), date);
    }
}
