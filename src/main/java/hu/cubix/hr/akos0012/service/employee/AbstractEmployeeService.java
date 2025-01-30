package hu.cubix.hr.akos0012.service.employee;

import hu.cubix.hr.akos0012.model.Employee;
import hu.cubix.hr.akos0012.dto.EmployeeFilterDTO;
import hu.cubix.hr.akos0012.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import static hu.cubix.hr.akos0012.service.employee.specification.EmployeeSpecifications.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public abstract class AbstractEmployeeService implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAll() {
        //return new ArrayList<>(employees.values());
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(long id) {
        //return employees.get(id);
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Employee> findEmployeesWithPaging(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Employee save(Employee employee) {
        //employees.put(employee.getId(), employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee create(Employee employee) {
        if (employeeRepository.existsById(employee.getId())) return null;
        return save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        if (!employeeRepository.existsById(employee.getId())) return null;
        return save(employee);
    }

    @Override
    public void delete(long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public int updateSalaryForPosition(long companyID, String positionName, int minSalary) {
        return employeeRepository.updateSalaryForPosition(companyID, positionName, minSalary);
    }

    @Override
    public List<Employee> findEmployeesByExample(EmployeeFilterDTO employeeFilterDTO) {

        long employeeID = employeeFilterDTO.employeeID();
        String employeePrefix = employeeFilterDTO.employeePrefix();
        String companyPrefix = employeeFilterDTO.companyPrefix();
        String position = employeeFilterDTO.position();
        int salary = employeeFilterDTO.salary();
        LocalDate entryDate = employeeFilterDTO.entryDate();

        Specification<Employee> specs = Specification.where(null);

        if (employeeID > 0) specs = specs.and(hasID(employeeID));

        if (StringUtils.hasText(employeePrefix)) specs = specs.and(employeeNameStartWith(employeePrefix));

        if (StringUtils.hasText(companyPrefix)) specs = specs.and(companyNameStartWith(companyPrefix));

        if (StringUtils.hasText(position)) specs = specs.and(hasEmployeePositionName(position));

        if (salary > 0) specs = specs.and(hasSalaryAround(salary));

        if (entryDate != null) specs = specs.and(hasEntryDate(entryDate));

        return employeeRepository.findAll(specs);
    }

    public List<Employee> findFilteredEmployees(Integer salary, String job, String name, LocalDateTime startDate, LocalDateTime endDate) {
        return employeeRepository.findAll(salary, job, name, startDate, endDate);
    }

}
