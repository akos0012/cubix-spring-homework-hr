package hu.cubix.hr.akos0012.controller;

import hu.cubix.hr.akos0012.dto.CompanyDTO;
import hu.cubix.hr.akos0012.dto.EmployeeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final Map<Long, CompanyDTO> companies = new HashMap<>();

    {
        companies.put(1L, new CompanyDTO(1, "HU-12345678", "OTP BANK", "Királyhágó utca 1"));
        companies.put(2L, new CompanyDTO(2, "04542623", "Suit Solutions Kft.", "Budaörsi út 64, 1118"));
        companies.get(1L).addEmployee(new EmployeeDTO(2, "Barna", "Tester", 10000, LocalDateTime.of(2010, 5, 4, 8, 0)));
        companies.get(2L).addEmployee(new EmployeeDTO(1, "Akos", "Developer", 12000, LocalDateTime.of(2020, 2, 14, 8, 0)));
    }

    //Getting all companies
    @GetMapping
    public List<CompanyDTO> getAllCompanies(@RequestParam Optional<Boolean> full) {
        boolean includeEmployees = full.orElse(false);

        if (includeEmployees) return new ArrayList<>(companies.values());

        return companies.values().stream().map(CompanyDTO::toBasicDTO).toList();

        /*return companies.values().stream()
                .map(c -> new CompanyDTO(c.getId(), c.getRegistrationNumber(), c.getName(), c.getAddress()))
                .toList();*/
    }

    //Getting one company by id
    @GetMapping("/{companyID}")
    public ResponseEntity<CompanyDTO> getCompanyByID(@PathVariable long companyID, @RequestParam Optional<Boolean> full) {
        CompanyDTO company = companies.get(companyID);
        if (company == null) return ResponseEntity.notFound().build();

        boolean includeEmployee = full.orElse(false);
        if (includeEmployee) return ResponseEntity.ok(company);

        //CompanyDTO copyCompany = new CompanyDTO(company.getId(), company.getRegistrationNumber(), company.getName(), company.getAddress());
        return ResponseEntity.ok(company.toBasicDTO());
    }

    //Creating a new company
    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO company) {
        if (companies.containsKey(company.getId())) return ResponseEntity.badRequest().build();
        companies.put(company.getId(), company);
        return ResponseEntity.ok(company);
    }

    //Updating an existing company
    @PutMapping("/{companyID}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable long companyID, @RequestBody CompanyDTO company) {
        if (!companies.containsKey(companyID)) return ResponseEntity.notFound().build();
        if (companyID != company.getId()) return ResponseEntity.badRequest().build();
        companies.put(companyID, company);
        return ResponseEntity.ok(company);
    }

    //Deleting a company
    @DeleteMapping("/{companyID}")
    public void deleteCompany(@PathVariable long companyID) {
        companies.remove(companyID);
    }

    //Adding a new employee to a given company
    @PostMapping("/{companyID}/addEmployee")
    public ResponseEntity<EmployeeDTO> addEmployeeToCompany(@PathVariable long companyID, @RequestBody EmployeeDTO employee) {
        if (!companies.containsKey(companyID)) return ResponseEntity.notFound().build();

        CompanyDTO company = companies.get(companyID);
        if (company.isEmployeeExist(employee.getId())) return ResponseEntity.badRequest().build();

        company.addEmployee(employee);
        return ResponseEntity.ok(employee);
    }

    //Deleting an employee of given id from a company of given id
    @DeleteMapping("/{companyID}/{employeeID}")
    public void deleteEmployeeFromCompany(@PathVariable long companyID, @PathVariable long employeeID) {
        if (!companies.containsKey(companyID))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found");
        companies.get(companyID).removeEmployee(employeeID);
    }

    //Replacing the whole employee list of a company with another list of employees
    @PutMapping("/{companyID}/employees")
    public ResponseEntity<List<EmployeeDTO>> replaceEmployees(@PathVariable long companyID, @RequestBody List<EmployeeDTO> employeeList) {
        if (!companies.containsKey(companyID)) return ResponseEntity.notFound().build();

        companies.get(companyID).replaceEmployeeList(employeeList);
        return ResponseEntity.ok(employeeList);
    }

}
