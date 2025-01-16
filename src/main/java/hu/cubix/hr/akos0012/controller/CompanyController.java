package hu.cubix.hr.akos0012.controller;

import hu.cubix.hr.akos0012.dto.JobTitleSalaryDTO;
import hu.cubix.hr.akos0012.dto.CompanyDTO;
import hu.cubix.hr.akos0012.dto.EmployeeDTO;
import hu.cubix.hr.akos0012.mapper.CompanyMapper;
import hu.cubix.hr.akos0012.mapper.EmployeeMapper;
import hu.cubix.hr.akos0012.model.Company;
import hu.cubix.hr.akos0012.model.Employee;
import hu.cubix.hr.akos0012.repository.CompanyRepository;
import hu.cubix.hr.akos0012.service.company.CompanyService;
import hu.cubix.hr.akos0012.service.employee.SalaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    private SalaryService salaryService;

    //Getting all companies
    //example url: http://localhost:8080/api/companies?full=true&salary=10000&limit=2

    @GetMapping
    public List<CompanyDTO> getAllCompanies(@RequestParam Optional<Boolean> full,
                                            @RequestParam(required = false) Integer salary,
                                            @RequestParam(required = false) Integer limit) {

        List<Company> companies = companyService.findFilteredCompanies(full.orElse(false), salary, limit);

        if (full.orElse(false)) return companyMapper.companiesToDtos(companies);
        else return companyMapper.companiesToSummaryDtos(companies);
    }

    @GetMapping("/paged")
    public Page<CompanyDTO> getPagedCompanies(@RequestParam int page, @RequestParam int size) {
        Page<Company> pagedCompanies = companyService.findCompaniesWithPaging(page, size);
        System.out.println("Page number: " + pagedCompanies.getNumber());
        System.out.println("Total pages: " + pagedCompanies.getTotalPages());
        System.out.println("Total elements: " + pagedCompanies.getTotalElements());
        System.out.println("Number of elements: " + pagedCompanies.getNumberOfElements());
        return companyMapper.pagedCompanyToDto(pagedCompanies);
    }


//    @GetMapping
//    public List<CompanyDTO> getAllCompanies(@RequestParam Optional<Boolean> full) {
//        boolean includeEmployees = full.orElse(false);
//
//        if (includeEmployees) return new ArrayList<>(companies.values());
//
//        return companies.values().stream().map(CompanyDTO::toBasicDTO).toList();
//
//        /*return companies.values().stream()
//                .map(c -> new CompanyDTO(c.getId(), c.getRegistrationNumber(), c.getName(), c.getAddress()))
//                .toList();*/
//    }

