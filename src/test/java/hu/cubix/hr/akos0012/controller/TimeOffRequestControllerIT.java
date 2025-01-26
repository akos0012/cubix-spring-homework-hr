package hu.cubix.hr.akos0012.controller;

import hu.cubix.hr.akos0012.dto.TimeOffRequestDTO;
import hu.cubix.hr.akos0012.model.Employee;
import hu.cubix.hr.akos0012.model.RequestStatus;
import hu.cubix.hr.akos0012.repository.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TimeOffRequestControllerIT {
    public static final String API_REQUESTS = "/api/time-off-request";

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    EmployeeRepository employeeRepository;

    private TimeOffRequestDTO newTimeOffRequest;

    @BeforeEach
    void setUp() {
        String username = "diana";
        Employee employee = employeeRepository.findByUsername(username).get();
        webTestClient = webTestClient.mutate()
                .defaultHeaders(headers -> headers.setBasicAuth(username, "pass"))
                .build();

        List<TimeOffRequestDTO> timeOffRequestsBefore = getAllTimeOffRequests();
        long newID = 100;
        if (!timeOffRequestsBefore.isEmpty()) {
            newID = timeOffRequestsBefore.get(timeOffRequestsBefore.size() - 1).id() + 1;
        }

        newTimeOffRequest = new TimeOffRequestDTO(newID, employee.getId(), LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5), employee.getManager().getId(), RequestStatus.PENDING, LocalDateTime.now(), LocalDateTime.now());
        createTimeOffRequest(newTimeOffRequest);
    }

    @Test
    @Order(1)
    void testThatTimeOffRequestIsCreated() {
        List<TimeOffRequestDTO> requestsAfter = getAllTimeOffRequests();

        assertThat(requestsAfter).anyMatch(request ->
                request.id() == newTimeOffRequest.id() &&
                        request.startDate().truncatedTo(ChronoUnit.SECONDS)
                                .isEqual(newTimeOffRequest.startDate().truncatedTo(ChronoUnit.SECONDS)) &&
                        request.endDate().truncatedTo(ChronoUnit.SECONDS)
                                .isEqual(newTimeOffRequest.endDate().truncatedTo(ChronoUnit.SECONDS))
        );
    }

    @Test
    @Order(2)
    void testThatTimeOffRequestJudgmentFailsForNonManager() {
        // Attempt to judge the time-off request as a non-manager
        attemptToJudgeTimeOffRequestAsNonManager(newTimeOffRequest.id(), true);

        TimeOffRequestDTO timeOffRequestAfter = getTimeOffRequestById(newTimeOffRequest.id());

        assertThat(timeOffRequestAfter.requestStatus()).isEqualTo(RequestStatus.PENDING);
    }

    @Test
    @Order(3)
    void testThatTimeOffRequestIsDeleted() {
        deleteTimeOffRequest(newTimeOffRequest.id());

        List<TimeOffRequestDTO> requestsAfter = getAllTimeOffRequests();

        assertThat(requestsAfter).noneMatch(request -> request.id() == newTimeOffRequest.id());
    }

    private void createTimeOffRequest(TimeOffRequestDTO newTimeOffRequest) {
        webTestClient
                .post()
                .uri(API_REQUESTS)
                .bodyValue(newTimeOffRequest)
                .exchange()
                .expectStatus().isOk();
    }

    private void deleteTimeOffRequest(long id) {
        webTestClient
                .delete()
                .uri(API_REQUESTS + "/" + id)
                .exchange()
                .expectStatus().isOk();
    }

    private void attemptToJudgeTimeOffRequestAsNonManager(long id, boolean accept) {
        webTestClient
                .put()
                .uri(API_REQUESTS + "/judge?requestID=" + id + "&accept=" + accept)
                .exchange()
                .expectStatus().isForbidden();
    }

    private List<TimeOffRequestDTO> getAllTimeOffRequests() {
        return webTestClient
                .get()
                .uri(API_REQUESTS)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TimeOffRequestDTO.class)
                .returnResult()
                .getResponseBody();
    }

    private TimeOffRequestDTO getTimeOffRequestById(long id) {
        return webTestClient
                .get()
                .uri(API_REQUESTS + "/id/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TimeOffRequestDTO.class)
                .returnResult()
                .getResponseBody();
    }
}
