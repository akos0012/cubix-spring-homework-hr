package hu.cubix.hr.akos0012.Service;

import hu.cubix.hr.akos0012.model.Employee;
import hu.cubix.hr.akos0012.model.RequestStatus;
import hu.cubix.hr.akos0012.model.TimeOffRequest;
import hu.cubix.hr.akos0012.repository.EmployeeRepository;
import hu.cubix.hr.akos0012.repository.TimeOffRequestRepository;
import hu.cubix.hr.akos0012.service.timeOffRequest.TimeOffRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
public class TimeOffRequestServiceIT {

    @Autowired
    TimeOffRequestService timeOffRequestService;

    @Autowired
    TimeOffRequestRepository timeOffRequestRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    public void init() {
        timeOffRequestRepository.deleteAllInBatch();
    }

    @Test
    void testRequestSearchByStatus() {
        Employee employee = createEmployee("János", 5000, LocalDateTime.now());
        Employee manager = createEmployee("Tamás", 9500, LocalDateTime.now());

        TimeOffRequest request1 = createTimeOffRequest(employee, LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(8));
        TimeOffRequest request2 = createTimeOffRequest(employee, LocalDateTime.now().plusDays(4), LocalDateTime.now().plusDays(10));
        TimeOffRequest request3 = createTimeOffRequest(employee, LocalDateTime.now().plusDays(8), LocalDateTime.now().plusDays(15));

        //timeOffRequestService.judgeRequest(manager.getId(), request1.getId(), true);
        //timeOffRequestService.judgeRequest(manager.getId(), request3.getId(), true);

        request1 = timeOffRequestRepository.findById(request1.getId()).get();
        request3 = timeOffRequestRepository.findById(request3.getId()).get();

        RequestStatus testRequestStatus = RequestStatus.ACCEPTED;
        int page = 0;
        String sortBy = "startDate";
        int size = timeOffRequestRepository.findAll().size();


        List<TimeOffRequest> foundRequests = timeOffRequestService.findRequestsByExample(page, size, sortBy, "", testRequestStatus, null, null, null, null, null, null).getContent();


        assertThat(foundRequests).containsExactlyInAnyOrder(request1, request3);
    }

    @Test
    void testRequestSearchByName() {
        Employee employee1 = createEmployee("János", 5000, LocalDateTime.now());
        Employee employee2 = createEmployee("Tamás", 9500, LocalDateTime.now());
        Employee employee3 = createEmployee("Júlia", 5000, LocalDateTime.now());

        TimeOffRequest request1 = createTimeOffRequest(employee1, LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(8));
        TimeOffRequest request2 = createTimeOffRequest(employee2, LocalDateTime.now().plusDays(4), LocalDateTime.now().plusDays(10));
        TimeOffRequest request3 = createTimeOffRequest(employee3, LocalDateTime.now().plusDays(8), LocalDateTime.now().plusDays(15));

        String name = "j";
        int page = 0;
        String sortBy = "startDate";
        int size = timeOffRequestRepository.findAll().size();


        List<TimeOffRequest> foundRequests = timeOffRequestService.findRequestsByExample(page, size, sortBy, "", null, name, null, null, null, null, null).getContent();


        assertThat(foundRequests).containsExactlyInAnyOrder(request1, request3);
    }

    private TimeOffRequest createTimeOffRequest(Employee employee, LocalDateTime startDate, LocalDateTime endDate) {
        return timeOffRequestRepository.save(new TimeOffRequest(employee, startDate, endDate));
    }

    private Employee createEmployee(String name, int salary, LocalDateTime dateOfStartWork) {
        //return employeeRepository.save(new Employee(name, salary, dateOfStartWork));
        return null;
    }

}
