package hu.cubix.hr.akos0012;

import hu.cubix.hr.akos0012.service.company.CompanyService;
import hu.cubix.hr.akos0012.service.db.InitDbService;
import hu.cubix.hr.akos0012.service.employee.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;


import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@SpringBootApplication
public class HrApplication implements CommandLineRunner {

    @Autowired
    SalaryService salaryService;

    @Autowired
    InitDbService initDbService;

    @Autowired
    CompanyService companyService;

    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initDbService.clearDB();
        initDbService.insertTestData();

//        Employee employee1 = new Employee("Akos", "OTP Bank", 10000, LocalDateTime.of(2020, 2, 14, 8, 0));
//        Employee employee2 = new Employee("Barna", "OTP Bank", 10000, LocalDateTime.of(2010, 5, 4, 8, 0));
//
//        salaryService.setNewSalary(employee1);
//        salaryService.setNewSalary(employee2);
//
//        System.out.println(employee1.getSalary());
//        System.out.println(employee2.getSalary());
    }
}
