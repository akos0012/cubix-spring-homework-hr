package hu.cubix.hr.akos0012;

import hu.cubix.hr.akos0012.model.Employee;
import hu.cubix.hr.akos0012.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

    @Autowired
    SalaryService salaryService;

    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Employee employee1 = new Employee(516151, "OTP Bank", 10000, LocalDateTime.of(2020, 2, 14, 8, 0));
        Employee employee2 = new Employee(425893, "OTP Bank", 10000, LocalDateTime.of(2010, 5, 4, 8, 0));

        salaryService.setNewSalary(employee1);
        salaryService.setNewSalary(employee2);

        System.out.println(employee1.getSalary());
        System.out.println(employee2.getSalary());
    }
}
