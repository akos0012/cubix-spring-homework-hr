package hu.cubix.hr.akos0012.controller;

import hu.cubix.hr.akos0012.dto.EmployeeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIT {
    public static final String API_EMPLOYEES = "/api/employees";

    @Autowired
    WebTestClient webTestClient;

    private EmployeeDTO newEmployee;

    @BeforeEach
    void setUp() {
        List<EmployeeDTO> employeesBefore = getAllEmployees();
        long newId = 100;
        if (!employeesBefore.isEmpty()) {
            newId = employeesBefore.get(employeesBefore.size() - 1).id() + 1;
        }

        newEmployee = new EmployeeDTO(newId, "Imre", 10000000, LocalDateTime.now());

        createEmployee(newEmployee);
    }


    @Test
    void testThatEmployeeIsCreated() {
        List<EmployeeDTO> employeesAfter = getAllEmployees();

        assertThat(employeesAfter).anyMatch(e -> e.equals(newEmployee));
    }

    @Test
    void testThatEmployeeIsUpdated() {
        EmployeeDTO updatedEmployee = new EmployeeDTO(newEmployee.id(), "Imre", 20000000, LocalDateTime.now());

        updateEmployee(updatedEmployee, newEmployee.id());

        List<EmployeeDTO> employeesAfter = getAllEmployees();

        assertThat(employeesAfter).anyMatch(e -> e.equals(updatedEmployee));
    }

    private void createEmployee(EmployeeDTO newEmployee) {
        webTestClient
                .post()
                .uri(API_EMPLOYEES)
                .bodyValue(newEmployee)
                .exchange()
                .expectStatus().isOk();
    }

    private void updateEmployee(EmployeeDTO newEmployee, long id) {
        webTestClient
                .put()
                .uri(API_EMPLOYEES + "/" + id)
                .bodyValue(newEmployee)
                .exchange()
                .expectStatus().isOk();
    }

    private List<EmployeeDTO> getAllEmployees() {
        List<EmployeeDTO> employeeList = webTestClient
                .get()
                .uri(API_EMPLOYEES)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDTO.class)
                .returnResult()
                .getResponseBody();

        if (employeeList != null)
            employeeList.sort(Comparator.comparing(EmployeeDTO::id));

        return employeeList;
    }
}