//    @GetMapping(params = "full=true")
//    public List<CompanyDTO> getAllCompanies() {
//        return companyMapper.companiesToDtos(companyService.findAll());
//    }
//
//    @GetMapping
//    @JsonView(Views.BaseData.class)
//    public List<CompanyDTO> getAllCompaniesWithoutEmployees() {
//        return companyMapper.companiesToDtos(companyService.findAll());
//    }

    //Getting one company by id
    @GetMapping("/{id}")
    public CompanyDTO findById(@PathVariable long id, @RequestParam Optional<Boolean> full) {
        //Company company = companyService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Optional<Company> optionalCompany = companyService.findCompanyById(id, full.orElse(false));
        if (optionalCompany.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Company company = optionalCompany.get();
        if (full.orElse(false)) return companyMapper.companyToDto(company);
        else return companyMapper.companyToSummaryDto(company);
    }

//    @GetMapping("/{companyID}")
//    public ResponseEntity<CompanyDTO> getCompanyByID(@PathVariable long companyID, @RequestParam Optional<Boolean> full) {
//        Company company = companyService.findById(companyID);
//        if (company == null) return ResponseEntity.notFound().build();
//
//        boolean includeEmployee = full.orElse(false);
//        if (includeEmployee) return ResponseEntity.ok(company);
//
//        //CompanyDTO copyCompany = new CompanyDTO(company.getId(), company.getRegistrationNumber(), company.getName(), company.getAddress());
//        return ResponseEntity.ok(company.toBasicDTO());
//    }

//    @GetMapping(value = "/{companyID}", params = "full=true")
//    public ResponseEntity<CompanyDTO> getCompanyByID(@PathVariable long companyID) {
//        Company company = companyService.findById(companyID);
//        if (company == null) return ResponseEntity.notFound().build();
//        return ResponseEntity.ok(companyMapper.companyToDto(company));
//    }
//
//    @GetMapping("/{companyID}")
//    @JsonView(Views.BaseData.class)
//    public ResponseEntity<CompanyDTO> getCompanyByIdWithoutEmployees(@PathVariable long companyID) {
//        Company company = companyService.findById(companyID);
//        if (company == null) return ResponseEntity.notFound().build();
//        return ResponseEntity.ok(companyMapper.companyToDto(company));
//    }

    //get average salary by job title and id
    @GetMapping("/average/{id}")
    public List<JobTitleSalaryDTO> getAverageSalaryByIdAndJobTitle(@PathVariable long id) {
        if (companyService.findById(id).isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return companyService.findAverageSalaryByJobTitleAndCompanyId(id);
    }

    //Creating a new company
    @PostMapping
    public CompanyDTO createCompany(@RequestBody @Valid CompanyDTO companyDTO) {

        Company company = companyMapper.dtoToCompany(companyDTO);
        Company createdCompany = companyService.save(company);
        return companyMapper.companyToDto(createdCompany);
    }

    //Updating an existing company
    @PutMapping("/{companyID}")
    public CompanyDTO updateCompany(@PathVariable long companyID, @RequestBody @Valid CompanyDTO companyDTO) {

        if (companyDTO.id() != companyID)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        //If employees are not provided, the employees list should remain unchanged.
//        if (companyDTO.employees() == null && companyService.findById(companyID).isPresent()) {
//            List<Employee> companyEmployees = companyService.findById(companyID).get().getEmployees();
//            companyDTO = new CompanyDTO(
//                    companyDTO.id(),
//                    companyDTO.registrationNumber(),
//                    companyDTO.name(),
//                    companyDTO.companyForm(),
//                    companyDTO.address(),
//                    new HashSet<>(employeeMapper.employeesToDtos(companyEmployees))
//            );
//        }

        Company company = companyMapper.dtoToCompany(companyDTO);
        Company updatedCompany = companyService.update(company);

        if (updatedCompany == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return companyMapper.companyToDto(company);
    }

    //Deleting a company
    @DeleteMapping("/{companyID}")
    public void deleteCompany(@PathVariable long companyID) {
        companyService.delete(companyID);
    }

    //Adding a new employee to a given company
    @PostMapping("/{companyID}/addEmployee")
    public CompanyDTO addEmployeeToCompany(@PathVariable long companyID, @RequestBody @Valid EmployeeDTO employeeDTO) {
        Company company = companyService.addEmployee(companyID, employeeMapper.dtoToEmployee(employeeDTO));

        if (company == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);


        return companyMapper.companyToDto(company);
    }

    //Deleting an employee of given id from a company of given id
    @DeleteMapping("/{companyID}/{employeeID}")
    public void deleteEmployeeFromCompany(@PathVariable long companyID, @PathVariable long employeeID) {
        Company company = companyService.deleteEmployee(companyID, employeeID);

        if (company == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    }

    //Replacing the whole employee list of a company with another list of employees
    @PutMapping("/{companyID}/employees")
    public CompanyDTO replaceEmployees(@PathVariable long companyID, @RequestBody @Valid List<@Valid EmployeeDTO> employeeDTOList) {
        Company company = companyService.replaceEmployees(companyID, employeeMapper.dtosToEmployees(employeeDTOList));

        if (company == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return companyMapper.companyToDto(company);
    }

    @Transactional
    @PutMapping("/updateMinSalaryByPosition")
    public void updateMinSalaryByPosition(@RequestParam long companyID, @RequestParam String positionName, @RequestParam int minSalary) {
        salaryService.raiseMinSalary(companyID, positionName, minSalary);
    }

}
