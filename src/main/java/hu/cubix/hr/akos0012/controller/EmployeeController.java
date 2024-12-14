package hu.cubix.hr.akos0012.controller;

import hu.cubix.hr.akos0012.dto.EmployeeDTO;
import hu.cubix.hr.akos0012.mapper.EmployeeMapper;
import hu.cubix.hr.akos0012.model.Employee;
import hu.cubix.hr.akos0012.service.EmployeeService;
import hu.cubix.hr.akos0012.service.payRaiseService.SalaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    SalaryService salaryService;


    //get the percentage of pay raise for that employee
    @PostMapping("/payRaisePercentage")
    public ResponseEntity<Integer> getPayRaisePercentage(@RequestBody Employee employee) {
        if (employee == null) return ResponseEntity.badRequest().build();
        int payRaisePercentage = salaryService.getPercentageOfPayRaise(employee);
        return ResponseEntity.ok(payRaisePercentage);
    }

    //Listing all employees
    //Listing employees with salary higher than a given value passed as query parameter
    //example url: http://localhost:8080/api/employees?salary=7000&jobTitle=Magyar Posta&startDate=2015-02-14T08:00:00&endDate=2020-02-14T08:00:00
    @GetMapping
    public List<EmployeeDTO> findAll(@RequestParam(required = false) Integer salary,
                                     @RequestParam(required = false) String jobTitle,
                                     @RequestParam(required = false) String name,
                                     @RequestParam(required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                     @RequestParam(required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        List<Employee> employeeList = employeeService.findAll();
        List<Employee> filteredEmployees = findFilteredEmployees(employeeList, salary, jobTitle, name, startDate, endDate);

        return employeeMapper.employeesToDtos(filteredEmployees);
    }

    private List<Employee> findFilteredEmployees(List<Employee> employees, Integer salary, String jobTitle, String name, LocalDateTime startDate, LocalDateTime endDate) {
        if (salary != null) {
            List<Employee> salaryFiltered = employeeService.findBySalaryIsGreaterThan(salary);
            employees = intersection(employees, salaryFiltered);
        }
        if (jobTitle != null) {
            List<Employee> jobTitleFiltered = employeeService.findByJobTitle(jobTitle);
            employees = intersection(employees, jobTitleFiltered);
        }
        if (name != null) {
            List<Employee> nameFiltered = employeeService.findByNameStartingWithIgnoreCase(name);
            employees = intersection(employees, nameFiltered);
        }
        if (startDate != null && endDate != null) {
            if (!startDate.isBefore(endDate)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            List<Employee> dateFiltered = employeeService.findByDateOfStartWorkBetween(startDate, endDate);
            employees = intersection(employees, dateFiltered);
        }

        return employees;
    }

    private List<Employee> intersection(List<Employee> list1, List<Employee> list2) {
        return list1.stream()
                .filter(list2::contains)
                .toList();
    }

    //Return an employee with a given id
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> findById(@PathVariable long id) {
        Employee employee = employeeService.findById(id);
        if (employee != null) return ResponseEntity.ok(employeeMapper.employeeToDto(employee));
        return ResponseEntity.notFound().build();
    }

    //Find the employees with a given a job
    @GetMapping("/{jobTitle}")
    public List<EmployeeDTO> findEmployeesByJobTitle(@PathVariable String jobTitle) {
        List<Employee> employees = employeeService.findByJobTitle(jobTitle);
        return employeeMapper.employeesToDtos(employees);
    }

    //Adding a new employee
    @PostMapping
    public EmployeeDTO create(@RequestBody @Valid EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.dtoToEmployee(employeeDTO);
        Employee savedEmployee = employeeService.create(employee);

        if (savedEmployee == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return employeeMapper.employeeToDto(savedEmployee);
    }

    //Modifying an existing employee
    @PutMapping("/{id}")
    public EmployeeDTO update(@PathVariable long id, @RequestBody @Valid EmployeeDTO employeeDTO) {

        //if (employeeDTO.id() != id) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        employeeDTO = new EmployeeDTO(id, employeeDTO.name(), employeeDTO.jobTitle(), employeeDTO.salary(), employeeDTO.dateOfStartWork());

        Employee employee = employeeMapper.dtoToEmployee(employeeDTO);
        Employee updatedEmployee = employeeService.update(employee);


        if (updatedEmployee == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return employeeMapper.employeeToDto(updatedEmployee);
    }

    //Deleting an existing employee
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        employeeService.delete(id);
    }

    //Listing employees with salary higher than a given value passed as query parameter
    /*
    @GetMapping("/filterBySalary")
    public List<EmployeeDTO> getEmployeesWithHighSalary(@RequestParam int salary) {
        List<EmployeeDTO> employeeList = new ArrayList<>(employees.values());
        return employeeList.stream().filter(e -> e.getSalary() > salary).toList();
    }
    */
}
