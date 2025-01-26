package hu.cubix.hr.akos0012.service.timeOffRequest;

import hu.cubix.hr.akos0012.model.Employee;
import hu.cubix.hr.akos0012.model.RequestStatus;
import hu.cubix.hr.akos0012.model.TimeOffRequest;
import hu.cubix.hr.akos0012.repository.EmployeeRepository;
import hu.cubix.hr.akos0012.repository.TimeOffRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static hu.cubix.hr.akos0012.service.timeOffRequest.specification.TimeOffRequestSpec.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TimeOffRequestService {

    @Autowired
    private TimeOffRequestRepository timeOffRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<TimeOffRequest> findAll() {
        return timeOffRequestRepository.findAll();
    }

    public Page<TimeOffRequest> findTimeOffRequestWithPaging(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return timeOffRequestRepository.findAll(pageable);
    }

    public TimeOffRequest findById(long requestID) {
        return timeOffRequestRepository.findById(requestID).orElse(null);
    }

    public TimeOffRequest save(TimeOffRequest newTimeOffRequest) {
        return timeOffRequestRepository.save(newTimeOffRequest);
    }

    private boolean isValidDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return startDate.isBefore(endDate);
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public TimeOffRequest create(LocalDateTime startDate, LocalDateTime endDate) {
        Optional<Employee> employee = employeeRepository.findByUsername(getCurrentUsername());
        if (employee.isEmpty()) return null;
        if (employee.get().getManager() == null) return null;
        if (!isValidDateRange(startDate, endDate)) return null;

        TimeOffRequest timeOffRequest = new TimeOffRequest(employee.get(), startDate, endDate);

        return save(timeOffRequest);
    }

    public TimeOffRequest update(long requestID, LocalDateTime startDate, LocalDateTime endDate) {
        Optional<Employee> employee = employeeRepository.findByUsername(getCurrentUsername());
        if (employee.isEmpty()) return null;

        TimeOffRequest timeOffRequest = timeOffRequestRepository.findById(requestID).orElse(null);
        if (timeOffRequest == null) return null;
        if (!employee.get().equals(timeOffRequest.getEmployee())) return null;
        if (!isValidDateRange(startDate, endDate)) return null;
        if (timeOffRequest.getRequestStatus() != RequestStatus.PENDING) return null;

        timeOffRequest.setStartDate(startDate);
        timeOffRequest.setEndDate(endDate);
        timeOffRequest.setUpdatedAt(LocalDateTime.now());

        return save(timeOffRequest);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    public TimeOffRequest judgeRequest(long requestID, boolean accept) {
        Optional<Employee> manager = employeeRepository.findByUsername(getCurrentUsername());
        if (manager.isEmpty()) return null;

        TimeOffRequest timeOffRequest = timeOffRequestRepository.findById(requestID).orElse(null);
        if (timeOffRequest == null) return null;

        if (!manager.get().equals(timeOffRequest.getManager())) return null;

        timeOffRequest.judge(accept ? RequestStatus.ACCEPTED : RequestStatus.REJECTED);

        return timeOffRequestRepository.save(timeOffRequest);
    }

    public boolean deleteRequest(long requestID) {
        Optional<Employee> employee = employeeRepository.findByUsername(getCurrentUsername());
        if (employee.isEmpty()) return false;

        Optional<TimeOffRequest> timeOffRequest = timeOffRequestRepository.findById(requestID);
        if (timeOffRequest.isPresent()) {
            if (!employee.get().equals(timeOffRequest.get().getEmployee())) return false;
            if (timeOffRequest.get().getRequestStatus() != RequestStatus.PENDING) return false;
        }

        timeOffRequestRepository.deleteById(requestID);
        return true;
    }

    public Page<TimeOffRequest> findRequestsByExample(int page,
                                                      int size,
                                                      String sortBy,
                                                      String sortDirection,
                                                      RequestStatus status,
                                                      String employeePrefix,
                                                      String managerPrefix,
                                                      LocalDateTime createdStartDate,
                                                      LocalDateTime createdEndDate,
                                                      LocalDateTime timeOffRequestedStartDate,
                                                      LocalDateTime timeOffRequestedEndDate) {

        Specification<TimeOffRequest> specs = Specification.where(null);

        //default: asc
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        if (status != null) specs = specs.and(hasStatus(status));

        if (StringUtils.hasLength(employeePrefix)) specs = specs.and(employeeNameStartWith(employeePrefix));

        if (StringUtils.hasLength(managerPrefix)) specs = specs.and(managerNameStartWith(managerPrefix));

        if (createdStartDate != null && createdEndDate != null)
            specs = specs.and(createdBetween(createdStartDate, createdEndDate));

        if (timeOffRequestedStartDate != null && timeOffRequestedEndDate != null)
            specs = specs.and(intersectsWith(timeOffRequestedStartDate, timeOffRequestedEndDate));

        return timeOffRequestRepository.findAll(specs, pageable);
    }

}
