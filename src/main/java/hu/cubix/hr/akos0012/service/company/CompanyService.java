package hu.cubix.hr.akos0012.service.company;

import hu.cubix.hr.akos0012.dto.CompanyDTO;
import hu.cubix.hr.akos0012.dto.JobTitleSalaryDTO;
import hu.cubix.hr.akos0012.model.Company;
import hu.cubix.hr.akos0012.model.Employee;
import hu.cubix.hr.akos0012.repository.CompanyRepository;
import hu.cubix.hr.akos0012.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EmployeeService employeeService;

    public List<Company> findAll() {
        //return new ArrayList<>(companies.values());
        return companyRepository.findAll();
    }

    public Optional<Company> findById(long id) {
        //return companies.get(id);
        return companyRepository.findById(id);
    }

    public Company save(Company company) {
        //companies.put(company.getId(), company);
        return companyRepository.save(company);
    }

    public Company update(Company company) {
        if (!companyRepository.existsById(company.getId())) return null;
        return save(company);
    }

    public void delete(long id) {
        companyRepository.findById(id).ifPresent(this::deleteAllEmployeeFromCompany);
        companyRepository.deleteById(id);
    }

    public Company addEmployee(long companyID, Employee employee) {
        Company company = companyRepository.findById(companyID).orElse(null);
        if (company == null) return null;
        company.addEmployee(employee);
        employeeService.save(employee);
        return company;
    }

    public Company deleteEmployee(long companyID, long employeeID) {
        Company company = companyRepository.findById(companyID).orElse(null);
        if (company == null) return null;
        Employee employee = employeeService.findById(employeeID);
        if (employee == null) return company;
        employee.setCompany(null);
        company.removeEmployee(employeeID);
        employeeService.save(employee);
        return company;
    }

    public Company replaceEmployees(long companyID, List<Employee> employees) {
        Company company = companyRepository.findById(companyID).orElse(null);
        if (company == null) return null;
        deleteAllEmployeeFromCompany(company);
        company.replaceEmployeeList(employees);
        employees.forEach(e -> employeeService.save(e));
        return company;
    }

    public void deleteAllEmployeeFromCompany(Company company) {
        List<Long> employeeIDs = company.getEmployees().stream().map(Employee::getId).toList();
        for (long id : employeeIDs) {
            Employee employee = employeeService.findById(id);
            if (employee != null) {
                employee.setCompany(null);
                company.removeEmployee(id);
                employeeService.save(employee);
            }
        }
    }

    public List<Company> findDistinctByEmployeesSalaryGreaterThan(int salary) {
        return companyRepository.findDistinctByEmployeesSalaryGreaterThan(salary);
    }

    public List<Company> findCompaniesWithMoreThanEmployees(int limit) {
        return companyRepository.findCompaniesWithMoreThanEmployees(limit);
    }

    public List<JobTitleSalaryDTO> findAverageSalaryByJobTitleAndCompanyId(long id) {
        List<Object[]> result = companyRepository.findAverageSalaryByJobTitleAndCompanyId(id);

        return result.stream().map(r -> new JobTitleSalaryDTO((String) r[0], (Double) r[1])).toList();
    }

    public Page<Company> findCompaniesWithPaging(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return companyRepository.findAll(pageable);
    }

    public List<Company> findFilteredCompanies(boolean full, Integer salary, Integer limit) {
        List<Company> companies = full
                ? companyRepository.findAllWithEmployees()
                : companyRepository.findAll();

        if (salary != null) {
            List<Company> salaryFiltered = findDistinctByEmployeesSalaryGreaterThan(salary);
            companies = intersection(companies, salaryFiltered);
        }

        if (limit != null) {
            List<Company> numberOfEmployeesFiltered = findCompaniesWithMoreThanEmployees(limit);
            companies = intersection(companies, numberOfEmployeesFiltered);
        }

        return companies;
    }

    private List<Company> intersection(List<Company> list1, List<Company> list2) {
        return list1.stream()
                .filter(list2::contains)
                .toList();
    }

    public Optional<Company> findCompanyById(long id, boolean full) {
        if (full) {
            return companyRepository.findByIdWithEmployees(id);
        } else {
            return findById(id);
        }
    }


}
