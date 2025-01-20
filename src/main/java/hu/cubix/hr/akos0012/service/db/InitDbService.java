package hu.cubix.hr.akos0012.service.db;

import hu.cubix.hr.akos0012.model.*;
import hu.cubix.hr.akos0012.repository.*;
import hu.cubix.hr.akos0012.service.timeOffRequest.TimeOffRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InitDbService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    CompanyFormRepository companyFormRepository;

    @Autowired
    PositionDetailsByCompanyRepository positionDetailsByCompanyRepository;

    @Autowired
    TimeOffRequestRepository timeOffRequestRepository;

    public void clearDB() {
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    public void insertTestData() {
        Position softwareEngineer = positionRepository.save(new Position("Software Engineer", Qualification.UNIVERSITY));
        Position projectManager = positionRepository.save(new Position("Project Manager", Qualification.HIGH_SCHOOL));
        Position marketingSpecialist = positionRepository.save(new Position("Marketing Specialist", Qualification.UNIVERSITY));
        Position rockAnalyst = positionRepository.save(new Position("Rock Analyst", Qualification.PHD));

        CompanyForm corporation = companyFormRepository.save(new CompanyForm("CORPORATION"));
        CompanyForm llc = companyFormRepository.save(new CompanyForm("LLC"));


        Company company1 = new Company("05155451SDA", "Test Innovators", corporation, "123 Innovation Drive, Silicon Valley, CA 94025, United States");

        Employee employee1 = new Employee("Alice Johnson", 8500, LocalDateTime.of(1999, 8, 12, 8, 0));
        employee1.setPosition(softwareEngineer);
        Employee employee2 = new Employee("Bob Smith", 11000, LocalDateTime.of(1985, 5, 20, 8, 0));
        employee2.setPosition(projectManager);

        companyRepository.save(company1);
        company1.addEmployees(List.of(employee1, employee2));
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);


        Company company2 = new Company("052531354UK", "Global Solutions", llc, "456 Enterprise Way, London EC1A 1BB, United Kingdom");

        Employee employee3 = new Employee("Charlie Brown", 5000, LocalDateTime.of(2015, 9, 3, 10, 30));
        employee3.setPosition(marketingSpecialist);
        Employee employee4 = new Employee("Diana Prince", 7000, LocalDateTime.of(2010, 11, 18, 9, 0));
        employee4.setPosition(marketingSpecialist);
        Employee employee5 = new Employee("Dwayne Johnson", 20000, LocalDateTime.of(1972, 5, 2, 0, 0));
        employee5.setPosition(rockAnalyst);

        companyRepository.save(company2);
        company2.addEmployees(List.of(employee3, employee4, employee5));
        employeeRepository.save(employee3);
        employeeRepository.save(employee4);
        employeeRepository.save(employee5);


        PositionDetailsByCompany pd1 = new PositionDetailsByCompany();
        pd1.setCompany(company1);
        pd1.setMinSalary(10000);
        pd1.setPosition(softwareEngineer);
        positionDetailsByCompanyRepository.save(pd1);

        PositionDetailsByCompany pd2 = new PositionDetailsByCompany();
        pd2.setCompany(company2);
        pd2.setMinSalary(11000);
        pd2.setPosition(projectManager);
        positionDetailsByCompanyRepository.save(pd2);


        //Time Off Requests
        TimeOffRequest request1 = new TimeOffRequest(employee3, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(6));
        TimeOffRequest request2 = new TimeOffRequest(employee4, LocalDateTime.now().plusDays(10), LocalDateTime.now().plusDays(30));
        TimeOffRequest request3 = new TimeOffRequest(employee4, LocalDateTime.now().plusDays(10), LocalDateTime.now().plusDays(15));

        request1.judge(employee5, RequestStatus.ACCEPTED);
        request2.judge(employee5, RequestStatus.REJECTED);

        timeOffRequestRepository.save(request1);
        timeOffRequestRepository.save(request2);
        timeOffRequestRepository.save(request3);
    }

}
