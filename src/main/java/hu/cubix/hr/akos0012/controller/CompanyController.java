package hu.cubix.hr.akos0012.controller;

import com.fasterxml.jackson.annotation.JsonView;
import hu.cubix.hr.akos0012.dto.Views;
import hu.cubix.hr.akos0012.dto.CompanyDTO;
import hu.cubix.hr.akos0012.dto.EmployeeDTO;
import hu.cubix.hr.akos0012.mapper.CompanyMapper;
import hu.cubix.hr.akos0012.mapper.EmployeeMapper;
import hu.cubix.hr.akos0012.model.Company;
import hu.cubix.hr.akos0012.model.Employee;
import hu.cubix.hr.akos0012.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.View;
import java.time.LocalDateTime;
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

    //Getting all companies

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

    @GetMapping(params = "full=true")
    public List<CompanyDTO> getAllCompanies() {
        return companyMapper.companiesToDtos(companyService.findAll());
    }

    @GetMapping
    @JsonView(Views.BaseData.class)
    public List<CompanyDTO> getAllCompaniesWithoutEmployees() {
        return companyMapper.companiesToDtos(companyService.findAll());
    }

    //Getting one company by id
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

    @GetMapping(value = "/{companyID}", params = "full=true")
    public ResponseEntity<CompanyDTO> getCompanyByID(@PathVariable long companyID) {
        Company company = companyService.findById(companyID);
        if (company == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(companyMapper.companyToDto(company));
    }

    @GetMapping("/{companyID}")
    @JsonView(Views.BaseData.class)
    public ResponseEntity<CompanyDTO> getCompanyByIdWithoutEmployees(@PathVariable long companyID) {
        Company company = companyService.findById(companyID);
        if (company == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(companyMapper.companyToDto(company));
    }

    //Creating a new company
    @PostMapping
    public CompanyDTO createCompany(@RequestBody @Valid CompanyDTO companyDTO) {

        Company company = companyMapper.dtoToCompany(companyDTO);
        Company savedCompany = companyService.create(company);

        if (savedCompany == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return companyMapper.companyToDto(savedCompany);
    }

    //Updating an existing company
    @PutMapping("/{companyID}")
    public CompanyDTO updateCompany(@PathVariable long companyID, @RequestBody @Valid CompanyDTO companyDTO) {

        if (companyDTO.id() != companyID)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        //If employees are not provided, the employees list should remain unchanged.
        if (companyDTO.employees() == null) {
            List<Employee> companyEmployees = companyService.findById(companyID).getEmployees();
            companyDTO = new CompanyDTO(
                    companyDTO.id(),
                    companyDTO.registrationNumber(),
                    companyDTO.name(),
                    companyDTO.address(),
                    new HashSet<>(employeeMapper.employeesToDtos(companyEmployees))
            );
        }

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
    public EmployeeDTO addEmployeeToCompany(@PathVariable long companyID, @RequestBody @Valid EmployeeDTO employeeDTO) {
        Company company = companyService.findById(companyID);
        Employee employee = employeeMapper.dtoToEmployee(employeeDTO);

        if (company == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        if (company.isEmployeeExist(employee.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        company.addEmployee(employee);
        return employeeMapper.employeeToDto(employee);
    }

    //Deleting an employee of given id from a company of given id
    @DeleteMapping("/{companyID}/{employeeID}")
    public void deleteEmployeeFromCompany(@PathVariable long companyID, @PathVariable long employeeID) {
        Company company = companyService.findById(companyID);

        if (company == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        company.removeEmployee(employeeID);
    }

    //Replacing the whole employee list of a company with another list of employees
    @PutMapping("/{companyID}/employees")
    public List<EmployeeDTO> replaceEmployees(@PathVariable long companyID, @RequestBody @Valid List<@Valid EmployeeDTO> employeeDTOList) {
        Company company = companyService.findById(companyID);

        if (company == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        List<Employee> employeeList = employeeMapper.dtosToEmployees(employeeDTOList);

        company.replaceEmployeeList(employeeList);

        return employeeMapper.employeesToDtos(employeeList);
    }

}
