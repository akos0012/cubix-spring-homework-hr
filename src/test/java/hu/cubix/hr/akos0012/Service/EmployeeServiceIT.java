package hu.cubix.hr.akos0012.Service;

import hu.cubix.hr.akos0012.dto.EmployeeFilterDTO;
import hu.cubix.hr.akos0012.model.Company;
import hu.cubix.hr.akos0012.model.Employee;
import hu.cubix.hr.akos0012.repository.CompanyRepository;
import hu.cubix.hr.akos0012.repository.EmployeeRepository;
import hu.cubix.hr.akos0012.service.company.CompanyService;
import hu.cubix.hr.akos0012.service.employee.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
public class EmployeeServiceIT {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CompanyService companyService;

    @Test
    void testEmployeeSearchByName() {
        //Employee employee = employeeService.create(new Employee("Imre", 3000, LocalDateTime.now()));

        EmployeeFilterDTO filter = new EmployeeFilterDTO(0, "Imre", null, null, 0, null);
        List<Employee> foundEmployees = employeeService.findEmployeesByExample(filter);

        //assertThat(foundEmployees).containsExactlyInAnyOrder(employee);

    }

    @Test
    void testEmployeeSearchByCompanyName() {
        Company company = new Company("53DSA-HU", "Magyar Posta", null, null);
        //Employee employee1 = new Employee("István", 10000, LocalDateTime.now());
        //Employee employee2 = new Employee("János", 9000, LocalDateTime.now());
        companyService.save(company);
//        company.addEmployees(List.of(employee1, employee2));
//        employeeService.save(employee1);
//        employeeService.save(employee2);

        EmployeeFilterDTO filter = new EmployeeFilterDTO(0, null, "Magyar", null, 0, null);
        List<Employee> foundEmployees = employeeService.findEmployeesByExample(filter);

        //assertThat(foundEmployees).containsExactlyInAnyOrder(employee1, employee2);
    }
}
